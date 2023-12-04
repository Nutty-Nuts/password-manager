package utils;

public class Logger {
    private boolean visibility;

    public Logger(boolean visibility) {
        this.visibility = visibility;
    }

    public void log(String caption, String message) {
        if (visibility) {
            System.out.printf("%s:, %s\n", caption, message);
        }
    }

    public void log(String message) {
        if (visibility) {
            System.out.printf("%s\n", message);
        }
    }
}
