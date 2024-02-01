package chapter1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class problem1 {

    @Test
    public void test() {
        assertEquals(1, 1);
    }

    @Test
    public void problem1_multiline_stirng() {
        String s = """
                This is a
                multiline
                string
                """;
        assertEquals("This is a\nmultiline\nstring\n", s);
;
    }

}
