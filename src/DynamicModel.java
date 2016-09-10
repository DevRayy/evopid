import blocks.*;
import math.Matrix;

import java.util.HashMap;

public class DynamicModel {
    private Matrix Amtx, Bmtx, Cmtx, Dmtx;
    private double P, I, D;
    private BlockManager manager;
    private Block constant, sum, gain, pid, stateSpace, gain2;

    public DynamicModel(HashMap<String, Double[][]> model, HashMap<String, Double> pid) {
        Amtx = new Matrix(model.get("A"));
        Bmtx = new Matrix(model.get("B"));
        Cmtx = new Matrix(model.get("C"));
        Dmtx = new Matrix(model.get("D"));
        P = pid.get("P");
        I = pid.get("I");
        D = pid.get("D");

        buildModel();
    }

    private void buildModel() {
        manager = new BlockManager();
        constant = new Constant(1);
        sum = new Sum();
        gain = new Gain(1, sum);
        pid = new PIDController(P, I, D, gain);
        stateSpace = new StateSpace(Amtx, Bmtx, Cmtx, Dmtx, pid);
        gain2 = new Gain(-1, stateSpace);
        sum.addInputBlock(constant);
        sum.addInputBlock(gain2);
        manager.addBlock(constant);
        manager.addBlock(sum);
        manager.addBlock(gain);
        manager.addBlock(pid);
        manager.addBlock(stateSpace);
        manager.addBlock(gain2);
    }

    public void tick(double dt) {
        manager.tick(dt);
    }

    public double getOutput() {
        return stateSpace.getOutput();
    }



}
