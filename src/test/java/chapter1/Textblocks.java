package chapter1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.*;

import textBlocks.Strings;

//import static java.lang.StringTemplate.STR;
//import static java.lang.StringTemplate.*;
import static org.junit.Assert.*;

public class Textblocks {

    @Test
    public void problem1_multiline_string() {
        String s = """
                This is a
                multiline
                string
                """;
        assertEquals("This is a\nmultiline\nstring\n", s);

    }

    @Test
    public void problem2_3_4_text_blocks() {
        String s1 = """
                This is a
                multiline
                string
                """;
        System.out.println(s1);

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
        System.out.println(sb);
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
    public void problem9_addCommentsToTextBlocks() {
        String textBlockWithComments = """
           SELECT *
           FROM users
           WHERE active = true
           -- This is a comment explaining the WHERE clause
           ORDER BY createdAt DESC
           """;

        String sqlQueryWithComments = """
           /* Querying active users */
           SELECT *
           FROM users
           WHERE active = true
           /* Sorts users by creation date */
           ORDER BY createdAt DESC
           """;

        // Query to get active users
        String selectUsers = """
           SELECT *
           FROM users
           WHERE active = true
           """;

        // Sort query by `createdAt`
        String orderByClause = """
           ORDER BY createdAt DESC
           """;

        // Combine both
        String fullQuery = selectUsers + "\n" + orderByClause;

        // Multi-line SQL query with active filter and ordering
        String query = """
           SELECT *
           FROM users
           WHERE active = true
           ORDER BY createdAt DESC
           """;

        assert(true);
    }

    @Test
    public void problem10_textBlocks() {
        String textBlock1 = """
                This is a multiline
                textblock\
                """;

        String regularString = "This is a multiline\ntextblock";

        String combined = textBlock1 + regularString;

        assertEquals(textBlock1, regularString);
    }

    @Test
    public void problem11_textBlocks() {
        String regex = """
                (?<day>\\d{2})-(?<month>\\d{2})-(?<year>\\d{4})  # Captures a date in DD-MM-YYYY format
                """;

        // Compile the pattern (strip inline comments for Java compatibility)
        Pattern pattern = Pattern.compile(regex.stripTrailing().replaceAll("#.*", "").trim());

        // Input string
        String input = "Today's date is 31-12-2023.";

        // Match the input against the regex
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            // Access named groups
            String day = matcher.group("day");
            String month = matcher.group("month");
            String year = matcher.group("year");

            // Print the extracted values
            System.out.printf("Extracted Date: Day = %s, Month = %s, Year = %s%n", day, month, year);
            assert(true);
        } else {
            System.out.println("No match found!");
            assert(false);
        }

    }

    @Test
    public void problem12_Isomorphic() {
        // Define two text blocks
        String textBlock1 = """
                xxyznnxiz
                """;
        String textBlock2 = """
                aavurraqu
                """;

        // Check if the two text blocks are isomorphic
        boolean isIsomorphic = Strings.areIsomorphic(textBlock1.strip(), textBlock2.strip());

        // Output the result
        System.out.printf("Are the text blocks \"%s\" and \"%s\" isomorphic? %b%n",
                textBlock1.strip(), textBlock2.strip(), isIsomorphic);

        assert(isIsomorphic);
    }

    @Test
    public void problem13_concatenating() {
        var result = Strings.concatenating();
        assertNotNull(result);

        var result1 = Strings.concatenatingStringBuilder();
        assertNotNull(result1);
    }

    @Test
    public void problem14_ConvertingIntToString() {
        // Example integer to demonstrate the conversions
        int number = 12345;

        // 1. Using Integer.toString()
        String str1 = Integer.toString(number);
        System.out.println("Using Integer.toString(): " + str1);

        // 2. Using String.valueOf()
        String str2 = String.valueOf(number);
        System.out.println("Using String.valueOf(): " + str2);

        // 3. Using concatenation with an empty string
        String str3 = number + "";
        System.out.println("Using concatenation with \"\": " + str3);

        // 4. Using String.format()
        String str4 = String.format("%d", number);
        System.out.println("Using String.format(): " + str4);

        // 5. Using the new StringBuilder().append()
        String str5 = new StringBuilder().append(number).toString();
        System.out.println("Using StringBuilder.append(): " + str5);

        // 6. Using StringBuffer.append()
        String str6 = new StringBuffer().append(number).toString();
        System.out.println("Using StringBuffer.append(): " + str6);

        // Print verification if they are all equal
        boolean allEqual = str1.equals(str2) && str2.equals(str3) && str3.equals(str4)
                && str4.equals(str5) && str5.equals(str6);
        System.out.println("Are all methods equal? " + allEqual);
        assert(allEqual);
    }

    @Test
    public void problem15_StringTemplates() {
        int a = 5;
        int b = 10;

        // Using String templates
//        String result = STR."The sum of {a} and {b} is {a + b}.";
//        System.out.println(result); // Output: The sum of 5 and 10 is 15.

    }


    @Test
    public void problem16_CustomTemplateProcessor() {

  /*      StringTemplate.Processor<String, RuntimeException> uppercaseProcessor = template -> {
            StringBuilder result = new StringBuilder();
            // Concatenate fragments and interpolated values
            for (int i = 0; i < template.fragments().size(); i++) {
                result.append(template.fragments().get(i));
                if (i < template.values().size()) {
                    result.append(template.values().get(i));
                }
            }
            // Return the result in uppercase
            return result.toString().toUpperCase();
        }; */



        // Use the custom processor
//        String output = uppercaseProcessor."Hello, Alice! Welcome to Wonderland.";

  //      System.out.println(output);

//        assert(output.equals("HELLO, ALICE! WELCOME TO WONDERLAND."));
    }

}
