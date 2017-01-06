package ee.blocks;


import java.util.ArrayList;

public class BlockManager {
    private ArrayList<Block> blocks = new ArrayList();

    public BlockManager() {

    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void tick(double dt) {
        for(Block block : blocks)
            block.tick(dt);
    }
}
