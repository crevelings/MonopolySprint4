module org.monopoly.View {
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
    requires javafx.media;
    requires java.desktop;

    opens org.monopoly.View to javafx.fxml, javafx.graphics;
    exports org.monopoly.View.GameScene.Board to javafx.graphics, javafx.fxml;
    exports org.monopoly.View to javafx.fxml, javafx.graphics;
    opens org.monopoly.View.GameScene.Board to javafx.fxml, javafx.graphics;
    exports org.monopoly.View.GameScene to javafx.fxml, javafx.graphics;
    opens org.monopoly.View.GameScene to javafx.fxml, javafx.graphics;
}