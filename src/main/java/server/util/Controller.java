package server.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public abstract class Controller {
    protected Map<String, Runnable> endpoints = new HashMap<>();
    protected String rawRequestBodyBuffer;
    protected Socket socket;
    private PrintWriter out;

    public Controller() {
        initialize();
    }

    // setup endpoints here
    protected abstract void initialize();

    public synchronized void handleRequest(Socket socket, Request request) throws IOException {
        rawRequestBodyBuffer = request.getBody();
        this.socket = socket;

        out = new PrintWriter(socket.getOutputStream(), true);

        String endpointKey = request.getType().getString() + " " + request.getEndpointName();
        if(!endpoints.containsKey(endpointKey)) {
            respond(new Response(Response.Type.ERROR, "Not found"));
            return;
        }

        endpoints.get(endpointKey).run();
    }

    protected void respond(Response response) {
        out.println(response.toString());
    }


}
