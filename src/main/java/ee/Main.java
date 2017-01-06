package ee;

public class Main {
    public static void main(String[] args) {

//        new Evopid();
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
