package chapter2;

import java.lang.reflect.Method;

public class PreJDK16Example {
    public static void main(String[] args) {
        Object anonymousClass = new Object() {
            class NestedClass {
                void nestedMethod() {
                    System.out.println("Hello from NestedClass in anonymous class (pre-JDK 16)!");
                }
            }

            void mainMethod() {
                NestedClass nested = new NestedClass();
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