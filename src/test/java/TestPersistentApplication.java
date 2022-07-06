import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

class TestPersistentApplication extends SparkBase {
    private static final int HTTP_INVALID = 404;
    private static final int HTTP_INVALID_REQ = 400;
    private static final int HTTP_OK = 200;

    @Test
    @DisplayName("Request leading nowhere responds with 404 status")
    void test404() {
        HttpResponse<String> response = sendGetRequest("invalid", "invalid");
        Assertions.assertEquals(HTTP_INVALID, response.statusCode());
    }

    @Test
    @DisplayName("An invalid JSON gets detected and is sent back as a 400 error code")
    void testInvalidJSON() {
        String requestBody = "{ \"nice\":}";
        HttpResponse<String> response = sendPostRequest("player", requestBody);
        Assertions.assertEquals(HTTP_INVALID_REQ, response.statusCode());
    }

    @Test
    @DisplayName("Valid GET request comes back as a 200 status code")
    void testValidRequest() {
        HttpResponse<String> response = sendGetRequest("uuid", "C4Squid");
        Assertions.assertEquals(HTTP_OK, response.statusCode());
    }

    @Test
    @DisplayName("Valid POST request comes back as a 200 status code")
    void testValidPost() {
        String requestBody = new JSONObject()
                .put("uuid", "C4Squid")
                .put("balance", 0.0)
                .toString();
        HttpResponse<String> response = sendPostRequest("balance", requestBody);
        Assertions.assertEquals(HTTP_OK, response.statusCode());
    }
}
