package algorithm1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

        // 队列2消息到达为平稳过程，均值到达速率为30000，权重为2
        MessageQueue q2 = new MessageQueue(Constant.STATIONARY);
        q2.setNumber(30000);
        q2.setDelte(2);
        q2.setC(0);
        q2.setName("q2");

        ArrayList<MessageQueue> queues = new ArrayList<MessageQueue>();
        queues.add(q1);
        queues.add(q2);

        Server server = new Server(queues, 50000);

        String message = "{\"27\":272.168762,\"31\":50.612495,\"35\":18" +
                "7.906250,\"39\":44.937500,\"43\":1.740277,\"19\":0,\"23\":" +
                "0,\"11\":0,\"15\":0,\"48\":0,\"55\":0,\"56\":0,\"57\":0,\"58\":" +
                "0,\"59\":0,\"60\":0,\"61\":0,\"62\":0,\"63\":0,\"64\":0,\"65\":" +
                "0,\"67\":0,\"68\":0}";

        Producer producer = new Producer(queues, message);

        new Thread(producer).start();
        Thread.sleep(1000);
        new Thread(server).start();
//        File file1 = new File("q1.txt");
//        File file2 = new File("q2.txt");
//        FileWriter fw1 = null;
//        FileWriter fw2 = null;

//        fw1 = new FileWriter(file1, true);
//        fw2 = new FileWriter(file2, true);
//        fw1.write("q1长度                    到达速率                    拥塞指数\r\n");
//        fw2.write("q2长度                    到达速率                    拥塞指数\r\n");

        while(true) {
            Thread.sleep(1000);
            System.out.println("q1长度                    到达速率                    拥塞指数");
            System.out.println(q1.getLength() + "                    " + q1.getRij1() + "                    " + (q1.getLength() / q1.getRij1()));

            System.out.println("q2长度                    到达速率                    拥塞指数");
            System.out.println(q2.getLength() + "                    " + q2.getRij1() + "                    " + (q2.getLength() / q2.getRij1()));
            System.out.println();


//            fw1.write(q1.getLength() + "____" + q1.getRij1() + "____" + (q1.getLength() / q1.getRij1()) + "\r\n");
//            fw2.write(q2.getLength() + "____" + q2.getRij1() + "____" + (q2.getLength() / q2.getRij1()) + "\r\n");


//            for(int i = 0; i < queues.size(); i++) {
//                System.out.println(queues.get(i).getType() + ":" + queues.get(i).getLength());
//            }

        }
    }
}
