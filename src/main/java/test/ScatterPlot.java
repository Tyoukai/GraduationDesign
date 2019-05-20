package test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;

public class ScatterPlot {
    public static void main(String[] args) {

        DefaultXYDataset xydataset = new DefaultXYDataset();


        JFreeChart chart = ChartFactory.createScatterPlot(
                "Driving record",
                "longitude",
                "latitude",
                xydataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
    }
}
