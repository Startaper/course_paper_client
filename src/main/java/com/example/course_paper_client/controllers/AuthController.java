package com.example.course_paper_client.controllers;

import com.example.course_paper_client.exceptions.NoConnectionException;
import com.example.course_paper_client.services.MainServiceApi;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthController {

    private static String token = "";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_sign_in;

    @FXML
    private Button lp_btn_login;

    @FXML
    private Text lp_error_msg;

    @FXML
    private TextField lp_password;

    @FXML
    private Text lp_txt_btn_sign_in;

    @FXML
    private TextField lp_username;

    @FXML
    private AnchorPane page_login;

    @FXML
    private AnchorPane page_regisrtation;

    @FXML
    private TextField sp_email;

    @FXML
    private Text sp_error_msg;

    @FXML
    private ImageView preview_img;

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
    void initialize() {
        initErrorMsg();

        lp_txt_btn_sign_in.setOnMouseClicked(mouseEvent -> {
            initErrorMsg();
            visiblePageLogin(false);
            visiblePageSignIn(true);
        });
        txt_btn_login.setOnMouseClicked(mouseEvent -> {
            initErrorMsg();
            visiblePageLogin(true);
            visiblePageSignIn(false);
        });

        btn_sign_in.setOnMouseClicked(mouseEvent -> {
            initErrorMsg();
            checkRequiredFieldInSignInPage();
            try {
                token = MainServiceApi.registration(
                        getStringByTextField(sp_last_name),
                        getStringByTextField(sp_first_name),
                        getStringByTextField(sp_email),
                        getStringByTextField(sp_password),
                        getStringByTextField(sp_password_repeat));
                sp_error_msg.setVisible(false);
//                initNewStage();
            } catch (NoConnectionException | IOException e) {
                sp_error_msg.setVisible(true);
                sp_error_msg.setText(addErrorMsg("Сервер не доступен! Повторите запрос позже!", sp_error_msg.getText().trim()));
            } catch (JSONException e) {
                sp_error_msg.setVisible(true);
                sp_error_msg.setText(addErrorMsg("Неизвестная ошибка! Для тех. поддержки: ошибка при десериализации в методе MainServiceApi.registration", sp_error_msg.getText().trim()));
            }
        });
        lp_btn_login.setOnMouseClicked(mouseEvent -> {
            initErrorMsg();
            checkRequiredFieldInLoginPage();
            try {
                token = MainServiceApi.login(getStringByTextField(lp_username), getStringByTextField(lp_password));
                lp_error_msg.setVisible(false);
//                initNewStage();
            } catch (NoConnectionException | IOException e) {
                lp_error_msg.setVisible(true);
                lp_error_msg.setText(addErrorMsg("Сервер не доступен! Повторите запрос позже!", lp_error_msg.getText().trim()));
            } catch (JSONException e) {
                lp_error_msg.setVisible(true);
                lp_error_msg.setText(addErrorMsg("Неизвестная ошибка! Для тех. поддержки: ошибка при десериализации в методе MainServiceApi.login", lp_error_msg.getText().trim()));
            }
        });
    }

    private void initNewStage() {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setOpacity(1);
//        stage.setTitle("HR Assistant");
//
//        MainController mainController = fxmlLoader.getController();
//        mainController.setStartData(MainServiceApi.getAll(token, null));
//
//        stage.setScene(scene);
//        stage.show();
    }

    /**
     * Метод скрывает или отображает страницу авторизации
     *
     * @param state Boolean если true, то отображать, иначе скрывать страницу
     */
    private void visiblePageLogin(boolean state) {
        if (state) {
            page_login.setDisable(false);
            page_login.setVisible(true);
        } else {
            page_login.setDisable(true);
            page_login.setVisible(false);
        }
    }

    /**
     * Метод инициализирует поле error_msg
     */
    private void initErrorMsg() {
        lp_error_msg.setText("");
        sp_error_msg.setText("");
        lp_error_msg.setVisible(false);
        sp_error_msg.setVisible(false);
    }

    /**
     * Метод скрывает или отображает страницу регистрации
     *
     * @param state Boolean если true, то отображать, иначе скрывать страницу
     */
    private void visiblePageSignIn(boolean state) {
        if (state) {
            page_regisrtation.setDisable(false);
            page_regisrtation.setVisible(true);
        } else {
            page_regisrtation.setDisable(true);
            page_regisrtation.setVisible(false);
        }
    }

    /**
     * Метод проверяет заполнение обязательных полей на странице авторизации
     * В случае незаполнения выводит ошибку в интерфейс
     */
    private void checkRequiredFieldInLoginPage() {
        if (getStringByTextField(lp_username).isBlank()) {
            lp_error_msg.setVisible(true);
            lp_error_msg.setText(addErrorMsg("Поле 'Эл. почта' не может быть пустым!", lp_error_msg.getText().trim()));
        }
        if (getStringByTextField(lp_password).isBlank()) {
            lp_error_msg.setVisible(true);
            lp_error_msg.setText(addErrorMsg("Поле 'Пароль' не может быть пустым!", lp_error_msg.getText().trim()));
        }
    }

    /**
     * Метод проверяет заполнение обязательных полей на странице регистрации
     * В случае незаполнения выводит ошибку в интерфейс
     */
    private void checkRequiredFieldInSignInPage() {
        if (getStringByTextField(sp_last_name).isBlank()) {
            sp_error_msg.setVisible(true);
            sp_error_msg.setText(addErrorMsg("Поле 'Фамилия' не может быть пустым!", sp_error_msg.getText().trim()));
        }
        if (getStringByTextField(sp_first_name).isBlank()) {
            sp_error_msg.setVisible(true);
            sp_error_msg.setText(addErrorMsg("Поле 'Имя' не может быть пустым!", sp_error_msg.getText().trim()));
        }
        if (getStringByTextField(sp_email).isBlank()) {
            sp_error_msg.setVisible(true);
            sp_error_msg.setText(addErrorMsg("Поле 'Эл. почта' не может быть пустым!", sp_error_msg.getText().trim()));
        }
        if (getStringByTextField(sp_password).isBlank()) {
            sp_error_msg.setVisible(true);
            sp_error_msg.setText(addErrorMsg("Поле 'Пароль' не может быть пустым!", sp_error_msg.getText().trim()));
        }
        if (getStringByTextField(sp_password_repeat).isBlank()) {
            sp_error_msg.setVisible(true);
            sp_error_msg.setText(addErrorMsg("Поле 'Повторите пароль' не может быть пустым!", sp_error_msg.getText().trim()));
        }
        if (!getStringByTextField(sp_password).equals(getStringByTextField(sp_password_repeat))) {
            sp_error_msg.setVisible(true);
            sp_error_msg.setText(addErrorMsg("Пароли не совпадают!", sp_error_msg.getText().trim()));
        }
    }

    /**
     * Метод возвращает преобразованный текст ошибки для последующего вывода его на экран.
     *
     * @param msg      String текст ошибки, которую необходимо добавить в errorMsg
     * @param errorMsg String текст ошибки, которая уже отображается на экране
     * @return String
     */
    private String addErrorMsg(String msg, String errorMsg) {
        if (!errorMsg.isBlank()) {
            errorMsg = errorMsg + "\n";
        }

        errorMsg = errorMsg + msg;

        return errorMsg;
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
