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
        // 队列1消息到达为平稳过程，均值到达速率 30000，权重为1
        MessageQueue q1 = new MessageQueue(Constant.STATIONARY);
        q1.setNumber(3000);
        q1.setDelte(2);
        q1.setC(0);
        q1.setName("q1");
        q1.setWRRQuan(1);

        // 队列2消息到达为平稳过程，均值到达速率为30000，权重为2
        MessageQueue q2 = new MessageQueue(Constant.STATIONARY);
        q2.setNumber(6000);
        q2.setDelte(3);
        q2.setC(0);
        q2.setName("q2");
        q2.setWRRQuan(1);

        // 队列3消息到达为平稳过程，均值到达速率为30000，权重为2
        MessageQueue q3 = new MessageQueue(Constant.STATIONARY);
        q3.setNumber(10000);
        q3.setDelte(3);
        q3.setC(0);
        q3.setName("q3");
        q3.setWRRQuan(1);

        // 队列4消息到达为平稳过程，均值到达速率为30000，权重为2
        MessageQueue q4 = new MessageQueue(Constant.STATIONARY);
        q4.setNumber(15000);
        q4.setDelte(4);
        q4.setC(0);
        q4.setName("q4");
        q4.setWRRQuan(1);

        // 队列5消息到达为平稳过程，均值到达速率为30000，权重为2
        MessageQueue q5 = new MessageQueue(Constant.STATIONARY);
        q5.setNumber(19000);
        q5.setDelte(5);
        q5.setC(0);
        q5.setName("q5");
        q5.setWRRQuan(1);

        ArrayList<MessageQueue> queues = new ArrayList<MessageQueue>();
        queues.add(q1);
        queues.add(q2);
        queues.add(q3);
        queues.add(q4);
        queues.add(q5);

        Server server = new Server(queues, 40000);

//        WRRServer wrrServer = new WRRServer(queues, 40000);

        String message = "";

        Producer producer = new Producer(queues, message);

        Monitor monitor = new Monitor(queues);

        new Thread(producer).start();
        Thread.sleep(1000);

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
