package blocks;

import math.Matrix;

import java.util.ArrayList;

public abstract class Block {

    protected double output;
    protected Matrix outputMatrix;

    protected Block inputBlock = null;
    protected ArrayList<Block> inputBlocks = new ArrayList();

    public void addInputBlock(Block block) {
        inputBlocks.add(block);
    }
    public abstract void tick(double dt);

    public double getOutput() {
        return output;
    }
    public Matrix getOutputMatrix() {
        return outputMatrix;
    }
}
