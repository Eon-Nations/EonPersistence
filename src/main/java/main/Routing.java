package main;

import com.google.gson.Gson;
import main.nosql.Database;
import main.player.*;
import main.request.UpdateBalanceRequest;
import main.request.UpdateBalanceResponse;
import main.request.UpdateJobRequest;
import main.request.UpdateJobResponse;
import main.uuid.UUIDRequest;
import main.uuid.UUIDResponse;
import main.uuid.UUIDRetrieval;
import spark.Request;
import spark.Response;
import utils.RequestStatus;

import java.util.logging.Logger;

public class Routing {
    private Routing() { }
    private static final int EVERYTHING_WORKED_HTTP = 200;
    private static final Logger logger = Logger.getLogger("Routing");

    private static void setupResponse(Response response) {
        response.type("application/json");
        response.status(EVERYTHING_WORKED_HTTP);
    }

    private static PlayerRequest obtainPlayerRequest(Request request) {
        return new Gson().fromJson(request.body(), PlayerRequest.class);
    }

    public static String handleUUIDRequest(Request request, Response response) {
        setupResponse(response);
        Gson gson = new Gson();
        UUIDRequest uuidRequest = gson.fromJson(request.body(), UUIDRequest.class);
        String uuidString = UUIDRetrieval.retrieveUUID(uuidRequest.username());
        UUIDResponse uuidResponse = new UUIDResponse(uuidString);
        return gson.toJson(uuidResponse);
    }

    public static String updatePlayer(Request request, Response response) {
        setupResponse(response);
        PlayerRequest playerRequest = obtainPlayerRequest(request);
        EonPlayer eonPlayer = DefaultPlayer.createDefaultPlayer(playerRequest.uuid());
        return new Gson().toJson(eonPlayer);
    }

    public static String retrievePlayer(Request request, Response response) {
        setupResponse(response);
        PlayerRequest playerRequest = obtainPlayerRequest(request);
        EonPlayer eonPlayer = Database.retrievePlayer(playerRequest.uuid());
        return new Gson().toJson(eonPlayer);
    }

    public static String retrieveBalance(Request request, Response response) {
        setupResponse(response);
        PlayerRequest playerRequest = obtainPlayerRequest(request);
        EonPlayer eonPlayer = Database.retrievePlayer(playerRequest.uuid());
        return new Gson().toJson(Balance.createBalance(eonPlayer.balance()));
    }

    public static String updateBalance(Request request, Response response) {
        setupResponse(response);
        UpdateBalanceRequest balanceRequest = new Gson().fromJson(request.body(), UpdateBalanceRequest.class);
        RequestStatus status = Database.updateBalance(balanceRequest.uuid(), balanceRequest.balance());
        UpdateBalanceResponse balanceResponse = new UpdateBalanceResponse(balanceRequest.balance(), status.name().toLowerCase());
        return new Gson().toJson(balanceResponse);
    }

    public static String retrieveJob(Request request, Response response) {
        setupResponse(response);
        PlayerRequest playerRequest = obtainPlayerRequest(request);
        EonPlayer eonPlayer = Database.retrievePlayer(playerRequest.uuid());
        return new Gson().toJson(eonPlayer.job());
    }

    public static String updateJob(Request request, Response response) {
        setupResponse(response);
        UpdateJobRequest jobRequest = new Gson().fromJson(request.body(), UpdateJobRequest.class);
        Job job = Job.createJob(jobRequest.job());
        RequestStatus status = Database.updateJob(jobRequest.uuidString(), job);
        UpdateJobResponse jobResponse = new UpdateJobResponse(status.name().toLowerCase(), jobRequest.job());
        return new Gson().toJson(jobResponse);
    }
}
