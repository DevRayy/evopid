package ee.blocks;

public class Sum extends Block {

    public Sum() {}

    public void tick(double dt) {
        output = 0;
        for(Block block : inputBlocks) {
            output = output + block.getOutput();
        }
    }
}
