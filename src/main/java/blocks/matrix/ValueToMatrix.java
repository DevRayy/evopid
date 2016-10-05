package blocks.matrix;

import blocks.Block;
import math.Matrix;

public class ValueToMatrix extends Block {

    public ValueToMatrix(Block input) {
        this.inputBlock = input;
        outputMatrix = new Matrix(1, 1);
    }

    public void tick(double dt) {
        outputMatrix.val[0][0] = inputBlock.getOutput();
    }
}
