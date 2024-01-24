package com.example.course_paper_client.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.course_paper_client.models.WarningModel;
import com.example.course_paper_client.utils.DataSingleton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WarningController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_close;

    @FXML
    private Button btn_confirm;

    @FXML
    private ImageView war_img;

    @FXML
    private Text txt_message;

    private final DataSingleton dataSingleton = DataSingleton.getInstance();
    private WarningModel warningModel;

    @FXML
    void initialize() {
        warningModel = dataSingleton.getWarningModel();

        war_img.setImage(new Image(warningModel.getImgWarning()));
        btn_confirm.setText(warningModel.getBtnConfirmTitle());
        txt_message.setText(warningModel.getMessage());

        btn_confirm.setOnMouseClicked(this::onClickConfirm);
        btn_close.setOnAction(this::onClickClose);
    }

    private void onClickClose(Event event) {
        warningModel.setResult(false);
        Stage stage = (Stage) btn_close.getScene().getWindow();
        dataSingleton.setWarningStage(null);
        stage.close();
    }

    private void onClickConfirm(Event event) {
        warningModel.setResult(true);
        Stage stage = (Stage) btn_close.getScene().getWindow();
        dataSingleton.setWarningStage(null);
        stage.close();
    }

}
