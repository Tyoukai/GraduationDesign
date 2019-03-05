package algorithm1;

import java.util.LinkedList;

/**
 * 抽象出来描述每一个队列
 */
public class MessageQueue {
    private LinkedList<String> queue = new LinkedList<String>();
    private int W; //记录每次轮询的权重
    private int urrW; // 记录URR算法的队列权重
    private int C; //记录每次轮询的计数器
    private int Qij1; //记录当前周期开始的队列长度
    private int Qij2; //记录上一周期开始的队列长度
    private double Rij1; //记录当前周期消息的到达速率
    private double Rij2; //记录上一周期消息放入到达速率
    private String type = Constant.STATIONARY; // 记录队列的类别，是泊松过程，还是平稳过程
    private double lamda = 10; // 对应泊松过程的lamda
    private double delte = 1; //记录每个队列的拥塞指数
    private int WRRQuan = 1; // WRR算法中对应队列的权值
    private double T = 1000; // 对应T的周期。默认周期1s
    private int number = 20000; // 对应的平稳过程中周期T内发送的消息个数
    private long startTime = 0; //周期开始的时间
    private long endTime = 0; //周期结束的时间
    private int count = 0; // 在开始跟结束时间内进入队列的消息个数
    private String name = ""; //队列的名字
    private double Uindex = 0; //每周期中的紧急指数
    private double PFWRRW = 0; // PFWRR算法的队列权值


    MessageQueue() {
        startTime = System.currentTimeMillis();
    }

    MessageQueue(String type) {
        this.type = type;
        startTime = System.currentTimeMillis();
    }

    //获取队列长度
    public int getLength() {
        return queue.size();
    }

    //获取队列的第一个元素并移除
    public String pollHead() {
        return queue.poll();
    }

    // 取出队列的第一个元素
    public String getHead() {
        return queue.getFirst();
    }

    //在尾部添加一个元素
    synchronized public void addTail(String str) {
        queue.addLast(str);
        count++;
    }

    //从队列中获取一个消息，并将计数器减一
    public String getAndDecrease() {
        String message = queue.poll();
        C--;
        return message;
    }

    // 从队列中获取一个消息
    public String decrease() {
        return queue.poll();
    }

    //将计数器减一
    public void decreaseOne() {
        C--;
    }

    public int getW() {
        return W;
    }

    public void setW(int w) {
        W = w;
    }

    public int getQij1() {
        return Qij1;
    }

    public void setQij1(int qij1) {
        Qij1 = qij1;
    }

    public int getQij2() {
        return Qij2;
    }

    public void setQij2(int qij2) {
        Qij2 = qij2;
    }

    public double getRij1() {
        return Rij1;
    }

    public void setRij1(double rij1) {
        Rij1 = rij1;
    }

    public double getRij2() {
        return Rij2;
    }

    public void setRij2(double rij2) {
        Rij2 = rij2;
    }

    public int getC() {
        return C;
    }

    public void setC(int c) {
        C = c;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLamda() {
        return lamda;
    }

    public void setLamda(double lamda) {
        this.lamda = lamda;
    }

    public double getT() {
        return T;
    }

    public void setT(double t) {
        T = t;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getDelte() {
        return delte;
    }

    public void setDelte(int delte) {
        this.delte = delte;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    synchronized public int getCount() {
        return count;
    }

    synchronized public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWRRQuan() {
        return WRRQuan;
    }

    public void setWRRQuan(int WRRQuan) {
        this.WRRQuan = WRRQuan;
    }

    public double getUindex() {
        return Uindex;
    }

    public void setUindex(double uindex) {
        Uindex = uindex;
    }

    public int getUrrW() {
        return urrW;
    }

    public void setUrrW(int urrW) {
        this.urrW = urrW;
    }

    public double getPFWRRW() {
        return PFWRRW;
    }

    public void setPFWRRW(double PFWRRW) {
        this.PFWRRW = PFWRRW;
    }
}
