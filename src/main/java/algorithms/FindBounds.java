package algorithms;

public class FindBounds {
    public static int lowerBounds(String[] array, String term, int length) {
        term = term.toLowerCase();
        int jump = (int) Math.sqrt(length);
        char firstChar = term.charAt(0);

        int i = jump;
        for (; i < length; i = i + jump) {
            if (firstChar <= array[i].toLowerCase().charAt(0)) {
                break;
            }
        }

        if (i >= length) {
            i = length - 1;
        }
        if (!(firstChar <= array[i].toLowerCase().charAt(0))) {
            return -1;
        }

        i = Math.max(0, i - jump);
        for (int j = 0; j <= jump && i < length; j++, i++) {
            if (firstChar <= array[i].toLowerCase().charAt(0)) {
                break;
            }
        }

        for (; i < length; i++) {
            if (array[i].toLowerCase().charAt(0) != firstChar) {
                break;
            }
            if ((array[i].toLowerCase()).substring(0, Math.min(array[i].length(), term.length())).equals(term)) {
                return i;
            }
        }

        return -1;
    }

    public static int upperBounds(String[] array, String term, int length) {
        term = term.toLowerCase();
        int jump = (int) Math.sqrt(length);
        char firstChar = term.charAt(0);

        int i = length - 1 - jump;
        for (; i >= 0; i = i - jump) {
            if (firstChar >= array[i].toLowerCase().charAt(0)) {
                break;
            }
        }

        if (i <= 0) {
            i = 0;
        }
        if (!(firstChar >= array[i].toLowerCase().charAt(0))) {
            return -1;
        }

        i = Math.min(i + jump, length - 1);
        for (int j = 0; j <= jump && i >= 0; j++, i--) {
            if (firstChar >= array[i].toLowerCase().charAt(0)) {
                break;
            }
        }

        for (; i >= 0; i--) {
            if (array[i].toLowerCase().charAt(0) != firstChar) {
                break;
            }
            if ((array[i].toLowerCase()).substring(0, Math.min(array[i].length(), term.length())).equals(term)) {
                return i;
            }
        }

        return -1;
    }
}
