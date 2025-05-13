module com.example.crudaluno {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.crudaluno to javafx.fxml;
    exports com.example.crudaluno;
}