package main.request;

import java.util.Map;

public class UpdateJobResponse {
    private String status;
    private Map<String, String> job;

    public UpdateJobResponse() {
        // Default constructor for GSON
    }

    public UpdateJobResponse(String status, Map<String, String> job) {
        this.status = status;
        this.job = job;
    }
}
