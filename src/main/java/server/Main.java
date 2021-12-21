package server;

import java.io.IOException;

public class Main {

    private static int port = 55555;
    private static Gate gate;

    static {
        try {
            gate = new Gate(port);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        gate.run();
    }
}