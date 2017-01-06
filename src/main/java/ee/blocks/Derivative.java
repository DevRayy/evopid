package ee.blocks;

public class Derivative extends Block {

    private double prevInput = 0;

    public Derivative(Block inputBlock) {
        this.inputBlock = inputBlock;
    }

    public void tick(double dt) {
        output = (inputBlock.getOutput() - prevInput) / dt;
        prevInput = inputBlock.getOutput();
    }
}
