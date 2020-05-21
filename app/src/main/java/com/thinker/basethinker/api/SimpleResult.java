package com.thinker.basethinker.api;

public class SimpleResult {
    private boolean success = false;
    private String message;

    public void setSuccess(boolean success){
        this.success = success;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public boolean isSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public SimpleResult() {
    }

    public SimpleResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public SimpleResult(boolean success) {
        this.success = success;
    }
}
