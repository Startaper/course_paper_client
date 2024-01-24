package com.example.course_paper_client.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.course_paper_client.exceptions.ApiResponseException;
import com.example.course_paper_client.exceptions.NoConnectionException;
import com.example.course_paper_client.models.User;
import com.example.course_paper_client.services.MainServiceApi;
import com.example.course_paper_client.utils.DataSingleton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

public class NewUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_close;

    @FXML
    private Button btn_save;

    @FXML
    private CheckBox chbox_is_admin;

    @FXML
    private TextField txtf_email;

    @FXML
    private TextField txtf_first_name;

    @FXML
    private TextField txtf_last_name;

    @FXML
    private TextField txtf_middle_name;

    private static final String errorTxtFieldStyle = "-fx-border-color: red; -fx-border-width: 0 0 0 2;";

    private final DataSingleton data = DataSingleton.getInstance();

    @FXML
    void initialize() {
        initFields();

        chbox_is_admin.setSelected(false);
        btn_save.setOnAction(this::onClickSave);
        btn_close.setOnAction(this::onClickClose);
    }

    private void onClickSave(Event event) {
        checkUser();
        User user = new User();
        user.setLastName(txtf_last_name.getText());
        user.setFirstName(txtf_first_name.getText());
        user.setMiddleName(txtf_middle_name.getText());
        user.setEmail(txtf_email.getText());
        user.setAdmin(chbox_is_admin.isSelected());

        try {
            JSONObject jsonUser = user.toJson();
            MainServiceApi.addUser(data.getToken(), jsonUser);
            onClickClose(event);
        } catch (NoConnectionException | ApiResponseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка при попытке обновления пользователя");
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (JSONException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void onClickClose(Event event) {
        Stage stage = data.getOpeningStage();
        stage.close();
        data.setOpeningStage(null);
    }

    private void checkUser() {
        if (txtf_last_name.getText().isBlank()) {
            txtf_last_name.setStyle(errorTxtFieldStyle);
        }
        if (txtf_first_name.getText().isBlank()) {
            txtf_first_name.setStyle(errorTxtFieldStyle);
        }
        if (txtf_email.getText().isBlank()) {
            txtf_email.setStyle(errorTxtFieldStyle);
        }
    }

    private void initFields() {
        txtf_last_name.setStyle("");
        txtf_first_name.setStyle("");
        txtf_email.setStyle("");
    }
}
