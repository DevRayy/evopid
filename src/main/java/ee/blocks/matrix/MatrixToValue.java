package ee.blocks.matrix;

import ee.blocks.Block;

public class MatrixToValue extends Block {

    public MatrixToValue(Block input) {
        this.inputBlock = input;
    }

    public void tick(double dt) {
        output = inputBlock.getOutputMatrix().val[0][0];
    }
}
