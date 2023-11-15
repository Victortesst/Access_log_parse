package ru.courses.access_log_parser;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private List<LogEntry> logEntries;

    private HashSet<String> pages;

    private HashMap<String, Integer> osCount;

    public Statistics() {
        totalTraffic = 0;
        minTime = null;
        maxTime = null;
        logEntries = new ArrayList<>();
        pages = new HashSet<>();
        osCount = new HashMap<>();
    }

    public void addEntry(LogEntry entry) {
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

            UserAgent userAgent = new UserAgent(entry.getUserAgent());
            if (osCount.containsKey(userAgent.getOperatingSystem())) {
                osCount.put(userAgent.getOperatingSystem(), osCount.get(userAgent.getOperatingSystem()) + 1);
            } else {
                osCount.put(userAgent.getOperatingSystem(), 1);
            }
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
}