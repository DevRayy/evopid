package blocks.matrix;

import blocks.Block;
import math.Matrix;

import java.util.ArrayList;

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
