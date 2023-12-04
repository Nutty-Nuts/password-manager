package algorithms;

public class Crypto {
    public static final String KEY = "pneumonoultramicroscopicsilicovolcanoconiosis";

    public static String encrypt(String message) {
        String output = "";
        for (int i = 0; i < message.length(); i++) {
            char shifted = (char) (message.charAt(i) + KEY.charAt(i % KEY.length()));
            output = output + shifted;
        }

        return output;
    }

    public static String decrypt(String message) {
        String output = "";
        for (int i = 0; i < message.length(); i++) {
            char shifted = (char) (message.charAt(i) - KEY.charAt(i % KEY.length()));
            output = output + shifted;
        }

        return output;
    }
}
