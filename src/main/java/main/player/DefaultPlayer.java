package main.player;

public class DefaultPlayer {
    private static final double DEFAULT_BALANCE = 0.0;
    private static final Job DEFAULT_JOB = Job.createJob("Miner", 0.0);
    private static final long DEFAULT_PLAYTIME = 0L;

    private DefaultPlayer() { }

    public static EonPlayer createDefaultPlayer(String uuidString) {
        return EonPlayer.createPlayer(uuidString, DEFAULT_BALANCE, DEFAULT_JOB, DEFAULT_PLAYTIME);
    }
}
