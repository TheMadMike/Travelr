package server.util;
import java.util.Arrays;

public class RequestParser {

    public static Request parse(String rawRequest) throws ParserException {
        String[] splitBySpaces = rawRequest.split("\\s+");

        if (splitBySpaces.length < 2) {
            throw new ParserException("Request format invalid. Valid request format: <method> /<controller>/<endpoint> <body (optional)>");
        }

        RequestType type = parseRequestType(splitBySpaces[0]);

        String[] splitBySlash = splitBySpaces[1].split("/");

        if(splitBySlash.length != 3) {
            throw new ParserException("Invalid path format. Valid request path format: /<controller>/<endpoint>");
        }

        String controllerName = splitBySlash[1];
        String endpointName = splitBySlash[2];

        String body = extractBody(splitBySpaces);

        return new Request(type, body, controllerName, endpointName);
    }

    private static String extractBody(String[] splitBySpaces) {
        return Arrays.stream(splitBySpaces)
                .skip(2)
                .reduce((first, second) -> first + " " + second)
                .orElse("");
    }

    private static RequestType parseRequestType(String typeString) throws ParserException {
        if(Arrays.stream(RequestType.values()).noneMatch(type -> type.getString().equals(typeString))) {
            throw new ParserException("Invalid request method. Available methods: GET, SET, REMOVE, UPDATE");
        }

        return RequestType.valueOf(typeString);
    }
}
