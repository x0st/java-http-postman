package postman.response;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class JSONObjectResponse extends Response<JSONObject> {
    public JSONObjectResponse(Integer statusCode, JSONObject body, Map<String, List<String>> headers) {
        super(statusCode, body, headers);
    }
}
