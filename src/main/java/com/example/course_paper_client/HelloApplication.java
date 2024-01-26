package com.example.course_paper_client;

import com.example.course_paper_client.utils.DataSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stageMain;
    private static final DataSingleton data = DataSingleton.getInstance();

    @Override
    public void start(Stage stage) throws IOException {
        stageMain = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("auth-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HR Assistant");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("hr-icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Метод меняет текущую сцену на полученную из параметра
     *
     * @param scene Scene, новая сцена
     */
    public static void changeScene(Scene scene) {
        stageMain.setScene(scene);
        stageMain.show();
    }

    /**
     * Метод открывает новое окно по заданным параметрам
     *
     * @param viewFxml файл с fxml разметкой
     * @param title String, название показываемого окна
     * @param resizable возможность изменять размер окна
     * @param style стиль отображаемого окна
     * @param modality формат показываемого окна
     * @throws IOException если возникли ошибки при открытии окна.
     */
    public static void openNewStage(String viewFxml, String title, boolean resizable, StageStyle style, Modality modality) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(viewFxml));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage(style);
        stage.setTitle(title);
        stage.setResizable(resizable);
        stage.setScene(scene);
        stage.initModality(modality);
        data.setOpeningStage(stage);
        stage.showAndWait();
    }

    /**
     * Метод показывает информационное окно
     *
     * @param title String, название показываемого окна
     * @param alertType AlertType, тип всплывающего окна
     * @param headerText String, заголовок
     * @param message String, основной текст
     */
    public static void showAlert(String title, Alert.AlertType alertType, String headerText, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Метод показывает предупреждающее окно и возвращает информацию о том, какаю кнопку нажали.
     *
     * @param title String, название показываемого окна
     * @param alertType AlertType, тип всплывающего окна
     * @param headerText String, заголовок
     * @param message String, основной текст
     * @return ButtonType
     */
    public static ButtonType showWarningAlert(String title, Alert.AlertType alertType, String headerText, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        return alert.showAndWait().orElse(ButtonType.CLOSE);
    }
}