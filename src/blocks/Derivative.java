package blocks;

public class Derivative extends Block {

    private double prevInput = 0;

    public Derivative(Block inputBlock) {
        this.inputBlock = inputBlock;
    }

    public void tick(double dt) {

//        System.out.println(inputBlock.getOutput());
        output = (inputBlock.getOutput() - prevInput) / dt;
        System.out.println(output);
        prevInput = inputBlock.getOutput();
    }
}
