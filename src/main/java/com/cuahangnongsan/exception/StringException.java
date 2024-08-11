package com.cuahangnongsan.exception;


public class StringException extends RuntimeException{
    private String message;
    public StringException(String message){
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
