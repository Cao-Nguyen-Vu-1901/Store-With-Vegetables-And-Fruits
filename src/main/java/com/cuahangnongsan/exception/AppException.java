package com.cuahangnongsan.exception;

public class AppException extends RuntimeException{
    public AppException(String message) {
        super();
        this.message = message;
    }

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
