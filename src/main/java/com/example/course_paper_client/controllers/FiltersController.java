package com.example.course_paper_client.controllers;

import com.example.course_paper_client.HelloApplication;
import com.example.course_paper_client.exceptions.ApiResponseException;
import com.example.course_paper_client.models.Resume;
import com.example.course_paper_client.models.enums.*;
import com.example.course_paper_client.utils.DataSingleton;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class FiltersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_close;

    @FXML
    private Button btn_reset_filters;

    @FXML
    private Button btn_save;

    @FXML
    private ChoiceBox<String> chbox_area;

    @FXML
    private ChoiceBox<String> chbox_bstrip;

    @FXML
    private ChoiceBox<String> chbox_education_level;

    @FXML
    private ChoiceBox<String> chbox_gender;

    @FXML
    private ChoiceBox<String> chbox_status;

    @FXML
    private ChoiceBox<String> chbox_travel_time;

    @FXML
    private Spinner<Integer> spinner_age_end;

    @FXML
    private Spinner<Integer> spinner_age_start;

    @FXML
    private Spinner<Integer> spinner_salary_end;

    @FXML
    private Spinner<Integer> spinner_salary_start;

    @FXML
    private VBox main_vbox;

    DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    void initialize() {
        initFilterFields();
        initFiltersState();

        btn_close.setOnAction(this::onClickClose);
        btn_reset_filters.setOnMouseClicked(this::resetFilters);
        btn_save.setOnAction(this::onClickSave);
    }

    private void onClickSave(Event event) {
        boolean status = false;
        Map<String, String> filters = new HashMap<>();

        try {
            if (chbox_status.getValue() != null && !chbox_status.getValue().isEmpty()) {
                filters.put("status", ResumeStatus.getStatusByName(chbox_status.getValue()));
                status = true;
            }
            if (chbox_gender.getValue() != null && !chbox_gender.getValue().isEmpty()) {
                filters.put("gender", Gender.getGenderByName(chbox_gender.getValue()));
                status = true;
            }
            if (chbox_bstrip.getValue() != null && !chbox_bstrip.getValue().isEmpty()) {
                filters.put("businessTripReadiness",
                        BusinessTripReadinessType.getBusinessTripReadinessTypeByName(chbox_bstrip.getValue()));
                status = true;
            }
            if (chbox_travel_time.getValue() != null && !chbox_travel_time.getValue().isEmpty()) {
                filters.put("travelTime", TravelTimeType.getTravelTimeTypeByName(chbox_travel_time.getValue()));
                status = true;
            }
            if (chbox_education_level.getValue() != null && !chbox_education_level.getValue().isEmpty()) {
                filters.put("educationLevel", EducationLevel.getEducationLevelByName(chbox_education_level.getValue()));
                status = true;
            }
            if (chbox_area.getValue() != null && !chbox_area.getValue().isEmpty()) {
                filters.put("areaName", chbox_area.getValue());
                status = true;
            }
            if (spinner_age_start.getValue() != null && spinner_age_start.getValue() != 0) {
                filters.put("ageStart", String.valueOf(spinner_age_start.getValue()));
                status = true;
            }
            if (spinner_age_end.getValue() != null && spinner_age_end.getValue() != 0) {
                filters.put("ageEnd", String.valueOf(spinner_age_end.getValue()));
                status = true;
            }
            if (spinner_salary_start.getValue() != null && spinner_salary_start.getValue() != 0) {
                filters.put("salaryStart", String.valueOf(spinner_salary_start.getValue()));
                status = true;
            }
            if (spinner_salary_end.getValue() != null && spinner_salary_end.getValue() != 0) {
                filters.put("salaryEnd", String.valueOf(spinner_salary_end.getValue()));
                status = true;
            }
        } catch (ApiResponseException e) {
            System.out.println(e.getMessage());
            HelloApplication.showAlert("Ошибка", Alert.AlertType.INFORMATION, "Ошибка при обновлении данных.", e.getMessage());
        }

        if (status) {
            dataSingleton.setFilters(filters);
        } else {
            dataSingleton.setFilters(null);
        }

        onClickClose(event);
    }

    private void onClickClose(Event event) {
        Stage stage = dataSingleton.getOpeningStage();
        dataSingleton.setOpeningStage(null);
        stage.close();
    }

    private void initFilterFields() {
        Set<String> areas = dataSingleton.getResumes().stream()
                .map(Resume::getArea)
                .collect(Collectors.toSet());
        chbox_area.setItems(FXCollections.observableList(areas.stream().toList()));
        chbox_gender.setItems(FXCollections.observableList(Arrays.stream(Gender.values())
                .map(Gender::getName).toList()).sorted());
        chbox_bstrip.setItems(FXCollections.observableList(Arrays.stream(BusinessTripReadinessType.values())
                .map(BusinessTripReadinessType::getName).toList()).sorted());
        chbox_education_level.setItems(FXCollections.observableList(Arrays.stream(EducationLevel.values())
                .map(EducationLevel::getName).toList()).sorted());
        chbox_status.setItems(FXCollections.observableList(Arrays.stream(ResumeStatus.values())
                .map(ResumeStatus::getName).toList()).sorted());
        chbox_travel_time.setItems(FXCollections.observableList(Arrays.stream(TravelTimeType.values())
                .map(TravelTimeType::getName).toList()).sorted());
        spinner_age_start.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, 0, 1));
        spinner_age_end.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, 0, 1));
        spinner_salary_start.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000000, 0, 1));
        spinner_salary_end.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000000, 0, 1));
    }

    private void initFiltersState() {
        Map<String, String> filtersState = dataSingleton.getFilters();
        if (filtersState != null && !filtersState.isEmpty()) {
            if (filtersState.containsKey("status")) {
                chbox_status.setValue(ResumeStatus.valueOf(filtersState.get("status")).getName());
            }
            if (filtersState.containsKey("gender")) {
                chbox_gender.setValue(Gender.valueOf(filtersState.get("gender")).getName());
            }
            if (filtersState.containsKey("businessTripReadiness")) {
                chbox_bstrip.setValue(BusinessTripReadinessType.valueOf(filtersState.get("businessTripReadiness")).getName());
            }
            if (filtersState.containsKey("travelTime")) {
                chbox_travel_time.setValue(TravelTimeType.valueOf(filtersState.get("travelTime")).getName());
            }
            if (filtersState.containsKey("educationLevel")) {
                chbox_education_level.setValue(EducationLevel.valueOf(filtersState.get("educationLevel")).getName());
            }
            if (filtersState.containsKey("ageStart")) {
                spinner_age_start.getValueFactory().setValue(Integer.parseInt(filtersState.get("ageStart")));
            }
            if (filtersState.containsKey("ageEnd")) {
                spinner_age_end.getValueFactory().setValue(Integer.parseInt(filtersState.get("ageEnd")));
            }
            if (filtersState.containsKey("salaryStart")) {
                spinner_salary_start.getValueFactory().setValue(Integer.parseInt(filtersState.get("salaryStart")));
            }
            if (filtersState.containsKey("salaryEnd")) {
                spinner_salary_end.getValueFactory().setValue(Integer.parseInt(filtersState.get("salaryEnd")));
            }
            chbox_area.setValue(filtersState.get("areaName"));
        }
    }

    public void resetFilters(Event event) {
        chbox_area.setValue("");
        chbox_gender.setValue("");
        chbox_bstrip.setValue("");
        chbox_status.setValue("");
        chbox_travel_time.setValue("");
        chbox_education_level.setValue("");
        spinner_age_end.getValueFactory().setValue(0);
        spinner_age_start.getValueFactory().setValue(0);
        spinner_salary_end.getValueFactory().setValue(0);
        spinner_salary_start.getValueFactory().setValue(0);
    }

}
