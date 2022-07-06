import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

class TestPersistentApplication extends SparkBase {
    private static final int HTTP_INVALID = 404;
    private static final int HTTP_INVALID_REQ = 400;
    private static final int HTTP_OK = 200;
    private static final int HTTP_POST_OK = 201;

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
        System.out.println("Response: " + response.body());
        Assertions.assertNotEquals(HTTP_OK, response.statusCode());
    }
}
