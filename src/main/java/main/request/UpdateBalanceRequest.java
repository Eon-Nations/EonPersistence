package main.request;

public class UpdateBalanceRequest {
    private String uuid;
    private double balance;

    public String uuid() {
        return uuid;
    }

    public double balance() {
        return balance;
    }
}
