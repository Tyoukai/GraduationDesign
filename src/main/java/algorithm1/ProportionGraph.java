package algorithm1;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class ProportionGraph {

    public static void main(String[] args) {
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("Times New Roman", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);
        CategoryDataset mDataset = GetDataset();
        JFreeChart mChart = ChartFactory.createLineChart(
                "",//图名字
                "各队列消息到达比例",//横坐标
                "时延比值",//纵坐标
                mDataset,//数据集
                PlotOrientation.VERTICAL,
                true, // 显示图例
                false, // 采用标准生成器
                false);// 是否生成超链接
        CategoryPlot mPlot = (CategoryPlot)mChart.getPlot();
        mPlot.setBackgroundPaint(Color.white);
        mPlot.setRangeGridlinePaint(Color.blue);//背景底部横虚线
        mPlot.setRangeGridlinesVisible(true); // 设置背景底部虚线是否可见
        mPlot.setOutlinePaint(Color.RED);//边界线


        /*
         * Y轴设置
         */
        NumberAxis vn = (NumberAxis) mPlot.getRangeAxis();
        vn.setUpperMargin(0.1);
        vn.setLowerMargin(0.1);
        vn.setAutoRangeMinimumSize(2);//最小跨度
        vn.setLowerBound(0);//最小值显示
        vn.setUpperBound(3.5);
        LineAndShapeRenderer lasp = (LineAndShapeRenderer) mPlot.getRenderer();// 获取显示线条的对象
        lasp.setBaseShapesVisible(true);// 设置拐点是否可见/是否显示拐点
        lasp.setDrawOutlines(true);// 设置拐点不同用不同的形状
        lasp.setUseFillPaint(true);// 设置线条是否被显示填充颜色
        lasp.setBaseFillPaint(Color.BLACK);//// 设置拐点颜色
        lasp.setSeriesLinesVisible(0, false); //设置线条是否可见
        lasp.setSeriesLinesVisible(1, false);
        lasp.setSeriesLinesVisible(2, false);
        lasp.setSeriesLinesVisible(3, false);
        lasp.setSeriesLinesVisible(4, false);
        lasp.setSeriesLinesVisible(5, false);
//        lasp.setSeriesLinesVisible(6, false);
//        lasp.setSeriesLinesVisible(7, false);

        /*
         * X轴
         */
        CategoryAxis domainAxis = mPlot.getDomainAxis();
        domainAxis.setLowerMargin(-0.08);

        domainAxis.setCategoryMargin(0.5);
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 15)); // 设置横轴字体
        domainAxis.setTickLabelFont(new Font("Times New Roman", Font.PLAIN, 15));// 设置坐标轴标尺值字体
        domainAxis.setLowerMargin(0.01);// 左边距 边框距离
        domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
        domainAxis.setMaximumCategoryLabelLines(10);

        try {
            File file = new File("proportionGraph.png");
            ChartUtilities.saveChartAsPNG(file,mChart,1028,600);//把报表保存为文件
        } catch (Exception e) {

        }
        ChartFrame mChartFrame = new ChartFrame("", mChart);
        mChartFrame.pack();
        mChartFrame.setVisible(true);
    }

    public static CategoryDataset GetDataset() {
        DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
        try {

            mDataset.addValue(2.17847, "UPWRR:class2/class1", "10-40-40-10");
            mDataset.addValue(2.15479, "UPWRR:class3/class2", "10-40-40-10");
            mDataset.addValue(2.07818, "UPWRR:class4/class3", "10-40-40-10");

            mDataset.addValue(2.18308, "UPWRR:class2/class1", "25-25-25-25");
            mDataset.addValue(2.10455, "UPWRR:class3/class2", "25-25-25-25");
            mDataset.addValue(2.05284, "UPWRR:class4/class3", "25-25-25-25");

            mDataset.addValue(2.07694, "UPWRR:class2/class1", "40-30-20-10");
            mDataset.addValue(2.13783, "UPWRR:class3/class2", "40-30-20-10");
            mDataset.addValue(2.07958, "UPWRR:class4/class3", "40-30-20-10");

            mDataset.addValue(2.02330, "UPWRR:class2/class1", "50-30-15-5");
            mDataset.addValue(2.03389, "UPWRR:class3/class2", "50-30-15-5");
            mDataset.addValue(2.04170, "UPWRR:class4/class3", "50-30-15-5");

            mDataset.addValue(2.14552, "UPWRR:class2/class1", "60-20-10-10");
            mDataset.addValue(2.14881, "UPWRR:class3/class2", "60-20-10-10");
            mDataset.addValue(1.93758, "UPWRR:class4/class3", "60-20-10-10");

            //--------------------------------------------------------------------------------------------------------------------------

            mDataset.addValue(2.54163, "PFWRR:class2/class1", "10-40-40-10");
            mDataset.addValue(2.42847, "PFWRR:class3/class2", "10-40-40-10");
            mDataset.addValue(2.36363, "PFWRR:class4/class3", "10-40-40-10");

            mDataset.addValue(1.69261, "PFWRR:class2/class1", "25-25-25-25");
            mDataset.addValue(2.38426, "PFWRR:class3/class2", "25-25-25-25");
            mDataset.addValue(2.39175, "PFWRR:class4/class3", "25-25-25-25");

            mDataset.addValue(2.32912, "PFWRR:class2/class1", "40-30-20-10");
            mDataset.addValue(1.79957, "PFWRR:class3/class2", "40-30-20-10");
            mDataset.addValue(2.53415, "PFWRR:class4/class3", "40-30-20-10");

            mDataset.addValue(2.51856, "PFWRR:class2/class1", "50-30-15-5");
            mDataset.addValue(2.36574, "PFWRR:class3/class2", "50-30-15-5");
            mDataset.addValue(2.43095, "PFWRR:class4/class3", "50-30-15-5");

            mDataset.addValue(2.34513, "PFWRR:class2/class1", "60-20-10-10");
            mDataset.addValue(2.40621, "PFWRR:class3/class2", "60-20-10-10");
            mDataset.addValue(2.61951, "PFWRR:class4/class3", "60-20-10-10");

        } catch (Exception e) {

        }
        return mDataset;
    }
}