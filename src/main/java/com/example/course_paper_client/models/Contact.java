package com.example.course_paper_client.models;

import com.example.course_paper_client.models.enums.ContactType;
import org.json.JSONException;
import org.json.JSONObject;

public class Contact {

    private Long id;
    private boolean verified;
    private boolean preferred;
    private ContactType type;
    private String value;
    private String comment;

    public Contact(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.optLong("id");
        this.verified = jsonObject.optBoolean("verified");
        this.preferred = jsonObject.optBoolean("preferred");
        this.type = ContactType.valueOf(jsonObject.getString("type"));
        this.value = jsonObject.optString("value");
        this.comment = jsonObject.optString("comment");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
