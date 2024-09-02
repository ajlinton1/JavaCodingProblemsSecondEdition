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
}
