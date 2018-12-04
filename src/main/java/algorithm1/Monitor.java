package algorithm1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 监控队列各个参数
 */
public class Monitor implements Runnable{
    private ArrayList<MessageQueue> queues;

    Monitor(ArrayList<MessageQueue> queues) {
        this.queues = queues;
    }

    public void run() {
        recordCongestionExponent();
    }

    // 记录拥塞指数
    public void recordCongestionExponent() {
        MessageQueue q1 = queues.get(0);
        MessageQueue q2 = queues.get(1);
        File file1 = new File("q1.txt"); // src\main\resources\q1.txt
        File file2 = new File("q2.txt");
        FileWriter fw1 = null;
        FileWriter fw2 = null;

        try {
            fw1 = new FileWriter(file1.getName(), true);
            fw2 = new FileWriter(file2.getName(), true);
//            fw1.write("q1长度                    到达速率                    拥塞指数\r\n");
//            fw2.write("q2长度                    到达速率                    拥塞指数\r\n");
            long sTime = System.currentTimeMillis();
            long eTime = System.currentTimeMillis();
            int flag = 0;
            while(eTime - sTime < 90000) {
//                if(eTime -sTime >= 45000 && flag == 0) {
//                    q2.setNumber(15000);
//                    flag = 1;
//                }
                Thread.sleep(1000);
                double q1Length = q1.getQij1();
                double q1R = q1.getRij1();
                double q2Length = q2.getQij1();
                double q2R = q2.getRij1();
                fw1.write(q1Length + "____" + q1.getRij1() + "____" + (q1Length / q1R) + "\r\n");
                fw2.write(q2Length + "____" + q2.getRij1() + "____" + (q2Length / q2R) + "\r\n");

                System.out.println("q1长度                    到达速率                    拥塞指数");
                System.out.println(q1Length + "                    " + q1R + "                    " + (q1Length / q1R));
                System.out.println("q2长度                    到达速率                    拥塞指数");
                System.out.println(q2Length + "                    " + q2R + "                    " + (q2Length / q2R));
                System.out.println();
                eTime = System.currentTimeMillis();
            }
            fw1.close();
            fw2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
