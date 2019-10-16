package org.openjfx.JUSBPlotter;

import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import org.openjfx.concurrency.Measureing;

public class Main extends Application{

	/*
	 * Start the start(Stage front)
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage front) throws Exception {
		front.setOnCloseRequest(e->{
			e.consume();
			askForClosing();
		});
		Parent root = FXMLLoader.load(getClass().getResource("/org/openjfx/JUSBPlotter/front.fxml"));
		Scene scene = new Scene(root);
		front.setScene(scene);
		front.setTitle("JUSBPlotter");		
		front.show();
	}

	private void askForClosing() {
		Alert question = new Alert(AlertType.CONFIRMATION);
		question.setTitle("Closing");
		question.setHeaderText("Do you want to close?");
		question.setResizable(false);
		question.setContentText("Press OK to close.");
		Optional<ButtonType> answer = question.showAndWait();
		if(answer.get() == ButtonType.OK) {
			Measureing.active = false;
			Platform.exit();
		}
		
	}

}
