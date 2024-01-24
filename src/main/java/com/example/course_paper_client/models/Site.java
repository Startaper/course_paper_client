package com.example.course_paper_client.models;

import org.json.JSONObject;

public class Site {

    private Long id;
    private String type;
    private String url;

    public Site(JSONObject jsonObject) {
        this.id = jsonObject.optLong("id");
        this.type = jsonObject.optString("type");
        this.url = jsonObject.optString("url");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
