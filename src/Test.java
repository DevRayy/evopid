import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import blocks.*;
import math.Matrix;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

class Test {

    private JFrame frame;
    private PIDController pid;

    public Test() {

//        pid = new PIDController(1, 2, 0.5, );

        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel aPanel = new JPanel();
        aPanel.setPreferredSize(new Dimension(600, 300));


        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new Dimension(600, 300));

        frame.getContentPane().add(chartPanel);

        frame.pack();
        frame.setVisible(true);
    }

    private JFreeChart createChart() {
        HashMap<String, Double[][]> modelMap = new HashMap<>();
        Double[][] A = { { 0.00,    1.00,   0.00,   0.00},
                         { 0.00,   -2.00, 0.00,   0.00},
                         { 0.00,    0.00,   0.00,   1.00},
                         { 0.00,    2.00, 0.981,   0.00} };
        modelMap.put("A", A);
        Double[][] B = { {  0.00},
                         { 10.00},
                         {  0.00},
                         {-10.00} };
        modelMap.put("B", B);
        Double[][] C = { { 0.00, 0.00, 1.00, 0.00 } };
        modelMap.put("C", C);
        Double[][] D = { { 0.00 } };
        modelMap.put("D", D);
        HashMap<String, Double> pidMap = new HashMap<>();
        pidMap.put("P", new Double(50));
        pidMap.put("I", new Double(10));
        pidMap.put("D", new Double(0.38));
        int maxSamples = 50000;
        double maxTime = 5;
        DynamicModel model = new DynamicModel(modelMap, pidMap);
        Simulator simulator = new Simulator(model);
        simulator.simulate(maxSamples, maxTime);
        XYSeries series = simulator.getXYSeries();

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        JFreeChart timechart = ChartFactory.createXYLineChart(
                "XY Chart", // Title
                "x",         // X-axis Label
                "y",       // Y-axis Label
                dataset,        // Dataset
                PlotOrientation.VERTICAL,
                true,          // Show legend
                true,          // Use tooltips
                false          // Generate URLs
        );

        return timechart;

    }

    public static void main(String[] args) {
        new Test();
    }

}