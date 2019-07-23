package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.w3c.dom.events.Event;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.primaryStage.setTitle("Database reader");
        this.primaryStage.setResizable(false);
        this.primaryStage.setScene(new Scene(root, 723, 559));
        this.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
