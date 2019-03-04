package algorithm1;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class URRServer implements  Runnable {
    //用来存放队列
    private ArrayList<MessageQueue> queue ;
    private int P = 50000; //单服务器T时间内的平均处理消息个数

    public URRServer(ArrayList<MessageQueue> queues, int P) {
        this.queue = queues;
        this.P = P;
    }

    public ArrayList<MessageQueue> getQueue() {
        return queue;
    }

    public void setQueue(ArrayList<MessageQueue> queue) {
        this.queue = queue;
    }

    public int getP() {
        return P;
    }

    public void setP(int p) {
        P = p;
    }

    public void run() {
        HashMap<String, Integer> speed = new HashMap<>();
        HashMap<String, Double> total = new HashMap<>();
        for(int i = 1; i <= 4; i++) {
            speed.put("Q" + i, 0);
            total.put("Q" + i, 0.0);
        }
        File file = new File("urr_time_delay.txt"); // src\main\resources\q1.txt
        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getName(), true);
        } catch (Exception e) {

        }
        while (true) {
            sortQueue();
            for(int i = 0; i < queue.size(); i++)
                System.out.println(queue.get(i).getName() + "      " + queue.get(i).getUindex());
            System.out.println("-----------------------------------------");
            long sTime = System.currentTimeMillis();
            for(int i = 0; i < queue.size(); i++) {
                MessageQueue tmpQ = queue.get(i);
                for(int j = 0; j < tmpQ.getUrrW(); j++) {
                    long curTime = System.currentTimeMillis();
                    String message = tmpQ.getAndDecrease();
                    if(message == null)
                        break;
                    speed.put(tmpQ.getName(), speed.get(tmpQ.getName()) + 1);
                    total.put(tmpQ.getName(), total.get(tmpQ.getName()) + curTime - Double.parseDouble(message));
                }
            }
            long eTime = System.currentTimeMillis();
            if((eTime - sTime) < 1000) {


                try {
                    Thread.sleep((1000 + sTime - eTime));
                } catch (Exception e) {

                }
            }

            try {
                for(int i = 1; i <= 4; i++) {
                    int tmpSpeed = speed.get("Q" + i);
                    double totalTime = total.get("Q" + i);
                    double aveTimeDelay = totalTime / tmpSpeed;
                    fw.write(aveTimeDelay + "____Q" + i +"\r\n");
                    fw.flush();

                    speed.put("Q" + i, 0);
                    total.put("Q" + i, 0.0);
                }
            } catch (Exception e) {

            }
        }
    }

    // 按紧急指数从小到大排列
    public void sortQueue() {
        Collections.sort(this.queue, new Comparator<MessageQueue>() {
            @Override
            public int compare(MessageQueue o1, MessageQueue o2) {
                o1.setUindex((double)o1.getLength() / o1.getUrrW());
                o2.setUindex((double)o2.getLength() / o2.getUrrW());
                if((o1.getUindex() - o2.getUindex()) > 0) {
                    return -1;
                } else if((o1.getUindex() - o2.getUindex()) == 0)
                    return 0;
                else
                    return 1;
            }
        });
    }



}
