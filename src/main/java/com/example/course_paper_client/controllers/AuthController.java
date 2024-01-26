package com.example.course_paper_client.controllers;

import com.example.course_paper_client.MainApp;
import com.example.course_paper_client.exceptions.ApiResponseException;
import com.example.course_paper_client.exceptions.NoConnectionException;
import com.example.course_paper_client.services.MainServiceApi;
import com.example.course_paper_client.utils.DataSingleton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthController {

    private static String token = "";
    private final String errorTxtFieldStyle = "-fx-border-color: red; -fx-border-width: 0 0 0 2;";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox main_container;

    @FXML
    private Button btn_sign_in;

    @FXML
    private Button lp_btn_login;

    @FXML
    private Text lp_error_msg;

    @FXML
    private PasswordField lp_password;

    @FXML
    private Text lp_txt_btn_sign_in;

    @FXML
    private TextField lp_username;

    @FXML
    private ImageView preview_img;

    @FXML
    private TextField sp_email;

    @FXML
    private Text sp_error_msg;

    @FXML
    private TextField sp_first_name;

    @FXML
    private TextField sp_last_name;

    @FXML
    private PasswordField sp_password;

    @FXML
    private PasswordField sp_password_repeat;

    @FXML
    private Text txt_btn_login;

    @FXML
    private VBox vbox_page_login;

    @FXML
    private VBox vbox_page_registration;

    DataSingleton data = DataSingleton.getInstance();

    @FXML
    void initialize() {
        main_container.getChildren().remove(vbox_page_registration);
        initErrorMsg();
        initTxtFields();
        preview_img.setImage(new Image("hr-generalist_1.jpg"));
        preview_img.setPreserveRatio(false);

        lp_txt_btn_sign_in.setOnMouseClicked(mouseEvent -> {
            initErrorMsg();
            initTxtFields();
            visiblePageLogin(false);
        });
        txt_btn_login.setOnMouseClicked(mouseEvent -> {
            initErrorMsg();
            initTxtFields();
            visiblePageLogin(true);
        });

        btn_sign_in.setOnAction(this::onClickBtnSignIn);
        lp_btn_login.setOnAction(this::onClickBtnLogin);
    }

    public void onClickBtnLogin(Event event) {
        initTxtFields();
        initErrorMsg();
        if (checkRequiredFieldInLoginPage()) {
            try {
                token = MainServiceApi.login(getStringByTextField(lp_username), getStringByTextField(lp_password));
                lp_error_msg.setVisible(false);
                data.setResumes(MainServiceApi.getAllResumes(token, null));
                changeScene();
            } catch (NoConnectionException | ApiResponseException | IOException | JSONException e) {
                lp_error_msg.setVisible(true);
                lp_error_msg.setText(e.getMessage());
            }
        }
    }

    public void onClickBtnSignIn(Event event) {
        initTxtFields();
        initErrorMsg();
        if (checkRequiredFieldInSignInPage()) {
            try {
                token = MainServiceApi.registration(
                        getStringByTextField(sp_last_name),
                        getStringByTextField(sp_first_name),
                        getStringByTextField(sp_email),
                        getStringByTextField(sp_password),
                        getStringByTextField(sp_password_repeat));
                sp_error_msg.setVisible(false);
                data.setResumes(MainServiceApi.getAllResumes(token, null));
                changeScene();
            } catch (NoConnectionException | ApiResponseException | IOException | JSONException e) {
                lp_error_msg.setVisible(true);
                lp_error_msg.setText(e.getMessage());
            }
        }
    }

    private void changeScene() throws IOException {
        data.setToken(token);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainApp.changeScene(scene);
    }

    /**
     * Метод в зависимости от получаемого boolean параметра отображает необходимое окно.
     * Если true, то окно авторизации, иначе окно регистрации.
     *
     * @param state Boolean если true, то отображать, иначе скрывать страницу
     */
    private void visiblePageLogin(boolean state) {
        if (state) {
            main_container.getChildren().add(vbox_page_login);
            main_container.getChildren().remove(vbox_page_registration);
        } else {
            main_container.getChildren().add(vbox_page_registration);
            main_container.getChildren().remove(vbox_page_login);
        }
    }

    /**
     * Метод инициализирует поле error_msg
     */
    private void initErrorMsg() {
        lp_error_msg.setWrappingWidth(200);
        sp_error_msg.setWrappingWidth(200);

        if (data.getTextErrorByAuth() == null || data.getTextErrorByAuth().isEmpty()) {
            lp_error_msg.setText("");
            lp_error_msg.setVisible(false);
        } else {
            lp_error_msg.setText(data.getTextErrorByAuth());
            lp_error_msg.setVisible(true);
            data.setTextErrorByAuth("");
        }
        sp_error_msg.setText("");
        sp_error_msg.setVisible(false);
    }

    /**
     * Метод инициализирует все текстовые поля
     */
    private void initTxtFields() {
        sp_last_name.setStyle("");
        sp_first_name.setStyle("");
        sp_email.setStyle("");
        sp_password.setStyle("");
        sp_password_repeat.setStyle("");

        lp_username.setStyle("");
        lp_password.setStyle("");
    }

    /**
     * Метод проверяет заполнение обязательных полей на странице авторизации
     * В случае незаполнения выводит ошибку в интерфейс
     */
    private boolean checkRequiredFieldInLoginPage() {
        boolean result = true;
        if (getStringByTextField(lp_username).isBlank()) {
            lp_username.setStyle(errorTxtFieldStyle);
            result = false;
        }
        if (getStringByTextField(lp_password).isBlank()) {
            lp_password.setStyle(errorTxtFieldStyle);
            result = false;
        }

        return result;
    }

    /**
     * Метод проверяет заполнение обязательных полей на странице регистрации
     * В случае незаполнения выводит ошибку в интерфейс
     */
    private boolean checkRequiredFieldInSignInPage() {
        boolean result = true;
        if (getStringByTextField(sp_last_name).isBlank()) {
            sp_last_name.setStyle(errorTxtFieldStyle);
            result = false;
        }
        if (getStringByTextField(sp_first_name).isBlank()) {
            sp_first_name.setStyle(errorTxtFieldStyle);
            result = false;
        }
        if (getStringByTextField(sp_email).isBlank()) {
            sp_email.setStyle(errorTxtFieldStyle);
            result = false;
        }
        if (getStringByTextField(sp_password).isBlank()) {
            sp_password.setStyle(errorTxtFieldStyle);
            result = false;
        }
        if (getStringByTextField(sp_password_repeat).equals("") || !getStringByTextField(sp_password).equals(getStringByTextField(sp_password_repeat))) {
            sp_error_msg.setVisible(true);
            sp_password.setStyle(errorTxtFieldStyle);
            sp_password_repeat.setStyle(errorTxtFieldStyle);
            sp_error_msg.setText("Пароли не совпадают!");
            result = false;
        }

        return result;
    }

    /**
     * Метод возвращает текст содержащийся в поле ввода, который передается в виде параметра
     *
     * @param textField TextField поле ввода, из которого нужно забрать текст
     * @return String
     */
    private String getStringByTextField(TextField textField) {
        return textField.getText().trim();
    }
}
