package nl.hsleiden.beans;

import twitter4j.Status;

public class TweetStatus {

    private String text;
    private Status status;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
