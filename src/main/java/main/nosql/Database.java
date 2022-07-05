package main.nosql;

import main.player.DefaultPlayer;
import main.player.EonPlayer;
import main.player.Job;
import oracle.nosql.driver.*;
import oracle.nosql.driver.iam.SignatureProvider;
import utils.RequestStatus;

import java.io.IOException;
import java.util.logging.Logger;

public class Database {
    private Database() { }
    private static final Logger log = Logger.getLogger("Database");
    private static final NoSQLHandle handle = createNoSQLConnection();
    private static final String COMPARTMENT_NAME = "mceonsurvival";

    public static NoSQLHandle createNoSQLConnection() {
        try {
            AuthorizationProvider provider = new SignatureProvider();
            NoSQLHandleConfig config = new NoSQLHandleConfig(Region.US_PHOENIX_1, provider);
            config.setDefaultCompartment(COMPARTMENT_NAME);
            return NoSQLHandleFactory.createNoSQLHandle(config);
        } catch (IOException e) {
            log.severe("Database connection failed to initialize. Make sure you set up the database correctly");
            return null;
        }
    }

    public static EonPlayer retrievePlayer(String uuidString) {
        return DefaultPlayer.createDefaultPlayer(uuidString);
    }

    public static RequestStatus updateBalance(String uuidString, double balance) {
        return RequestStatus.UNIMPLEMENTED;
    }

    public static RequestStatus updateJob(String uuidString, Job job) { return RequestStatus.UNIMPLEMENTED; }

    public static void closeNoSQLHandle(NoSQLHandle noSQLHandle) {
        noSQLHandle.close();
    }
}
