package main.uuid;

public class UUIDResponse {
    private String uuid;

    public UUIDResponse() { }

    public UUIDResponse(String uuid) {
        this.uuid = uuid;
    }

    public String uuid() {
        return uuid;
    }
}
