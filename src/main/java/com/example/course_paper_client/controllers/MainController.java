package com.example.course_paper_client.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_reset_filters;

    @FXML
    private Button btn_search;

    @FXML
    private TextField field_age_limit;

    @FXML
    private TextField field_age_with;

    @FXML
    private ChoiceBox<?> field_area;

    @FXML
    private ChoiceBox<?> field_education_level;

    @FXML
    private ChoiceBox<?> field_gender;

    @FXML
    private TextField field_salary_limit;

    @FXML
    private TextField field_salary_with;

    @FXML
    private TextField field_search_text;

    @FXML
    private ChoiceBox<?> field_status_resume;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> table_column_age;

    @FXML
    private TableColumn<?, ?> table_column_area;

    @FXML
    private TableColumn<?, ?> table_column_education_level;

    @FXML
    private TableColumn<?, ?> table_column_email;

    @FXML
    private TableColumn<?, ?> table_column_firstname;

    @FXML
    private TableColumn<?, ?> table_column_gender;

    @FXML
    private TableColumn<?, ?> table_column_lastname;

    @FXML
    private TableColumn<?, ?> table_column_number;

    @FXML
    private TableColumn<?, ?> table_column_phone;

    @FXML
    private TableColumn<?, ?> table_column_post;

    @FXML
    private TableColumn<?, ?> table_column_salary;

    @FXML
    private TableColumn<?, ?> table_column_status;

    @FXML
    void initialize() {
        assert btn_reset_filters != null : "fx:id=\"btn_reset_filters\" was not injected: check your FXML file 'main-view.fxml'.";
        assert btn_search != null : "fx:id=\"btn_search\" was not injected: check your FXML file 'main-view.fxml'.";
        assert field_age_limit != null : "fx:id=\"field_age_limit\" was not injected: check your FXML file 'main-view.fxml'.";
        assert field_age_with != null : "fx:id=\"field_age_with\" was not injected: check your FXML file 'main-view.fxml'.";
        assert field_area != null : "fx:id=\"field_area\" was not injected: check your FXML file 'main-view.fxml'.";
        assert field_education_level != null : "fx:id=\"field_education_level\" was not injected: check your FXML file 'main-view.fxml'.";
        assert field_gender != null : "fx:id=\"field_gender\" was not injected: check your FXML file 'main-view.fxml'.";
        assert field_salary_limit != null : "fx:id=\"field_salary_limit\" was not injected: check your FXML file 'main-view.fxml'.";
        assert field_salary_with != null : "fx:id=\"field_salary_with\" was not injected: check your FXML file 'main-view.fxml'.";
        assert field_search_text != null : "fx:id=\"field_search_text\" was not injected: check your FXML file 'main-view.fxml'.";
        assert field_status_resume != null : "fx:id=\"field_status_resume\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_age != null : "fx:id=\"table_column_age\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_area != null : "fx:id=\"table_column_area\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_education_level != null : "fx:id=\"table_column_education_level\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_email != null : "fx:id=\"table_column_email\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_firstname != null : "fx:id=\"table_column_firstname\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_gender != null : "fx:id=\"table_column_gender\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_lastname != null : "fx:id=\"table_column_lastname\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_number != null : "fx:id=\"table_column_number\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_phone != null : "fx:id=\"table_column_phone\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_post != null : "fx:id=\"table_column_post\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_salary != null : "fx:id=\"table_column_salary\" was not injected: check your FXML file 'main-view.fxml'.";
        assert table_column_status != null : "fx:id=\"table_column_status\" was not injected: check your FXML file 'main-view.fxml'.";

    }

}
