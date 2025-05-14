module com.example.crudaluno {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.crudaluno to javafx.fxml;
    opens br.com.puc.model to javafx.base;
    exports com.example.crudaluno;
}