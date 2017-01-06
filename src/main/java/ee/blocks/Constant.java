package ee.blocks;

public class Constant extends Block {

    private double value = 0;

    public Constant() {

    }

    public Constant(double value) {
        this.value = value;
    }

    public void tick(double dt) {
        output = value;
    }
}
