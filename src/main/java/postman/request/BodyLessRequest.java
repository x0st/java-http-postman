package postman.request;

import java.util.HashMap;
import java.util.Map;

public class BodyLessRequest extends AbstractRequest {
    public enum Method {GET, HEAD};

    private HashMap<String, String> fields = new HashMap<String, String>();

    public BodyLessRequest(Method method, String url) {
        this.method = method.toString();
        this.url = url;
        this.headers.put("Content-Type", "application/x-www-form-urlencoded");
    }

    public String getBody() {
        return null;
    }

    @Override
    public String getUrl() {
        String url = String.format("%s?", super.getUrl());

        for (Map.Entry<String, String> field : this.fields.entrySet()) {
            url = String.format("%s%s=%s&", url, field.getKey(), field.getValue());
        }

        return url;
    }

    public void addField(String name, String value) {
        this.fields.put(name, value);
    }
}
