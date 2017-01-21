package ee;

public class Main {

    public static Thread thread;

    public static void main(String[] args) {
        try {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            loader.setDefaultAssertionStatus(true);
            Class<?> c = loader.loadClass("ee.Evopid");
            Evopid myObj = (Evopid) c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
