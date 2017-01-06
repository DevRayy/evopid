package ee.blocks.matrix;

import ee.blocks.Block;
import ee.math.Matrix;

public class MatrixSum extends Block {

    public MatrixSum(Block input) {
        outputMatrix = new Matrix(input.getOutputMatrix().val.length,
                input.getOutputMatrix().val[0].length);
        addInputBlock(input);
    }

    public void tick (double dt) {
        outputMatrix.clear();
        for(Block block : inputBlocks) {
            outputMatrix = Matrix.add(outputMatrix, block.getOutputMatrix());
        }
    }
}
