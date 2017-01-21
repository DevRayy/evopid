package ee;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

class Evopid implements Runnable {

    private JFrame frame;
    GuiRoot guiRoot;

    public Evopid() {
        JFrame frame = new JFrame("Evopid");
        guiRoot = new GuiRoot(this);
        frame.setContentPane(guiRoot.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void run() {
        validateSettings();

        XYSeries bestPIDResults = doSimulation();

        frame = new JFrame("Evopid");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel aPanel = new JPanel();
        aPanel.setPreferredSize(new Dimension(600, 300));
        XYSeries constant = new XYSeries("Step excitation");
        constant.add(0, 1);
        constant.add(SettingsContainer.get().getTime(), 1);
        ChartPanel chartPanel = new ChartPanel(createChart(bestPIDResults, constant));
        chartPanel.setPreferredSize(new Dimension(600, 300));
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public XYSeries doSimulation() {
        HashMap<String, Double[][]> modelMap = new HashMap();
        modelMap.put("A", SettingsContainer.get().getA());
        modelMap.put("B", SettingsContainer.get().getB());
        modelMap.put("C", SettingsContainer.get().getC());
        modelMap.put("D", SettingsContainer.get().getD());
        HashMap<String, Double> pidMap = new HashMap();
        int maxSamples = SettingsContainer.get().getSamples();
        double maxTime = SettingsContainer.get().getTime();

        try {
            Generator generator = new Generator(modelMap, maxSamples, maxTime, guiRoot);
            pidMap = generator.generate(SettingsContainer.get().getPopulations(), SettingsContainer.get().getSpecimens());
            DynamicModel model = new DynamicModel(modelMap, pidMap);
            Simulator simulator = new Simulator(model);
            simulator.simulate(maxSamples, maxTime);
            ArrayList<Simulator.Result> results = simulator.getResults();
            Evaluator evaluator = new Evaluator(results);
            System.out.println("\n\n===== BEST PID FOUND =====");
            generator.printEvaluatorStats(evaluator, pidMap);
            return simulator.getXYSeries();
        } catch (IllegalArgumentException e) {
            Utils.showErrorMessage(e.getMessage());
            throw e;
        }
    }

    private JFreeChart createChart(XYSeries series, XYSeries constant) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(constant);
        JFreeChart timechart = ChartFactory.createXYLineChart(
                "Step response", // Title
                "Time (s)",         // X-axis Label
                "Output",       // Y-axis Label
                dataset,        // Dataset
                PlotOrientation.VERTICAL,
                true,          // Show legend
                true,          // Use tooltips
                false          // Generate URLs
        );
        return timechart;
    }

    private void validateSettings() {
        SettingsContainer sc = SettingsContainer.get();
        try {
            assert sc.getSamples() > 0;
            assert sc.getTime() > 0;
            assert sc.getPopulations() > 0;
            assert sc.getSpecimens() > 0;
            assert sc.getRisingTimeLow() >= 0;
            assert sc.getRisingTimeLow() < 100;
            assert sc.getRisingTimeHigh() <= 100;
            assert sc.getRisingTimeHigh() > 0;
            assert sc.getRisingTimeLow() < sc.getRisingTimeHigh();
            assert sc.getSettlingMargin() > 0;
            assert sc.getSettlingMargin() < 100;
            assert sc.getOvershoot() >= 0;
            assert sc.getRisingTime() >= 0;
            assert sc.getSettlingTime() >= 0;
            if(sc.isLimitOvershootEnabled())
                assert sc.getLimitOvershootValue() >=0;

        } catch (AssertionError e) {
            Utils.showErrorMessage("Invalid settings");
        }
    }

}