package server.util;

public class Request {
    private RequestType type;
    private String body, controllerName, endpointName;

    public Request(RequestType type, String body, String controllerName, String endpointName) {
        this.type = type;
        this.body = body;
        this.controllerName = controllerName;
        this.endpointName = endpointName;
    }

    public Request() {
        this(RequestType.GET, "", "", "");
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", body='" + body + '\'' +
                ", controllerName='" + controllerName + '\'' +
                ", endpointName='" + endpointName + '\'' +
                '}';
    }
}
