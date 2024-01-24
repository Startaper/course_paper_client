package com.example.course_paper_client.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AboutAuthorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_ok;

    @FXML
    private ImageView image_info;

    @FXML
    void onClickOk(ActionEvent event) {
        Stage stage = (Stage) btn_ok.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        image_info.setImage(new Image("info-icon.png"));
    }

}
