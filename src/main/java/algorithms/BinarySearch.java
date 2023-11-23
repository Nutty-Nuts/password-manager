package algorithms;

/**
 * BinarySearch
 */
public class BinarySearch {
    public static int binarySearch(String[] array, String target, int size) {
        int left = 0;
        int right = size - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int res = target.compareTo(array[mid]);

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