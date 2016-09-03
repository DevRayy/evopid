import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    private HashMap<String, Double[][]> modelMap;
    private int maxSamples;
    private double maxTime;

    public Generator(HashMap<String, Double[][]> modelMap, int maxSamples, double maxTime) {
        this.modelMap = modelMap;
        this.maxSamples = maxSamples;
        this.maxTime = maxTime;
    }

    public HashMap<String, Double> generate(int quantity) {
        HashMap<String, Double> best = null;
        double bestScore = Double.MAX_VALUE;
        for(int i=0; i<quantity; i++) {
            System.out.println("\n===== Generating PID " + (i+1) + "/" + quantity + " =====");
            HashMap<String, Double> pidMap = new HashMap<>();
            double initialGain = ThreadLocalRandom.current().nextDouble(-50, 50);
            pidMap.put("P", ThreadLocalRandom.current().nextDouble(0, 1)*initialGain);
            pidMap.put("I", ThreadLocalRandom.current().nextDouble(0, 1)*initialGain);
            pidMap.put("D", ThreadLocalRandom.current().nextDouble(0, 0.1)*initialGain);
            System.out.println(pidMap);

            DynamicModel model = new DynamicModel(modelMap, pidMap);
            Simulator simulator = new Simulator(model);
            simulator.simulate(maxSamples, maxTime);
            ArrayList<Simulator.Result> results = simulator.getResults();
            Evaluator evaluator = new Evaluator(results);

            if(evaluator.isValid) {
                DecimalFormat df = new DecimalFormat("#.00");
                System.out.println("Overshoot: " + df.format(evaluator.getOvershoot() * 100) + "%");
                System.out.println("Rising time: " + evaluator.getRisingTime() + " seconds");
                System.out.println("Settling time: " + evaluator.getSettlingTime() + " seconds");
                System.out.println("SCORE: " + evaluator.getScore());
                if(evaluator.getScore() < bestScore) {
                    bestScore = evaluator.getScore();
                    best = pidMap;
                }
            } else {
                System.out.println("PID invalid");
            }


        }
        return best;

    }

}
