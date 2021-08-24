package com.example.engine.business.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Feedback {
    private boolean success;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String feedback;

    public static final String SUCCESS_MESSAGE = "Congratulations, you're right!";
    public static final String FAILURE_MESSAGE = "Wrong answer! Please, try again.";

    public Feedback(boolean success) {
        setSuccess(success);
    }

    public void setSuccess(boolean success) {
        this.success = success;
        this.feedback = success ? SUCCESS_MESSAGE : FAILURE_MESSAGE;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "success=" + success +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
