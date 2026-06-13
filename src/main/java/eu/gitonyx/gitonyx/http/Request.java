package eu.gitonyx.gitonyx.http;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private String method;
    private String path;
    private String body;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> queryParams = new HashMap<>();
    private final Map<String, String> pathParams = new HashMap<>();

    public Request() {}

    public Request method(String method) {
        this.method = method.toUpperCase();
        return this;
    }

    public String method() {
        return method;
    }

    public Request path(String path) {
        this.path = path;
        return this;
    }

    public String path() {
        return path;
    }

    public Request body(String body) {
        this.body = body;
        return this;
    }

    public String body() {
        return body;
    }

    public JSONObject json() {
        if (body == null || body.isBlank()) return new JSONObject();
        return new JSONObject(body);
    }

    public boolean hasBody() {
        return body != null && !body.isBlank();
    }


    public Request header(String key, String value) {
        headers.put(key.toLowerCase(), value);
        return this;
    }

    public String header(String key) {
        return headers.get(key.toLowerCase());
    }

    public Map<String, String> headers() {
        return Collections.unmodifiableMap(headers);
    }

    public String contentType() {
        return header("content-type");
    }

    public String authorization() {
        return header("authorization");
    }

    public String bearerToken() {
        String auth = authorization();
        if (auth != null && auth.startsWith("Bearer ")) {
            return auth.substring(7);
        }
        return null;
    }

    public Request queryParam(String key, String value) {
        queryParams.put(key, value);
        return this;
    }

    public String queryParam(String key) {
        return queryParams.get(key);
    }

    public String queryParamOrDefault(String key, String defaultValue) {
        return queryParams.getOrDefault(key, defaultValue);
    }

    public Map<String, String> queryParams() {
        return Collections.unmodifiableMap(queryParams);
    }

    public boolean hasQueryParam(String key) {
        return queryParams.containsKey(key);
    }

    public Request pathParam(String key, String value) {
        pathParams.put(key, value);
        return this;
    }

    public String pathParam(String key) {
        return pathParams.get(key);
    }

    public Map<String, String> pathParams() {
        return Collections.unmodifiableMap(pathParams);
    }
}
