package impl;

import java.nio.ByteBuffer;
import java.util.Random;

public class Key {

    private long value;

    public Key() {
        this.value = generateKey();
    }

    private long generateKey() {
        Random random = new Random();
        return Math.abs(random.nextLong());
    }

    public byte[] parseToBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(this.value);
        return buffer.array();
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
