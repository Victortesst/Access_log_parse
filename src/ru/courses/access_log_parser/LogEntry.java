package ru.courses.access_log_parser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LogEntry {
    private final String ipAddress;
    private final LocalDateTime dateTime;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final String userAgent;

    public LogEntry(String logString) {

        String[] parts = logString.split(" ");
        String timestampString = parts[3].substring(1);


        ipAddress = parts[0];

        method = HttpMethod.valueOf(parts[5].substring(1));

        responseCode = Integer.parseInt(parts[8]);
        responseSize = Integer.parseInt(parts[9]);
        referer = parts[10];
        path = parts[6];

        List<String> userAgentValues = new ArrayList<>();
        if (parts.length > 12)
            for (int i = 11; i < parts.length; i++) {
                userAgentValues.add(parts[i]);
            }

        userAgent = String.join(" ", userAgentValues);


        dateTime = LocalDateTime.parse(timestampString, DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH));

    }

    public String getIpAddress() {
        return ipAddress;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public HttpMethod getMethod() {

        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public long getDataSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

}

