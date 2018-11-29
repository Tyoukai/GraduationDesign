package algorithm1;


import java.util.ArrayList;

/**
 * 抽象出来描述从队列中获取消息的服务器
 */
public class Server implements  Runnable {
    //用来存放队列
    private ArrayList<MessageQueue> queue ;
    private int P = 50000; //单服务器T时间内的平均处理消息个数


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

    //删除一个队列
    public MessageQueue removeMessageQueue(int index) {
        return queue.remove(index);
    }

    public void run() {
        int[] flag = new int[queue.size()]; // 记录每次循环中各个队列是否还有消息
        int numOfQueueEndInOneCycle = 0;
        int count = 0;
        int[] speed = new int[queue.size()]; // 统计处理的消息个数
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
                     speed[i]++;
                     count++;
                    if(tmpQueue.getC() <= 0  || count >= P) {
                        flag[i] = 1;
                        numOfQueueEndInOneCycle++;
                    }
                }
            }

            System.out.println("count1:" + count);
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
            for(int i = 0; i < speed.length; i++) {
                System.out.println("speed[" + i + "]:" + speed[i]);
                speed[i] = 0;
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
        double A12 = Q1.getQij1() * Q2.getDelte() + Q1.getRij1() * Q2.getDelte() * Q1.getT() / 1000
                - Q2.getQij1() * Q1.getDelte() - Q2.getRij1() * Q1.getDelte() * Q1.getT() / 1000;
        int W1 = (int)((Q1.getDelte() * this.P + A12) / (Q1.getDelte() + Q2.getDelte()));
        int W2 = (int)((Q2.getDelte() * this.P - A12) / (Q1.getDelte() + Q2.getDelte()));
        Q1.setW(W1);
        Q2.setW(W2);
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
}