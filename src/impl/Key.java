package impl;

import java.util.Random;

public class Key {

    private long value;

    public Key(long value) {
        this.value = value;
    }

    private long generateKey() {
        Random random = new Random();
        return Math.abs(random.nextLong());
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Key{" +
                "value=" + value +
                '}';
    }
}
