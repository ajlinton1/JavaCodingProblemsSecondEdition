package chapter4;

public record BinaryOp(String operator, Expr left, Expr right) implements Expr {}

