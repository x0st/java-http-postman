package postman;

import org.json.JSONArray;
import org.json.JSONObject;
import postman.request.RequestInterface;
import postman.response.JSONArrayResponse;
import postman.response.JSONObjectResponse;
import postman.response.Response;
import postman.response.StringResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class Client implements AutoCloseable {
    private ArrayList<Thread> threads;

    public interface Listener<T> {
        public void success(Response<T> r);

        public void error(Response<T> r);
    }

    public interface ErrorListener {
        public void exception(PostmanException e);
    }

    public Client() {
        this.threads = new ArrayList<Thread>();
    }

    public void asJSONObjectAsync(final RequestInterface request, final Listener<JSONObject> listener, final ErrorListener errorListener) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    JSONObjectResponse response = Client.this.asJSONObject(request);

                    if (response.getStatusCode() >= 400) {
                        listener.error(response);
                    }

                    if (response.getStatusCode() < 300) {
                        listener.success(response);
                    }

                } catch (PostmanException e) {
                    errorListener.exception(e);
                }
            }
        });

        this.threads.add(thread);

        thread.start();
    }

    public void asJSONArrayAsync(final RequestInterface request, final Listener<JSONArray> listener, final ErrorListener errorListener) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    JSONArrayResponse response = Client.this.asJSONArray(request);

                    if (response.getStatusCode() >= 400) {
                        listener.error(response);
                    }

                    if (response.getStatusCode() < 300) {
                        listener.success(response);
                    }

                } catch (PostmanException e) {
                    errorListener.exception(e);
                }
            }
        });

        this.threads.add(thread);

        thread.start();
    }

    public JSONArrayResponse asJSONArray(final RequestInterface request) throws PostmanException {
        try {
            StringResponse stringResponse = this.performRequest(request);

            return new JSONArrayResponse(
                    stringResponse.getStatusCode(),
                    new JSONArray(stringResponse.getBody()),
                    stringResponse.getHeaders()
            );
        } catch (IOException e) {
            throw new PostmanException(e);
        }
    }

    public JSONObjectResponse asJSONObject(RequestInterface request) throws PostmanException {
        try {
            StringResponse stringResponse = this.performRequest(request);

            return new JSONObjectResponse(
                    stringResponse.getStatusCode(),
                    new JSONObject(stringResponse.getBody()),
                    stringResponse.getHeaders()
            );

        } catch (IOException e) {
            throw new PostmanException(e);
        }
    }

    private StringResponse performRequest(final RequestInterface request) throws IOException {
        HttpURLConnection connection = null;

        try {
            // 1: SET URL
            URL url = new URL(request.getUrl());
            connection = (HttpURLConnection) url.openConnection();

            // 2: SET METHOD
            if (request.getMethod().equals("PATCH")) {
                connection.setRequestMethod("POST");
                connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            } else {
                connection.setRequestMethod(request.getMethod());
            }

            // 3: SET HEADERS
            for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }

            connection.setUseCaches(false);

            // 4: WRITE BODY
            if (!request.getMethod().equals("GET") && !request.getMethod().equals("HEAD")) {
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(String.valueOf(request.getBody()));
                wr.close();
            }

            // 5: GET RESPONSE
            InputStreamReader inputStreamReader;

            if (connection.getResponseCode() >= 400) {
                inputStreamReader = new InputStreamReader(connection.getErrorStream());
            } else {
                inputStreamReader = new InputStreamReader(connection.getInputStream());
            }

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder resultContent = new StringBuilder();
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {
                resultContent.append(inputLine);
            }

            connection.disconnect();

            // 6: COMPOSE RESPONSE
            return new StringResponse(connection.getResponseCode(), resultContent.toString(), connection.getHeaderFields());
        } catch (IOException e) {
            if (connection != null) {
                connection.disconnect();
            }

            throw e;
        }
    }

    @Override
    public void close() throws Exception {
        for (Thread thread: this.threads) {
            thread.interrupt();
        }
    }
}
