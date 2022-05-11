/**
 *
 */
module com.example.micro_wins {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires java.persistence;
    requires lombok;
    requires spring.data.jpa;
    requires spring.beans;
    requires java.datatransfer;
    requires java.sql;
    requires logback.classic;

    opens com.example.micro_wins to javafx.fxml;
    exports com.example.micro_wins;
    exports com.example.micro_wins.controller;
    opens com.example.micro_wins.controller to javafx.fxml;
}