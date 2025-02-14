package chapter4;

import java.io.*;

public record Point(int x, int y) implements IPrintable, Serializable, Shape {

    public Point(int x) {
        this(x, 3);
    }

    public Point {
        if (x < 0) {
            throw new IllegalArgumentException("x must be non-negative");
        }
    }

    public String getStringValue() {
        return "This is a test";
    }

    static String getStringValueStatic() {
        return "This is a test static";
    }

    static String constantString = "This is a constant";

    // private String privateField;

    // private int counter = 0;

    // Static factory method for creating a point at the origin
    public static Point atOrigin() {
        return new Point(0, 0);
    }

    // Static factory method for creating a symmetric point (equal x and y)
    public static Point symmetric(int value) {
        return new Point(value, value);
    }

    @Override
    public void print() {
        System.out.println("Point: " + x + ", " + y);
    }
}
