package main.request;

public class UpdateBalanceResponse {
    private double balance;
    private String status;

    public UpdateBalanceResponse() { }

    public UpdateBalanceResponse(double balance, String status) {
        this.balance = balance;
        this.status = status;
    }

    public double balance() {
        return balance;
    }

    public String status() {
        return status;
    }
}
