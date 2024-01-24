package com.example.course_paper_client.models;
import com.example.course_paper_client.models.enums.EducationType;
import org.json.JSONObject;

public class Education {

    private Long id;
    private String type;
    private String name;
    private String nameId;
    private String organization;
    private String organizationId;
    private String result;
    private String resultId;
    private int year;

    public Education(JSONObject jsonObject) {
        this.id = jsonObject.optLong("id");
        this.type = EducationType.valueOf(jsonObject.optString("type")).getName();
        this.name = jsonObject.optString("name");
        this.nameId = jsonObject.optString("nameId");
        this.organization = jsonObject.optString("organization");
        this.organizationId = jsonObject.optString("organizationId");
        this.result = jsonObject.optString("result");
        this.resultId = jsonObject.optString("resultId");
        this.year = jsonObject.optInt("year", 0);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
