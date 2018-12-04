package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Test {
    public static void main(String[] args) throws InterruptedException {
//        LinkedList<String> list = new LinkedList<String>();
//
//        for(int i = 0; i < 50000; i++)
//            list.add(new String(new byte[5]));
//
//        long start = System.currentTimeMillis();
//        for(int i = 0; i < 50000; i++)
//            list.poll();
//        long end = System.currentTimeMillis();
//
//        System.out.println(list.size());
//
//        System.out.println(end - start);

//        File file = new File("src\\main\\resources\\q1.txt");
//        try {
//            FileWriter fw = new FileWriter(file.getName(), true);
//            fw.write("11111\r\n");
//            fw.write("2222");
//            fw.flush();
//            //fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        double a = 1000 / 30000;
//
//        for(int i = 0; i < 100; i++) {
//            Thread.sleep(1);
//            System.out.println(a);
//        }
//
//        LinkedList<String> list = new LinkedList<String>();
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        list.poll();
//        System.out.print(list.size());
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        System.out.println(map.get("Q1") == null);

        LinkedList<String> list = new LinkedList<String>();
        System.out.println(list.poll() == null);


    }
}
