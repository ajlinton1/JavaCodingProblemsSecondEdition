package chapter2;

public class ReceiverParameterExample {
    private int value;

    public ReceiverParameterExample(int value) {
        this.value = value;
    }

    public void setValue(ReceiverParameterExample this, int value) {
        this.value = value; // Refers to the instance variable
    }

    public int getValue(ReceiverParameterExample this) {
        return this.value; // Refers to the instance variable
    }

}