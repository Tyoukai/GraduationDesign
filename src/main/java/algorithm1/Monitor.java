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
        MessageQueue q3 = queues.get(2);
        MessageQueue q4 = queues.get(3);
        File file1 = new File("q1.txt"); // src\main\resources\q1.txt
        File file2 = new File("q2.txt");
        File file3 = new File("q3.txt");
        File file4 = new File("q4.txt");
        FileWriter fw1 = null;
        FileWriter fw2 = null;
        FileWriter fw3 = null;
        FileWriter fw4 = null;

        try {
            fw1 = new FileWriter(file1.getName(), true);
            fw2 = new FileWriter(file2.getName(), true);
            fw3 = new FileWriter(file3.getName(), true);
            fw4 = new FileWriter(file4.getName(), true);

            long sTime = System.currentTimeMillis();
            long eTime = System.currentTimeMillis();
            int flag = 0;
            while(eTime - sTime < 90000) {
                if(eTime -sTime >= 30000 && flag == 0) {
//                    q1.setNumber(5000);
//                    q2.setNumber(9000);
//                    q3.setNumber(4000);
//                    q4.setNumber(10000);
                    flag = 1;
                }
                Thread.sleep(1000);
                double q1Length = q1.getLength();
                double q1W = q1.getW();
                double q2Length = q2.getLength();
                double q2W = q2.getW();
                double q3Length = q3.getLength();
                double q3W = q3.getW();
                double q4Length = q4.getLength();
                double q4W = q4.getW();


                fw1.write(q1Length + "____" + q1W + "____" + (q1Length / q1W) + "\r\n");
                fw2.write(q2Length + "____" + q2W + "____" + (q2Length / q2W) + "\r\n");
                fw3.write(q3Length + "____" + q3W + "____" + (q3Length / q3W) + "\r\n");
                fw4.write(q4Length + "____" + q4W + "____" + (q4Length / q4W) + "\r\n");

                System.out.println("q1长度                    到达速率                    拥塞指数");
                System.out.println(q1Length + "                    " + q1.getRij1() + "                    " + (q1Length / q1W));
                System.out.println("q2长度                    到达速率                    拥塞指数");
                System.out.println(q2Length + "                    " + q2.getRij1() + "                    " + (q2Length / q2W));
                System.out.println("q3长度                    到达速率                    拥塞指数");
                System.out.println(q3Length + "                    " + q3.getRij1() + "                    " + (q3Length / q3W));
                System.out.println("q4长度                    到达速率                    拥塞指数");
                System.out.println(q4Length + "                    " + q4.getRij1() + "                    " + (q4Length / q4W));
                System.out.println();
                eTime = System.currentTimeMillis();
            }
            fw1.close();
            fw2.close();
            fw3.close();
            fw4.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
