package main.request;

import java.util.Map;

public class UpdateJobRequest {
    private String uuidString;
    private Map<String, String> job;

    public String uuidString() {
        return uuidString;
    }

    public Map<String, String> job() {
        return job;
    }
}
