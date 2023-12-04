package algorithms;

import structures.Entry;

/**
 * BinarySearch
 */
public class BinarySearch {
    public static int binarySearch(Entry[] array, String target, int size) {
        int left = 0;
        int right = size - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            String key = array[mid].key();
            int res = target.compareTo(key);

            if (res == 0) {
                return mid;
            } else if (res < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1; // Element not found
    }
}