package main.request;

import utils.RequestStatus;

public class UpdatePlayerResponse {
    private String status;

    public static UpdatePlayerResponse createResponse(RequestStatus status) {
        UpdatePlayerResponse response = new UpdatePlayerResponse();
        response.status = status.name().toLowerCase();
        return response;
    }
}
