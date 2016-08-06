import javax.swing.*;
import java.awt.*;

import blocks.*;
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

        pid = new PIDController(1, 2, 0.5);

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

        XYSeries series = new XYSeries("XYGraph");
        XYSeries series2 = new XYSeries("XYGraph2");
        XYSeries series3 = new XYSeries("sum");
        series.add(0, pid.tick(0, 0));
        series2.add(0, pid.tick(0, 0));
        int maxSamples = 5000;
        double maxTime = 1;
        double dt = maxTime / maxSamples;
        BlockManager manager = new BlockManager();
        Block constant = new Constant(1);
        Block gain = new Gain(5, constant);
        Block integral = new Integral(gain);
        Block derivative = new Derivative(integral);
        manager.addBlock(constant);
        manager.addBlock(gain);
        manager.addBlock(integral);
        manager.addBlock(derivative);

        for (int sample = 1; sample < maxSamples; sample++) {
//            series.add(sample*dt, pid.tick(Math.sin(dt*sample*10), dt));
            manager.tick(dt);
            series.add(sample*dt, gain.getOutput());
            series2.add(sample*dt, integral.getOutput());
            series3.add(sample*dt, derivative.getOutput());
        }
//        series.add(1, pid.tick(1));
//        series.add(2, pid.tick(1));
//        series.add(3, pid.tick(1));
//        series.add(4, pid.tick(1));
//        series.add(5, pid.tick(1));
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
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