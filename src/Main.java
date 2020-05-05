import impl.Des;
import impl.Key;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static final int SIZE = 16; // 8 - input as a String, 16 - input as a HexString

    public static void main(String[] args) {
        String pathToFile = "input.txt";
        try {
            String encryptedMessage = "";
            String message = readFileAsString(pathToFile);
            message = message.replace(" ", "");
            Key key = new Key();
            System.out.println("Generated key: " + Long.toBinaryString(key.getValue()) + "\n");

            for (int i=0; i<message.length(); i+=SIZE) {
                System.out.println("Execution of " + (i/SIZE+1) + " block.");
                String block = message.substring(i, Math.min(i+SIZE, message.length()));
                Des des = new Des(key, block);
                encryptedMessage += des.execute();
                System.out.println("\n");
            }
            System.out.println("Encrypted message: " + encryptedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
