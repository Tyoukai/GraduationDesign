package algorithm1;

/**
 * 泊松分布的产生类
 */
public class Possion {
    public static int getPossionVariable(double lamda) {
        int x = 0;
        double y = Math.random(), cdf = getPossionProbability(x, lamda);
        while (cdf < y) {
            x++;
            cdf += getPossionProbability(x, lamda);
        }
        return x;
    }

    public static double getPossionProbability(int k, double lamda) {
        double c = Math.exp(-lamda), sum = 1;
        for (int i = 1; i <= k; i++) {
            sum *= lamda / i;
        }
        return sum * c;
    }

    public static void main(String[] args) {
        long s = System.currentTimeMillis();
        for(int i = 0; i < 200; i++) {
            //long start = System.currentTimeMillis();
            int tmp = getPossionVariable(200);
            //System.out.println(tmp);
            //long end = System.currentTimeMillis();
            //System.out.println(end - start);
        }
        System.out.println(System.currentTimeMillis() - s);
    }
}
