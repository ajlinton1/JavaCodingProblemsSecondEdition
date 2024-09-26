package chapter1;

import org.junit.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.SplittableRandom;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.Random;

import static org.junit.Assert.*;

public class Math {

    @Test
    public strictfp void problem19_floating_point(){
        double result = 0.1 + 0.2;
        assertEquals(0.3, result, 0.000000000000001);
    }

    @Test
    public void problem20_math(){
        Assert.assertNotEquals(Integer.MAX_VALUE, java.lang.Math.abs(Integer.MIN_VALUE));

        assertEquals(safeAbs(Integer.MAX_VALUE), safeAbs(Integer.MIN_VALUE));
    }

    public static int safeAbs(int value) {
        if (value == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE; // Handle overflow case
        }
        return java.lang.Math.abs(value);
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
        return java.lang.Math.floorDiv(dividend, divisor);
    }

    // Method to safely compute the smallest value greater than or equal to the algebraic quotient
    public static int safeCeilDiv(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE; // Handle overflow case
        }
        return (int) java.lang.Math.ceil((double) dividend / divisor);
    }

    @Test
    public void problem23_Getting_integral_and_fractional_parts_from_a_double() {
        double number = 123.456;

        // Technique 1: Using Math.floor
        double integralPart1 = java.lang.Math.floor(number);
        double fractionalPart1 = number - integralPart1;
        System.out.println("Using Math.floor:");
        System.out.println("Integral Part: " + integralPart1);
        System.out.println("Fractional Part: " + fractionalPart1);

        // Technique 2: Using Math.ceil
        double integralPart2 = java.lang.Math.ceil(number) - 1;
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
        return java.lang.Math.floor(number) == java.lang.Math.ceil(number);
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

    @Test
    public void problem26_floor() {
        int dividend = -5;
        int divisor = 3;

        // Floor modulus
        int floorMod = java.lang.Math.floorMod(dividend, divisor);
        System.out.println("Floor Modulus: " + floorMod);

        // Ceil modulus
        int ceilMod = ceilMod(dividend, divisor);
        System.out.println("Ceil Modulus: " + ceilMod);
    }

    // Method to compute the ceil modulus
    public static int ceilMod(int dividend, int divisor) {
        return dividend - divisor * (int) java.lang.Math.ceil((double) dividend / divisor);
    }

    @Test
    public void problem27_primeFactors() {
        assertEquals(List.of(2, 2, 2, 7), getPrimeFactors(56));
        assertEquals(List.of(3, 3, 5), getPrimeFactors(45));
        assertEquals(List.of(11), getPrimeFactors(11));
        assertEquals(List.of(), getPrimeFactors(1));
    }

    // Method to check if a number is prime
    public boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= java.lang.Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Method to collect all prime factors of a given positive number
    public List<Integer> getPrimeFactors(int number) {
        List<Integer> primeFactors = new ArrayList<>();
        for (int i = 2; i <= number; i++) {
            while (isPrime(i) && number % i == 0) {
                primeFactors.add(i);
                number /= i;
            }
        }
        return primeFactors;
    }

    @Test
    public void problem28_squareRoot() {
        double number = 25.0;
        double sqrt = babylonianSqrt(number);
        System.out.println("The square root of " + number + " is approximately " + sqrt);
    }

    double babylonianSqrt(double S) {
        if (S < 0) {
            throw new IllegalArgumentException("Cannot compute square root of a negative number");
        }
        if (S == 0) {
            return 0;
        }

        double x0 = S / 2;
        double epsilon = 1e-7;
        double x1 = (x0 + S / x0) / 2;

        while (java.lang.Math.abs(x1 - x0) > epsilon) {
            x0 = x1;
            x1 = (x0 + S / x0) / 2;
        }

        return x1;
    }

    @Test
    public void problem29_rounding() {
        double number = 123.456789;
        int decimals = 2;

        double roundedMathRound = roundUsingMathRound(number, decimals);
        double roundedBigDecimal = roundUsingBigDecimal(number, decimals);
        double roundedStringFormat = roundUsingStringFormat(number, decimals);

        System.out.println("Original number: " + number);
        System.out.println("Rounded using Math.round: " + roundedMathRound);
        System.out.println("Rounded using BigDecimal: " + roundedBigDecimal);
        System.out.println("Rounded using String.format: " + roundedStringFormat);
    }

    public double roundUsingMathRound(double value, int decimals) {
        double scale = java.lang.Math.pow(10, decimals);
        return java.lang.Math.round(value * scale) / scale;
    }

    // Method using BigDecimal
    public double roundUsingBigDecimal(double value, int decimals) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(decimals, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    // Method using String.format
    public double roundUsingStringFormat(double value, int decimals) {
        String format = "%." + decimals + "f";
        return Double.parseDouble(String.format(format, value));
    }

    @Test
    public void problem30_Clamping(){
        double value = 10.5;
        double min = 5.0;
        double max = 10.0;

        double clampedValue = clamp(value, min, max);
        System.out.println("Clamped value: " + clampedValue);
        assertTrue(clampedValue <= max);
    }

    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    @Test
    public void problem31_multiply() {
        int a = 6;
        int b = 7;
        int product = multiply(a, b);
        assertEquals(product, 42);
    }

    public int multiply(int a, int b) {
        int sumSquare = (a + b) * (a + b);
        int diffSquare = (a - b) * (a - b);
        return (sumSquare - diffSquare) / 4;
    }

    @Test
    public void problem32_tau() {
        /* Explain the meaning of TAU in geometry/trigonometry, and write a program that solves the following problem: A circle has a circumference of 21.33 cm. What is the radius of the circle?*/
        double circumference = 21.33;
        double radius = calculateRadius(circumference);
        System.out.println("The radius of the circle with circumference " + circumference + " cm is approximately " + radius + " cm.");
    }

    final double TAU = 2 * java.lang.Math.PI;

    // Method to calculate the radius from the circumference
    double calculateRadius(double circumference) {
        return circumference / TAU;
    }

    @Test
    public void problem33_random() {
        useRandom();
        useSplittableRandom();
/*        useXoroshiro128Plus();
        useL64X128MixRandom(); */
        /* import java.util.random.Xoroshiro128Plus;
import java.util.random.L64X128MixRandom;
*/
    }

    // Method to use java.util.Random
    void useRandom() {
        RandomGenerator random = new java.util.Random();
        System.out.println("Random: " + random.nextInt());
    }

    // Method to use SplittableRandom
    void useSplittableRandom() {
        RandomGenerator random = new SplittableRandom();
        System.out.println("SplittableRandom: " + random.nextInt());
    }

    // Method to use Xoroshiro128Plus
/*    void useXoroshiro128Plus() {
        RandomGenerator random = new Xoroshiro128Plus();
        System.out.println("Xoroshiro128Plus: " + random.nextInt());
    } */

    // Method to use L64X128MixRandom
/*    void useL64X128MixRandom() {
        RandomGenerator random = new L64X128MixRandom();
        System.out.println("L64X128MixRandom: " + random.nextInt());
    } */

    @Test
    public void problem34_random1() {
        /* Write a program that fills an array of long arrays with pseudo-random numbers in a parallel and non-parallel fashion. */
        int outerSize = 10;
        int innerSize = 1000;
        long[][] array = new long[outerSize][innerSize];

        // Fill array in non-parallel fashion
        fillArrayNonParallel(array);
        System.out.println("Non-Parallel Array: " + Arrays.deepToString(array));
        assertNotNull(array);

        // Fill array in parallel fashion
        fillArrayParallel(array);
        System.out.println("Parallel Array: " + Arrays.deepToString(array));
        assertNotNull(array);
    }

    public static void fillArrayNonParallel(long[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = ThreadLocalRandom.current().nextLong();
            }
        }
    }

    // Method to fill array in parallel fashion
    void fillArrayParallel(long[][] array) {
        ForkJoinPool.commonPool().invoke(new FillArrayTask(array, 0, array.length));
    }

    // RecursiveAction to fill array in parallel
    static class FillArrayTask extends RecursiveAction {
        private static final int THRESHOLD = 1; // Threshold for splitting tasks
        private final long[][] array;
        private final int start;
        private final int end;

        FillArrayTask(long[][] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= THRESHOLD) {
                for (int i = start; i < end; i++) {
                    for (int j = 0; j < array[i].length; j++) {
                        array[i][j] = ThreadLocalRandom.current().nextLong();
                    }
                }
            } else {
                int mid = (start + end) / 2;
                invokeAll(new FillArrayTask(array, start, mid), new FillArrayTask(array, mid, end));
            }
        }
    }

    @Test
    public void problem35_random2() {
        /* Write a program that creates a stream of pseudo-random numbers and a stream of pseudo-random generators. */
        Stream<Long> randomNumbers = ThreadLocalRandom.current().longs(10).boxed();
        randomNumbers.forEach(System.out::println);

        // Stream of pseudo-random generators
        Stream<RandomGenerator> randomGenerators = Stream.generate(() -> RandomGeneratorFactory.of("L64X128MixRandom").create()).limit(5);
        randomGenerators.forEach(generator -> System.out.println(generator.getClass().getName()));
    }

    @Test
    public void problem36_random3() {
        /* Write a program that instantiates a legacy pseudo-random generator (for instance, Random) that can delegate method calls to a JDK 17 RandomGenerator. */
        RandomGenerator randomGenerator = RandomGeneratorFactory.of("L64X128MixRandom").create();
        Random legacyRandom = new LegacyRandomAdapter(randomGenerator);

        // Demonstrate usage
        System.out.println("Random int: " + legacyRandom.nextInt());
        System.out.println("Random long: " + legacyRandom.nextLong());
        System.out.println("Random boolean: " + legacyRandom.nextBoolean());
        System.out.println("Random float: " + legacyRandom.nextFloat());
        System.out.println("Random double: " + legacyRandom.nextDouble());

        byte[] bytes = new byte[10];
        legacyRandom.nextBytes(bytes);
        System.out.println("Random bytes: " + Arrays.toString(bytes));

    }

    @Test
    public void problem37_random4() {
        /* Explain and exemplify the usage of pseudo-random generators in a multithreaded environment (for instance, using an ExecutorService). */
        int numberOfTasks = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < numberOfTasks; i++) {
            executorService.submit(() -> {
                long randomValue = ThreadLocalRandom.current().nextLong();
                System.out.println("Generated random value: " + randomValue);
            });
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
