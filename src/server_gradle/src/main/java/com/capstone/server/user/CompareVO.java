package com.capstone.server.user;

import java.util.List;

public class CompareVO extends UserSubmitVO{

    private String company;
    private String job;
    private List<String> timestamp;

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

    public List<String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<String> timestamp) {
        this.timestamp = timestamp;
    }

    public CompareVO(String company, String job, List<String> timestamp) {
        this.company = company;
        this.job = job;
        this.timestamp = timestamp;
    }
}
