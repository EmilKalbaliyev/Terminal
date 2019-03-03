package com.example.emilkalbaliyev.terminal;

/**
 * Created by EmilKelbali on 3/9/18.
 */

public class Response {
    private boolean success;
    private String message;

    public Response() {
    }

    private Response(boolean success, String msg) {
        this.success = success;
        this.message = msg;
    }

    public static Response fail(String msg) {
        return new Response(false, msg);
    }

    public static Response success(String msg) {
        return new Response(true, msg);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
