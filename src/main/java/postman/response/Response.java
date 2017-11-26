package postman.response;

import java.util.List;
import java.util.Map;

public class Response<T> {
    private Map<String, List<String>> headers;
    private Integer statusCode;
    private T body;

    public Response(Integer statusCode, T body, Map<String, List<String>> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    public List<String> getHeader(String header) {
        try {
            return this.headers.get(header);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public T getBody() {
        return this.body;
    }
}
