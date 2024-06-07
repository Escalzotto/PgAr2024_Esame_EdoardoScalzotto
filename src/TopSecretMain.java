import java.util.Scanner;

public class TopSecretMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        String[] encryptedWords = new String[N];
        for (int i = 0; i < N; i++) {
            encryptedWords[i] = scanner.next();
        }

        String[] keys = new String[N];
        for (int i = 0; i < N; i++) {
            keys[i] = scanner.next();
        }

        for (int i = 0; i < N; i++) {
            String decryptedWord = decrypt(encryptedWords[i], keys[i]);
            System.out.println(decryptedWord);
        }

        scanner.close();
    }

    private static String decrypt(String encryptedWord, String key) {
        StringBuilder decryptedWord = new StringBuilder();

        for (int i = 0; i < encryptedWord.length(); i++) {
            char encryptedChar = encryptedWord.charAt(i);
            char keyChar = key.charAt(i % key.length());

            int shift = keyChar - 'a';

            char decryptedChar;
            if (Character.isUpperCase(encryptedChar)) {
                decryptedChar = (char) ('A' + (encryptedChar - 'A' - shift + 26) % 26);
            } else if (Character.isLowerCase(encryptedChar)) {
                decryptedChar = (char) ('a' + (encryptedChar - 'a' - shift + 26) % 26);
            } else {
                decryptedChar = encryptedChar;
            }

            decryptedWord.append(decryptedChar);
        }

        return decryptedWord.toString();
    }

}