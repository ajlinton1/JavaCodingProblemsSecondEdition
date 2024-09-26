package chapter2;

import org.junit.*;
import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Chapter2 {

    @Test
    public void problem38() {
        /*
        Explaining and exemplifying UTF-8, UTF-16, and UTF-32: Provide a detailed explanation of what UTF-8, UTF-16, and UTF-32 are. Include several snippets of code to show how these work in Java.
         */
        String original = "Hello, 世界";

        // Encoding to UTF-8
        byte[] utf8Bytes = original.getBytes(StandardCharsets.UTF_8);
        System.out.println("UTF-8 Encoded: " + Arrays.toString(utf8Bytes));

        // Decoding from UTF-8
        String decoded = new String(utf8Bytes, StandardCharsets.UTF_8);
        System.out.println("UTF-8 Decoded: " + decoded);

        // Encoding to UTF-16
        byte[] utf16Bytes = original.getBytes(StandardCharsets.UTF_16);
        System.out.println("UTF-16 Encoded: " + Arrays.toString(utf16Bytes));

        // Decoding from UTF-16
        String decoded1 = new String(utf16Bytes, StandardCharsets.UTF_16);
        System.out.println("UTF-16 Decoded: " + decoded1);

        // Encoding to UTF-32
        Charset utf32Charset = Charset.forName("UTF-32");
        ByteBuffer utf32Buffer = utf32Charset.encode(original);
        byte[] utf32Bytes = utf32Buffer.array();
        System.out.println("UTF-32 Encoded: " + Arrays.toString(utf32Bytes));

        // Decoding from UTF-32
        String decoded2 = utf32Charset.decode(ByteBuffer.wrap(utf32Bytes)).toString();
        System.out.println("UTF-32 Decoded: " + decoded2);
    }

    @Test
    public void problem39() {
        /*
        Write a program that checks whether the given sub-range [given start, given start + given end) is within the bounds of the range from [0, given length). If the given sub-range is not in the [0, given length) range, then throw an IndexOutOfBoundsException.
         */
        try {
            checkSubRange(2, 5, 10); // Valid range
            System.out.println("Sub-range is within bounds.");

            checkSubRange(8, 5, 10); // Invalid range, should throw exception
            System.out.println("Sub-range is within bounds.");
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void checkSubRange(int start, int end, int length) {
        if (start < 0 || end < 0 || start >= length || start + end > length) {
            throw new IndexOutOfBoundsException("Sub-range [" + start + ", " + (start + end) + ") is out of bounds for range [0, " + length + ")");
        }
    }

    @Test
    public void problem40() {
        /* Returning an identity string: Write a program that returns a string representation of an object without calling the overridden toString() or hashCode() */
        Object obj = new Object();
        System.out.println(getIdentityString(obj));

        String str = "Hello, World!";
        System.out.println(getIdentityString(str));
    }

    public String getIdentityString(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj));
    }

    @Test
    public void problem42() {
    /* Adding code snippets in Java API documentation: Provide examples of adding code snippets in Java API documentation via the new @snippet tag. */
        add(1, 2);
        subtract(2, 1);
    }

    /**
     * This method demonstrates a simple addition operation.
     *
     * @param a the first number
     * @param b the second number
     * @return the sum of {@code a} and {@code b}
     * @snippet :
     * // This is a code snippet demonstrating the addition of two numbers.
     * int sum = a + b;
     * System.out.println("Sum: " + sum);
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * This method demonstrates a simple subtraction operation.
     *
     * @param a the first number
     * @param b the second number
     * @return the difference between {@code a} and {@code b}
     * @snippet :
     * // This is a code snippet demonstrating the subtraction of two numbers.
     * int difference = a - b;
     * System.out.println("Difference: " + difference);
     */
    public int subtract(int a, int b) {
        return a - b;
    }

    @Test
    public void problem43() {
        /* Invoking default methods from Proxy instances: Write several programs that invoke interface default methods from Proxy instances in JDK 8, JDK 9, and JDK 16. */

        // JDK 8
        MyInterface proxyInstance = (MyInterface) Proxy.newProxyInstance(
                MyInterface.class.getClassLoader(),
                new Class<?>[]{MyInterface.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.isDefault()) {
                            return InvocationHandler.invokeDefault(proxy, method, args);
                        }
                        return null;
                    }
                });

        proxyInstance.defaultMethod();

        // JDK 9
        MyInterface proxyInstance1 = (MyInterface) Proxy.newProxyInstance(
                MyInterface.class.getClassLoader(),
                new Class<?>[]{MyInterface.class},
                new InvocationHandler() {
                    private final MethodHandles.Lookup lookup = MethodHandles.lookup();

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.isDefault()) {
                            return lookup.unreflectSpecial(method, method.getDeclaringClass())
                                    .bindTo(proxy)
                                    .invokeWithArguments(args);
                        }
                        return null;
                    }
                });

        proxyInstance1.defaultMethod();

        // JDK 16
        MyInterface proxyInstance2 = (MyInterface) Proxy.newProxyInstance(
                MyInterface.class.getClassLoader(),
                new Class<?>[]{MyInterface.class},
                new InvocationHandler() {
                    private final MethodHandles.Lookup lookup = MethodHandles.lookup();

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.isDefault()) {
                            MethodHandles.Lookup privateLookup = MethodHandles.privateLookupIn(method.getDeclaringClass(), lookup);
                            return privateLookup.unreflectSpecial(method, method.getDeclaringClass())
                                    .bindTo(proxy)
                                    .invokeWithArguments(args);
                        }
                        return null;
                    }
                });

        proxyInstance2.defaultMethod();
    }

    interface MyInterface {
        default void defaultMethod() {
            System.out.println("Default method in MyInterface");
        }
    }

    @Test
    public void problem44() {
        /*
        Converting between bytes and hex-encoded strings: Provide several snippets of code for converting between bytes and hex-encoded strings (including byte arrays).
         */
        byte[] byteArray = {0x01, 0x2A, 0x3F, 0x4B};
        String hexString = bytesToHex(byteArray);
        System.out.println("Hex String: " + hexString);

        String hexString1 = "012A3F4B";
        byte[] byteArray1 = hexToBytes(hexString);
        System.out.println("Byte Array: " + Arrays.toString(byteArray1));
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] hexToBytes(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }
}

