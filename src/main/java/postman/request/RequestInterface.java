package postman.request;

import java.util.HashMap;

public interface RequestInterface {
    public String getUrl();

    public String getMethod();

    public HashMap<String, String> getHeaders();

    public String getBody();
}
