package impl;

import java.nio.ByteBuffer;
import java.util.Random;

public class Key {

    private long value;

    public Key() {
        this.value = parseBytesToLong(parseStringToBytes("abcdefgh"));
//        this.value = generateKey();
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

    private byte[] parseStringToBytes(String s) {
        return s.getBytes();
    }

    private long parseBytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }
}
