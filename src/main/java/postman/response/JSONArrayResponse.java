package postman.response;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

public class JSONArrayResponse extends Response<JSONArray> {
    public JSONArrayResponse(Integer statusCode, JSONArray body, Map<String, List<String>> headers) {
        super(statusCode, body, headers);
    }
}
