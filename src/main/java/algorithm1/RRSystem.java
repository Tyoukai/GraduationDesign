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
        q1.setNumber(30000);
        q1.setDelte(1);
        q1.setC(0);
        q1.setName("q1");
        q1.setWRRQuan(1);

        // 队列2消息到达为平稳过程，均值到达速率为30000，权重为2
        MessageQueue q2 = new MessageQueue(Constant.STATIONARY);
        q2.setNumber(25000);
        q2.setDelte(1);
        q2.setC(0);
        q2.setName("q2");
        q2.setWRRQuan(2);

        ArrayList<MessageQueue> queues = new ArrayList<MessageQueue>();
        queues.add(q1);
        queues.add(q2);

//        Server server = new Server(queues, 40000);

        WRRServer wrrServer = new WRRServer(queues, 40000);

        String message = "{\"27\":272.168762,\"31\":50.612495,\"35\":18" +
                "7.906250,\"39\":44.937500,\"43\":1.740277,\"19\":0,\"23\":" +
                "0,\"11\":0,\"15\":0,\"48\":0,\"55\":0,\"56\":0,\"57\":0,\"58\":" +
                "0,\"59\":0,\"60\":0,\"61\":0,\"62\":0,\"63\":0,\"64\":0,\"65\":" +
                "0,\"67\":0,\"68\":0}";

        Producer producer = new Producer(queues, message);

        Monitor monitor = new Monitor(queues);

        new Thread(producer).start();
        Thread.sleep(1000);

//        new Thread(server).start(); // 拥塞指数算法
        new Thread(wrrServer).start(); // 传统的WRR算法

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
