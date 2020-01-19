module languagedetection {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.danielgiljam to javafx.fxml;
    exports com.danielgiljam;
}
