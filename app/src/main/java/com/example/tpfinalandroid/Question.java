package com.example.tpfinalandroid;

public class Question {
    private String id;
    private String question;
    private String reponse;

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getReponse() {
        return reponse;
    }

    public Question(String id, String question, String reponse) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
