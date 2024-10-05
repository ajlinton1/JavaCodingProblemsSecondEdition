package chapter2;

public class ErasureVsOverloading {

    // Generic method
    public <T> void print(T t) {
        System.out.println("Generic method: " + t);
    }

    // Overloaded method for String
    public void print(String s) {
        System.out.println("Overloaded method: " + s);
    }

}