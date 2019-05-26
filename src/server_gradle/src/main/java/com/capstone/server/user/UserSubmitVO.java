package com.capstone.server.user;

import java.util.List;

public class UserSubmitVO {

    private String company;
    private String job;
    private List<String> question;
    private List<String> text;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<String> getQuestion() {
        return question;
    }

    public String getQuestion(int n) {
        return question.get(n);
    }

    public String getText(int n) {
        return text.get(n);
    }

    public void setQuestion(List<String> question) {
        this.question = question;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }


}
