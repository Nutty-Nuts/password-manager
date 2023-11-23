package algorithms;

import java.util.LinkedList;

public class RadixSort {
    public static void radixSort(String[] array) {
        int maxLength = findMaxLength(array);

        for (int i = maxLength - 1; i >= 0; i--) {
            countingSort(array, i);
        }
    }

    private static int findMaxLength(String[] array) {
        int maxLength = 0;
        for (String string : array) {
            if (string == null)
                break;
            maxLength = Math.max(maxLength, string.length());
        }
        return maxLength;
    }

    private static void countingSort(String[] array, int position) {
        final int ASCII_RANGE = 128; // ASCII characters
        LinkedList<String>[] buckets = new LinkedList[ASCII_RANGE];

        for (int i = 0; i < ASCII_RANGE; i++) {
            buckets[i] = new LinkedList<>();
        }

        // Place strings into buckets based on the character at the current position
        for (String s : array) {
            if (s == null)
                break;

            int charIndex;
            if (position < s.length()) {
                charIndex = s.charAt(position);
            } else {
                charIndex = 0;
            }
            buckets[charIndex].add(s);
        }

        int index = 0;
        for (LinkedList<String> bucket : buckets) {
            for (String s : bucket) {
                array[index++] = s;
            }
        }
    }

    static void print(String caption, String[] array) {
        System.out.println(caption);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();

    }
}
