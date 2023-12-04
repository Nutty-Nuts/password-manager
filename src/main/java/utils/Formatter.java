package utils;

import structures.Entry;
import structures.HashMap;

public class Formatter {
    public static void print(Entry[] entries, HashMap<String, String> storage, int start, int end, int length) {
        String[] accountRows = new String[length];
        String[] serviceRows = new String[length];
        String[] passwordRows = new String[length];

        for (int i = 0, index = start; i < length; i++, index++) {
            accountRows[i] = entries[index].account;
            serviceRows[i] = entries[index].service;
            passwordRows[i] = storage.get(entries[index].key());
        }

        Formatter.format(serviceRows, accountRows, passwordRows, length);
        System.out.println();
    }

    private static void format(String[] service, String[] account, String[] password, int rows) {
        int[] max = { max(service, "SERVICE"), max(account, "ACCOUNT"), max(password, "PASSWORD") };
        int numberSpacing = digits(rows) + 2;
        int entrySpacing = 4;
        int totalPadding = max[0] + max[1] + max[2] + (entrySpacing * 2) + numberSpacing;

        String spacing = "";
        for (int i = 0; i < entrySpacing; i++)
            spacing += " ";

        String headerPadding = "";
        for (int j = headerPadding.length(); j <= numberSpacing; j++)
            headerPadding += " ";
        System.out.println(headerPadding + pad("SERVICE", max[0]) + spacing + pad("ACCOUNT", max[1]) + spacing
                + pad("PASSWORD", max[2]));

        for (int i = 0; i < totalPadding; i++)
            System.out.print("-");
        System.out.println();

        for (int i = 0; i < rows; i++) {
            String[] entries = { pad(service[i], max[0]), pad(account[i], max[1]), pad(password[i], max[2]) };
            String numbering = (i + 1) + ".";

            for (int j = numbering.length(); j <= numberSpacing; j++)
                numbering += " ";

            System.out.println(numbering + entries[0] + spacing + entries[1] + spacing + entries[2]);
        }
    }

    private static String pad(String input, int padding) {
        String output = input;
        int amount = Math.max(0, (padding - input.length()));

        for (int i = 0; i < amount; i++) {
            output = output + " ";
        }

        return output;
    }

    private static int max(String[] arr, String caption) {
        int max = caption.length();
        for (String string : arr)
            max = Math.max(max, string.length());

        return max;
    }

    private static int digits(int number) {
        int output = 0;
        for (int exp = 1; number / exp > 0; exp *= 10)
            output++;

        return output;
    }
}
