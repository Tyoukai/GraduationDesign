package algorithm1;

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

        while(true) {
            long sTime = System.currentTimeMillis();
            while(Math.abs(count - P) <= 3) {
                for(int i = 0; i < queue.size(); i++) {
                    MessageQueue tmpQ = queue.get(i);
                    int WRRQuan = tmpQ.getWRRQuan();
                    for(int j = 0; j < WRRQuan; j++)
                        tmpQ.decrease();
                    count += WRRQuan;
                }
            }

            long eTime = System.currentTimeMillis();

            if((eTime - sTime) < queue.get(0).getT()) {
                try {
                    Thread.sleep((long)queue.get(0).getT() - eTime + sTime);
                } catch (Exception e) {

                }
                count = 0;
            }
        }
    }

}
