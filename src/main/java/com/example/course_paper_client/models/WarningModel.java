package com.example.course_paper_client.models;

import javafx.event.Event;

public class WarningModel {

    private String btnConfirmTitle;
    private String message;
    private String imgWarning;
    private boolean result;

    public WarningModel() {
    }

    public WarningModel(String btnConfirmTitle, String message, String imgWarning) {
        this.btnConfirmTitle = btnConfirmTitle;
        this.message = message;
        this.imgWarning = imgWarning;
    }

    public String getBtnConfirmTitle() {
        return btnConfirmTitle;
    }

    public void setBtnConfirmTitle(String btnConfirmTitle) {
        this.btnConfirmTitle = btnConfirmTitle;
    }

    public String getImgWarning() {
        return imgWarning;
    }

    public void setImgWarning(String imgWarning) {
        this.imgWarning = imgWarning;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
