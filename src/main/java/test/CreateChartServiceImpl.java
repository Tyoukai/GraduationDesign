package test;


import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.io.File;

public class CreateChartServiceImpl {
    public static void data(String title,String[] a,String[] b)
    {
        DefaultXYDataset   xydataset = new DefaultXYDataset ();

        double[][] data=new double[2][a.length];
        double[][] data1 = new double[2][3];
        data1[0][0] = 1;
        data1[1][0] = 1;
        data1[0][1] = 1;
        data1[1][1] = 2;
        data1[0][2] = 1;
        data1[1][2] = 3;

        for(int i=0;i<a.length;i++)
        {
            data[0][i]=Double.parseDouble(a[i]);
            data[1][i]=Double.parseDouble(b[i]);
        }
        xydataset.addSeries("25-25-25-25", data);
        xydataset.addSeries("10-40-40-10", data1);

        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);



        final JFreeChart chart =ChartFactory.createScatterPlot(
                "比例图",
                "队列负载分布",
                "紧急指数的比值",
                 xydataset,
                 PlotOrientation.VERTICAL,
                true,
                false,
                false);

        ChartFrame frame = new ChartFrame(title,chart);

        try {
            File file = new File("graph11.png");
            ChartUtilities.saveChartAsPNG(file,chart,1028,600);//把报表保存为文件
        } catch (Exception e) {

        }

        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        String[] a ={"7","2","4"};
        String[] b ={"5","2","4"};
        CreateChartServiceImpl.data("title", a, b);
    }

}
