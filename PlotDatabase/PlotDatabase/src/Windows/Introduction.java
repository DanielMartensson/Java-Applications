package Windows;


import CoolFeatures.WindowDynamics;
import Interfaces.FadeDelay;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Introduction extends Application {

    // Load the Introduction.fxml file
    @Override
    public void start(Stage introStage) throws Exception{
        Parent introParent = FXMLLoader.load(getClass().getResource("/SceneBuilderFXML/Introduction.fxml"));
        introStage.initStyle(StageStyle.UNDECORATED); // Remove the menubar and shut down button
        introStage.setScene(new Scene(introParent, 500, 200));
        introStage.show();

        // Close with a delay and open MainWindow
        FadeDelay fadeDelay = new WindowDynamics();
        fadeDelay.OpenWithDelay(3000, new MainWindow()); // Open MainWindow after 3000
        fadeDelay.CloseStageWithFade(introStage, 3000);

    }

    @FXML
    public void initialize() {

    }
}