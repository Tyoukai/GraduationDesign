package algorithm1;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * 模拟传统的WRR算法服务器
 */
public class WRRServer implements Runnable {
    //用来存放队列
    private ArrayList<MessageQueue> queue ;
    private int P = 50000; //单服务器T时间内的平均处理消息个数

    WRRServer(ArrayList<MessageQueue> queue, int P) {
        this.queue = queue;
        this.P = P;
    }

    public void run() {
        int count = 0;
        File file = new File("time_delay.txt"); // src\main\resources\q1.txt
        File file1 = new File("Q_length.txt");
        FileWriter fw = null;
        FileWriter fw1 = null;
        try {
            fw = new FileWriter(file.getName(), true);
            fw1 = new FileWriter(file1.getName(), true);
        } catch(Exception e) {
        }
        double[] totalTime = new double[queue.size()];
        int[] speed = new int[queue.size()];

        while(true) {

            for(int i = 0; i < queue.size(); i++) {
                MessageQueue tmpQ = queue.get(i);
                tmpQ.setQij1(tmpQ.getLength());
                long eTime = System.currentTimeMillis();
                double R = (double)tmpQ.getCount() / (eTime - tmpQ.getStartTime());
                tmpQ.setRij1(R);
                tmpQ.setCount(0);
                tmpQ.setStartTime(eTime);
            }


            long sTime = System.currentTimeMillis();
            while(Math.abs(count - P) >= 3) {
                for(int i = 0; i < queue.size(); i++) {
                    MessageQueue tmpQ = queue.get(i);
//                    int WRRQuan = tmpQ.getWRRQuan();
//                    for(int j = 0; j < WRRQuan; j++) {
                        String tmpStr = tmpQ.decrease();
                        if(tmpStr != null) {
                            count++;
                            long tmpTime = System.currentTimeMillis();
                            totalTime[i] += tmpTime - Long.parseLong(tmpStr);
                            speed[i]++;
                        }
//                    }
                }
            }

            long eTime = System.currentTimeMillis();

            if((eTime - sTime) < queue.get(0).getT()) {
                try {
                    Thread.sleep((long)queue.get(0).getT() - eTime + sTime);
                } catch (Exception e) {
                }
            }

            System.out.println("服务周期:" + (System.currentTimeMillis() - sTime));
            System.out.println("服务消息个数：" + count);
            count = 0;
            for(int i = 0; i < queue.size(); i++) {
                double delay = totalTime[i] / speed[i];
                System.out.println("speed[" + i + "]:" + speed[i]);
                try {
                    fw.write(delay + "____Q" + (i + 1) + "\r\n");
                    fw.flush();
                    fw1.write(queue.get(i).getLength() + "____Q" + (i + 1) + "\r\n");
                    fw1.flush();
                } catch(Exception e) {
                }
                totalTime[i] = 0;
                speed[i] = 0;
            }
        }
    }
}
