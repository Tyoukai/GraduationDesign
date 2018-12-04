package algorithm1;

import java.awt.Color;
import java.awt.Font;
import java.io.File;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
public class GraphShow {

    public static void main(String[] args) {
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);
        CategoryDataset mDataset = GetDataset();
        JFreeChart mChart = ChartFactory.createLineChart(
                "折线图",//图名字
                "邻居用户数",//横坐标
                "RMSE",//纵坐标
                mDataset,//数据集
                PlotOrientation.VERTICAL,
                true, // 显示图例
                true, // 采用标准生成器
                false);// 是否生成超链接
//.LIGHT_GRAY
        CategoryPlot mPlot = (CategoryPlot)mChart.getPlot();
        mPlot.setBackgroundPaint(Color.white);
        mPlot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
        mPlot.setOutlinePaint(Color.RED);//边界线


        /*
         * Y轴设置
         */
        NumberAxis vn = (NumberAxis) mPlot.getRangeAxis();
// DecimalFormat df = new DecimalFormat("#0.00");
// vn.setNumberFormatOverride(df);	// 数据轴数据标签的显示格式
        vn.setUpperMargin(0.1);
        vn.setLowerMargin(0.1);
        vn.setAutoRangeMinimumSize(0.01);//最小跨度
        vn.setLowerBound(0.70);//最小值显示
        vn.setUpperBound(1.10);
        LineAndShapeRenderer lasp = (LineAndShapeRenderer) mPlot.getRenderer();// 获取显示线条的对象
        lasp.setBaseShapesVisible(true);// 设置拐点是否可见/是否显示拐点
        lasp.setDrawOutlines(true);// 设置拐点不同用不同的形状
        lasp.setUseFillPaint(true);// 设置线条是否被显示填充颜色
        lasp.setBaseFillPaint(Color.BLACK);//// 设置拐点颜色

        /*
         * X轴
         */
        CategoryAxis domainAxis = mPlot.getDomainAxis();
//domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        domainAxis.setLowerMargin(-0.08);

        domainAxis.setCategoryMargin(0.5);;
        System.out.println(domainAxis.getCategoryMargin());;


        domainAxis.setLabelFont(new Font("宋书", Font.PLAIN, 15)); // 设置横轴字体
        domainAxis.setTickLabelFont(new Font("宋书", Font.PLAIN, 15));// 设置坐标轴标尺值字体
        domainAxis.setLowerMargin(0.01);// 左边距 边框距离
        domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
        domainAxis.setMaximumCategoryLabelLines(10);
 //domainAxis.setMaximumCategoryLabelLinesgoryLabelPositions(CategoryLabelPositions.DOWN_45);// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
// domainAxis.setm

        try {
            File file = new File("c:/student.png");
            ChartUtilities.saveChartAsPNG(file,mChart,400,300);//把报表保存为文件
        }catch (Exception e) {
            String s = e.getLocalizedMessage();
            s = e.getMessage();
            s = e.toString();
        }
        ChartFrame mChartFrame = new ChartFrame("折线图", mChart);
        mChartFrame.pack();
        mChartFrame.setVisible(true);
    }
    public static CategoryDataset GetDataset()
    {
        DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
        mDataset.addValue(1.10, "First", "5");
        mDataset.addValue(1.05, "First", "10");
        mDataset.addValue(1.00, "First", "15");
        mDataset.addValue(0.95, "First", "20");
        mDataset.addValue(0.90, "First", "25");

        mDataset.addValue(1.05, "Second", "5");
        mDataset.addValue(1.00, "Second", "10");
        mDataset.addValue(0.96, "Second", "15");
        mDataset.addValue(0.91, "Second", "20");
        mDataset.addValue(0.88, "Second", "25");

        mDataset.addValue(1.02, "Third", "5");
        mDataset.addValue(0.90, "Third", "10");
        mDataset.addValue(0.88, "Third", "15");
        mDataset.addValue(0.85, "Third", "20");
        mDataset.addValue(0.7, "Third", "25");
        return mDataset;
    }

}