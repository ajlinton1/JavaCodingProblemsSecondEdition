package textBlocks;

import java.util.HashMap;

public class Strings {

    public static String concatenating() {
        String s = "";
        for (int i=0; i<1000; i++) {
            Integer i1 = Integer.valueOf(i);
            s = s + i1.toString();
        }
        return s;
    }

    public static String concatenatingStringBuilder() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<1000; i++) {
            sb.append(i);
        }
        return sb.toString();
    }

    /**
     * Method to check if two strings are isomorphic.
     * @param s1 First string
     * @param s2 Second string
     * @return true if the strings are isomorphic, false otherwise
     */
    public static boolean areIsomorphic(String s1, String s2) {
        // If lengths are different, they cannot be isomorphic
        if (s1.length() != s2.length()) {
            return false;
        }

        // Create maps for character mapping in both directions
        HashMap<Character, Character> mapS1toS2 = new HashMap<>();
        HashMap<Character, Character> mapS2toS1 = new HashMap<>();

        // Iterate through the strings
        for (int i = 0; i < s1.length(); i++) {
            char charS1 = s1.charAt(i);
            char charS2 = s2.charAt(i);

            // Check consistency of mapping from s1 -> s2
            if (mapS1toS2.containsKey(charS1)) {
                if (mapS1toS2.get(charS1) != charS2) {
                    return false; // Conflict in mapping
                }
            } else {
                mapS1toS2.put(charS1, charS2);
            }

            // Check consistency of mapping from s2 -> s1
            if (mapS2toS1.containsKey(charS2)) {
                if (mapS2toS1.get(charS2) != charS1) {
                    return false; // Conflict in mapping
                }
            } else {
                mapS2toS1.put(charS2, charS1);
            }
        }

        // If we process the entire strings without conflicts, they are isomorphic
        return true;
    }

}
