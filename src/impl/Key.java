package impl;

import java.math.BigInteger;
import java.util.Random;

public class Key {

    private long value;

    public Key() {
        this.value = new BigInteger("07399d5955ffd2c2", 16).longValue(); // Key as a HexNumber
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
