package algorithm1;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * 抽象出来描述从队列中获取消息的服务器
 */
public class Server implements  Runnable {
    //用来存放队列
    private ArrayList<MessageQueue> queue ;
    private int P = 50000; //单服务器T时间内的平均处理消息个数
    private int type = 0; // 表示使用基于紧急指数的--》0，基于时间的BPR--》1, 基于队列长度的BPR--》2， type-》3 PFWRR


    Server() {
        queue = new ArrayList<MessageQueue>();
    }

    Server(ArrayList<MessageQueue> queue) {
        this.queue = queue;
    }

    Server(ArrayList<MessageQueue> queue, int P) {
        this.queue = queue;
        this.P = P;
    }

    //增加一个队列
    public void addMessageQueue(MessageQueue queue) {
        this.queue.add(queue);
    }

    public int getP() {
        return P;
    }

    public void setP(int p) {
        P = p;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //删除一个队列
    public MessageQueue removeMessageQueue(int index) {
        return queue.remove(index);
    }

    public void run() {
        int[] flag = new int[queue.size()]; // 记录每次循环中各个队列是否还有消息
        int numOfQueueEndInOneCycle = 0;
        int count = 0;
        int[] speed = new int[queue.size()]; // 统计处理的消息个数
        double[] totalTime = new double[queue.size()]; // 统计周期内的平均时延
        File file = new File("time_delay.txt"); // src\main\resources\q1.txt
        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getName(), true);
        } catch(Exception e) {
        }

        while(true) {
            long sTime = System.currentTimeMillis();
            while(numOfQueueEndInOneCycle != queue.size()) { // 一个循环周期内
                for(int i = 0; i < queue.size(); i++) {
                    if(flag[i] != 0)
                        continue;
                    MessageQueue tmpQueue = queue.get(i);
                    if(tmpQueue.getLength() == 0) {
                        flag[i] = 1;
                        numOfQueueEndInOneCycle++;
                        continue;
                     }
                    String message = tmpQueue.getAndDecrease();
                    long cuTime = System.currentTimeMillis();
                    totalTime[i] += cuTime - Long.parseLong(message);
                    speed[i]++;
                    count++;
                    if(tmpQueue.getC() <= 0  || count >= P) {
                        flag[i] = 1;
                        numOfQueueEndInOneCycle++;
                    }
                }
            }

            System.out.println("count:" + count);
            for(int i = 0; i < queue.size(); i++) {
                MessageQueue tmpQueue = queue.get(i);
                System.out.println("C" + i + ":" + tmpQueue.getC());
            }
            try {
                long endTime = System.currentTimeMillis();
                if(endTime - sTime < 1000)
                    Thread.sleep(1000 - endTime + sTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count = 0;

            long eTime = System.currentTimeMillis();
//            System.out.println("拿出循环周期：" + (eTime - sTime));
            for(int i = 0; i < speed.length; i++) { // 计算队列的时延
                System.out.println("speed[" + i + "]:" + speed[i]);
                double tmpDelay = totalTime[i] / speed[i];
                try {
                    fw.write(tmpDelay + "____Q" + (i + 1) + "\r\n");
                    fw.flush();
                } catch(Exception e) {

                }
                speed[i] = 0;
                totalTime[i] = 0;
            }
            System.out.println("-----------------------------------------");

            //执行到此处，需要根据公式重新计算各个参数的值
            numOfQueueEndInOneCycle = 0;
            for(int i = 0; i < flag.length; i++)
                flag[i] = 0;
            //计算各个队列参数的值

            long endTime = System.currentTimeMillis();
            for(int i = 0; i < queue.size(); i++) {
                MessageQueue tmpQueue = queue.get(i);
                if((tmpQueue.getQij1() == 0) && (tmpQueue.getQij2() == 0)) { //计算队列长度
                    tmpQueue.setQij1(tmpQueue.getLength());
                } else {
                    tmpQueue.setQij2(tmpQueue.getQij1());
                    tmpQueue.setQij1(tmpQueue.getLength());
                }

                // 计算消息的到达速率
//                System.out.println(tmpQueue.getName());
//                System.out.println("count:" + tmpQueue.getCount());
//                System.out.println("length:" + tmpQueue.getLength());
//                System.out.println("time:" + endTime + "            " + tmpQueue.getStartTime());
//                System.out.println("time1:" + (endTime - tmpQueue.getStartTime()));
//                System.out.println("C:" + tmpQueue.getC());
                double R = (double)tmpQueue.getCount() / (endTime - tmpQueue.getStartTime());
                tmpQueue.setStartTime(endTime);
                tmpQueue.setRij2(tmpQueue.getRij1());
                tmpQueue.setRij1(R);
                tmpQueue.setCount(0);
            }


            int flag1 = 0;
            for(int i = 0; i < queue.size(); i++) {
                MessageQueue tmpQueue = queue.get(i);
                //计算新的权值
                if ((tmpQueue.getRij1() == tmpQueue.getRij2()) &&
                        (tmpQueue.getQij1() == tmpQueue.getQij2())) // 若到达速率和队列长度都不变则不需要再次计算新的权值
                    continue;
                else {
                    for (int j = 0; j < queue.size() - 1; j++) { // 查看各个队列的权值比例是否相同
                        if (queue.get(j).getDelte() != queue.get(j + 1).getDelte()) {
                            flag1 = 1;
                            break;
                        }
                    }
                }
            }
            if (flag1 == 0) { //权值比例都相同的情况
                sameWeightParameter(queue);
            } else if (queue.size() == 2) { //系统为两个队列时
                twoQueueParameter(queue);
            } else if (queue.size() == 3) { //系统为三队列时
                threeQueueParameter(queue);
            } else if(queue.size() == 4) { //系统为四队列时--》采用新的算法
                if(this.type == 0) // 基于紧急指数
                    fourQueueParameter(queue);
                else if (this.type == 1) // 基于时间的BPR
                    BPR_Time(queue);
                else if(this.type == 2) // 基于队列长度的BPR
                    BPR_Length(queue);
                else if(this.type == 3) // PFWRR算法
                    PFWRR(queue);
            } else if(queue.size() == 5) { // 系统为五队列时
                fiveQueueParameter(queue);
            }

            // 设置新的计数器
            for(int j = 0; j < queue.size(); j++) {
                MessageQueue tmpQ = queue.get(j);
                int C = tmpQ.getC() + tmpQ.getW();
                tmpQ.setC(C);
            }
            System.out.println("=======================================");
        }
    }

    // 计算并更新系统只有两个队列时的权值W
    public void twoQueueParameter(ArrayList<MessageQueue> queue) {
        MessageQueue Q1 = queue.get(0);
        MessageQueue Q2 = queue.get(1);
//        System.out.println("q1长度：" + Q1.getQij1());
//        System.out.println("q1德尔塔:" + Q1.getDelte());
//        System.out.println("q1的到达率:" + Q1.getRij1());
//        System.out.println("q1的时间周期:" + Q1.getT());
//        System.out.println("q2长度：" + Q2.getQij1());
//        System.out.println("q2德尔塔:" + Q2.getDelte());
//        System.out.println("q2的到达率:" + Q2.getRij1());
//        System.out.println("q2的时间周期:" + Q2.getT());

//        double A12 = Q1.getQij1() * Q2.getDelte() + Q1.getRij1() * Q2.getDelte() * Q1.getT()
////                - Q2.getQij1() * Q1.getDelte() - Q2.getRij1() * Q1.getDelte() * Q1.getT();
////        int W1 = (int)((Q1.getDelte() * this.P + A12) / (Q1.getDelte() + Q2.getDelte()));
////        int W2 = (int)((Q2.getDelte() * this.P - A12) / (Q1.getDelte() + Q2.getDelte()));
////        Q1.setW(W1);
////        Q2.setW(W2);

        //新方法
        double A = (Q1.getQij1() + Q1.getRij1() * Q1.getT()) * Q2.getDelte();
        double B = (Q2.getQij1() + Q2.getRij1() * Q2.getT()) * Q1.getDelte();
        double C = Q1.getDelte() - Q2.getDelte();
        int W1;
        int W2;
        W1 = (int)(((C * this.P - A - B) + Math.sqrt((Math.pow((A + B -C * this.P), 2) + 4 * A * this.P * C))) / (2.0 * C));
        W2 = (int)(((C * this.P + A + B) - Math.sqrt((Math.pow((A + B -C * this.P), 2) + 4 * A * this.P * C))) / (2.0 * C));
        if(W1 < 0 || W2 < 0) {
            W1 = (int)(((C * this.P - A - B) - Math.sqrt((Math.pow((A + B -C * this.P), 2) + 4 * A * this.P * C))) / (2.0 * C));
            W2 = (int)(((C * this.P + A + B) + Math.sqrt((Math.pow((A + B -C * this.P), 2) + 4 * A * this.P * C))) / (2.0 * C));
        }
        Q1.setW(W1);
        Q2.setW(W2);

//        System.out.println("A12:" + A12);
        System.out.println("W1:" + W1);
        System.out.println("W2:" + W2);
//        System.out.println("q1长度                    到达速率                    拥塞指数");
//        System.out.println(Q1.getQij1() + "                    " + Q1.getRij1() + "                    " + (Q1.getQij1() / Q1.getRij1()));
//        System.out.println("q2长度                    到达速率                    拥塞指数");
//        System.out.println(Q2.getQij1() + "                    " + Q2.getRij1() + "                    " + (Q2.getQij1() / Q2.getRij1()));
//        System.out.println();
    }

    // 计算并更新系统有三个队列时的权值W
    public void threeQueueParameter(ArrayList<MessageQueue> queue) {
        MessageQueue Q1 = queue.get(0);
        MessageQueue Q2 = queue.get(1);
        MessageQueue Q3 = queue.get(3);
        double A12 = Q1.getQij1() * Q2.getDelte() + Q1.getRij1() * Q2.getDelte() * Q1.getT() / 1000
                - Q2.getQij1() * Q1.getDelte() - Q2.getRij1() * Q1.getDelte() * Q1.getT() / 1000;
        double A23 = Q2.getQij1() * Q3.getDelte() + Q2.getRij1() * Q3.getDelte() * Q2.getT() / 1000
                - Q3.getQij1() * Q2.getDelte() - Q3.getRij1() * Q2.getDelte() * Q2.getT() / 1000;
        int W1 = (int)((this.P * Q1.getDelte() * Q2.getDelte() + Q3.getDelte() * A12 + Q1.getDelte() * A23 + Q2.getDelte() * A12)
                / (Q1.getDelte() * Q2.getDelte() + Q2.getDelte() + Q3.getDelte()));
        int W2 = (int)(Q2.getDelte() / Q1.getDelte() * W1 - A12 / Q1.getDelte());
        int W3 = (int)(Q3.getDelte() / Q1.getDelte() * W1 - Q3.getDelte() / Q1.getDelte() / Q2.getDelte() * A12 - A23 / Q2.getDelte());
        Q1.setW(W1);
        Q2.setW(W2);
        Q3.setW(W3);
    }

    // 计算并更新系统有四个队列时的权值W
    public void fourQueueParameter(ArrayList<MessageQueue> queue) {
        MessageQueue Q1 = queue.get(0);
        MessageQueue Q2 = queue.get(1);
        MessageQueue Q3 = queue.get(2);
        MessageQueue Q4 = queue.get(3);

        double Wnew1 = getNewW(queue, Q1);
        double Wnew2 = getNewW(queue, Q2);
        double Wnew3 = getNewW(queue, Q3);
        double Wnew4 = getNewW(queue, Q4);
//        if(Wnew1 < 0)
//            Wnew1 = 0;
//        if(Wnew2 < 0)
//            Wnew2 = 0;
//        if(Wnew3 < 0)
//            Wnew3 = 0;
//        if(Wnew4 < 0)
//            Wnew4 = 0;

        System.out.println("Wnew1:" + Wnew1);
        System.out.println("Wnew2:" + Wnew2);
        System.out.println("Wnew3:" + Wnew3);
        System.out.println("Wnew4:" + Wnew4);

//        double A12 = Q1.getDelte() * Wnew1 / (Q2.getDelte() * Wnew2);
//        double A23 = Q2.getDelte() * Wnew2 / (Q3.getDelte() * Wnew3);
//        double A34 = Q3.getDelte() * Wnew3 / (Q4.getDelte() * Wnew4);
//
//        System.out.println("A12:" + A12);
//        System.out.println("A23:" + A23);
//        System.out.println("A34:" + A34);
//
//        //利用高斯赛德尔逼近法求解
//
//        double error = 10; //迭代终止的误差
//        double W1 = 0, W2 = 0, W3 = 0,W4 = 0;
//        int times = 0; // 当前迭代次数
//        int totalTimes = 10000; // 最大迭代次数
//
//        System.out.println("111:" + (Q1.getQij1() + Q1.getRij1() * Q1.getT()));
//        System.out.println("1111:" + (A12 * (Q2.getQij1() + Q2.getRij1() * Q2.getT())));
//
//        System.out.println("222:" + (Q3.getQij1() + Q3.getRij1() * Q3.getT()));
//        System.out.println("2222:" + (A34 * (Q4.getQij1() + Q4.getRij1() * Q4.getT())));
//
//        System.out.println("333:" + (Q2.getQij1() + Q2.getRij1() * Q2.getT()));
//        System.out.println("3333:" + (A23 * (Q3.getQij1() + Q3.getRij1() * Q3.getT())));
//
//        while(Math.abs(W1 + W2 + W3 + W4 - this.P) > error && times < totalTimes) {
//            W1 = Q1.getQij1() + Q1.getRij1() * Q1.getT() + A12 * W2 - A12 * (Q2.getQij1() + Q2.getRij1() * Q2.getT());
//            W4 = this.P - W1 - W2 - W3;
//            W3 = Q3.getQij1() + Q3.getRij1() * Q3.getT() + A34 * W4 - A34 * (Q4.getQij1() + Q4.getRij1() * Q4.getT());
//            W2 = Q2.getQij1() + Q2.getRij1() * Q2.getT() + A23 * W3 - A23 * (Q3.getQij1() + Q3.getRij1() * Q3.getT());
//            times++;
//        }

        double W1 = 0, W2 = 0, W3 = 0,W4 = 0;
        double A21 = (Q2.getQij1() + Q2.getRij2() * Q2.getT() - Wnew2) /
                (Q1.getQij1() + Q1.getRij1() * Q1.getT() - Wnew1) * Q1.getDelte() / Q2.getDelte();

        double A31 = (Q3.getQij1() + Q3.getRij2() * Q3.getT() - Wnew3) /
                (Q1.getQij1() + Q1.getRij1() * Q1.getT() - Wnew1) * Q1.getDelte() / Q3.getDelte();

        double A41 = (Q4.getQij1() + Q4.getRij2() * Q4.getT() - Wnew4) /
                (Q1.getQij1() + Q1.getRij1() * Q1.getT() - Wnew1) * Q1.getDelte() / Q4.getDelte();

//        System.out.println("Q1.getQij1():" + Q1.getQij1());
//        System.out.println("Q1.getRij2():" + Q1.getRij2());
//        System.out.println("Wnew1:" + Wnew1);
//
//        System.out.println("A21:" + A21);
//        System.out.println("A31:" + A31);
//        System.out.println("A41:" + A41);

        if(A21 <= 0) {
            A21 = (Q2.getQij1() + Q2.getRij2() * Q2.getT()) /
                    (Q1.getQij1() + Q1.getRij1() * Q1.getT()) * Q1.getDelte() / Q2.getDelte();
        }
        if(A31 <= 0) {
            A31 = (Q3.getQij1() + Q3.getRij2() * Q3.getT()) /
                    (Q1.getQij1() + Q1.getRij1() * Q1.getT()) * Q1.getDelte() / Q3.getDelte();
        }
        if(A41 <= 0) {
            A41 = (Q4.getQij1() + Q4.getRij2() * Q4.getT()) /
                    (Q1.getQij1() + Q1.getRij1() * Q1.getT()) * Q1.getDelte() / Q4.getDelte();
        }

        W1 = this.P / (A21 + A31 + A41 +1);
        W2 = A21 * W1;
        W3 = A31 * W1;
        W4 = A41 * W1;

        System.out.println("W1:" + W1);
        System.out.println("W2:" + W2);
        System.out.println("W3:" + W3);
        System.out.println("W4:" + W4);

        Q1.setW((int)W1);
        Q2.setW((int)W2);
        Q3.setW((int)W3);
        Q4.setW((int)W4);
    }

    // 获取新的W值
    public double getNewW(ArrayList<MessageQueue> queue, MessageQueue mq) {
        double alpha = 0.5;
        double beta = 0.5;

        double firstDen = 0; // 第一项分母
        double secondDen = 0; // 第二项分母
        double thirdDen = 0; // 第三项分母
        double forthDen = 0; // 第四项分母

        for(int i = 0; i < queue.size(); i++) {
            MessageQueue tmp = queue.get(i);
            firstDen += tmp.getDelte() * tmp.getRij1();
            secondDen += tmp.getDelte() * tmp.getRij2();
            thirdDen += tmp.getDelte() * tmp.getQij1();
            forthDen += tmp.getDelte() * tmp.getQij2();
        }

        double first = mq.getDelte() * mq.getRij1() / firstDen;
        double second = mq.getDelte() * mq.getRij2() / secondDen;
        double third = mq.getDelte() * mq.getQij1() / thirdDen;
        double forth = mq.getDelte() * mq.getQij2() / forthDen;

        double exponent = alpha * (first - second) + beta * (third - forth); //计算出来的指数

        return mq.getW() * Math.pow(Math.E, exponent);
    }

    // 使用BPR算法
    public void BPR_Time(ArrayList<MessageQueue> queue) {
        MessageQueue Q1 = queue.get(0);
        MessageQueue Q2 = queue.get(1);
        MessageQueue Q3 = queue.get(2);
        MessageQueue Q4 = queue.get(3);

        long curTime = System.currentTimeMillis();
        double T1 = curTime - Long.parseLong(Q1.getHead());
        double T2 = curTime - Long.parseLong(Q2.getHead());
        double T3 = curTime - Long.parseLong(Q3.getHead());
        double T4 = curTime - Long.parseLong(Q4.getHead());

        double p12 = T1 * Q2.getDelte() / (T2 * Q1.getDelte());
        double p32 = T3 * Q2.getDelte() / (T2 * Q3.getDelte());
        double p42 = T4 * Q2.getDelte() / (T2 * Q4.getDelte());

        double W2 = this.P / (p12 + 1 + p32 + p42);
        double W1 = p12 * W2;
        double W3 = p32 * W2;
        double W4 = p42 * W2;

        System.out.println("W1:" + W1);
        System.out.println("W2:" + W2);
        System.out.println("W3:" + W3);
        System.out.println("W4:" + W4);

        Q1.setW((int)W1);
        Q2.setW((int)W2);
        Q3.setW((int)W3);
        Q4.setW((int)W4);
    }

    // 基于队列长度的BPR
    public void BPR_Length(ArrayList<MessageQueue> queue) {
        MessageQueue Q1 = queue.get(0);
        MessageQueue Q2 = queue.get(1);
        MessageQueue Q3 = queue.get(2);
        MessageQueue Q4 = queue.get(3);

        double p12 = Q2.getDelte() * Q1.getQij1() / (Q1.getDelte() * Q2.getQij1());
        double p32 = Q2.getDelte() * Q3.getQij1() / (Q3.getDelte() * Q2.getQij1());
        double p42 = Q2.getDelte() * Q4.getQij1() / (Q4.getDelte() * Q2.getQij1());

        double W2 = this.P / (p12 + 1 + p32 + p42);
        double W1 = p12 * W2;
        double W3 = p32 * W2;
        double W4 = p42 * W2;

        System.out.println("W1:" + W1);
        System.out.println("W2:" + W2);
        System.out.println("W3:" + W3);
        System.out.println("W4:" + W4);

        Q1.setW((int)W1);
        Q2.setW((int)W2);
        Q3.setW((int)W3);
        Q4.setW((int)W4);
    }

    // PFWRR算法
    public void PFWRR(ArrayList<MessageQueue> queues) {
        MessageQueue Q1 = queues.get(0);
        MessageQueue Q2 = queues.get(1);
        MessageQueue Q3 = queues.get(2);
        MessageQueue Q4 = queues.get(3);
        double k1 = 0, k2 = 0, k3 = 0, k4 = 0;
        k1 = Q1.getPFWRRW() * Q1.getRij1();
        k2 = Q2.getPFWRRW() * Q2.getRij1();
        k3 = Q3.getPFWRRW() * Q3.getRij1();
        k4 = Q4.getPFWRRW() * Q4.getRij1();

        double W1 = 0, W2 = 0, W3 = 0, W4 = 0;

        int count = 0;
        int error = 10;
        while(count <= 1000  && Math.abs(this.P - W1 - W2 - W3 - W4) > error) {
            W2 = k2 / k3 * W3 +Q2.getRij1()*Q2.getT() - k2 / k3 * Q3.getRij1() * Q3.getT();
            W1 = k1 / k2 * W2 +Q1.getRij1()*Q1.getT() - k1 / k2 * Q2.getRij1() * Q2.getT();
            W4 = this.P - W1 - W2 -W3;
            W3 = k3 / k4 * W4 +Q3.getRij1()*Q3.getT() - k3 / k4 * Q4.getRij1() * Q4.getT();
        }

        System.out.println("W1:" + W1);
        System.out.println("W2:" + W2);
        System.out.println("W3:" + W3);
        System.out.println("W4:" + W4);

        Q1.setW((int) W1);
        Q2.setW((int) W2);
        Q3.setW((int) W3);
        Q4.setW((int) W4);
    }

    //计算并更新系统有五个队列时的权值W
    public void fiveQueueParameter(ArrayList<MessageQueue> queue) {
        MessageQueue Q1 = queue.get(0);
        MessageQueue Q2 = queue.get(1);
        MessageQueue Q3 = queue.get(2);
        MessageQueue Q4 = queue.get(3);
        MessageQueue Q5 = queue.get(4);

        double lengthQ1 = Q1.getQij1() + Q2.getQij1();
        double R1 = Q1.getRij1() + Q2.getRij1();
        double Delte1 = Q1.getDelte() + Q2.getDelte();

        double lengthQ2 = Q3.getQij1() + Q4.getQij1() + Q5.getQij1();
        double R2 = Q3.getRij1() + Q4.getRij1() + Q5.getRij1();
        double Delte2 = Q3.getDelte() + Q4.getDelte() + Q5.getDelte();

        double[] tmpW_1;  //第一轮
        tmpW_1 = calculate(lengthQ1, R1, Delte1, lengthQ2, R2, Delte2, Q1.getT(), this.P);

        lengthQ1 = Q1.getQij1();
        R1 = Q1.getRij1();
        Delte1 = Q1.getDelte();

        lengthQ2 = Q2.getQij1();
        R2 = Q2.getRij1();
        Delte2 = Q2.getDelte();

        // 计算出的Q1Q2的权值
        double[] Q1Q2W = calculate(lengthQ1, R1, Delte1, lengthQ2, R2, Delte2, Q1.getT(), tmpW_1[0]);


        Q1.setW((int) Q1Q2W[0]);
        Q2.setW((int) Q1Q2W[1]);

        lengthQ1 = Q3.getQij1() + Q4.getQij1();
        R1 = Q3.getRij1() + Q4.getRij1();
        Delte1 = Q3.getDelte() + Q4.getDelte();

        lengthQ2 = Q5.getQij1();
        R2 = Q5.getRij1();
        Delte2 = Q5.getDelte();

        //计算出Q3Q4的总权值以及Q5的权值
        double[] tmpW_34_Q5 = calculate(lengthQ1, R1, Delte1, lengthQ2, R2, Delte2, Q1.getT(), tmpW_1[1]);
        Q5.setW((int)tmpW_34_Q5[1]);

        lengthQ1 = Q3.getQij1();
        R1 = Q3.getRij1();
        Delte1 = Q3.getDelte();

        lengthQ2 = Q4.getQij1();
        R2 = Q4.getRij1();
        Delte2 = Q4.getDelte();

        // 计算出Q3，Q4的权值
        double[] Q3Q4W = calculate(lengthQ1, R1, Delte1, lengthQ2, R2, Delte2, Q1.getT(), tmpW_34_Q5[0]);
        Q3.setW((int)Q3Q4W[0]);
        Q4.setW((int)Q3Q4W[1]);

        System.out.println("Q1权值:" + (int) Q1Q2W[0] + "，Q1长度:" + Q1.getQij1() + "，到达率:" + Q1.getRij1());
        System.out.println("Q2权值:" + (int) Q1Q2W[1] + "，Q2长度:" + Q2.getQij1() + "，到达率:" + Q2.getRij1());
        System.out.println("Q3权值:" + (int)Q3Q4W[0] + "，Q3长度:" + Q3.getQij1() + "，到达率:" + Q3.getRij1());
        System.out.println("Q4权值:" + (int)Q3Q4W[1] + "，Q4长度:" + Q4.getQij1() + "，到达率:" + Q4.getRij1());
        System.out.println("Q5权值:" + (int)tmpW_34_Q5[1] + "，Q5长度:" + Q5.getQij1() + "，到达率:" + Q5.getRij1());
    }

    //每条队列的权值全部相同时的权值W计算
    public void sameWeightParameter(ArrayList<MessageQueue> queue) {
        ArrayList<Double> proportion = new ArrayList<Double>();
        double total = 0;
        for(int i = 0; i < queue.size(); i++) {
            MessageQueue tmpQueue = queue.get(i);
            double tmpPro = tmpQueue.getLength() + tmpQueue.getRij1() * tmpQueue.getT() / 1000;
            proportion.add(tmpPro);
            total += tmpPro;
        }
        for(int i = 0; i < queue.size(); i++) {
            int tmpW = (int)(proportion.get(i) / total * this.P);
            queue.get(i).setW(tmpW);
        }
    }

    // 计算一元二次方程
    double [] calculate(double lengthQ1, double R1, double Delte1, double lengthQ2, double R2, double Delte2, double T,double P) {
        double[] W = new double[2];
        double A = (lengthQ1 + R1 * T) * Delte2;
        double B = (lengthQ2 + R2 * T) * Delte1;
        double C = Delte1 - Delte2;
        W[0] = ((C * P - A - B) + Math.sqrt((Math.pow((A + B -C * P), 2) + 4 * A * P * C))) / (2.0 * C);
        W[1] = ((C *P + A + B) - Math.sqrt((Math.pow((A + B -C * P), 2) + 4 * A * P * C))) / (2.0 * C);
        if(W[0] < 0 || W[1] < 0) {
            W[0] = ((C * P - A - B) - Math.sqrt((Math.pow((A + B -C * P), 2) + 4 * A * P * C))) / (2.0 * C);
            W[1] = ((C * P + A + B) + Math.sqrt((Math.pow((A + B -C * P), 2) + 4 * A * P * C))) / (2.0 * C);
        }
        return W;
    }
}