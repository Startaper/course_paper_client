module com.example.course_paper_client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires android.json;

    opens com.example.course_paper_client to javafx.fxml;
    exports com.example.course_paper_client;
    exports com.example.course_paper_client.controllers;
    opens com.example.course_paper_client.controllers to javafx.fxml;
}