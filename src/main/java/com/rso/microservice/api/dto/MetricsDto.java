package com.rso.microservice.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetricsDto {

    @JsonProperty("request_counter")
    private long requestCounter;

    @JsonProperty("max_execution_time")
    private double maxExecutionTime;

    @JsonProperty("min_execution_time")
    private double minExecutionTime;

    @JsonProperty("number_of_requests_in_last_day")
    private long numberOfRequestsInLastDay;

    public long getRequestCounter() {
        return requestCounter;
    }

    public void setRequestCounter(long requestCounter) {
        this.requestCounter = requestCounter;
    }

    public double getMaxExecutionTime() {
        return maxExecutionTime;
    }

    public void setMaxExecutionTime(double maxExecutionTime) {
        this.maxExecutionTime = maxExecutionTime;
    }

    public double getMinExecutionTime() {
        return minExecutionTime;
    }

    public void setMinExecutionTime(double minExecutionTime) {
        this.minExecutionTime = minExecutionTime;
    }

    public long getNumberOfRequestsInLastDay() {
        return numberOfRequestsInLastDay;
    }

    public void setNumberOfRequestsInLastDay(long numberOfRequestsInLastDay) {
        this.numberOfRequestsInLastDay = numberOfRequestsInLastDay;
    }
}
