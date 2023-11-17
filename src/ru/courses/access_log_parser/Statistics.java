package ru.courses.access_log_parser;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private List<LogEntry> logEntries;
    private HashSet<String> pages;
    private HashMap<String, Integer> osCount;
    private HashSet<String> badPages;
    private HashMap<String, Integer> browserCount;
    private int botCount;
    private int errorCount;
    private HashSet<String> uniqueIPs;

    public Statistics() {
        totalTraffic = 0;
        minTime = null;
        maxTime = null;
        logEntries = new ArrayList<>();
        pages = new HashSet<>();
        osCount = new HashMap<>();
        badPages = new HashSet<>();
        browserCount = new HashMap<>();
        botCount = 0;
        errorCount = 0;
        uniqueIPs = new HashSet<>();
    }

    public void addEntry(LogEntry entry) {
        if (!entry.getUserAgent().contains("bot")) {
            uniqueIPs.add(entry.getIpAddress());
        }

        if (minTime == null || entry.getDateTime().isBefore(minTime)) {
            minTime = entry.getDateTime();
        }

        if (maxTime == null || entry.getDateTime().isAfter(maxTime)) {
            maxTime = entry.getDateTime();
        }

        totalTraffic += entry.getDataSize();
        logEntries.add(entry);

        if (entry.getResponseCode() == 200) {
            pages.add(entry.getPath());
        } else if (entry.getResponseCode() == 404)
            badPages.add(entry.getPath());

        if (entry.getResponseCode() >= 400 && entry.getResponseCode() <= 599) {
            errorCount++;
        }

        UserAgent userAgent = new UserAgent(entry.getUserAgent());

        if (osCount.containsKey(userAgent.getOperatingSystem())) {
            osCount.put(userAgent.getOperatingSystem(), osCount.get(userAgent.getOperatingSystem()) + 1);
        } else {
            osCount.put(userAgent.getOperatingSystem(), 1);
        }

        if (browserCount.containsKey(userAgent.getBrowser())) {
            browserCount.put(userAgent.getBrowser(), browserCount.get(userAgent.getBrowser()) + 1);
        } else {
            browserCount.put(userAgent.getBrowser(), 1);
        }
        if ((userAgent.isBot())) {
            botCount++;
        }
    }

    public double getTrafficRate() {
        long hours = 0;

        if (minTime != null && maxTime != null) {
            hours = Duration.between(minTime, maxTime).toHours();
        }

        if (hours > 0) {
            return (double) totalTraffic / hours;
        } else {
            return 0;
        }
    }

    public HashSet<String> getAllPages() {
        return pages;
    }

    public HashSet<String> getAllBadPages() {
        return badPages;
    }

    public HashMap<String, Double> getOSStatistics() {
        HashMap<String, Double> osStatistics = new HashMap<>();
        int total = 0;

        for (int count : osCount.values()) {
            total += count;
        }

        for (String os : osCount.keySet()) {
            double percentage = (double) osCount.get(os) / total;
            osStatistics.put(os, percentage);
        }

        return osStatistics;
    }

    public HashMap<String, Double> getBrowserStatistics() {
        HashMap<String, Double> browserStatistics = new HashMap<>();
        int total = 0;

        for (int count : browserCount.values()) {
            total += count;
        }

        for (String os : browserCount.keySet()) {
            double percentage = (double) browserCount.get(os) / total;
            browserStatistics.put(os, percentage);
        }

        return browserStatistics;
    }

    public double getAverageVisitsPerHour() {

        int visits = 0;
        long hours = 0;


        for (Map.Entry<String, Integer> entry : browserCount.entrySet()) {
            visits += entry.getValue();
        }

        if (minTime != null && maxTime != null) {
            hours = Duration.between(minTime, maxTime).toHours();
        }

        if (hours > 0) {
            return (double) (visits - botCount) / hours;
        } else {
            return 0;
        }
    }

    public double getAverageErrorRequestsPerHour() {
        long hours = 0;

        if (minTime != null && maxTime != null) {
            hours = Duration.between(minTime, maxTime).toHours();
        }

        if (hours > 0) {
            return (double) errorCount / hours;
        } else {
            return 0;
        }
    }

    public double getAverageVisitsPerUser() {
        int totalVisitsByRealUsers = 0;

        for (int visits : browserCount.values()) {
            totalVisitsByRealUsers += visits;
        }

        return (double) (totalVisitsByRealUsers - botCount) / uniqueIPs.size();
    }

}