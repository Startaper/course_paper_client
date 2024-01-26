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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem btn_about_author;

    @FXML
    private Button btn_add_new_user;

    @FXML
    private Button btn_update;

    @FXML
    private MenuItem btn_home;

    @FXML
    private MenuItem btn_logout;

    @FXML
    private TableView<User> table_users;

    @FXML
    private TableColumn<User, String> table_users_email;

    @FXML
    private TableColumn<User, String> table_users_first_name;

    @FXML
    private TableColumn<User, String> table_users_last_name;

    @FXML
    private TableColumn<User, String> table_users_middle_name;

    @FXML
    private TableColumn<User, String> table_users_role;

    @FXML
    private TableColumn<User, UserStatus> table_users_status;

    private final DataSingleton data = DataSingleton.getInstance();

    @FXML
    void initialize() {
        initTableColumns();
        fillTable();

        btn_about_author.setOnAction(this::onClickOpenAboutAuthor);
        btn_logout.setOnAction(this::onClickLogout);
        btn_home.setOnAction(this::onClickHome);
        btn_update.setOnAction(this::onClickUpdateTable);
        table_users.setOnMouseClicked(this::onClickTableItem);
        table_users.setOnKeyPressed(this::onClickEnterInTable);
        btn_add_new_user.setOnAction(this::onClickAddUser);
    }

    private void onClickAddUser(Event event) {
        try {
            openNewUserStage();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickTableItem(MouseEvent event) {
        try {
            User user = table_users.getSelectionModel().getSelectedItem();
            if (user != null && event.getClickCount() == 2) {
                openUserItem(user);
                onClickUpdateTable(event);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickEnterInTable(KeyEvent keyEvent) {
        try {
            KeyCode keyCode = keyEvent.getCode();
            User user = table_users.getSelectionModel().getSelectedItem();
            if (user != null && keyCode.getName().equals("Enter")) {
                openUserItem(user);
                onClickUpdateTable(keyEvent);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickHome(Event event) {
        try {
            changeScene("main-view.fxml");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void onClickLogout(Event event) {
        try {
            changeScene("auth-view.fxml");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void onClickOpenAboutAuthor(Event event) {
        HelloApplication.showAlert("Об авторе", Alert.AlertType.INFORMATION, "Работа выполнена студентом группы ЗБ-ПИ21-2 Ошроевым Азаматом Заудиновичем", "");
    }

    private void onClickUpdateTable(Event event) {
        try {
            data.setUsers(MainServiceApi.getAllUsers(data.getToken()));
            table_users.getItems().clear();
            fillTable();
        } catch (NoConnectionException | ApiResponseException e) {
            HelloApplication.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при обновлении данных", e.getMessage());
        } catch (JSONException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void openNewUserStage() throws IOException {
        HelloApplication.openNewStage(
                "new-user-view.fxml",
                "Добавление нового пользователя",
                false,
                StageStyle.DECORATED,
                Modality.APPLICATION_MODAL,
                false);
    }

    private void openUserItem(User user) throws IOException {
        data.setSelectedUser(user);
        HelloApplication.openNewStage(
                "user-item-view.fxml",
                "Пользователь",
                false,
                StageStyle.UNDECORATED,
                Modality.APPLICATION_MODAL,
                false);
    }

    private void changeScene(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.changeScene(scene);
    }

    private void initTableColumns() {
        table_users_email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        table_users_first_name.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        table_users_last_name.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        table_users_middle_name.setCellValueFactory(new PropertyValueFactory<User, String>("middleName"));
        table_users_role.setCellValueFactory(new PropertyValueFactory<User, String>("admin"));
        table_users_status.setCellValueFactory(new PropertyValueFactory<User, UserStatus>("status"));
    }

    private void fillTable() {
        table_users.setItems(FXCollections.observableList(data.getUsers()));
    }

}
