package blocks;

public class Gain extends Block {

    private double value = 0;

    public Gain(double value, Block inputBlock) {

        this.value = value;
        this.inputBlock = inputBlock;
    }

    public void tick(double dt) {
        double input = inputBlock.getOutput();
        output = input*value;
    }
}
