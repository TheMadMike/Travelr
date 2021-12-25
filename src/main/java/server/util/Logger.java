package server.util;


//TODO: logging levels and logging to files
public class Logger {

    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void warning(String message) {
        System.out.println("[WARN] " + message);
    }

    public static void error(String message) {
        System.out.println("[ERROR] " + message);
    }

}
