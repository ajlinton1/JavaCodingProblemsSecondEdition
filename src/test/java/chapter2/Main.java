package chapter2;

// Problem 41
/* Hooking unnamed classes and instance main methods: Give a quick introduction to JDK 21 unnamed classes and instance main methods. */

public class Main {
    public static void main(String[] args) {
        new Object() {
            void main() {
                System.out.println("Hello from an unnamed class with an instance main method!");
            }
        }.main();
    }
}