package chapter4;

public sealed interface Shape permits Point, Circle, StringShape, Rectangle

{}
