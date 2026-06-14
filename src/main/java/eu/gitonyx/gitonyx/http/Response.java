package eu.gitonyx.gitonyx.http;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Response {

    private int code = 200;
    private String body = null;
    private String contentType = "application/json";
    private final List<String[]> headers = new ArrayList<>();

    public Response() {}

    public Response statusCode(int code) {
        this.code = code;
        return this;
    }

    public int code() {
        return code;
    }

    public Response json(JSONObject jsonObject) {
        this.body = jsonObject.toString();
        this.contentType = "application/json";
        return this;
    }

    public Response json(String raw) {
        this.body = raw;
        this.contentType = "application/json";
        return this;
    }

    public Response text(String text) {
        this.body = text;
        this.contentType = "text/plain";
        return this;
    }

    public Response html(String html) {
        this.body = html;
        this.contentType = "text/html";
        return this;
    }

    public String body() {
        return body;
    }

    public Response header(String key, String value) {
        headers.add(new String[]{key, value});
        return this;
    }

    public Response contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String contentType() {
        return contentType;
    }

    public List<String[]> headers() {
        return Collections.unmodifiableList(headers);
    }

    public static Response ok() {
        return new Response().statusCode(200);
    }

    public static Response ok(JSONObject json) {
        return new Response().statusCode(200).json(json);
    }

    public static Response created() {
        return new Response().statusCode(201);
    }

    public static Response created(JSONObject json) {
        return new Response().statusCode(201).json(json);
    }

    public static Response noContent() {
        return new Response().statusCode(204);
    }

    public static Response badRequest(String message) {
        return new Response().statusCode(400)
                .json(new JSONObject().put("error", message));
    }

    public static Response unauthorized(String message) {
        return new Response().statusCode(401)
                .json(new JSONObject().put("error", message));
    }

    public static Response forbidden(String message) {
        return new Response().statusCode(403)
                .json(new JSONObject().put("error", message));
    }

    public static Response notFound(String message) {
        return new Response().statusCode(404)
                .json(new JSONObject().put("error", message));
    }

    public static Response conflict(String message) {
        return new Response().statusCode(409)
                .json(new JSONObject().put("error", message));
    }

    public static Response internalServerError(String message) {
        return new Response().statusCode(500)
                .json(new JSONObject().put("error", message));
    }

    public static Response redirect(String location) {
        return new Response().statusCode(302).header("Location", location);
    }

    public static Response permanentRedirect(String location) {
        return new Response().statusCode(301).header("Location", location);
    }
}
