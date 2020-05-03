package impl;

import utils.Permutations;

import java.nio.ByteBuffer;

public class Des {

    private String message;
    private Key key;

    public Des(Key key, String message) {
        this.key = key;
        this.message = message;
    }

    public void execute() {
        long messageValue = parseBytesToLong(parseStringToBytes(message));
        long keyValue = key.getValue();
        long[] subKeys;

        // Initial Permutation
        messageValue = permute(Permutations.getInitialPermutation(), 64, messageValue);

        // Permuted choice
        keyValue = permute(Permutations.getPermutedChoice(), 64, keyValue);

        // Divide, shift and union key
        subKeys = divideShiftAndUnionKey(keyValue);

    }

    private long[] divideShiftAndUnionKey(long value) {
        long[] resultKeys = new long[16];

        int c = (int) (value >> 28);
        int d = (int) (value & 0x0FFFFFFF);

        int[] shiftTable = Permutations.getShiftTab();
        for (int i=0; i<16; i++) {
            if(shiftTable[i] == 1) {
                c = ((c << 1) & 0x0FFFFFFF) | (c >> 27);
                d = ((d << 1) & 0x0FFFFFFF) | (d >> 27);
            } else {
                c = ((c << 2) & 0x0FFFFFFF) | (c >> 26);
                d = ((d << 2) & 0x0FFFFFFF) | (d >> 26);
            }
            long result = (c & 0xFFFFFFFFL) << 28 | (d & 0xFFFFFFFFL);
            resultKeys[i] = permute(Permutations.getPermutedChoiceTwo(), 56, result);
        }

        return resultKeys;
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

    private long permute(int[] table, int size, long value) {
        long result = 0;
        for (int i=0; i<table.length; i++) {
            int pos = size - table[i];
            result = (result << 1) | (value >> pos & 0x01);
        }
        return result;
    }
}
