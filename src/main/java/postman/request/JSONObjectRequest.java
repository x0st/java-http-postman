package postman.request;

import org.json.JSONObject;

import java.util.HashMap;

public class JSONObjectRequest extends AbstractRequest {
    public enum Method {POST, PUT, DELETE, PATCH, OPTIONS};

    private JSONObject body;

    public JSONObjectRequest(Method method, String url) {
        this.method = method.toString();
        this.url = url;
        this.headers.put("Content-Type", "application/json");
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public String getBody() {
        return body != null ? this.body.toString() : null;
    }
}
