package server.util;

public enum RequestType {
    GET("GET"),
    SET("SET"),
    REMOVE("REMOVE"),
    UPDATE("UPDATE");

    private String string;

    public String getString() {
        return string;
    };

    RequestType(String string) {
        this.string = string;
    }
}
