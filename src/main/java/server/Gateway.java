package server;

import server.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Gateway implements Runnable {
    private ServerSocket socket;
    private boolean running;
    private Map<String, Controller> controllers = new HashMap<>();

    public Gateway(int port) throws IOException {
        socket = new ServerSocket(port);
        registerControllers();
    }

    private void registerControllers() {
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
        Logger.info("Request received, [raw]: " + rawRequest);

        try {
            Request request = RequestParser.parse(rawRequest);

            if(!controllers.containsKey(request.getControllerName())) {
                throw new Exception("Controller not found.");
            }

            controllers.get(request.getControllerName()).handleRequest(client, request);

        } catch (Exception e) {

            var out = new PrintWriter(client.getOutputStream(), true);
            out.println("ERROR 1");
            out.println(e.getMessage());
        }

    }

    public void stop() {
        running = false;
    }
}
