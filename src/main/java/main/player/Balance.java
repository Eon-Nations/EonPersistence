package main.player;

public class Balance {
    double balance;

    public static Balance createBalance(double balance) {
        Balance newBalance = new Balance();
        newBalance.balance = balance;
        return newBalance;
    }

    public double balance() {
        return balance;
    }
}
