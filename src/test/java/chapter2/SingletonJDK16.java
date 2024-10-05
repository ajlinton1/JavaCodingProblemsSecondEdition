package chapter2;

public class SingletonJDK16 {
    private SingletonJDK16() {
        // private constructor to prevent instantiation
    }

    private static class Holder {
        static {
            System.out.println("Holder class loaded");
        }
        private static final SingletonJDK16 INSTANCE = new SingletonJDK16();
    }

    public static SingletonJDK16 getInstance() {
        return Holder.INSTANCE;
    }

    public void showMessage() {
        System.out.println("Hello from SingletonJDK16!");
    }

    public static void main(String[] args) {
        SingletonJDK16 singleton = SingletonJDK16.getInstance();
        singleton.showMessage();
    }
}
