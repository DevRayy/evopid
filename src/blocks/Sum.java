package blocks;

import java.util.ArrayList;

public class Sum extends Block {

    private ArrayList<Block> inputBlocks = new ArrayList<>();

    public Sum() {}

    public void tick(double dt) {
        output = 0;
        for(Block block : inputBlocks) {
            System.out.println(block.getOutput());
            output = output + block.getOutput();
        }
    }
}
