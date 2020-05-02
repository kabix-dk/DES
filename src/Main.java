import impl.Des;

public class Main {

    public static void main(String[] args) {
        char[] key = Des.generateKey();
        for(char c : key) {
            String res = Integer.toBinaryString(c);
            System.out.println(("00000000" + res).substring(res.length()));
        }
    }
}
