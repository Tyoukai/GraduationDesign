package test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Test {
    public static void main(String[] args) throws InterruptedException {
//        try {
//            File file1 = new File("delay.txt");
//            FileWriter fw1 = new FileWriter(file1.getName(), true);
//
//            FileInputStream fis = new FileInputStream("time_delay.txt");
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            String line = "";
//            int count = 1;
//            while((line = br.readLine()) != null) {
//                if(count <= 1520) {
//                    if(line.split("____")[1].equals("Q1") || line.split("____")[1].equals("Q2")) {
//                        double tmp = Double.parseDouble(line.split("____")[0]);
//                        fw1.write((tmp + 200) + "____" + line.split("____")[1] + "\r\n");
//                        count++;
//                    } else {
//                        fw1.write(line + "\r\n");
//                        count++;
//                    }
//                } else {
//                    fw1.write(line + "\r\n");
//                    count++;
//                }
//            }
//            fw1.close();
//
//        } catch(Exception e) {
//
//        }
        System.out.println((int)0.5);

        int count = 0;
        double error = 10;
        double W1 = 0, W2 = 0, W3 = 0, W4 = 0;
        double k1 = 0, k2 = 0, k3 = 0, k4 = 0;
        k1 = 1 * 20;
        k2 = 2 * 15;
        k3 = 4 * 10;
        k4 = 8 * 5;

        while(count <= 1000  && Math.abs(50000 - W1 - W2 - W3 - W4) > error) {
            W2 = k2 / k3 * W3 +15*1000 - k2 / k3 * 10 * 1000;
            W1 = k1 / k2 * W2 +20*1000 - k1 / k2 * 15 * 1000;
            W4 = 50000 - W1 - W2 -W3;
            W3 = k3 / k4 * W4 +10*1000 - k3 / k4 * 5 * 1000;
        }

        System.out.println("W1:" + W1);
        System.out.println("W2:" + W2);
        System.out.println("W3:" + W3);
        System.out.println("W4:" + W4);

    }
}
