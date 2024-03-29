package algorithms;

import structures.Entry;
import structures.LinkedList;

public class RadixSort {
    /*
     * 0: service
     * 1: account
     * 2: key
     */
    public static void sort(Entry[] array, int args) {
        int maxLength = findMaxLength(array);

        for (int i = maxLength - 1; i >= 0; i--) {
            countingSort(array, i, args);
        }
    }

    private static void countingSort(Entry[] array, int position, int args) {
        final int ASCII_RANGE = 128; // ASCII characters
        LinkedList<Entry>[] buckets = new LinkedList[ASCII_RANGE];

        for (int i = 0; i < ASCII_RANGE; i++) {
            buckets[i] = new LinkedList<>();
        }

        // Place strings into buckets based on the character at the current position
        for (Entry entry : array) {
            if (entry == null)
                break;
            String string = "";

            switch (args) {
                case 0 -> string = entry.service;
                case 1 -> string = entry.account;
                case 2 -> string = entry.key();
                default -> string = entry.key();
            }

            int charIndex;
            if (position < string.length()) {
                charIndex = string.charAt(position);
            } else {
                charIndex = 0;
            }
            buckets[charIndex].append(entry);
        }

        int index = 0;
        for (LinkedList<Entry> bucket : buckets) {
            for (int i = 0; i < bucket.length(); i++) {
                array[index++] = bucket.get(i).getValue();
            }
        }
    }

    private static int findMaxLength(Entry[] array) {
        int maxLength = 0;
        for (Entry entry : array) {
            if (entry == null)
                break;
            String string = entry.account;
            maxLength = Math.max(maxLength, string.length());
        }
        return maxLength;
    }
}
