import java.util.ArrayList;


public class Evaluator {
    private double overshoot;
    private double risingTime;
    private double settlingTime;

    private ArrayList<Simulator.Result> results;

    public Evaluator(ArrayList<Simulator.Result> results) {
        this.results = results;
        evaluate();
    }

    private void evaluate() {
        overshoot = evaluateOvershoot();
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

    public double getOvershoot() {
        return overshoot;
    }
}
