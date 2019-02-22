package algorithm1;

/**
 * 高斯赛德尔逼近法
 */
public class GaussSeidel {
    public static void main(String[] args) {
        int num = 1;
        double W1 = 0;
        double W2 = 0;
        double W3 = 0;
        int MaxTime = 10000000;
        int count = 0;
        int tmpCount = 0;
        while((fun1(W1, W2) + fun2(W1, W3) + fun3(W1, W2, W3)) > 0.000001 || W1 < 0 || W2 < 0 || W3 < 0) {
            W1 = 3 * W2 / (8 - W2);
            W3 = 10 * W1 / (3 + W1);
            W2 = 12 -W1 - W3;
//            tmpCount++;
//            if(tmpCount > 10000) {
//                W1 = num;
//                num++;
//                tmpCount = 0;
//            }
            count++;
//            if(count > MaxTime)
//                break;
            System.out.println("W1=" + W1 + "; W2=" + W2 + "; W3=" + W3);
            System.out.println(count);
        }

        System.out.println("W1=" + W1 + "; W2=" + W2 + "; W3=" + W3);
        System.out.println(count);
    }

    public static double fun1(double W1, double W2) {
//         System.out.println("fun1:" + Math.abs(3 * W2 - 8 * W1 + W1 * W2));
        return Math.abs(3 * W2 - 8 * W1 + W1 * W2);
    }

    public static double fun2(double W1, double W3) {
//         System.out.println("fun2:" + Math.abs(3 * W3 - 10 * W1 + W1 * W3));
        return Math.abs(3 * W3 - 10 * W1 + W1 * W3);
    }

    public static double fun3(double W1, double W2, double W3) {
//         System.out.println("fun3:" + Math.abs(W1 + W2 + W3 - 12));
        return Math.abs(W1 + W2 + W3 - 12);
    }
}
