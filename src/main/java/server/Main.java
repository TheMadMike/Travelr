package server;

import server.util.Logger;

import java.io.IOException;

public class Main {

    private final static int PORT = 55555;
    private static Gateway gateway;

    static {
        try {
            gateway = new Gateway(PORT);
            Logger.info("Listening on port " + PORT + "...");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        gateway.run();
    }
}
