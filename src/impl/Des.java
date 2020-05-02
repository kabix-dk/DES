package impl;

public class Des {

    public static char[] generateKey() {
        char[] key = new char[8];
        for (int i=0; i<8; i++) {
            key[i] = (char) (Math.random()*255);
        }
        return key;
    }

}
