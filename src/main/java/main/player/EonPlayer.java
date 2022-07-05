package main.player;


public class EonPlayer {
    private String uuidString;
    private double balance;
    private Job job;
    private long playtime;

    public static EonPlayer createPlayer(String uuidString, double balance, Job job, long playtime) {
        EonPlayer eonPlayer = new EonPlayer();
        eonPlayer.balance = balance;
        eonPlayer.uuidString = uuidString;
        eonPlayer.job = job;
        eonPlayer.playtime = playtime;
        return eonPlayer;
    }

    public String uuidString() {
        return uuidString;
    }

    public double balance() {
        return balance;
    }

    public Job job() {
        return job;
    }

    public long playtime() {
        return playtime;
    }
}
