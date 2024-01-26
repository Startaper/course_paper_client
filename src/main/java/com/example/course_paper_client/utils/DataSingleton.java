package com.example.course_paper_client.utils;

import com.example.course_paper_client.models.Resume;
import com.example.course_paper_client.models.User;
import com.example.course_paper_client.models.WarningModel;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

/**
 * Класс util-ит типа, предназначен для хранения данных передаваемых между окнами приложения.
 */
public class DataSingleton {

    public static final DataSingleton instance = new DataSingleton();
    private String token;
    private boolean isAdmin;
    private Stage openingStage;
    private Stage warningStage;
    private Resume selectedResume;
    private User selectedUser;
    private WarningModel warningModel;
    private String textErrorByAuth;
    private List<Resume> resumes;
    private List<User> users;
    private Map<String, String> filters;

    public DataSingleton() {
    }

    public static DataSingleton getInstance() {
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public WarningModel getWarningModel() {
        return warningModel;
    }

    public void setWarningModel(WarningModel warningModel) {
        this.warningModel = warningModel;
    }

    public List<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }

    public Stage getOpeningStage() {
        return openingStage;
    }

    public void setOpeningStage(Stage openingStage) {
        this.openingStage = openingStage;
    }

    public Resume getSelectedResume() {
        return selectedResume;
    }

    public void setSelectedResume(Resume selectedResume) {
        this.selectedResume = selectedResume;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public String getTextErrorByAuth() {
        return textErrorByAuth;
    }

    public void setTextErrorByAuth(String textErrorByAuth) {
        this.textErrorByAuth = textErrorByAuth;
    }

    public Stage getWarningStage() {
        return warningStage;
    }

    public void setWarningStage(Stage warningStage) {
        this.warningStage = warningStage;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
