package main.player;

import java.util.Map;

public class Job {
    private String name;
    private double exp;

    public static Job createJob(String name, double exp) {
        Job job = new Job();
        job.exp = exp;
        job.name = name;
        return job;
    }

    public static Job createJob(Map<String, String> serializedJob) {
        Job job = new Job();
        job.exp = Double.parseDouble(serializedJob.get("exp"));
        job.name = serializedJob.get("name");
        return job;
    }

    public String name() {
        return name;
    }

    public double exp() {
        return exp;
    }
}
