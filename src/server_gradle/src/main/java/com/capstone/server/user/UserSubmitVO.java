package com.capstone.server.user;

import java.util.List;

public class UserSubmitVO {

    private String title;
    private String company;
    private String job;
    private Integer q_num;
    private List<String> question;
    private List<String> text;
    private List<String> timestamp;
    private List<String> check;

    public List<String> getCheck() {
        return check;
    }

    public String getCheck(int n) {
        return check.get(n);
    }

    public void setCheck(List<String> check) {
        this.check = check;
    }

    public List<String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<String> timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp(int n) {
        return timestamp.get(n);
    }

    public Integer getQ_num() {
        return q_num;
    }

    public void setQ_num(Integer q_num) {
        this.q_num = q_num;
    }

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
        if(question.get(n) == null) return "문항을 입력하세요";
        else return question.get(n);
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

    public void companyToUpper() {

        switch (this.company) {
            case "sk":
                this.company = "SK";
                break;
            case "cj":
                this.company = "CJ";
                break;
            case "lg":
                this.company = "LG";
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
