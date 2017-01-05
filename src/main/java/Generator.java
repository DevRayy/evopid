import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    private HashMap<String, Double[][]> modelMap;
    private int maxSamples;
    private double maxTime;

    private final double FACTOR = 2;
    private double MIN_GAIN = -50;
    private double MAX_GAIN = 50;
    private double MIN_P = 0;
    private double MAX_P = 1;
    private double MIN_I = 0;
    private double MAX_I = 1;
    private double MIN_D = 0;
    private double MAX_D = 0.1;

    public Generator(HashMap<String, Double[][]> modelMap, int maxSamples, double maxTime) {
        this.modelMap = modelMap;
        this.maxSamples = maxSamples;
        this.maxTime = maxTime;
    }

    public HashMap<String, Double> generate(int populations, int specimens) {
        HashMap<String, Double> best = null;
        double bestScore = Double.MAX_VALUE;
        double bestInitialGain = 0;

        for(int j=0; j<populations; j++) {
            setNewMinMaxValues(best, bestInitialGain);
            for (int i = 0; i < specimens; i++) {
                System.out.println("\n===== Generating PID " + (i + 1) + "/" + (j+1) + " =====");
                HashMap<String, Double> pidMap = new HashMap();
                double initialGain = ThreadLocalRandom.current().nextDouble(MIN_GAIN, MAX_GAIN);
                pidMap.put("P", ThreadLocalRandom.current().nextDouble(MIN_P, MAX_P) * initialGain);
                pidMap.put("I", ThreadLocalRandom.current().nextDouble(MIN_I, MAX_I) * initialGain);
                pidMap.put("D", ThreadLocalRandom.current().nextDouble(MIN_D, MAX_D) * initialGain);
                System.out.println(pidMap);

                DynamicModel model = new DynamicModel(modelMap, pidMap);
                Simulator simulator = new Simulator(model);
                simulator.simulate(maxSamples, maxTime);
                ArrayList<Simulator.Result> results = simulator.getResults();
                Evaluator evaluator = new Evaluator(results);

                if (evaluator.isValid) {
                    printEvaluatorStats(evaluator);
                    if (evaluator.getScore() < bestScore) {
                        bestScore = evaluator.getScore();
                        best = pidMap;
                        bestInitialGain = initialGain;
                    }
                } else {
                    System.out.println("PID invalid");
                }
            }
        }
        return best;

    }

    private void setNewMinMaxValues(HashMap<String, Double> best, double initialGain) {
        try {
            double currP = best.get("P") / initialGain;
            double currI = best.get("I") / initialGain;
            double currD = best.get("D") / initialGain;

            MIN_GAIN = (MIN_GAIN + initialGain) / FACTOR;
            System.out.println("\t\tMIN_GAIN = " + MIN_GAIN);
            MAX_GAIN = (MAX_GAIN + initialGain) / FACTOR;
            System.out.println("\t\tMAX_GAIN = " + MAX_GAIN);
            MIN_P = (MIN_P + currP) / FACTOR;
            MAX_P = (MAX_P + currP) / FACTOR;
            MIN_I = (MIN_I + currI) / FACTOR;
            MAX_I = (MAX_I + currI) / FACTOR;
            MIN_D = (MIN_D + currD) / FACTOR;
            MAX_D = (MAX_D + currD) / FACTOR;
        } catch (NullPointerException e) {
            //best==null
            //then do nothing and generate once more
        }
    }

    public void printEvaluatorStats(Evaluator evaluator) {
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Overshoot: " + df.format(evaluator.getOvershoot() * 100) + "%");
        System.out.println("Rising time: " + df.format(evaluator.getRisingTime()) + " seconds");
        System.out.println("Settling time: " + df.format(evaluator.getSettlingTime()) + " seconds");
        System.out.println("SCORE: " + df.format(evaluator.getScore()));
    }

}
