package chapter1;

import org.junit.*;
import static org.junit.Assert.*;

public class math {

    @Test
    public strictfp void problem19_floating_point(){
        double result = 0.1 + 0.2;
        assertEquals(0.3, result, 0.000000000000001);
    }

    @Test
    public void problem20_math(){
        assertNotEquals(Integer.MAX_VALUE, Math.abs(Integer.MIN_VALUE));

        assertEquals(safeAbs(Integer.MAX_VALUE), safeAbs(Integer.MIN_VALUE));
    }

    public static int safeAbs(int value) {
        if (value == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE; // Handle overflow case
        }
        return Math.abs(value);
    }

    @Test
    public void problem21_math(){
        int intMinValue = Integer.MIN_VALUE;
        int divisor = -1;
        try {
            int quotient = intMinValue / divisor;
            System.out.println("Quotient: " + quotient); // Overflow occurs here
        } catch (ArithmeticException e) {
            System.out.println("Overflow occurred: " + e.getMessage());
        }

        // Solution to handle overflow
        int safeQuotient = safeDivide(intMinValue, divisor);
        System.out.println("Safe Quotient: " + safeQuotient);

    }

    public static int safeDivide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE; // Handle overflow case
        }
        return dividend / divisor;
    }
}
