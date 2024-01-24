package com.example.course_paper_client;

import com.example.course_paper_client.utils.DataSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(Scene scene) {
        stageMain.setScene(scene);
        stageMain.show();
    }

    public static void openNewStage(String viewFxml, String title, boolean resizable, StageStyle style, Modality modality, boolean isWarningStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(viewFxml));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage(style);
        stage.setTitle(title);
        stage.setResizable(resizable);
        stage.setScene(scene);
        stage.initModality(modality);
        if (!isWarningStage) {
            data.setOpeningStage(stage);
        } else {
            data.setWarningStage(stage);
        }
    }

}