package ee.blocks;

public class Integral extends Block {

    public Integral(double initialValue, Block inputBlock) {

        this.output = initialValue;
        this.inputBlock = inputBlock;
    }

    public Integral(Block inputBlock) {
        this.inputBlock = inputBlock;
    }

    public void tick(double dt) {
        output = output + inputBlock.getOutput() * dt;
    }
}
