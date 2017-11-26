package postman.request;

import java.util.HashMap;

abstract public class AbstractRequest implements RequestInterface {
    protected String method;
    protected String url;
    protected HashMap<String, String> headers = new HashMap<String, String>();

    /**
     * @return current using url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @return current using method
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * @return headers to be sent
     */
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    /**
     * @param name of a header to be sent
     * @param value of a header to be sent
     */
    public void setHeader(String name, String value) {
        this.headers.put(name, value);
    }
}
