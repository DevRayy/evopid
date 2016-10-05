import blocks.Block;
import blocks.Derivative;
import blocks.Gain;
import blocks.Integral;

public class PIDController extends Block {
    public double P;
    public double I;
    public double D;

//    private double input = 0;
    private Block integral;
    private Block derivative;

    public PIDController(double P, double I, double D, Block input) {
        this.P = P;
        this.I = I;
        this.D = D;
        this.inputBlock = input;
        integral = new Integral(input);
        derivative = new Derivative(input);
    }

    public void tick(double dt) {
//        double dt = 1; //change later
//        integral = integral + input*dt;
//        derivative = (input - prevInput) / dt;
//        prevInput = input;
//        output = (P * input) + (I * integral) + (D * derivative);
        integral.tick(dt);
        derivative.tick(dt);
        output = (P*inputBlock.getOutput()) + (I*integral.getOutput()) + (D*derivative.getOutput());
    }
}
