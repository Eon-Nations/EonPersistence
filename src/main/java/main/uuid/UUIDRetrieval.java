package main.uuid;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class UUIDRetrieval {
    private UUIDRetrieval() { }

    private static String processResponse(String responseBody) {
        MojangUUIDResponse response = new Gson().fromJson(responseBody, MojangUUIDResponse.class);
        return response.id();
    }

    public static String retrieveUUID(String username) {
        String mojangURL = "https://api.mojang.com/users/profiles/minecraft/" + username;
        URI uuidURL = URI.create(mojangURL);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uuidURL)
                .GET().timeout(Duration.ofSeconds(2))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(UUIDRetrieval::processResponse)
                .join();
    }
}
