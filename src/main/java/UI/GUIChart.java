package UI;

import Simulator.ModelConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

public class GUIChart {


    public static DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    public static XYSeriesCollection lineDataset = new XYSeriesCollection();
    public static int graphNum = 0;

    static JFreeChart createChart() {

        String chartName = null;
        //var dataset = new XYSeriesCollection();

        //if (dataset == null){dataset = createDefaultDataset();}
        //if (chartName == null){chartName = "default chart name";}
        switch (ModelConstants.CHART) {
            case (1):
                chartName = "Voter Opinion distribution";
                return initChart(chartName, dataset);
            case (2):
                chartName = "Voter Opinion variance";
                break;
            case (3):
                chartName = "Voter Opinion Excess Kurtosis";
                break;
            case (4):
                chartName = "Voter Opinion ER index";
                break;
        }
        graphNum++;
        lineDataset.addSeries(new XYSeries("graph " + graphNum));
        lineDataset.getSeries("graph " + graphNum).add(0, 1);
        return initChart(chartName, lineDataset);

    }

    static JFreeChart initChart(String chartName, CategoryDataset dataset){
        JFreeChart chart = ChartFactory.createBarChart(
                        chartName,
                        null /* x-axis label*/,
                        null /* y-axis label */,
                        dataset);

        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }

    static JFreeChart initChart(String chartName, XYSeriesCollection dataset){
        JFreeChart chart = ChartFactory.createXYLineChart(
                chartName,
                null,
                null,
                dataset);

        chart.setBackgroundPaint(Color.WHITE);
        //CategoryPlot plot = (CategoryPlot) chart.getPlot();
        //NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }

    static void addData(CategoryDataset data){

    }

    static CategoryDataset createDefaultDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(7445, "JFreeSVG", "Warm-up");
        dataset.addValue(24448, "Batik", "Warm-up");
        dataset.addValue(4297, "JFreeSVG", "Test");
        dataset.addValue(21022, "Batik", "Test");
        return dataset;
    }



}
