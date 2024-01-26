package com.example.course_paper_client.controllers;

import com.example.course_paper_client.HelloApplication;
import com.example.course_paper_client.exceptions.ApiResponseException;
import com.example.course_paper_client.exceptions.NoConnectionException;
import com.example.course_paper_client.models.User;
import com.example.course_paper_client.models.enums.UserStatus;
import com.example.course_paper_client.services.MainServiceApi;
import com.example.course_paper_client.utils.DataSingleton;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONException;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class UserItemController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_close;

    @FXML
    private Button btn_deleted;

    @FXML
    private Button btn_save;

    @FXML
    private CheckBox chbox_is_admin;

    @FXML
    private ChoiceBox<String> chbox_status;

    @FXML
    private TextField txtf_email;

    @FXML
    private TextField txtf_first_name;

    @FXML
    private TextField txtf_last_name;

    @FXML
    private TextField txtf_middle_name;

    private final DataSingleton data = DataSingleton.getInstance();

    @FXML
    void initialize() {
        initUser();

        btn_close.setOnAction(this::onClickClose);
        btn_save.setOnAction(this::onClickSave);
        btn_deleted.setOnAction(this::onClickDeleted);

        chbox_status.setItems(FXCollections.observableList(Arrays.stream(UserStatus.values())
                .map(String::valueOf).toList()).sorted());
    }

    private void onClickSave(Event event) {
        if (checkUserByUpdate()) {
            try {
                MainServiceApi.updateUser(
                        data.getToken(),
                        data.getSelectedUser().getId(),
                        data.getSelectedUser());
                onClickClose(event);
            } catch (NoConnectionException | ApiResponseException e) {
                HelloApplication.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при попытке обновления пользователя", e.getMessage());
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void onClickDeleted(Event event) {
        try {
            ButtonType result =
                    HelloApplication.showWarningAlert("Удаление", Alert.AlertType.WARNING, "Удалить пользователя?", "");

            if (result == ButtonType.OK) {
                MainServiceApi.deleteUser(data.getToken(), data.getSelectedUser().getId());
                onClickClose(event);
            }
        } catch (NoConnectionException | ApiResponseException e) {
            HelloApplication.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при удалении пользователя", e.getMessage());
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickClose(Event event) {
        Stage stage = data.getOpeningStage();
        stage.close();
        data.setOpeningStage(null);
    }

    private void initUser() {
        User user = data.getSelectedUser();
        if (user != null) {
            txtf_last_name.setText(user.getLastName());
            txtf_first_name.setText(user.getFirstName());
            txtf_middle_name.setText(user.getMiddleName());
            txtf_email.setText(user.getEmail());
            chbox_status.setValue(user.getStatus());
            if (user.isAdmin().equals("Да")) {
                chbox_is_admin.setSelected(true);
            }
        }
    }

    private boolean checkUserByUpdate() {
        boolean result = false;
        User user = data.getSelectedUser();

        if (!txtf_last_name.getText().isBlank() && !user.getLastName().equals(txtf_last_name.getText())) {
            result = true;
            user.setLastName(txtf_last_name.getText());
        }
        if (!txtf_first_name.getText().isBlank() && !user.getFirstName().equals(txtf_first_name.getText())) {
            result = true;
            user.setFirstName(txtf_first_name.getText());
        }
        if (!txtf_middle_name.getText().isBlank() && !user.getMiddleName().equals(txtf_middle_name.getText())) {
            result = true;
            user.setMiddleName(txtf_middle_name.getText());
        }
        if (!chbox_status.getValue().isBlank() && !user.getStatus().equals(chbox_status.getValue())) {
            result = true;
            user.setStatus(UserStatus.valueOf(chbox_status.getValue()));
        }
        if (user.getAdminState() != chbox_is_admin.isSelected()) {
            result = true;
            user.setAdmin(chbox_is_admin.isSelected());
        }

        return result;
    }
}
