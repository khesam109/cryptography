package com.khesam.cryptography.common;

public class ChronometerService {

    private long startTime;
    private long endTime;

    private ChronometerService() {}

    public static ChronometerService getInstance() {
        return new ChronometerService();
    }

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public double durationInSec() {
        return (this.endTime - this.startTime) / 1E9;
    }
}
