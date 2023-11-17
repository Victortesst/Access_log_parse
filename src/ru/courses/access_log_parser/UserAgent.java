package ru.courses.access_log_parser;

public class UserAgent {
    private final String operatingSystem;
    private final String browser;
    private final boolean isBot;

    public UserAgent(String userAgentString) {
        String[] components = userAgentString.split(" ");

        operatingSystem = getOperatingSystem(components);
        browser = getBrowser(components);
        isBot = userAgentString.contains("bot");
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean isBot() {
        return isBot;
    }

    private String getOperatingSystem(String[] components) {
        String osToken = "";

        for (String component : components) {
            if (component.contains("(")) {
                osToken = component.substring(1);
                break;
            }
        }

        if (osToken.contains("Windows")) {
            return "Windows";
        } else if (osToken.contains("Mac")) {
            return "macOS";
        } else if (osToken.contains("Linux")) {
            return "Linux";
        } else {
            return "Unknown";
        }
    }

    private String getBrowser(String[] components) {

        String browserToken = components[components.length - 1];

        if (browserToken.contains("Edge")) {
            return "Edge";
        } else if (browserToken.contains("Firefox")) {
            return "Firefox";
        } else if (browserToken.contains("Chrome")) {
            return "Chrome";
        } else if (browserToken.contains("Opera")) {
            return "Opera";
        } else {
            return "Other";
        }
    }
}