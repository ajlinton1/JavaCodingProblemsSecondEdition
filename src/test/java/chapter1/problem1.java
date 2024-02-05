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
    public void problem2_3_4_text_blocks() {
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

    @Test
    public void problem5_text_blocks() {
        String s1 = """
                This is a
                multiline
                string
                """;

        int loc = s1.indexOf("multiline");
        assertEquals(10, loc);
    }

    @Test
    public void problem6_text_blocks() {
        String s1 = """
                This is a
                the "new" multiline
                \r
                string
                """;

        System.out.println(s1);
        assert(true);
    }

    @Test
    public void problem7_text_blocks() {
        String s1 = """
                This is a
                the "new" multiline
                \r
                string
                """;

        int l = s1.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l; i++) {
            System.out.println(i + " " + s1.charAt(i));
            if (!Character.isISOControl(s1.charAt(i))) {
                sb.append(s1.charAt(i));
            }
        }
        System.out.println(sb.toString());
        assert(true);
    }

}
