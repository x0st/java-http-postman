package postman.response;

import java.util.List;
import java.util.Map;

public class StringResponse extends Response<String> {
    public StringResponse(Integer statusCode, String body, Map<String, List<String>> headers) {
        super(statusCode, body, headers);
    }
}
