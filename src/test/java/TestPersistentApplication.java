import main.PersistentApplication;
import org.junit.jupiter.api.*;
import spark.Spark;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class TestPersistentApplication {

    @BeforeAll
    static void startServer() {
        PersistentApplication.main(new String[0]);
    }

    @BeforeEach
    void waitForStart() {
        Spark.awaitInitialization();
    }

    private static HttpResponse<String> sendRequest(String endPoint, String requestBody, String extraParam) {
        final String BASE_URL = "http://localhost:35353";
        HttpRequest request;
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(BASE_URL + "/" + endPoint + "/" + extraParam));
            builder = requestBody.isEmpty() ? builder.GET() : builder.POST(HttpRequest.BodyPublishers.ofString(requestBody));
            request = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        HttpClient client = HttpClient.newHttpClient();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
    }

    private static HttpResponse<String> sendGetRequest(String endPoint, String extraParam) {
        return sendRequest(endPoint, "", extraParam);
    }

    private static HttpResponse<String> sendPostRequest(String endPoint, String requestBody) {
        return sendRequest(endPoint, requestBody, "");
    }

    @Test
    @DisplayName("Request leading nowhere responds with 404 status")
    void test404() {
        HttpResponse<String> response = sendGetRequest("invalid", "invalid");
        Assertions.assertEquals(404, response.statusCode());
    }

    @AfterAll
    static void shutdownServer() {
        Spark.stop();
        Spark.awaitStop();
    }
}
