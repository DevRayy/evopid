package ee.blocks.matrix;

import ee.blocks.Block;
import ee.math.Matrix;

public class MatrixMultiply extends Block {

    public Matrix matrix;

    public MatrixMultiply(Matrix matrix, Block input) {
        this.inputBlock = input;
        this.matrix = matrix;
        outputMatrix = new Matrix(matrix.val.length,
                                  input.getOutputMatrix().val[0].length);
    }

    public void tick(double dt) {
        outputMatrix = Matrix.multiply(matrix, inputBlock.getOutputMatrix());
    }

}
