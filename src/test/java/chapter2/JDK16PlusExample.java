package chapter2;

import java.lang.reflect.Method;

public class JDK16PlusExample {
    public static void main(String[] args) {
        Object anonymousClass = new Object() {
            static class StaticNestedClass {
                static {
                    System.out.println("StaticNestedClass loaded");
                }

                void nestedMethod() {
                    System.out.println("Hello from StaticNestedClass in anonymous class (JDK 16+)!");
                }
            }

            void mainMethod() {
                StaticNestedClass nested = new StaticNestedClass();
                nested.nestedMethod();
            }
        };

        // Invoke the main method of the anonymous class
        try {
            Method mainMethod = anonymousClass.getClass().getDeclaredMethod("mainMethod");
            mainMethod.invoke(anonymousClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}