package algorithm1;

import java.io.*;
import java.util.HashMap;

public class ProportionFile {
    public static void main(String[] args) {
        try {
//            File file1 = new File("JJBL.txt");
//            FileWriter fw1 = new FileWriter(file1.getName(), true);
//
//            FileInputStream fis1 = new FileInputStream("q1.txt");
//            InputStreamReader isr1 = new InputStreamReader(fis1);
//            BufferedReader br1 = new BufferedReader(isr1);
//
//            FileInputStream fis2 = new FileInputStream("q2.txt");
//            InputStreamReader isr2 = new InputStreamReader(fis2);
//            BufferedReader br2 = new BufferedReader(isr2);
//
//            FileInputStream fis3 = new FileInputStream("q3.txt");
//            InputStreamReader isr3 = new InputStreamReader(fis3);
//            BufferedReader br3 = new BufferedReader(isr3);
//
//            FileInputStream fis4 = new FileInputStream("q4.txt");
//            InputStreamReader isr4 = new InputStreamReader(fis4);
//            BufferedReader br4 = new BufferedReader(isr4);
//
//            int count1 = 0;
//            int count3 = 0;
//            double total = 0;
//            br2.mark(100000 );
//            br3.mark(100000);
//
//            String line1 = "";
//            String line2 = "";
//
//            while((line1 = br1.readLine()) != null && (line2 = br2.readLine()) != null) {
//                if(count1 > 10 && count1 < 50) {
//                    double tmp1 = Double.parseDouble(line1.split("____")[2]);
//                    double tmp2 = Double.parseDouble(line2.split("____")[2]);
//                    total += tmp2 / tmp1;
//                    count3++;
//                } else {
//                    count1++;
//                }
//            }
//            fw1.write((total / count3) + "____class2/class1\r\n");
//
//            count1 = 0;
//            count3 = 0;
//            total = 0;
//            br2.reset();
//
//
//            while((line1 = br2.readLine()) != null && (line2 = br3.readLine()) != null) {
//                if(count1 > 10 && count1 < 50) {
//                    double tmp1 = Double.parseDouble(line1.split("____")[2]);
//                    double tmp2 = Double.parseDouble(line2.split("____")[2]);
//                    total += tmp2 / tmp1;
//                    count3++;
//                } else {
//                    count1++;
//                }
//            }
//
//            fw1.write((total / count3) + "____class3/class2\r\n");
//
//            count1 = 0;
//            count3 = 0;
//            total = 0;
//            br3.reset();
//
//            while((line1 = br3.readLine()) != null && (line2 = br4.readLine()) != null) {
//                if(count1 > 10 && count1 < 50) {
//                    double tmp1 = Double.parseDouble(line1.split("____")[2]);
//                    double tmp2 = Double.parseDouble(line2.split("____")[2]);
//                    total += tmp2 / tmp1;
//                    count3++;
//                } else {
//                    count1++;
//                }
//            }
//
//            fw1.write((total / count3) + "____class4/class3\r\n");
//            fw1.close();


            File file = new File("DelayBL.txt");
            FileWriter fw = new FileWriter(file.getName(), true);

            FileInputStream fis = new FileInputStream("time_delay.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String oneStr = "";
            double[] total1 = new double[3];
            double[] nums = new double[4];
            int Linecount = 0;
            int count = 0;
            while((oneStr = br.readLine()) != null) {
                if(Linecount % 4 == 0 && Linecount != 0) {
                    total1[0] += nums[1] / nums[0];
                    total1[1] += nums[2] / nums[1];
                    total1[2] += nums[3] / nums[2];
                    count++;
                    Linecount = 0;
                    br.reset();
                } else {
                    nums[Linecount] = Double.parseDouble(oneStr.split("____")[0]);
                    Linecount++;
                    if(Linecount == 4) {
                        br.mark(1000);
                    }
                }
            }

            fw.write((total1[0] / count) + "____class2/class1\r\n");
            fw.write((total1[1] / count) + "____class3/class2\r\n");
            fw.write((total1[2] / count) + "____class4/class3\r\n");
            fw.close();
        } catch(Exception e) {

        }
    }
}
