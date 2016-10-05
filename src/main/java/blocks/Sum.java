package blocks;

import java.util.ArrayList;

public class Sum extends Block {

    public Sum() {}

    public void tick(double dt) {
        output = 0;
        for(Block block : inputBlocks) {
            output = output + block.getOutput();
        }
    }
}
