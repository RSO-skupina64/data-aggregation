package com.rso.microservice.service;

import com.rso.microservice.api.dto.MetricsDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetricsService {

    private long requestCounter = 0;
    private double maxExecutionTime = -1;
    private double minExecutionTime = -1;
    private List<LocalDateTime> requestsInLastDay = new ArrayList<>();

    public void increaseRequestCounterAndLogDate() {
        requestCounter++;
        requestsInLastDay.add(LocalDateTime.now());
    }

    public void measureExecutionTime(long start) {
        long time = System.currentTimeMillis() - start;
        if (time > maxExecutionTime)
            maxExecutionTime = time;
        else if (time < minExecutionTime || minExecutionTime == -1)
            minExecutionTime = time;
    }

    public MetricsDto returnMetrics() {
        MetricsDto metrics = new MetricsDto();
        metrics.setRequestCounter(requestCounter);

        metrics.setMaxExecutionTime(maxExecutionTime);
        metrics.setMinExecutionTime(minExecutionTime);

        LocalDateTime lastDay = LocalDateTime.now().minusDays(1);
        requestsInLastDay = requestsInLastDay.stream().filter(date ->
                date.isAfter(lastDay)).collect(Collectors.toList());
        metrics.setNumberOfRequestsInLastDay(requestsInLastDay.size());

        return metrics;
    }


}
