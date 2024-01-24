package com.example.course_paper_client.models;

import com.example.course_paper_client.models.enums.UserStatus;
import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private Long id;
    private UserStatus status;
    private String email;
    private String lastName;
    private String firstName;
    private String middleName;
    private boolean admin;

    public User() {
    }

    public User(JSONObject jsonObject) {
        this.id = jsonObject.optLong("id");
        this.status = UserStatus.valueOf(jsonObject.optString("status"));
        this.email = jsonObject.optString("email");
        this.lastName = jsonObject.optString("lastName");
        this.firstName = jsonObject.optString("firstName");
        this.middleName = jsonObject.optString("middleName");
        this.admin = jsonObject.optBoolean("admin");
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.getId());
        if (this.status != null) {
            jsonObject.put("status", this.getStatus());
        }
        jsonObject.put("email", this.getEmail());
        jsonObject.put("lastName", this.getLastName());
        jsonObject.put("firstName", this.getFirstName());
        jsonObject.put("middleName", this.getMiddleName());
        jsonObject.put("admin", this.admin);
        return jsonObject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return String.valueOf(status);
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String isAdmin() {
        if (this.admin) {
            return "Да";
        } else {
            return "Нет";
        }
    }

    public boolean getAdminState(){
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
