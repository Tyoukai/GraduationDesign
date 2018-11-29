package algorithm1;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用来模拟生产各个消息的产生
 */
public class Producer implements Runnable {

    /**
     * 根据队列类型发送数据
     */
    class DataSendHelper implements Runnable {
        private MessageQueue queue;
        private String message;

        DataSendHelper(MessageQueue queue, String message) {
            this.queue = queue; // 发送消息的队列
            this.message = message; // 发送的消息
        }

        public void run() {
            while(true) {
                int number = 0; // 时间T内的消息个数
                long numOfEachMilli = 0; //每毫秒发送的消息个数
                int count = 0; //计数器
                if(queue.getType().equals(Constant.POSSION)) { //泊松过程
                    number = Possion.getPossionVariable(queue.getLamda()); //T时间内产生的消息个数
                    numOfEachMilli = (long)(number / queue.getT());
                } else if(queue.getType().equals(Constant.STATIONARY)) { //平稳过程
                    number = queue.getNumber();
                    numOfEachMilli = (long)(number / queue.getT());
                }

                long startTime = System.currentTimeMillis();
                for(int i = 0; i < number; i++) {
                    queue.addTail(this.message);
                    count++;
//                    if(count > numOfEachMilli) {
//                        try {
//                            Thread.sleep(1);
//                            count = 0;
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
                long endTime = System.currentTimeMillis();

                if(endTime - startTime < queue.getT()) {
                    try {
                        Thread.sleep((long)queue.getT() - endTime + startTime - 10);
//                        System.out.println("睡眠时间：" + (queue.getT() - endTime + startTime));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                System.out.println("放入所需时间：" + (System.currentTimeMillis() - startTime));
            }
        }
    }

    private ArrayList<MessageQueue> queue;
    private ExecutorService service = Executors.newCachedThreadPool();
    private String message = new String(new byte[10]); //发送的消息大小

    Producer() {
        queue = new ArrayList<MessageQueue>();
    }

    Producer(ArrayList<MessageQueue> queue) {
        this.queue = queue;
    }

    Producer(ArrayList<MessageQueue> queue, String message) {
        this.queue = queue;
        this.message = message;
    }

    // 向生产者中添加一个队列
    public void addMessageQueue(MessageQueue queue) {
        this.queue.add(queue);
    }

    //删除一个队列
    public MessageQueue removeMessageQueue(int index) {
        return queue.remove(index);
    }

    public void run() {
        for(int i = 0; i < queue.size(); i++) {
            service.submit(new DataSendHelper(queue.get(i), this.message));
        }
    }
}
