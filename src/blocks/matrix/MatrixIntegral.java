package blocks.matrix;

import blocks.Block;
import math.Matrix;

public class MatrixIntegral extends Block {

    public MatrixIntegral (Block inputBlock) {
        this.inputBlock = inputBlock;
        outputMatrix = new Matrix(inputBlock.getOutputMatrix().val.length,
                inputBlock.getOutputMatrix().val[0].length);
    }

    public MatrixIntegral (Matrix initialValue, Block inputBlock) {
        this.inputBlock = inputBlock;
        outputMatrix = initialValue;
    }

    public void tick(double dt) {
        for (int i = 0; i < outputMatrix.val.length; i++) {
            for (int j = 0; j < outputMatrix.val[0].length; j++) {
                outputMatrix.val[i][j] = outputMatrix.val[i][j] + inputBlock.getOutputMatrix().val[i][j] * dt;
            }
        }
    }
}
