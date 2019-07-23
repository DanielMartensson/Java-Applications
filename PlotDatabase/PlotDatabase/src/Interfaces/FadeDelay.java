package Interfaces;

import Windows.MainWindow;
import javafx.scene.Parent;
import javafx.stage.Stage;

public interface FadeDelay {
    void OpenWithDelay(int delayMilliseconds, MainWindow mainWindow);
    void CloseStageWithFade(Stage closeStage, int fadeMilliseconds);
    void FadeParent(int milliSeconds, Parent parent, boolean fadeUp);
}
