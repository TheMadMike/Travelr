package server.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvParser {
    public static List<String> parse(String raw) {
        String[] tokenArray = raw.split(",");
        List<String> tokens = Arrays.asList(tokenArray);
        return tokens.stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
