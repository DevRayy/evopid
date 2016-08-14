import javax.swing.*;
import java.awt.*;

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

        Double[][] A = { { 0.00,    1.00,   0.00,   0.00},
                         { 0.00,   -2.00, 0.00,   0.00},
                         { 0.00,    0.00,   0.00,   1.00},
                         { 0.00,    2.00, 0.981,   0.00} };
        Matrix aMtx = new Matrix(A);
        Double[][] B = { {  0.00},
                         { 10.00},
                         {  0.00},
                         {-10.00} };
        Matrix bMtx = new Matrix(B);
        Double[][] C = { { 0.00, 0.00, 1.00, 0.00 } };
        Matrix cMtx = new Matrix(C);
        Double[][] D = { { 0.00 } };
        Matrix dMtx = new Matrix(D);

        XYSeries series = new XYSeries("XYGraph");
//        XYSeries series2 = new XYSeries("XYGraph2");
//        XYSeries series3 = new XYSeries("pid");
        int maxSamples = 5000;
        double maxTime = 1;
        double dt = maxTime / maxSamples;
        BlockManager manager = new BlockManager();
        Block constant = new Constant(1);
        Block sum = new Sum();
        Block gain = new Gain(-1, sum);
        Block pid = new PIDController(8.68, 13.18, 1.38, gain);
        Block stateSpace = new StateSpace(aMtx, bMtx, cMtx, dMtx, pid);
        Block gain2 = new Gain(-1, stateSpace);
        sum.addInputBlock(constant);
        sum.addInputBlock(gain2);
        manager.addBlock(constant);
        manager.addBlock(sum);
        manager.addBlock(gain);
        manager.addBlock(pid);
        manager.addBlock(stateSpace);
        manager.addBlock(gain2);
//        manager.addBlock(gain);
//        manager.addBlock(pid);

        for (int sample = 1; sample < maxSamples; sample++) {
//            series.add(sample*dt, pid.tick(Math.sin(dt*sample*10), dt));
            manager.tick(dt);
            System.out.println(gain2.getOutput());
            series.add(sample*dt, stateSpace.getOutput());
//            series2.add(sample*dt, integral.getOutput());
//            series3.add(sample*dt, pid.getOutput());
        }
//        series.add(1, pid.tick(1));
//        series.add(2, pid.tick(1));
//        series.add(3, pid.tick(1));
//        series.add(4, pid.tick(1));
//        series.add(5, pid.tick(1));
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
//        dataset.addSeries(series2);
//        dataset.addSeries(series3);
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