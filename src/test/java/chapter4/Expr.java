package chapter4;

public sealed interface Expr permits Constant, BinaryOp, UnaryOp {}

