package utils;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class RequestValidation {
    private RequestValidation() { }

    public static boolean validate(String requestBody, Type requestType) {
        List<String> classes = Arrays.stream(requestType.getTypeName().split("\\."))
                .toList();
        String className = classes.get(classes.size() - 1);
        Predicate<String> validator = loadSchema(className);
        return validator.test(requestBody);
    }

    // Taken from https://github.com/everit-org/json-schema/
    public static Predicate<String> loadSchema(String schemaName) {
        String fullPath = "/schemas/" + schemaName + ".json";
        try (InputStream inputStream = RequestValidation.class.getResourceAsStream(fullPath)) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            return createValidatorFunction(schema);
        } catch (NullPointerException | IOException e) {
            return request -> false;
        }
    }

    private static Predicate<String> createValidatorFunction(Schema schema) {
        return request -> {
            try {
                schema.validate(request);
                return true;
            } catch (ValidationException e) {
                return false;
            }
        };
    }
}
