module HRM_JAVAFX_JAVACORE {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires javafx.base;

    opens application to javafx.fxml, javafx.base;
    exports application to javafx.graphics;
}
