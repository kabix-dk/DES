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

        // Divide message to left and right parts
        int l = (int) (messageValue >> 32);
        int r = (int) (messageValue & 0xFFFFFFFFL);

        long encryptedMessage = encrypt(l, r, subKeys);
        System.out.println(Long.toBinaryString(encryptedMessage));
        String result = new String(parseLongToBytes(encryptedMessage));
        System.out.println(result);
    }

    private long encrypt(int l, int r, long[] subKeys) {
        for (int i=0; i<16; i++) {
            int prevL = l;
            l = r;
            r = prevL ^ f(r, subKeys[i]);
        }
        long result = (r & 0xFFFFFFFFL) << 32 | (l & 0xFFFFFFFFL);
        return permute(Permutations.getFinalPermutation(), 64, result);
    }

    private int f(int R, long key) {
        long extendedR = permute(Permutations.getExtensionPermutation(), 32, R);
        long xor = extendedR ^ key;
        int result = 0;
        int[][] S = Permutations.getS();

        for (int i=0; i<8; i++) {
            result >>>= 4;
            int position = (int) (xor & 0x3F);
            position = (position & 0x20 | ((position & 0x01) << 4) | ((position & 0x1E) >> 1));
            int sValue = S[7-i][position];
            result |= sValue << 28;
            xor >>= 6;
        }

        return (int) permute(Permutations.getPermutationFunction(), 32, result);
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

    private byte[] parseLongToBytes(long l) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(l);
        return buffer.array();
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
