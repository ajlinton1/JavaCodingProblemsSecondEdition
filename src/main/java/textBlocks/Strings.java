package textBlocks;

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

}
