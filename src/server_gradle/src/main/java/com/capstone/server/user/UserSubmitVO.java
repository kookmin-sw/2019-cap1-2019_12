package com.capstone.server.user;

import java.util.List;

public class UserSubmitVO {

    private String title;
    private String company;
    private String job;
    private List<String> question;
    private List<String> text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public void companyToEng() {

        switch (this.company) {
            case "현대":
                this.company = "hyundai";
                break;
            case "삼성":
                this.company = "samsung";
                break;
            case "SK":
                this.company = "sk";
                break;
            case "CJ":
                this.company = "cj";
                break;
            case "LG":
                this.company = "lg";
                break;
        }
    }

    public void jobToEng() {

        switch (this.job) {
            case "영업":
                this.job = "marketing";
                break;
            case "생산":
                this.job = "production";
                break;
            case "경영지원관리":
                this.job = "management";
                break;
            case "건설":
                this.job = "architecture";
                break;
            case "IT":
                break;
        }
    }

}
