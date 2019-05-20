package test;

import java.awt.Font;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;
import java.util.Map.Entry;


public class BarChart2 {

    /**
     * 提供静态方法：获取报表图形2：柱状图
     * @param title        标题
     * @param datas        数据
     * @param type        分类（第一季，第二季.....）
     * @param danwei    柱状图的数量单位
     * @param font        字体
     */
    public static void createPort(String title,Map<String,Map<String,Double>> datas,String type,String danwei,Font font){
        try {
            //种类数据集
            DefaultCategoryDataset ds = new DefaultCategoryDataset();


            //获取迭代器：
            Set<Entry<String, Map<String, Double>>> set1 =  datas.entrySet();    //总数据
            Iterator iterator1=(Iterator) set1.iterator();                        //第一次迭代
            Iterator iterator2=null;
            HashMap<String, Double> map =  null;
            Set<Entry<String,Double>> set2=null;
            Entry entry1=null;
            Entry entry2=null;

            while(iterator1.hasNext()){
                entry1=(Entry) iterator1.next();                    //遍历分类

                map=(HashMap<String, Double>) entry1.getValue();//得到每次分类的详细信息
                set2=map.entrySet();                               //获取键值对集合
                iterator2=set2.iterator();                        //再次迭代遍历
                while (iterator2.hasNext()) {
                    entry2= (Entry) iterator2.next();
                    ds.setValue(Double.parseDouble(entry2.getValue().toString()),//每次统计数量
                            entry2.getKey().toString(),                         //名称
                            entry1.getKey().toString());                        //分类
                    System.out.println("当前：--- "+entry2.getKey().toString()+"--"
                            +entry2.getValue().toString()+"--"
                            +entry1.getKey().toString());
                }
                System.out.println("-------------------------------------");
            }

            //创建柱状图,柱状图分水平显示和垂直显示两种
            JFreeChart chart = ChartFactory.createBarChart(title, type, danwei, ds, PlotOrientation.VERTICAL, true, false, false);

            //设置整个图片的标题字体
            chart.getTitle().setFont(font);

            //设置提示条字体
            font = new Font("宋体", Font.BOLD, 15);
            chart.getLegend().setItemFont(font);

            //得到绘图区
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            //得到绘图区的域轴(横轴),设置标签的字体
            plot.getDomainAxis().setLabelFont(font);

            //设置横轴标签项字体
            plot.getDomainAxis().setTickLabelFont(font);

            //设置范围轴(纵轴)字体
            plot.getRangeAxis().setLabelFont(font);
            //存储成图片

            //设置chart的背景图片
//            chart.setBackgroundImage(ImageIO.read(new File("f:/test/1.jpg")));
//
//            plot.setBackgroundImage(ImageIO.read(new File("f:/test/2.jpg")));

            plot.setForegroundAlpha(1.0f);



            ChartUtilities.saveChartAsJPEG(new File("BarChart2.png"), chart, 1028, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Map<String, Map<String, Double>> datas =new HashMap<String, Map<String,Double>>();

        Map<String, Double> map1=new HashMap<String, Double>();
        Map<String, Double> map2=new HashMap<String, Double>();
        Map<String, Double> map3=new HashMap<String, Double>();
        Map<String, Double> map4=new HashMap<String, Double>();

        //设置第一期的投票信息
        map1.put("class1/class2", (double) 1000);
        map1.put("class2/class3", (double) 700);
        map1.put("class3/class4", (double) 600);

        //设置第二期的投票信息
        map2.put("class1/class2", (double) 1300);
        map2.put("class2/class3", (double) 900);
        map2.put("class3/class4", (double) 800);

        //设置第三期的投票信息
        map3.put("class1/class2", (double) 2000);
        map3.put("class2/class3", (double) 1700);
        map3.put("class3/class4", (double) 1000);

        //设置第四期的投票信息
        map4.put("class1/class2", (double) 3000);
        map4.put("class2/class3", (double) 2500);
        map4.put("class3/class4", (double) 1600);

        //压入数据
        datas.put("25-25-25-25", map1);
        datas.put("10-40-40-10", map2);
        datas.put("50-30-15-5", map3);
        datas.put("60-20-10-10", map4);

        Font font = new Font("宋体", Font.BOLD, 20);
        BarChart2.createPort("",datas,"队列负载分布","比值关系",font);
    }

}
