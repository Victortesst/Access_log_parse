package ru.courses.access_log_parser;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private List<LogEntry> logEntries;

    public Statistics() {
        totalTraffic = 0;
        minTime = null;
        maxTime = null;
        logEntries = new ArrayList<>();
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
}