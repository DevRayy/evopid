import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

class Test {

    private JFrame frame;
    private PIDController pid;

    public Test() {

//        pid = new PIDController(1, 2, 0.5, );

        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel aPanel = new JPanel();
        aPanel.setPreferredSize(new Dimension(600, 300));


        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new Dimension(600, 300));

        frame.getContentPane().add(chartPanel);

        frame.pack();
        frame.setVisible(true);
    }

    public JFreeChart createChart() {
        HashMap<String, Double[][]> modelMap = new HashMap();
        Double[][] A = { { 0.00,    1.00,   0.00,   0.00},
                         { 0.00,   -2.00, 0.00,   0.00},
                         { 0.00,    0.00,   0.00,   1.00},
                         { 0.00,    2.00, 0.981,   0.00} };
        modelMap.put("A", SettingsContainer.get().getA());
        Double[][] B = { {  0.00},
                         { 10.00},
                         {  0.00},
                         {-10.00} };
        modelMap.put("B", SettingsContainer.get().getB());
        Double[][] C = { { 0.00, 0.00, 1.00, 0.00 } };
        modelMap.put("C", SettingsContainer.get().getC());
        Double[][] D = { { 0.00 } };
        modelMap.put("D", SettingsContainer.get().getD());
        HashMap<String, Double> pidMap = new HashMap();
//        pidMap.put("P", new Double(50));
//        pidMap.put("I", new Double(1));
//        pidMap.put("D", new Double(1.38));
        int maxSamples = SettingsContainer.get().getSamples();
        double maxTime = SettingsContainer.get().getTime();

        Generator generator = new Generator(modelMap, maxSamples, maxTime);
        pidMap = generator.generate(SettingsContainer.get().getPopulations(), SettingsContainer.get().getSpecimens());

        DynamicModel model = new DynamicModel(modelMap, pidMap);
        Simulator simulator = new Simulator(model);
        simulator.simulate(maxSamples, maxTime);
        ArrayList<Simulator.Result> results = simulator.getResults();
        Evaluator evaluator = new Evaluator(results);
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("\n\n===== BEST PID FOUND =====");
        System.out.println(pidMap);
        System.out.println("Overshoot: " + df.format(evaluator.getOvershoot()*100) + "%");
        System.out.println("Rising time: " + evaluator.getRisingTime() + " seconds");
        System.out.println("Settling time: " + evaluator.getSettlingTime() + " seconds");
        System.out.println("SCORE: " + evaluator.getScore());
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
        JFrame frame = new JFrame("Evopid");
        frame.setContentPane(new GuiRoot().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}