package chapter1;

import org.junit.*;

import java.math.BigDecimal;

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

    @Test
    public void problem22_algebraic_quotient(){
        int dividend = Integer.MIN_VALUE;
        int divisor = -1;

        // Compute the largest value less than or equal to the algebraic quotient
        int floorQuotient = safeFloorDiv(dividend, divisor);
        System.out.println("Floor Quotient: " + floorQuotient);

        // Compute the smallest value greater than or equal to the algebraic quotient
        int ceilQuotient = safeCeilDiv(dividend, divisor);
        System.out.println("Ceil Quotient: " + ceilQuotient);
    }

    // Method to safely compute the largest value less than or equal to the algebraic quotient
    public static int safeFloorDiv(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE; // Handle overflow case
        }
        return Math.floorDiv(dividend, divisor);
    }

    // Method to safely compute the smallest value greater than or equal to the algebraic quotient
    public static int safeCeilDiv(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE; // Handle overflow case
        }
        return (int) Math.ceil((double) dividend / divisor);
    }

    @Test
    public void problem23_Getting_integral_and_fractional_parts_from_a_double() {
        double number = 123.456;

        // Technique 1: Using Math.floor
        double integralPart1 = Math.floor(number);
        double fractionalPart1 = number - integralPart1;
        System.out.println("Using Math.floor:");
        System.out.println("Integral Part: " + integralPart1);
        System.out.println("Fractional Part: " + fractionalPart1);

        // Technique 2: Using Math.ceil
        double integralPart2 = Math.ceil(number) - 1;
        double fractionalPart2 = number - integralPart2;
        System.out.println("Using Math.ceil:");
        System.out.println("Integral Part: " + integralPart2);
        System.out.println("Fractional Part: " + fractionalPart2);

        // Technique 3: Using BigDecimal
        BigDecimal bd = new BigDecimal(number);
        BigDecimal integralPart3 = bd.setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal fractionalPart3 = bd.subtract(integralPart3);
        System.out.println("Using BigDecimal:");
        System.out.println("Integral Part: " + integralPart3);
        System.out.println("Fractional Part: " + fractionalPart3);
    }

    @Test
    public void problem24_is_double_integer() {
        double number1 = 123.0;
        double number2 = 123.456;

        System.out.println("Using Math.floor and Math.ceil:");
        System.out.println("Is " + number1 + " an integer? " + isIntegerUsingFloorCeil(number1));
        System.out.println("Is " + number2 + " an integer? " + isIntegerUsingFloorCeil(number2));

        System.out.println("Using modulo operation:");
        System.out.println("Is " + number1 + " an integer? " + isIntegerUsingModulo(number1));
        System.out.println("Is " + number2 + " an integer? " + isIntegerUsingModulo(number2));

        System.out.println("Using BigDecimal:");
        System.out.println("Is " + number1 + " an integer? " + isIntegerUsingBigDecimal(number1));
        System.out.println("Is " + number2 + " an integer? " + isIntegerUsingBigDecimal(number2));
    }

    // Technique 1: Using Math.floor and Math.ceil
    public static boolean isIntegerUsingFloorCeil(double number) {
        return Math.floor(number) == Math.ceil(number);
    }

    // Technique 2: Using modulo operation
    public static boolean isIntegerUsingModulo(double number) {
        return number % 1 == 0;
    }

    // Technique 3: Using BigDecimal
    public static boolean isIntegerUsingBigDecimal(double number) {
        BigDecimal bd = new BigDecimal(number);
        return bd.stripTrailingZeros().scale() <= 0;
    }

    @Test
    public void problem25_unsignedIntegers() {
        // Signed integer example
        int signedInt = -123;
        System.out.println("Signed int: " + signedInt);

        // Unsigned integer example using Integer class methods
        int unsignedInt = Integer.parseUnsignedInt("123");
        System.out.println("Unsigned int: " + unsignedInt);

        // Demonstrating unsigned addition
        int result = Integer.sum(unsignedInt, signedInt);
        System.out.println("Unsigned addition result: " + result);

        // Demonstrating unsigned comparison
        int comparison = Integer.compareUnsigned(unsignedInt, signedInt);
        System.out.println("Unsigned comparison result: " + comparison);

        // Demonstrating unsigned division
        int dividend = -1;
        int divisor = 2;
        int unsignedDivResult = Integer.divideUnsigned(dividend, divisor);
        System.out.println("Unsigned division result: " + unsignedDivResult);

        // Demonstrating unsigned remainder
        int unsignedRemResult = Integer.remainderUnsigned(dividend, divisor);
        System.out.println("Unsigned remainder result: " + unsignedRemResult);
    }
}
