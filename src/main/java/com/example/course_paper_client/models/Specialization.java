package com.example.course_paper_client.models;

import org.json.JSONObject;

public class Specialization {

    private String id;
    private String name;
    private boolean laboring;
    private Integer profAreaId;
    private String profAreaName;

    public Specialization(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.name = jsonObject.optString("name");
        this.laboring = jsonObject.optBoolean("laboring");
        this.profAreaId = jsonObject.optInt("profAreaId");
        this.profAreaName = jsonObject.optString("profAreaName");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLaboring() {
        return laboring;
    }

    public void setLaboring(boolean laboring) {
        this.laboring = laboring;
    }

    public Integer getProfAreaId() {
        return profAreaId;
    }

    public void setProfAreaId(Integer profAreaId) {
        this.profAreaId = profAreaId;
    }

    public String getProfAreaName() {
        return profAreaName;
    }

    public void setProfAreaName(String profAreaName) {
        this.profAreaName = profAreaName;
    }
}
