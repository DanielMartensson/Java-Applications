package CoolFeatures;

import Interfaces.FadeDelay;
import Windows.MainWindow;
import javafx.animation.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Duration;


/* This class works as it close the current stage after a few milli seconds
    and open a new window after that.
    To add new windows to open, create another constructor with the parameters

    WindowDynamics(Stage closeStage, another-class another-object)

 */

public class WindowDynamics implements FadeDelay {

    public WindowDynamics(){
    }

    public void OpenWithDelay(int delayMilliseconds, MainWindow mainWindow){
        PauseTransition delay = new PauseTransition(Duration.millis(delayMilliseconds));
        delay.setOnFinished( e -> {
            try {
                mainWindow.start(new Stage()); // Start new window
            } catch (Exception f) {
                f.printStackTrace();
            }
        });
        delay.play();
    }

    public void CloseStageWithFade(Stage closeStage, int fadeMilliseconds){
        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(Duration.millis(fadeMilliseconds), new KeyValue(closeStage.getScene().getRoot().opacityProperty(), 0));
        timeline.getKeyFrames().add(key);
        timeline.setOnFinished((ae) -> {
            closeStage.close();
        });
        timeline.play();

    }

    public void FadeParent(int milliSeconds, Parent parent, boolean fadeUp){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(milliSeconds), parent);
        if(fadeUp) {
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
        }else {
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
        }
        fadeTransition.play();
    }

}
