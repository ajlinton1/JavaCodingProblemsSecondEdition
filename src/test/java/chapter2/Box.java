package chapter2;

class Box<T> {
    private T value;

    public Box(T value) {
        this.value = value;
    }

/*    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Box<?> box = (Box<?>) obj;
        return value.equals(box.value);
    } */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Box<?> box) {
            return value.equals(box.value);
        }
        return false;
    }


    @Override
    public int hashCode() {
        return value.hashCode();
    }
}