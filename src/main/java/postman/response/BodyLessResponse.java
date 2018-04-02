package postman.response;

import java.util.List;
import java.util.Map;

public class BodyLessResponse extends StringResponse {
    public BodyLessResponse(Integer statusCode, String body, Map<String, List<String>> headers) {
        super(statusCode, body, headers);
    }

    @Override
    public String getBody() {
        return null;
    }
}
