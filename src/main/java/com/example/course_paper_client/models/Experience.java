package com.example.course_paper_client.models;

import org.json.JSONObject;

public class Experience {

    private Long id;
    private String area;
    private String company;
    private String position;
    private String companyId;
    private String companyUrl;
    private String industries;
    private String description;
    private String start;
    private String end;

    public Experience(JSONObject jsonObject) {
        this.id = jsonObject.optLong("id");
        this.area = jsonObject.optString("area");
        this.company = jsonObject.optString("company");
        this.position = jsonObject.optString("position");
        this.companyId = jsonObject.optString("companyId");
        this.companyUrl = jsonObject.optString("companyUrl");
        this.industries = jsonObject.optString("industries");
        this.description = jsonObject.optString("description");
        this.start = jsonObject.optString("start");
        this.end = jsonObject.optString("end");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getIndustries() {
        return industries;
    }

    public void setIndustries(String industries) {
        this.industries = industries;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
