package main;

import com.google.gson.Gson;
import main.nosql.Database;
import main.player.*;
import main.request.*;
import main.uuid.UUIDResponse;
import main.uuid.UUIDRetrieval;
import spark.Request;
import spark.Response;
import utils.RequestStatus;
import utils.RequestValidation;

import java.util.Optional;

public class Routing {
    private Routing() { }
    private static final int EVERYTHING_WORKED_HTTP = 200;
    private static final int JSON_VALIDATION_FAILED = 400;


    private static <T> Optional<T> setupResponse(Request request, Response response, Class<T> requestType) {
        response.type("application/json");
        if (!RequestValidation.validate(request.body(), requestType)) {
            response.status(JSON_VALIDATION_FAILED);
            return Optional.empty();
        }
        response.status(EVERYTHING_WORKED_HTTP);
        T requestObject = new Gson().fromJson(request.body(), requestType);
        return Optional.of(requestObject);
    }

    private static void setupResponse(Response response) {
        response.type("application/json");
    }

    private static PlayerRequest obtainPlayerRequest(Request request) {
        return new Gson().fromJson(request.body(), PlayerRequest.class);
    }

    public static String handleUUIDRequest(Request request, Response response) {
        setupResponse(response);
        Gson gson = new Gson();
        String username = request.params(":username");
        String uuidString = UUIDRetrieval.retrieveUUID(username);
        UUIDResponse uuidResponse = new UUIDResponse(uuidString);
        return gson.toJson(uuidResponse);
    }

    public static String updatePlayer(Request request, Response response) {
        Optional<UpdatePlayerRequest> maybePlayerReq = setupResponse(request, response, UpdatePlayerRequest.class);
        if (maybePlayerReq.isPresent()) {
            UpdatePlayerRequest playerRequest = maybePlayerReq.get();
            EonPlayer eonPlayer = EonPlayer.createPlayer(playerRequest.uuidString(), playerRequest.balance(), playerRequest.job(), playerRequest.playtime());
            RequestStatus requestStatus = Database.updatePlayer(eonPlayer);
            return new Gson().toJson(UpdatePlayerResponse.createResponse(requestStatus));
        } else {
            EonPlayer eonPlayer = DefaultPlayer.createDefaultPlayer("Invalid");
            return new Gson().toJson(eonPlayer);
        }
    }

    public static String retrievePlayer(Request request, Response response) {
        setupResponse(response);
        String uuidString = request.params(":uuid");
        EonPlayer eonPlayer = Database.retrievePlayer(uuidString);
        return new Gson().toJson(eonPlayer);
    }

    public static String retrieveBalance(Request request, Response response) {
        setupResponse(response);
        PlayerRequest playerRequest = obtainPlayerRequest(request);
        EonPlayer eonPlayer = Database.retrievePlayer(playerRequest.uuid());
        return new Gson().toJson(Balance.createBalance(eonPlayer.balance()));
    }

    public static String updateBalance(Request request, Response response) {
        Optional<UpdateBalanceRequest> playerRequest = setupResponse(request, response, UpdateBalanceRequest.class);
        if (playerRequest.isPresent()) {
            UpdateBalanceRequest updateRequest = playerRequest.get();
            RequestStatus status = Database.updateBalance(updateRequest.uuid(), updateRequest.balance());
            UpdateBalanceResponse balanceResponse = new UpdateBalanceResponse(updateRequest.balance(), status.name().toLowerCase());
            return new Gson().toJson(balanceResponse);
        } else {
            final double BAD_BALANCE = -1.0;
            String status = RequestStatus.FAILURE.name().toLowerCase();
            UpdateBalanceResponse balanceResponse = new UpdateBalanceResponse(BAD_BALANCE, status);
            return new Gson().toJson(balanceResponse);
        }
    }

    public static String retrieveJob(Request request, Response response) {
        setupResponse(response);
        String uuidString = request.params(":uuid");
        EonPlayer eonPlayer = Database.retrievePlayer(uuidString);
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
