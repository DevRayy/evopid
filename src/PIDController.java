public class PIDController {
    public double P;
    public double I;
    public double D;

//    private double input = 0;
    public double output = 0;
    private double prevInput = 0;
    private double integral = 0;
    private double derivative = 0;


    public PIDController(double P, double I, double D) {
        this.P = P;
        this.I = I;
        this.D = D;
    }

    public double tick(double input, double dt) {
//        double dt = 1; //change later
        integral = integral + input*dt;
        derivative = (input - prevInput) / dt;
        prevInput = input;
        output = (P * input) + (I * integral) + (D * derivative);
        return output;
    }
}
