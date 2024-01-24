package com.example.course_paper_client.models;

import org.json.JSONObject;

public class Recommendation {

    private Long id;
    private String contact;
    private String name;
    private String organization;
    private String position;

    public Recommendation(JSONObject jsonObject) {
        this.id = jsonObject.optLong("id");
        this.contact = jsonObject.optString("contact");
        this.name = jsonObject.optString("name");
        this.organization = jsonObject.optString("organization");
        this.position = jsonObject.optString("position");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
