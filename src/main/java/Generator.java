import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    private HashMap<String, Double[][]> modelMap;
    private int maxSamples;
    private double maxTime;

    private double minGain = -50;
    private double maxGain = 50;
    private double minP = 0;
    private double maxP = 1;
    private double minI = 0;
    private double maxI = 1;
    private double minD = 0;
    private double maxD = 0.1;

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
                double initialGain = ThreadLocalRandom.current().nextDouble(minGain, maxGain);
                pidMap.put("P", ThreadLocalRandom.current().nextDouble(minP, maxP) * initialGain);
                pidMap.put("I", ThreadLocalRandom.current().nextDouble(minI, maxI) * initialGain);
                pidMap.put("D", ThreadLocalRandom.current().nextDouble(minD, maxD) * initialGain);
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

            minGain = (minGain + initialGain) / 2;
            System.out.println("\t\tminGain = " + minGain);
            maxGain = (maxGain + initialGain) / 2;
            System.out.println("\t\tmaxGain = " + maxGain);
            minP = (minP + currP) / 2;
            maxP = (maxP + currP) / 2;
            minI = (minI + currI) / 2;
            maxI = (maxI + currI) / 2;
            minD = (minD + currD) / 2;
            maxD = (maxD + currD) / 2;
        } catch (NullPointerException e) {
            //best==null
            //then do nothing and generate once more
        }
    }

    private void printEvaluatorStats(Evaluator evaluator) {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("Overshoot: " + df.format(evaluator.getOvershoot() * 100) + "%");
        System.out.println("Rising time: " + evaluator.getRisingTime() + " seconds");
        System.out.println("Settling time: " + evaluator.getSettlingTime() + " seconds");
        System.out.println("SCORE: " + evaluator.getScore());
    }

}
