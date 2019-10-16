package org.openjfx.JUSBPlotter;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewMeasurement {

    @FXML
    private TextField textField;
    
    private String nameField;
    private File measurement;

    /*
     * Create new measurement
     */
    @FXML
    void OK(ActionEvent event) throws IOException {
    	nameField = textField.getText();
    	File measurements = new File("measurements");
    	File[] lists = measurements.listFiles();
    	boolean exist = false;
    	for(int i = 0; i < lists.length; i++) {
    		String fileName = lists[i].getName();
    		if(fileName.equals(nameField + ".mat")) {
    			exist = true;
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Exist!");
    			alert.setHeaderText("Already exist!");
    			alert.setContentText("Select another name.");
    			alert.showAndWait();
    			break;
    		}
    	}
    	
    	if(exist == false) {
    		String osName = System.getProperty("os.name").toLowerCase();
    		if(osName.contains("win")) {
    			measurement = new File("measurements\\" + nameField + ".mat");
    			measurement.createNewFile();
    		}else {
    			measurement = new File("measurements/" + nameField + ".mat");
    			measurement.createNewFile();
    		}
    		
    		/*
    		 * Close
    		 */
    		Stage stage = (Stage) textField.getScene().getWindow();
    		stage.close();
    	}
    }

	public String getNameField() {
		return nameField;
	}

	public File getMeasurement() {
		return measurement;
	}
    
}
