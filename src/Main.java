import impl.Des;
import impl.Key;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    private static final int SIZE = 16; // 8 - input as a String, 16 - input as a HexString

    public static void main(String[] args) {

        System.out.print(">> Enter file name: ");

        Scanner scanner = new Scanner(System.in);
        String pathToFile = scanner.next();

        System.out.print(">> Enter key as a hex number: ");

        Key key = new Key(new BigInteger(scanner.next(), 16).longValue());
        System.out.println("\nGenerated binary key: " + Long.toBinaryString(key.getValue()));

        try {
            String encryptedMessage = "";
            String message = readFileAsString(pathToFile);
            message = message.replace(" ", "");

            if(message.length() % 16 != 0) {
                int numberOfZeros = 16 - (message.length() % 16);
                message = message + leadWithZeros(numberOfZeros);
            }

            System.out.println("Message with following zeros: " + message + "\n");

            for (int i=0; i<message.length(); i+=SIZE) {
                System.out.println("Execution of " + (i/SIZE+1) + " block.\n");
                String block = message.substring(i, Math.min(i+SIZE, message.length()));
                Des des = new Des(key, block);
                encryptedMessage += des.execute();
                System.out.println("\n");
            }
            System.out.println("Encrypted message: " + encryptedMessage + "\n");

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
