package algorithms;

public class Crypto {
    public static String encrypt(String text) {
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                int base = Character.isUpperCase(c) ? 'A' : 'a';
                encryptedText.append((char) ((c - base + 13) % 26 + base));
            } else if (Character.isDigit(c)) {
                encryptedText.append((char) ((c - '0' + 5) % 10 + '0')); // Rotate digits by 5
            } else {
                encryptedText.append(c);
            }
        }

        return encryptedText.toString();
    }

    public static String decrypt(String text) {
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                int base = Character.isUpperCase(c) ? 'A' : 'a';
                decryptedText.append((char) ((c - base + 13) % 26 + base));
            } else if (Character.isDigit(c)) {
                decryptedText.append((char) ((c - '0' + 10 - 5) % 10 + '0')); // Reverse rotation of digits
            } else {
                decryptedText.append(c);
            }
        }

        return decryptedText.toString();
    }
}
