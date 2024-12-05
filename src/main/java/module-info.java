module com.market.newmarketapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jdk.httpserver;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;
    requires annotations;

    opens com.market.newmarketapp to javafx.fxml;
    exports com.market.newmarketapp;
    exports com.market.newmarketapp.adminScenes;
    opens com.market.newmarketapp.adminScenes to javafx.fxml;
    exports com.market.entity;
    exports com.market.newmarketapp.userScenes;
    opens com.market.newmarketapp.userScenes to javafx.fxml;
}