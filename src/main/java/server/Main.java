package server;

import java.io.IOException;

public class Main {

    private final static int PORT = 55555;
    private static Gateway gateway;

    static {
        try {
            gateway = new Gateway(PORT);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        gateway.run();
    }
}
