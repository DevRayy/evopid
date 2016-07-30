import javax.swing.*;
import java.awt.*;
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
        series.add(0, pid.tick(0, 0));
        int maxSamples = 5000;
        double maxTime = 0.2;
        double dt = maxTime / maxSamples;
        for (int sample = 1; sample < maxSamples; sample++) {
            series.add(sample*dt, pid.tick(Math.sin(dt*sample*10), dt));
        }
//        series.add(1, pid.tick(1));
//        series.add(2, pid.tick(1));
//        series.add(3, pid.tick(1));
//        series.add(4, pid.tick(1));
//        series.add(5, pid.tick(1));
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