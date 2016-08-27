import org.jfree.data.xy.XYSeries;

import java.util.ArrayList;

public class Simulator {
    private DynamicModel model;
    private int samples;
    private double time;

    private ArrayList<Result> results = new ArrayList<>();
    private XYSeries xySeries = new XYSeries("Model output");

    public Simulator(DynamicModel model) {
        this.model = model;
    }

    public ArrayList<Result> simulate(int samples, double time) {
        double dt = time/samples;
        results.clear();
        xySeries.clear();
        for(int s=1; s < samples; s++) {
            model.tick(dt);
            xySeries.add(s*dt, model.getOutput());
            results.add(new Result(s*dt, model.getOutput()));
        }
        return results;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public XYSeries getXYSeries() {
        return xySeries;
    }



    public static class Result {
        double time;
        double value;

        public Result (double time, double value) {
            this.time = time;
            this.value = value;
        }
    }
}
