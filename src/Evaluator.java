import java.util.ArrayList;


public class Evaluator {
    private double overshoot;
    private double risingTime;
    private double settlingTime;
    private double score;
    public boolean isValid;

    private final double RISING_TIME_LOWER = 0.1;
    private final double RISING_TIME_HIGHER = 0.9;
    private final double SETTLING_MARGIN = 0.01;

    private ArrayList<Simulator.Result> results;

    public Evaluator(ArrayList<Simulator.Result> results) {
        this.results = results;
        evaluate();
    }

    private double calculateScore() {
        //TODO
        return overshoot+risingTime+settlingTime;
    }

    private void evaluate() {
        try {
            for(Simulator.Result r : results)
                if(r.value<0) {
                    isValid=false;
                    return;
            }
            overshoot = evaluateOvershoot();
            risingTime = evaluateRisingTime();
            settlingTime = evalueateSettlingTime();
            score = calculateScore();
            isValid = true;
        } catch (Exception e) {
            isValid = false;
        }
    }

    private double evaluateOvershoot() {
        double overshoot = 0;
        Simulator.Result maxResult = new Simulator.Result(-1, 0);
        for(Simulator.Result r : results) {
            if(r.value>maxResult.value)
                maxResult = r;
        }
        if(maxResult.value > 1)
            overshoot = maxResult.value-1;
        return overshoot;
    }

    private double evaluateRisingTime() {
        Simulator.Result lower = null;
        Simulator.Result higher = null;
        for(Simulator.Result r : results) {
            if (lower == null && r.value > RISING_TIME_LOWER)
                lower = r;
            if (higher == null && r.value > RISING_TIME_HIGHER)
                higher = r;
        }
        return higher.time-lower.time;
    }

    private double evalueateSettlingTime() {
        Simulator.Result settled = null;
        for(Simulator.Result r : results) {
            if(r.value > 1-SETTLING_MARGIN && r.value < 1+SETTLING_MARGIN && settled == null)
                settled = r;
            else if (r.value < 1-SETTLING_MARGIN || r.value > 1+SETTLING_MARGIN)
                settled=null;
        }

        if(settled!=null)
            return settled.time;
        else return Double.MAX_VALUE;
    }

    public double getOvershoot() {
        return overshoot;
    }

    public double getRisingTime() {
        return risingTime;
    }

    public double getSettlingTime() {
        return settlingTime;
    }

    public double getScore() {
        return score;
    }
}
