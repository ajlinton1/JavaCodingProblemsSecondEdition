package chapter1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class problem1 {

    @Test
    public void test() {
        assertEquals(1, 1);
    }

    @Test
    public void problem1_multiline_string() {
        String s = """
                This is a
                multiline
                string
                """;
        assertEquals("This is a\nmultiline\nstring\n", s);
;
    }

    @Test
    public void problem2_text_blocks() {
        String s1 = """
                This is a
                multiline
                string
                """;
        System.out.println(s1);
        ;
        String s2 =
                """
                    This is a
                    multiline
                    string
                """;
        System.out.println(s2);

        String s3 =
                """
This is a
multiline
string
                """;
        System.out.println(s3);

        assert(true);
    }
}
