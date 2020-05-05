import impl.Des;
import impl.Key;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static final String PATH = "input.txt";

    private static final int SIZE = 16; // 8 - input as a String, 16 - input as a HexString
    private static final int ENCRYPT = 1; // Encryption mode
    private static final int DECRYPT = 2; // Decryption mode

    public static void main(String[] args) {

        try {
            String encryptedMessage = "";
            String message = readFileAsString(PATH);
            message = message.replace(" ", "");
            Key key = new Key();
            System.out.println("Generated key: " + Long.toBinaryString(key.getValue()) + "\n");

            if(message.length() % 16 != 0) {
                int numberOfZeros = 16 - (message.length() % 16);
                message = message + leadWithZeros(numberOfZeros);
            }

            System.out.println("Message with following zeros: " + message + "\n");

            for (int i=0; i<message.length(); i+=SIZE) {
                System.out.println("Execution of " + (i/SIZE+1) + " block.");
                String block = message.substring(i, Math.min(i+SIZE, message.length()));
                Des des = new Des(key, block, ENCRYPT);
                encryptedMessage += des.execute();
                System.out.println("\n");
            }
            System.out.println("\nEncrypted message: " + encryptedMessage + "\n");

            String decryptedMessage = "";
            for (int i=0; i<encryptedMessage.length(); i+=SIZE) {
                System.out.println("Execution of " + (i/SIZE+1) + " block.");
                String block = message.substring(i, Math.min(i+SIZE, message.length()));
                Des des = new Des(key, block, DECRYPT);
                decryptedMessage += des.execute();
                System.out.println("\n");
            }
            System.out.println("\nDecrypted message: " + decryptedMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    private static String leadWithZeros(int numb) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<numb; i++) {
            sb.append("0");
        }
        return sb.toString();
    }
}
