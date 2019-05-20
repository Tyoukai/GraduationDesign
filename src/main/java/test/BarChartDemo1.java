package test;

import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
//import jxl.biff.drawing.Chart;

import java.awt.*;


public class BarChartDemo1 extends ApplicationFrame {
    public BarChartDemo1(String title){
        super(title);
        JFreeChart chart=this.createBarChart(this.createDataset());
        ChartPanel chartPanel=new ChartPanel(chart);


        this.getContentPane().add(chartPanel);
        this.setSize(new Dimension(800,600));
        this.setVisible(true);

    }

    private CategoryDataset createDataset(){
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        dataset.addValue(100,"completed","2010 June");
        dataset.addValue(80,"completed","2010 July");
        dataset.addValue(50,"completed","2010 August");
        dataset.addValue(70,"completed","2010 September");
        return dataset;
    }

    private JFreeChart createBarChart(CategoryDataset dataset){
        String chartTitle="MyFirstBarChart";
        String axisXLabel="category";
        String axisYLabel="Value";
        JFreeChart chart= ChartFactory.createBarChart(chartTitle,axisXLabel,axisYLabel,
                dataset, PlotOrientation.VERTICAL,true,true,false);

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer3D renderer = new BarRenderer3D();
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setItemLabelsVisible(true);
        plot.setRenderer(renderer);

        return chart;
    }

    public static void main(String[] args) {
        new BarChartDemo1("demo1");
    }
}