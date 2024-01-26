package com.example.course_paper_client.controllers;

import com.example.course_paper_client.HelloApplication;
import com.example.course_paper_client.exceptions.ApiResponseException;
import com.example.course_paper_client.exceptions.NoConnectionException;
import com.example.course_paper_client.models.*;
import com.example.course_paper_client.models.enums.ContactType;
import com.example.course_paper_client.models.enums.ResumeStatus;
import com.example.course_paper_client.services.MainServiceApi;
import com.example.course_paper_client.utils.DataSingleton;
import com.example.course_paper_client.utils.MainUtil;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ResumeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_close;

    @FXML
    private Button btn_delete;

    @FXML
    private MenuItem btn_download_pdf;

    @FXML
    private MenuItem btn_download_rtf;

    @FXML
    private MenuButton btn_menu_download;

    @FXML
    private Button btn_save;

    @FXML
    private Label txt_education_level;

    @FXML
    private ChoiceBox<String> cbox_status;

    @FXML
    private HBox hbox_commands;

    @FXML
    private ImageView img_profile_photo;

    @FXML
    private TableView<Education> table_educations;

    @FXML
    private TableColumn<Education, Long> table_educations_id;

    @FXML
    private TableColumn<Education, String> table_educations_name;

    @FXML
    private TableColumn<Education, String> table_educations_organisation;

    @FXML
    private TableColumn<Education, String> table_educations_result;

    @FXML
    private TableColumn<Education, String> table_educations_type;

    @FXML
    private TableColumn<Education, Integer> table_educations_year;

    @FXML
    private TableView<Experience> table_experience;

    @FXML
    private TableColumn<Experience, String> table_experience_area;

    @FXML
    private TableColumn<Experience, String> table_experience_company;

    @FXML
    private TableColumn<Experience, String> table_experience_description;

    @FXML
    private TableColumn<Experience, String> table_experience_end;

    @FXML
    private TableColumn<Experience, Long> table_experience_id;

    @FXML
    private TableColumn<Experience, String> table_experience_industries;

    @FXML
    private TableColumn<Experience, String> table_experience_position;

    @FXML
    private TableColumn<Experience, String> table_experience_start;

    @FXML
    private TableView<Recommendation> table_recommendations;

    @FXML
    private TableColumn<Recommendation, String> table_recommendations_contact;

    @FXML
    private TableColumn<Recommendation, String> table_recommendations_name;

    @FXML
    private TableColumn<Recommendation, String> table_recommendations_organisation;

    @FXML
    private TableColumn<Recommendation, String> table_recommendations_position;

    @FXML
    private Text txt_area_metro;

    @FXML
    private Text txt_citizenship;

    @FXML
    private Text txt_driver_license;

    @FXML
    private Text txt_email;

    @FXML
    private TextArea txt_employments;

    @FXML
    private Label txt_fio_applicant;

    @FXML
    private Text txt_gender_age_birth_date;

    @FXML
    private TextArea txt_languages;

    @FXML
    private Text txt_phone_comment;

    @FXML
    private Text txt_resume_title;

    @FXML
    private Text txt_salary;

    @FXML
    private TextArea txt_schedules;

    @FXML
    private TextArea txt_skill_set;

    @FXML
    private TextArea txt_skills;

    @FXML
    private TextArea txt_specializations;

    @FXML
    private Text txt_travel_time;

    @FXML
    private Text txt_work_tickets;

    DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    void initialize() {
        // Убираем из интерфейса две команды, если пользователь не админ
        if (!dataSingleton.isAdmin()) {
            hbox_commands.getChildren().remove(btn_delete);
        }

        initTableEducations();
        initTableExperience();
        initTableRecommendations();

        try {
            initResume();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        btn_close.setOnAction(this::onClickClose);
        btn_save.setOnAction(this::onClickSave);
        btn_delete.setOnAction(this::onClickDeleted);
        btn_download_pdf.setOnAction(this::onClickDownloadPdf);
        btn_download_rtf.setOnAction(this::onClickDownloadRtf);
        cbox_status.setItems(FXCollections.observableList(Arrays.stream(ResumeStatus.values())
                .map(ResumeStatus::getName).toList()).sorted());

    }

    private void onClickSave(Event event) {
        if (!dataSingleton.getSelectedResume().getStatus().equals(cbox_status.getValue())) {
            try {
                MainServiceApi.updateResume(
                        dataSingleton.getToken(),
                        dataSingleton.getSelectedResume().getId(),
                        ResumeStatus.getStatusByName(cbox_status.getValue())
                );
                HelloApplication.showAlert("Обновление резюме", Alert.AlertType.INFORMATION, "Резюме успещно обновлено", "");
                onClickClose(event);
            } catch (NoConnectionException | ApiResponseException e) {
                HelloApplication.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при попытке обновить резюме", e.getMessage());
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void onClickDeleted(Event event) {
        try {
            ButtonType result =
                    HelloApplication.showWarningAlert("Удаление", Alert.AlertType.WARNING, "Удалить резюме?", "");

            if (result == ButtonType.OK) {
                MainServiceApi.deleteResume(dataSingleton.getToken(), dataSingleton.getSelectedResume().getId());
                HelloApplication.showAlert("Удаление", Alert.AlertType.INFORMATION, "Резюме успешно удалено", "");
                onClickClose(event);
            }
        } catch (NoConnectionException | ApiResponseException e) {
            HelloApplication.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при удалении данных", e.getMessage());
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickClose(Event event) {
        Stage stage = dataSingleton.getOpeningStage();
        stage.close();
        dataSingleton.setOpeningStage(null);
    }

    private void onClickDownloadPdf(Event event) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(dataSingleton.getSelectedResume().getUrlDownloadPdf()).openConnection();
            if (httpURLConnection.getResponseCode() == 403) {
                HelloApplication.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при скачивании файла.", "У вас недостаточно прав для этого действия.");
            }
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(txt_fio_applicant.getText() + " " + txt_resume_title.getText())) {
                    int data;
                    while ((data = bufferedInputStream.read()) != -1) {
                        fileOutputStream.write(data);
                    }
                    HelloApplication.showAlert("Файл загружен", Alert.AlertType.INFORMATION, "Файл загружен", "");

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void onClickDownloadRtf(Event event) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(dataSingleton.getSelectedResume().getUrlDownloadRtf()).openConnection();
            if (httpURLConnection.getResponseCode() == 403) {
                HelloApplication.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при скачивании файла.", "У вас недостаточно прав для этого действия.");
            }
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(txt_fio_applicant.getText() + " " + txt_resume_title.getText())) {
                    int data;
                    while ((data = bufferedInputStream.read()) != -1) {
                        fileOutputStream.write(data);
                    }
                    HelloApplication.showAlert("Файл загружен", Alert.AlertType.INFORMATION, "Файл загружен", "");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initResume() throws ParseException {
        Resume resume = dataSingleton.getSelectedResume();
        loadImg(resume.getPhoto());

        Contact phone = MainUtil.getContactByContactType(resume.getContacts(), ContactType.CELL);
        if (phone != null) {
            txt_phone_comment.setText(String.format("%s, %s", phone.getValue(), phone.getComment()));
        }
        cbox_status.setValue(resume.getStatus());
        txt_travel_time.setText(resume.getTravelTime().getName());
        txt_email.setText(resume.getEmail());
        txt_education_level.setText(resume.getEducationLevel());
        txt_resume_title.setText(resume.getTitle().trim());
        txt_gender_age_birth_date.setText(String.format("%s, %s, %s",
                resume.getGender(),
                resume.getAge(),
                resume.getBirthDate().substring(0, 10)));
        txt_salary.setText(resume.getSalary() + " руб.");
        txt_area_metro.setText(String.format("%s, %s", resume.getArea(), resume.getMetro()));
        txt_fio_applicant.setText(resume.getLastName() + " " + resume.getFirstName() + " " + resume.getMiddleName());
        txt_citizenship.setText(resume.getCitizenship());
        txt_driver_license.setText(resume.getDriverLicenseTypes());
        txt_employments.setText(resume.getEmployments());
        txt_schedules.setText(resume.getSchedules());
        txt_skill_set.setText(resume.getSkillSet());
        txt_skills.setText(resume.getSkills());
        txt_work_tickets.setText(resume.getWorkTickets());
        if (resume.getSpecializations() != null && !resume.getSpecializations().isEmpty()) {
            txt_specializations.setText(MainUtil.convertListToString(
                    resume.getSpecializations().stream().map(Specialization::getName).toList()));
        }
        if (resume.getLanguages() != null && !resume.getLanguages().isEmpty()) {
            txt_languages.setText(MainUtil.convertListToString(
                    resume.getLanguages().stream().map(Language::getName).toList()));
        }
        if (resume.getRecommendations() != null && !resume.getRecommendations().isEmpty()) {
            table_recommendations.setItems(FXCollections.observableList(resume.getRecommendations()));
        }
        if (resume.getExperience() != null && !resume.getExperience().isEmpty()) {
            table_experience.setItems(FXCollections.observableList(resume.getExperience()));
        }
        if (resume.getEducations() != null && !resume.getEducations().isEmpty()) {
            table_educations.setItems(FXCollections.observableList(resume.getEducations()));
        }
    }

    private void initTableEducations() {
        table_educations_id.setCellValueFactory(new PropertyValueFactory<Education, Long>("id"));
        table_educations_type.setCellValueFactory(new PropertyValueFactory<Education, String>("type"));
        table_educations_organisation.setCellValueFactory(new PropertyValueFactory<Education, String>("organization"));
        table_educations_name.setCellValueFactory(new PropertyValueFactory<Education, String>("name"));
        table_educations_result.setCellValueFactory(new PropertyValueFactory<Education, String>("result"));
        table_educations_year.setCellValueFactory(new PropertyValueFactory<Education, Integer>("year"));
    }

    private void initTableExperience() {
        table_experience_id.setCellValueFactory(new PropertyValueFactory<Experience, Long>("id"));
        table_experience_area.setCellValueFactory(new PropertyValueFactory<Experience, String>("area"));
        table_experience_company.setCellValueFactory(new PropertyValueFactory<Experience, String>("company"));
        table_experience_industries.setCellValueFactory(new PropertyValueFactory<Experience, String>("industries"));
        table_experience_description.setCellValueFactory(new PropertyValueFactory<Experience, String>("description"));
        table_experience_position.setCellValueFactory(new PropertyValueFactory<Experience, String>("position"));
        table_experience_start.setCellValueFactory(new PropertyValueFactory<Experience, String>("start"));
        table_experience_end.setCellValueFactory(new PropertyValueFactory<Experience, String>("end"));
    }

    private void initTableRecommendations() {
        table_recommendations_name.setCellValueFactory(new PropertyValueFactory<Recommendation, String>("name"));
        table_recommendations_organisation.setCellValueFactory(new PropertyValueFactory<Recommendation, String>("organization"));
        table_recommendations_position.setCellValueFactory(new PropertyValueFactory<Recommendation, String>("position"));
        table_recommendations_contact.setCellValueFactory(new PropertyValueFactory<Recommendation, String>("contact"));
    }

    private void loadImg(String photoUrl) {
        Image image;
        if (photoUrl != null && !photoUrl.isBlank()) {
            image = new Image(photoUrl);
        } else {
            image = new Image("img_standard_profile_photo.jpg");
        }
        img_profile_photo.setImage(image);
    }
}
