package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Gate implements Runnable {
    private ServerSocket socket;
    private boolean running;

    public Gate(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    @Override
    public void run() {
        running = true;

        while(running) {
            listenForRequests();
        }
    }

    private void listenForRequests() {
        try {
            Socket client = socket.accept();
            processRequest(client);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void processRequest(Socket client) throws IOException {
        var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String rawRequest = in.readLine();
    }

    public void stop() {
        running = false;
    }
}
