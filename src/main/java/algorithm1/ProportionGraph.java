package algorithm1;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class ProportionGraph {

    public static void main(String[] args) {
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);
        CategoryDataset mDataset = GetDataset();
        JFreeChart mChart = ChartFactory.createLineChart(
                "比例变化图",//图名字
                "时间周期（s）",//横坐标
                "紧急指数比例",//纵坐标
                mDataset,//数据集
                PlotOrientation.VERTICAL,
                true, // 显示图例
                false, // 采用标准生成器
                false);// 是否生成超链接
        CategoryPlot mPlot = (CategoryPlot)mChart.getPlot();
        mPlot.setBackgroundPaint(Color.white);
        mPlot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
        mPlot.setOutlinePaint(Color.RED);//边界线


        /*
         * Y轴设置
         */
        NumberAxis vn = (NumberAxis) mPlot.getRangeAxis();
        vn.setUpperMargin(0.1);
        vn.setLowerMargin(0.1);
        vn.setAutoRangeMinimumSize(20);//最小跨度
        vn.setLowerBound(0);//最小值显示
        vn.setUpperBound(10);
        LineAndShapeRenderer lasp = (LineAndShapeRenderer) mPlot.getRenderer();// 获取显示线条的对象
        lasp.setBaseShapesVisible(true);// 设置拐点是否可见/是否显示拐点
        lasp.setDrawOutlines(true);// 设置拐点不同用不同的形状
        lasp.setUseFillPaint(true);// 设置线条是否被显示填充颜色
        lasp.setBaseFillPaint(Color.BLACK);//// 设置拐点颜色

        /*
         * X轴
         */
        CategoryAxis domainAxis = mPlot.getDomainAxis();
        domainAxis.setLowerMargin(-0.08);

        domainAxis.setCategoryMargin(0.5);
        domainAxis.setLabelFont(new Font("宋体", Font.CENTER_BASELINE, 15)); // 设置横轴字体
        domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 10));// 设置坐标轴标尺值字体
        domainAxis.setLowerMargin(0.01);// 左边距 边框距离
        domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
        domainAxis.setMaximumCategoryLabelLines(10);

        try {
//            File file = new File("graph1.png");
//            ChartUtilities.saveChartAsPNG(file,mChart,1028,600);//把报表保存为文件
        } catch (Exception e) {

        }
        ChartFrame mChartFrame = new ChartFrame("紧急指数图", mChart);
        mChartFrame.pack();
        mChartFrame.setVisible(true);
    }

    public static CategoryDataset GetDataset() {
        DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
        try {
            FileInputStream fis = new FileInputStream("q3.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            String line1 = "";
            int time = 1;

            FileInputStream fis1 = new FileInputStream("q4.txt");
            InputStreamReader isr1 = new InputStreamReader(fis1);
            BufferedReader br1 = new BufferedReader(isr1);

            while((line = br.readLine()) != null && (line1 = br1.readLine()) != null) {
                double exponent = Double.parseDouble(line.split("____")[2]);
                double exponent1 = Double.parseDouble(line1.split("____")[2]);
                mDataset.addValue(exponent1 / exponent, "Proportion", time + "");
                time++;
            }
            br.close();
            br1.close();



        } catch (Exception e) {

        }
        return mDataset;
    }
}