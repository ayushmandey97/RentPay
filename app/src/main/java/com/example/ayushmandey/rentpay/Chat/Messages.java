package com.example.ayushmandey.rentpay;

/**
 * Created by Raghu on 4/3/18.
 */


public class Messages {
    String message;

    public Messages() {
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Messages(String message, String type, String from, long time, boolean seen) {

        this.message = message;
        this.type = type;
        this.from = from;
        this.time = time;
        this.seen = seen;
    }

    String type;
    String from;
    long time;
    boolean seen;



}

