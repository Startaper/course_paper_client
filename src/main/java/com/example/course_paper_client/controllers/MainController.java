package com.example.course_paper_client.controllers;

import com.example.course_paper_client.HelloApplication;
import com.example.course_paper_client.exceptions.ApiResponseException;
import com.example.course_paper_client.exceptions.NoConnectionException;
import com.example.course_paper_client.models.Resume;
import com.example.course_paper_client.models.WarningModel;
import com.example.course_paper_client.models.enums.EducationLevel;
import com.example.course_paper_client.models.enums.Gender;
import com.example.course_paper_client.models.enums.ResumeStatus;
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
    }

    private void initTableColumns() {
        table_column_age.setCellValueFactory(new PropertyValueFactory<Resume, Integer>("age"));
        table_column_area.setCellValueFactory(new PropertyValueFactory<Resume, String>("area"));
        table_column_education_level.setCellValueFactory(new PropertyValueFactory<Resume, EducationLevel>("educationLevel"));
        table_column_firstname.setCellValueFactory(new PropertyValueFactory<Resume, String>("firstName"));
        table_column_gender.setCellValueFactory(new PropertyValueFactory<Resume, Gender>("gender"));
        table_column_lastname.setCellValueFactory(new PropertyValueFactory<Resume, String>("lastName"));
        table_column_number.setCellValueFactory(new PropertyValueFactory<Resume, String>("id"));
        table_column_post.setCellValueFactory(new PropertyValueFactory<Resume, String>("title"));
        table_column_salary.setCellValueFactory(new PropertyValueFactory<Resume, Integer>("salary"));
        table_column_status.setCellValueFactory(new PropertyValueFactory<Resume, ResumeStatus>("status"));
        table_column_rating.setCellValueFactory(new PropertyValueFactory<Resume, Float>("rating"));
        table_column_email.setCellValueFactory(new PropertyValueFactory<Resume, String>("email"));
        table_column_phone.setCellValueFactory(new PropertyValueFactory<Resume, String>("mainPhone"));
    }

    private void onClickButtonUsers(Event event) {
        try {
            dataSingleton.setUsers(MainServiceApi.getAllUsers(dataSingleton.getToken()));
            changeScene("admin-panel-view.fxml");
        } catch (NoConnectionException | ApiResponseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка при получении списка пользователей");
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (JSONException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickAddAllFromFile(Event event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(btn_add_from_file.getScene().getWindow());
        try {
            MainServiceApi.addResumes(file, dataSingleton.getToken());
        } catch (NoConnectionException | ApiResponseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка при добавлении данных");
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (JSONException | IOException e) {
            System.out.println(e.getMessage());
        }
        onClickRefreshTable(event);
    }

    private void onClickResetFilters(Event event) {
        dataSingleton.setFilters(null);
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
            table.getItems().clear();
            fillTable();
        } catch (NoConnectionException | ApiResponseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка при обновлении данных");
            alert.setContentText(e.getMessage());
            alert.show();
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
        try {
            openAboutAuthorStage();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
            openWarningStage("warning-icon.png", "Удалить", "Удалить все резюме?");

            if (dataSingleton.getWarningModel().isResult()) {
                deleteAllResume();
                dataSingleton.getResumes().clear();
                fillTable();
            }
        } catch (NoConnectionException | ApiResponseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка при удалении данных");
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (IOException | JSONException e) {
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.changeScene(scene);
    }

    private void openFiltersStage() throws IOException {
        HelloApplication.openNewStage(
                "filters-view.fxml",
                "Фильтры",
                false,
                StageStyle.UNDECORATED,
                Modality.APPLICATION_MODAL,
                false);
        dataSingleton.getOpeningStage().showAndWait();
    }

    private void openResumeStage(Resume resume) throws IOException {
        dataSingleton.setSelectedResume(resume);
        HelloApplication.openNewStage(
                "resume-view.fxml",
                "Резюме",
                false,
                StageStyle.UNDECORATED,
                Modality.APPLICATION_MODAL,
                false);
        dataSingleton.getOpeningStage().showAndWait();
    }

    private void openAboutAuthorStage() throws IOException {
        HelloApplication.openNewStage(
                "about-author.fxml",
                "Об авторе",
                false,
                StageStyle.DECORATED,
                Modality.APPLICATION_MODAL,
                false);
        dataSingleton.getOpeningStage().showAndWait();
    }

    private void openWarningStage(String img, String txtBtnConfirm, String message) throws IOException {
        WarningModel warningModel = new WarningModel(txtBtnConfirm, message, img);
        dataSingleton.setWarningModel(warningModel);

        HelloApplication.openNewStage(
                "warning-view.fxml",
                "Удаление",
                false,
                StageStyle.DECORATED,
                Modality.APPLICATION_MODAL,
                true);
        dataSingleton.getOpeningStage().showAndWait();
    }

    private void fillTable() {
        table.setItems(FXCollections.observableList(dataSingleton.getResumes()));
    }

    private void deleteAllResume() throws NoConnectionException, ApiResponseException, JSONException {
        MainServiceApi.deleteAllResumes(dataSingleton.getToken());
    }

}
