package main;

import java.util.logging.Logger;

import static spark.Spark.*;

public class PersistentApplication {
    private static final Logger logger = Logger.getLogger("Test");

    public static void main(String[] args) {
        setupServerConfiguration();
        before("/*", (req, res) -> logger.info("Received API call"));
        get("/uuid/:username", Routing::handleUUIDRequest);
        registerPlayerEndpoints();
        registerBalanceEndpoints();
        registerJobEndpoints();
    }

    private static void registerPlayerEndpoints() {
        get("/player/:uuid", Routing::retrievePlayer);
        post("/player", Routing::updatePlayer);
    }

    private static void registerBalanceEndpoints() {
        get("/balance/:uuid", Routing::retrieveBalance);
        post("/balance", Routing::updateBalance);
    }

    private static void registerJobEndpoints() {
        get("/job/:uuid", Routing::retrieveJob);
        post("/job", Routing::updateJob);
    }

    private static void setupServerConfiguration() {
        final int PORT_NUM = 35353;
        port(PORT_NUM);
    }
}
