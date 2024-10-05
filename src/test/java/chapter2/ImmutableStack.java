package chapter2;

public final class ImmutableStack<T> {
    private final T head;
    private final ImmutableStack<T> tail;
    private final int size;

    private ImmutableStack(T head, ImmutableStack<T> tail) {
        this.head = head;
        this.tail = tail;
        this.size = (tail == null) ? 1 : tail.size + 1;
    }

    public ImmutableStack() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public ImmutableStack<T> push(T value) {
        return new ImmutableStack<>(value, this);
    }

    public ImmutableStack<T> pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty stack");
        }
        return tail;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek from an empty stack");
        }
        return head;
    }

    public int size() {
        return size;
    }
}