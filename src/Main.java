import impl.Des;
import impl.Key;

public class Main {

    public static void main(String[] args) {
        String message = "abcdefgh";
        Des des = new Des(new Key(), message);
        des.execute();
    }
}
