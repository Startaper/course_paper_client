package com.example.course_paper_client.controllers;

import com.example.course_paper_client.MainApp;
import com.example.course_paper_client.exceptions.ApiResponseException;
import com.example.course_paper_client.exceptions.NoConnectionException;
import com.example.course_paper_client.models.Resume;
import com.example.course_paper_client.models.enums.EducationLevel;
import com.example.course_paper_client.models.enums.Gender;
import com.example.course_paper_client.models.enums.ResumeStatus;
import com.example.course_paper_client.services.MainServiceApi;
import com.example.course_paper_client.utils.DataSingleton;
import com.example.course_paper_client.utils.MainUtil;
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
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem btn_about_author;

    @FXML
    private Button btn_add_from_file;

    @FXML
    private MenuItem btn_users;

    @FXML
    private Button btn_deleted_all;

    @FXML
    private MenuItem btn_logout;

    @FXML
    private MenuButton btn_menu_yet;

    @FXML
    private Button btn_open_filters;

    @FXML
    private Button btn_refresh_table;

    @FXML
    private Button btn_search;

    @FXML
    private TextField field_search_text;

    @FXML
    private HBox hbox_commands;

    @FXML
    private TableView<Resume> table;

    @FXML
    private TableColumn<Resume, Integer> table_column_age;

    @FXML
    private TableColumn<Resume, String> table_column_area;

    @FXML
    private TableColumn<Resume, EducationLevel> table_column_education_level;

    @FXML
    private TableColumn<Resume, String> table_column_email;

    @FXML
    private TableColumn<Resume, String> table_column_firstname;

    @FXML
    private TableColumn<Resume, Gender> table_column_gender;

    @FXML
    private TableColumn<Resume, String> table_column_lastname;

    @FXML
    private TableColumn<Resume, String> table_column_number;

    @FXML
    private TableColumn<Resume, String> table_column_phone;

    @FXML
    private TableColumn<Resume, Float> table_column_rating;

    @FXML
    private TableColumn<Resume, String> table_column_post;

    @FXML
    private TableColumn<Resume, Integer> table_column_salary;

    @FXML
    private TableColumn<Resume, ResumeStatus> table_column_status;

    @FXML
    private Text txt_btn_reset_filters;

    DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    void initialize() {
//        Убираем из интерфейса две команды, если пользователь не админ
        if (!dataSingleton.isAdmin()) {
            hbox_commands.getChildren().remove(btn_deleted_all);
            hbox_commands.getChildren().remove(btn_add_from_file);
            btn_menu_yet.getItems().remove(btn_users);
        }

        initTableColumns();
        fillTable();

        btn_open_filters.setOnAction(this::onClickOpenFilters);
        btn_deleted_all.setOnAction(this::onClickDeletedAll);
        btn_add_from_file.setOnAction(this::onClickAddAllFromFile);
        table.setOnMouseClicked(this::onClickTableItem);
        table.setOnKeyPressed(this::onClickEnterInTable);
        btn_about_author.setOnAction(this::onClickOpenAboutAuthor);
        btn_refresh_table.setOnAction(this::onClickRefreshTable);
        btn_logout.setOnAction(this::onClickLogout);
        txt_btn_reset_filters.setOnMouseClicked(this::onClickResetFilters);
        btn_users.setOnAction(this::onClickButtonUsers);
        btn_search.setOnAction(this::onClickSearch);
    }

    private void initTableColumns() {
        table_column_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        table_column_area.setCellValueFactory(new PropertyValueFactory<>("area"));
        table_column_education_level.setCellValueFactory(new PropertyValueFactory<>("educationLevel"));
        table_column_firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        table_column_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table_column_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        table_column_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        table_column_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        table_column_number.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_column_post.setCellValueFactory(new PropertyValueFactory<>("title"));
        table_column_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        table_column_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        table_column_phone.setCellValueFactory(new PropertyValueFactory<>("mainPhone"));
    }

    private void onClickSearch(Event event) {
        onClickRefreshTable(event);
        if (!field_search_text.getText().isBlank()) {
            dataSingleton.setResumes(MainUtil.searchByText(dataSingleton.getResumes(), field_search_text.getText()));
            fillTable();
        }
    }

    private void onClickButtonUsers(Event event) {
        try {
            dataSingleton.setUsers(MainServiceApi.getAllUsers(dataSingleton.getToken()));
            changeScene("admin-panel-view.fxml");
        } catch (NoConnectionException | ApiResponseException e) {
            MainApp.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при получении списка пользователей", e.getMessage());
        } catch (JSONException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickAddAllFromFile(Event event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(btn_add_from_file.getScene().getWindow());
        try {
            if (file != null) {
                MainServiceApi.addResumes(file, dataSingleton.getToken());
            }
        } catch (NoConnectionException | ApiResponseException e) {
            MainApp.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при добавлении данных", e.getMessage());
        } catch (JSONException | IOException e) {
            System.out.println(e.getMessage());
        }
        onClickRefreshTable(event);
    }

    private void onClickResetFilters(Event event) {
        dataSingleton.setFilters(null);
        field_search_text.clear();
        onClickRefreshTable(event);
    }

    private void onClickLogout(Event event) {
        try {
            changeScene("auth-view.fxml");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void onClickRefreshTable(Event event) {
        try {
            dataSingleton.setResumes(MainServiceApi.getAllResumes(dataSingleton.getToken(), dataSingleton.getFilters()));
//            table.getItems().;
            fillTable();
        } catch (NoConnectionException | ApiResponseException e) {
            MainApp.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при обновлении данных", e.getMessage());
        } catch (JSONException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickEnterInTable(KeyEvent keyEvent) {
        try {
            KeyCode keyCode = keyEvent.getCode();
            Resume resume = table.getSelectionModel().getSelectedItem();
            if (resume != null && keyCode.getName().equals("Enter")) {
                openResumeStage(resume);
                onClickRefreshTable(keyEvent);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickOpenAboutAuthor(Event event) {
        MainApp.showAlert("Об авторе", Alert.AlertType.INFORMATION, "Работа выполнена студентом группы ЗБ-ПИ21-2 Ошроевым Азаматом Заудиновичем", "");
    }

    private void onClickOpenFilters(Event event) {
        try {
            openFiltersStage();
            onClickRefreshTable(event);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickDeletedAll(Event event) {
        try {
            ButtonType result =
                    MainApp.showWarningAlert("Удаление", Alert.AlertType.WARNING, "Удалить все резюме?", "");
            if (result == ButtonType.OK) {
                deleteAllResume();
                dataSingleton.getResumes().clear();
                fillTable();
            }
        } catch (NoConnectionException | ApiResponseException e) {
            MainApp.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при удалении данных", e.getMessage());
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickTableItem(MouseEvent event) {
        try {
            Resume resume = table.getSelectionModel().getSelectedItem();
            if (resume != null && event.getClickCount() == 2) {
                openResumeStage(resume);
                onClickRefreshTable(event);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void changeScene(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load());
        MainApp.changeScene(scene);
    }

    private void openFiltersStage() throws IOException {
        MainApp.openNewStage(
                "filters-view.fxml",
                "Фильтры",
                false,
                StageStyle.UNDECORATED,
                Modality.APPLICATION_MODAL);
    }

    private void openResumeStage(Resume resume) throws IOException {
        dataSingleton.setSelectedResume(resume);
        MainApp.openNewStage(
                "resume-view.fxml",
                "Резюме",
                false,
                StageStyle.UNDECORATED,
                Modality.APPLICATION_MODAL);
    }

    private void fillTable() {
        table.setItems(FXCollections.observableList(dataSingleton.getResumes()));
    }

    private void deleteAllResume() throws NoConnectionException, ApiResponseException, JSONException {
        MainServiceApi.deleteAllResumes(dataSingleton.getToken());
    }

}
