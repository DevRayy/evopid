package ee;

public class Utils {

    public static void showErrorMessage(String message) {
        ErrorWindow errorWindow = new ErrorWindow(message);
        errorWindow.pack();
        errorWindow.setVisible(true);
        Main.thread.stop();
    }
}
