package chapter1;

import org.junit.jupiter.api.Test;

import textBlocks.Strings;

import static org.junit.Assert.*;

public class textblocks {

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

    @Test
    public void problem8_variables() {
        String name = "John";
        String s = """
                This is a
                multiline %s
                string
                """;
        String s1 = String.format(s, name);
        int pos = s1.indexOf(name);
        assertTrue(pos > 0);

        String s2 = """
                This is a
                multiline %s
                string
                """.formatted(name);
        int pos1 = s2.indexOf(name);
        assertTrue(pos1 > 0);

    }

    @Test
    public void problem12_concatenating() {
        var result = Strings.concatenating();
        assertNotNull(result);
    }

    @Test
    public void problem12_StringBuilder() {
        var result = Strings.concatenatingStringBuilder();
        assertNotNull(result);
    }
}
