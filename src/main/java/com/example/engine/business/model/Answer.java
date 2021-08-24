package com.example.engine.business.model;

import javax.validation.constraints.NotNull;

public class Answer {
    @NotNull(message = "Answers must not be null, use an empty array '[]' for no Answer")
    private int[] answer;

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
