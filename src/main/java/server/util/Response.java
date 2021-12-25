package server.util;

public class Response {
    private Type type;
    private String rawStringResponse;

    public Response(Type type, String rawStringResponse) {
        this.type = type;
        this.rawStringResponse = rawStringResponse;
    }

    public enum Type {
        OK,
        ERROR
    };

    @Override
    public String toString() {
        return (type == Type.OK ? "OK" : "ERROR") + " "
                + rawStringResponse.chars().filter(c -> c == '\n').count() + "\n"
                + rawStringResponse + "\n";
    }
}
