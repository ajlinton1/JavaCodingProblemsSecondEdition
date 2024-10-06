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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

            Objects.checkFromIndexSize(2, 5, 10);

            checkSubRange(8, 5, 10); // Invalid range, should throw exception
            System.out.println("Sub-range is within bounds.");

            Objects.checkFromIndexSize(8, 5, 10);
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
//        return obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj));
        return obj.getClass().getName() + "@" + Objects.toIdentityString(obj);
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

    @Test
    public void problem45() {
        /*
        Exemplify the initialization-on-demand holder design pattern: Write a program that implements the initialization-on-demand holder design pattern in the classical way (before JDK 16) and another program that implements this design pattern based on the fact that, from JDK 16+, Java inner classes can have static members and static initializers.
         */

        // Initialization-on-Demander Holder Design Pattern (Classical Way)
        Singleton singleton = Singleton.getInstance();
        singleton.showMessage();

        // JDK 16+
        SingletonJDK16 singleton1 = SingletonJDK16.getInstance();
        singleton1.showMessage();
    }

    @Test
    public void problem46() {
        /*
        Adding nested classes in anonymous classes: Write a meaningful example that uses nested classes in anonymous classes (pre-JDK 16, and JDK 16+).
         */
        PreJDK16Example.main(new String[]{});

        JDK16PlusExample.main(new String[]{});
    }

    @Test
    public void problem47() {
        /*
        Exemplify erasure vs. overloading: Explain in a nutshell what type erasure in Java and polymorphic overloading are, and exemplify how they work together.
         */
        ErasureVsOverloading example = new ErasureVsOverloading();

        // Calls the overloaded method for String
        example.print("Hello");

        // Calls the generic method
        example.print(123);
    }

    @Test
    public void problem48() {
        /*
        Xlinting default constructors: Explain and exemplify the JDK 16+ hint for classes with default constructors,-Xlint:missing-explicit-ctor.

        // javac -Xlint:missing-explicit-ctor DefaultConstructorExample.java ExplicitConstructorExample.java
         */

        DefaultConstructorExample defaultConstructorExample = new DefaultConstructorExample();

        ExplicitConstructorExample explicitConstructorExample = new ExplicitConstructorExample();
    }

    @Test
    public void problem49() {
        /*
        Working with the receiver parameter: Explain the role of the Java receiver parameter and exemplify its usage in code.
         */

        ReceiverParameterExample example = new ReceiverParameterExample(10);
        System.out.println("Initial Value: " + example.getValue());

        example.setValue(20);
        System.out.println("Updated Value: " + example.getValue());
    }

    @Test
    public void problem50() {
        /*
        Implementing an immutable stack: Provide a program that creates an immutable stack implementation from zero (implement isEmpty(), push(), pop(), and peek() operations).
         */
        ImmutableStack<Integer> stack = new ImmutableStack<>();
        stack = stack.push(1);
        stack = stack.push(2);
        stack = stack.push(3);

        System.out.println("Top element: " + stack.peek()); // Output: Top element: 3
        stack = stack.pop();
        System.out.println("Top element after pop: " + stack.peek()); // Output: Top element after pop: 2
        System.out.println("Stack size: " + stack.size()); // Output: Stack size: 2
    }

    @Test
    public void problem51() {
        /*
        Revealing a common mistake with Strings: Write a simple use case of strings that contain a common mistake (for instance, related to the String immutability characteristic).
         */

        String original = "Hello";
        System.out.println("Original String: " + original);

        // Attempt to modify the string
        original.concat(", World!");
        System.out.println("After concat: " + original);

        // Correct way to modify the string
        String modified = original.concat(", World!");
        System.out.println("Modified String: " + modified);
    }

    @Test
    public void problem52() {
        /*
        Using the enhanced NullPointerException: Exemplify, from your experience, the top 5 causes of NullPointerException and explain how JDK 14 improves NPE messages.

### Top 5 Causes of NullPointerException

1. **Dereferencing a null object**:
   ```java
   String str = null;
   int length = str.length(); // Causes NullPointerException
   ```

2. **Accessing a field of a null object**:
   ```java
   class Person {
       String name;
   }
   Person person = null;
   String name = person.name; // Causes NullPointerException
   ```

3. **Calling a method on a null object**:
   ```java
   class Calculator {
       int add(int a, int b) {
           return a + b;
       }
   }
   Calculator calculator = null;
   int result = calculator.add(1, 2); // Causes NullPointerException
   ```

4. **Array elements being null**:
   ```java
   String[] array = new String[5];
   String element = array[0].toUpperCase(); // Causes NullPointerException
   ```

5. **Unboxing a null object**:
   ```java
   Integer number = null;
   int value = number; // Causes NullPointerException
   ```

### Enhanced NullPointerException Messages in JDK 14

JDK 14 introduced enhanced NullPointerException messages that provide more detailed information about what was `null` when the exception was thrown. This helps in quickly identifying the root cause of the issue.

#### Example

```java
public class EnhancedNPEExample {
    public static void main(String[] args) {
        String str = null;
        System.out.println(str.length()); // Causes NullPointerException
    }
}
```

#### Enhanced Message

In JDK 14 and later, the message will be more descriptive:
```
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.length()" because "str" is null
```

This enhanced message clearly indicates that the `str` variable was `null` when attempting to call the `length()` method.
         */
    }

    @Test
    public void problem53() {
        /*
        Using yield in switch expressions: Explain and exemplify the usage of the yield keyword with switch expressions in JDK 13+.
         */

        int dayOfWeek = 3;
        String dayName = switch (dayOfWeek) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> {
                System.out.println("Midweek day");
                yield "Wednesday";
            }
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saturday";
            case 7 -> "Sunday";
            default -> throw new IllegalArgumentException("Invalid day: " + dayOfWeek);
        };

        System.out.println("Day name: " + dayName);
    }

    @Test
    public void problem54() {
        /*
        Tackling the case null clause in switch: Write a bunch of examples to show different approaches for handling null values in switch expressions (including JDK 17+ approaches).
         */

        // Using a Traditional Switch Statement with Null Check
        String value = null;
        String result;

        if (value == null) {
            result = "Value is null";
        } else {
            switch (value) {
                case "A":
                    result = "Value is A";
                    break;
                case "B":
                    result = "Value is B";
                    break;
                default:
                    result = "Unknown value";
            }
        }
        System.out.println("Traditional Switch: " + result);

        // Using Switch Expression with Null Check
        String value1 = null;
        String result1 = (value1 == null) ? "Value is null" : switch (value1) {
            case "A" -> "Value is A";
            case "B" -> "Value is B";
            default -> "Unknown value";
        };

        System.out.println(result1);

        // Using Switch Expression with Null Check (JDK 17+)
        String value2 = null;
        String result2 = switch (value2) {
            case null -> "Value is null";
            case "A" -> "Value is A";
            case "B" -> "Value is B";
            default -> "Unknown value";
        };

        System.out.println(result2);
    }

    @Test
    public void problem55() {
        /*
        Taking on the hard way to discover equals(): Explain and exemplify how equals() is different from the == operator.
         */
        String str1 = new String("Hello");
        String str2 = new String("Hello");

        // Using == operator
        boolean referenceComparison = (str1 == str2);
        System.out.println("Using == : " + referenceComparison); // Output: false

        // Using equals() method
        boolean valueComparison = str1.equals(str2);
        System.out.println("Using equals() : " + valueComparison); // Output: true

        Person person1 = new Person("Alice", 30);
        Person person2 = new Person("Alice", 30);
        Person person3 = person1;

        // Using == operator
        System.out.println(person1 == person2); // Output: false
        System.out.println(person1 == person3); // Output: true

        // Using equals() method
        System.out.println(person1.equals(person2)); // Output: true
        System.out.println(person1.equals(person3)); // Output: true

    }

    @Test
    public void problem56() {
        /*
        Hooking instanceof in a nutshell: Provide a brief overview with snippets of code to highlight the main aspect of the instanceof operator.
         */
        Object obj = "Hello, World!";

        // Check if obj is an instance of String
        if (obj instanceof String) {
            System.out.println("obj is a String");
        } else {
            System.out.println("obj is not a String");
        }

        // Check if obj is an instance of Integer
        if (obj instanceof Integer) {
            System.out.println("obj is an Integer");
        } else {
            System.out.println("obj is not an Integer");
        }
    }

    @Test
    public void problem57() {
        /*
        Introducing pattern matching: Provide a theoretical dissertation including the main aspects and terminology for pattern matching in Java.

  ### Pattern Matching in Java

Pattern matching is a feature that allows you to test an object against a pattern. It simplifies the code by combining the logic of testing and casting into a single operation. Java has introduced pattern matching in several stages, starting with `instanceof` enhancements and moving towards more complex patterns in switch expressions.

#### Key Aspects and Terminology

1. **Type Patterns**:
   - A type pattern consists of a type and a variable name.
   - It allows you to test if an object is of a certain type and, if so, cast it to that type in a single step.

   ```java
   if (obj instanceof String s) {
       System.out.println(s.toUpperCase());
   }
   ```

2. **Pattern Matching for `instanceof`**:
   - Introduced in JDK 16, it enhances the `instanceof` operator to include type patterns.
   - Simplifies the common pattern of using `instanceof` followed by a cast.

   ```java
   if (obj instanceof String s) {
       System.out.println(s.length());
   }
   ```

3. **Pattern Matching in Switch Expressions**:
   - Available from JDK 17, it allows switch expressions to use patterns.
   - Patterns can be used in case labels to match against types and values.

   ```java
   switch (obj) {
       case String s -> System.out.println("String: " + s);
       case Integer i -> System.out.println("Integer: " + i);
       default -> System.out.println("Unknown type");
   }
   ```

4. **Guarded Patterns**:
   - Patterns can include additional conditions, known as guards.
   - Guards are boolean expressions that must be true for the pattern to match.

   ```java
   switch (obj) {
       case String s && s.length() > 5 -> System.out.println("Long String: " + s);
       case String s -> System.out.println("Short String: " + s);
       default -> System.out.println("Unknown type");
   }
   ```

5. **Pattern Label Dominance**:
   - Ensures that more specific patterns are checked before more general ones.
   - Prevents unreachable code by ordering patterns correctly.

   ```java
   switch (obj) {
       case String s && s.isEmpty() -> System.out.println("Empty String");
       case String s -> System.out.println("Non-empty String: " + s);
       default -> System.out.println("Unknown type");
   }
   ```

6. **Completeness**:
   - A switch expression must cover all possible input types.
   - The compiler checks for completeness to ensure all cases are handled.

   ```java
   switch (obj) {
       case String s -> System.out.println("String: " + s);
       case Integer i -> System.out.println("Integer: " + i);
       default -> throw new IllegalStateException("Unexpected value: " + obj);
   }
   ```

Pattern matching in Java enhances code readability and reduces boilerplate code by combining type checking and casting into a single, concise operation.
         */

        Object obj = "Hello, World!";

        if (obj instanceof String s) {
            System.out.println(s.toUpperCase());
        }

        if (obj instanceof String s) {
            System.out.println(s.length());
        }

        switch (obj) {
            case String s -> System.out.println("String: " + s);
            case Integer i -> System.out.println("Integer: " + i);
            default -> System.out.println("Unknown type");
        }

        switch (obj) {
            case String s -> System.out.println("String: " + s);
            case Integer i -> System.out.println("Integer: " + i);
            default -> throw new IllegalStateException("Unexpected value: " + obj);
        }
    }

    @Test
    public void problem58() {
        /*
        Introducing type pattern matching for instanceof: Provide the theoretical and practical support for using the type pattern matching for instanceof.
         */
        Object obj = "Hello, World!";

        // Traditional way
        if (obj instanceof String) {
            String s = (String) obj;
            System.out.println(s.toUpperCase());
        }

        // Using type pattern matching
        if (obj instanceof String s) {
            System.out.println(s.toUpperCase());
        }
    }

    @Test
    public void problem59() {
        /*
        Handling the scope of a binding variable in type patterns for instanceof: Explain in detail, including snippets of code, the scope of binding variables in type patterns for instanceof.
         */
        Object obj = "Hello, World!";

        // Using type pattern matching
        if (obj instanceof String s) {
            // The variable 's' is in scope here
            System.out.println(s.toUpperCase());
        } else {
            // The variable 's' is not in scope here
            System.out.println("obj is not a String");
        }

        // The variable 's' is not in scope here
        // System.out.println(s); // This would cause a compilation error
    }

    @Test
    public void problem60() {
        /*
        Rewriting equals() via type patterns for instanceof: Exemplify in code the implementation of equals() (including for generic classes) before and after type patterns for instanceof have been introduced.

        Explanation
Before Type Patterns:
The equals() method checks if obj is an instance of the same class using getClass().
If true, it casts obj to Box<?> and compares the value fields.
After Type Patterns:
The equals() method uses instanceof with a type pattern to check and cast obj in one step.
If obj is an instance of Box<?>, it directly compares the value fields.
         */
    }

    @Test
    public void problem61() {
        /*
        Tackling type patterns for instanceof and generics: Provide several examples that use the combo type patterns for instanceof and generics.
         */
        List<String> stringList = List.of("A", "B", "C");
        List<Integer> intList = List.of(1, 2, 3);

        processList(stringList); // List size: 3
        processList(intList);    // List size: 3
        processList("Not a List"); // Not a List

    }

    public static void processList(Object obj) {
        if (obj instanceof List<?> list) {
            System.out.println("List size: " + list.size());
        } else {
            System.out.println("Not a List");
        }
    }

    @Test
    public void problem62() {
        /*
        Tackling type patterns for instanceof and streams: Can we use type patterns for instanceof and the Stream API together? If yes, provide at least an example.
         */
        List<Object> mixedList = List.of("Hello", 123, "World", 456, "Java");

        List<String> upperCaseStrings = mixedList.stream()
                .filter(obj -> obj instanceof String s)
                .map(s -> ((String) s).toUpperCase())
                .collect(Collectors.toList());

        upperCaseStrings.forEach(System.out::println);

    }

    @Test
    public void problem63() {
        /*
        Introducing type pattern matching for switch: Type patterns are available for instanceof but are also available for switch. Provide here the theoretical headlines and an example of this topic.
         */
        Object obj = "Hello, World!";

        String result = switch (obj) {
            case String s -> "String: " + s.toUpperCase();
            case Integer i -> "Integer: " + (i * 2);
            case Double d -> "Double: " + (d / 2);
            default -> "Unknown type";
        };

        System.out.println(result);
    }

    @Test
    public void problem64() {
        /*
        Adding guarded pattern labels in switch: Provide a brief coverage of guarded pattern labels in switch for JDK 17 and 21.
         */

        Object obj = List.of("Hello", "World");

        String result = switch (obj) {
            case List<?> list -> "List with multiple elements: " + list;
            // case List<?> list && list.size() > 1 -> "List with multiple elements: " + list;
            case String s -> "String contains 'World': " + s;
            default -> "Unknown type";
        };

        System.out.println(result);
    }

    @Test
    public void problem65() {
        /*
        Dealing with pattern label dominance in switch: Pattern label dominance in switch is a cool feature, so exemplify it here in a comprehensive approach with plenty of examples.
         */
        Object obj = null;

        String result = switch (obj) {
            case null -> "Value is null";
            case String s -> "String: " + s;
            case Integer i -> "Integer: " + i;
            default -> "Unknown type";
        };

        System.out.println(result); // Output: Value is null

    }

    @Test
    public void problem66() {
        /*
        Dealing with completeness (type coverage) in pattern labels for switch: This is another cool topic for switch expressions. Explain and exemplify it in detail (theory ad examples).
         */
        Object obj = "Hello";

        String result = switch (obj) {
            case String s -> "String: " + s;
            case Integer i -> "Integer: " + i;
            default -> "Unknown type";
        };

        System.out.println(result); // Output: String: Hello

        Shape shape = new Circle(5.0);

        String result1 = switch (shape) {
            case Circle c -> "Circle with radius: " + c.radius;
            case Rectangle r -> "Rectangle with length: " + r.length + " and width: " + r.width;
        };

        System.out.println(result1); // Output: Circle with radius: 5.0
    }

    sealed interface Shape permits Circle, Rectangle {}

    final class Circle implements Shape {
        double radius;
        Circle(double radius) { this.radius = radius; }
    }

    final class Rectangle implements Shape {
        double length, width;

        Rectangle(double length, double width)
        {
            this.length = length;
            this.width = width;
        }
    }


    @Test
    public void problem67() {
        /*
        Understanding the unconditional patterns and nulls in switch expressions: Explain how null values are handled by unconditional patterns of switch expressions before and after JDK 19.
         */
        Object obj = null;
        String result;

        if (obj == null) {
            result = "Value is null";
        } else {
            result = switch (obj) {
                case String s -> "String: " + s;
                case Integer i -> "Integer: " + i;
                default -> "Unknown type";
            };
        }

        System.out.println(result);

        result = switch (obj) {
            case null -> "Value is null";
            case String s -> "String: " + s;
            case Integer i -> "Integer: " + i;
            default -> "Unknown type";
        };

        System.out.println(result);
    }
}

