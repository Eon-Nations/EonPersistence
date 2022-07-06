import main.PersistentApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import spark.Spark;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SparkBase {
    @BeforeAll
    static void startServer() {
        PersistentApplication.main(new String[0]);
    }

    @BeforeEach
    void waitForStart() {
        Spark.awaitInitialization();
    }

    protected static HttpResponse<String> sendRequest(String endPoint, String requestBody, String extraParam) {
        final String BASE_URL = "http://localhost:35353";
        String urlString = BASE_URL + "/" + endPoint;
        if (!extraParam.isEmpty()) {
            urlString += "/" + extraParam;
        }
        HttpRequest request;
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(urlString));
            builder = requestBody.isEmpty() ? builder.GET() : builder.POST(HttpRequest.BodyPublishers.ofString(requestBody));
            request = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        HttpClient client = HttpClient.newHttpClient();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
    }

    protected static HttpResponse<String> sendGetRequest(String endPoint, String extraParam) {
        return sendRequest(endPoint, "", extraParam);
    }

    protected static HttpResponse<String> sendPostRequest(String endPoint, String requestBody) {
        return sendRequest(endPoint, requestBody, "");
    }

    @AfterAll
    static void shutdownServer() {
        Spark.stop();
        Spark.awaitStop();
    }
}
