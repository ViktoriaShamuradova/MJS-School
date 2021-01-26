package com.epam.esm.web.exceptionHandling;

public class IncorrectData {
    private String message;
    private int code;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setInfo(String message) {
        this.message = message;
    }
}
