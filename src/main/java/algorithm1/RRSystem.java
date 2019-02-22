package algorithm1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 抽象出来的整个轮询系统
 */
public class RRSystem {

    public static void main(String[] args) throws Exception {
        // 队列1消息到达为平稳过程，均值到达速率 20000，权重为1
        MessageQueue q1 = new MessageQueue(Constant.STATIONARY);
        q1.setNumber(20000);
        q1.setLamda(20000);
        q1.setDelte(1);
        q1.setC(0);
        q1.setName("q1");
        q1.setWRRQuan(1);
        q1.setRij1(20);
        q1.setRij2(20);


        // 队列2消息到达为平稳过程，均值到达速率为15000，权重为2
        MessageQueue q2 = new MessageQueue(Constant.STATIONARY);
        q2.setNumber(15000);
        q2.setLamda(15000);
        q2.setDelte(2);
        q2.setC(0);
        q2.setName("q2");
        q2.setWRRQuan(1);
        q2.setRij1(15);
        q2.setRij2(15);

        // 队列3消息到达为平稳过程，均值到达速率为10000，权重为4
        MessageQueue q3 = new MessageQueue(Constant.STATIONARY);
        q3.setNumber(10000);
        q3.setLamda(10000);
        q3.setDelte(4);
        q3.setC(0);
        q3.setName("q3");
        q3.setWRRQuan(1);
        q3.setRij1(10);
        q3.setRij2(10);

        // 队列4消息到达为平稳过程，均值到达速率为5000，权重为8
        MessageQueue q4 = new MessageQueue(Constant.STATIONARY);
        q4.setNumber(5000);
        q4.setLamda(5000);
        q4.setDelte(8);
        q4.setC(0);
        q4.setName("q4");
        q4.setWRRQuan(1);
        q4.setRij1(5);
        q4.setRij2(5);

        ArrayList<MessageQueue> queues = new ArrayList<MessageQueue>();
        queues.add(q1);
        queues.add(q2);
        queues.add(q3);
        queues.add(q4);

        Server server = new Server(queues, 50000);

//        WRRServer wrrServer = new WRRServer(queues, 40000);

        String message = "";

        Producer producer = new Producer(queues, message);

        Monitor monitor = new Monitor(queues);

        new Thread(producer).start();
        Thread.sleep(1000);

        for(int i = 0; i < queues.size(); i++) {
            MessageQueue tmp = queues.get(i);
            tmp.setQij2(tmp.getLength());
            tmp.setQij1(tmp.getLength());
        }

        new Thread(server).start(); // 拥塞指数算法
//        new Thread(wrrServer).start(); // 传统的RR算法

        new Thread(monitor).start();

        while(true) {
            Thread.sleep(1000);
//            System.out.println("请输入q1，q2的发送速率：");
//            Scanner in = new Scanner(System.in);
//            int num1 = in.nextInt();
//            int num2 = in.nextInt();
//            q1.setNumber(num1);
//            q2.setNumber(num2);
        }

    }
}
