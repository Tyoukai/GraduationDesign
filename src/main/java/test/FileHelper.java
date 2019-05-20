package test;

import java.io.*;

public class FileHelper {
    public static void main(String[] args) {
        try {
            File file4 = new File("q4_1.txt");
            FileWriter fw4 = null;
            fw4 = new FileWriter(file4.getName(), true);

            FileInputStream fis = new FileInputStream("q4.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = "";

            while((line = br.readLine()) != null) {
                double exponent = Double.parseDouble(line.split("____")[2]);
                exponent -= 0.5;
                String str = line.split("____")[0] + "____" + line.split("____")[1] + "____" + exponent;
                fw4.write(str + "\r\n");
            }
            fw4.flush();
            fw4.close();
            br.close();

        } catch(Exception e) {

        }
    }
}
