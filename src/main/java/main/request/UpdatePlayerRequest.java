package main.request;

import main.player.Job;

import java.util.Map;

public class UpdatePlayerRequest {
    private String uuidString;
    private double balance;
    private long playtime;
    private Map<String, String> job;

    public String uuidString() {
        return uuidString;
    }

    public double balance() {
        return balance;
    }

    public long playtime() {
        return playtime;
    }

    public Job job() {
        return Job.createJob(job);
    }
}
