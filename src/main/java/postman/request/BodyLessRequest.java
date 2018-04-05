package postman.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BodyLessRequest extends AbstractRequest {
    public enum Method {GET, HEAD};

    private HashMap<String, String> fields = new HashMap<>();
    private HashMap<String, ArrayList<String>> arrays = new HashMap<>();

    public BodyLessRequest(Method method, String url) {
        this.url = url;
        this.method = method.toString();
        this.headers.put("Content-Type", "application/x-www-form-urlencoded");
    }

    public String getBody() {
        return null;
    }

    @Override
    public String getUrl() {
        String url = String.format("%s?", super.getUrl());

        for (Iterator<Map.Entry<String, String>> iterator = this.fields.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> field = iterator.next();
            url = String.format("%s%s=%s&", url, field.getKey(), field.getValue());
        }

        for (Iterator<Map.Entry<String, ArrayList<String>>> arrayIterator = this.arrays.entrySet().iterator(); arrayIterator.hasNext(); ) {
            Map.Entry<String, ArrayList<String>> array = arrayIterator.next();

            for (Iterator<String> arrayValueIterator = array.getValue().iterator(); arrayValueIterator.hasNext(); ) {
                String arrayValue = arrayValueIterator.next();

                url = String.format("%s%s[]=%s&", url, array.getKey(), arrayValue);
            }
        }

        return url;
    }

    public void addArray(String name, ArrayList<String> values) {
        this.arrays.put(name, values);
    }

    public void addField(String name, String value) {
        this.fields.put(name, value);
    }
}
