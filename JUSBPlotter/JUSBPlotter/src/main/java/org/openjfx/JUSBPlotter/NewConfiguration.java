package org.openjfx.JUSBPlotter;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewConfiguration {

    @FXML
    private TextField textField; 
    
    private String nameConfig;

    /*
     * Create new configuration
     */
    @FXML
    void OK(ActionEvent event) throws IOException {
    	nameConfig = textField.getText();
    	File configurations = new File("configurations");
    	File[] lists = configurations.listFiles();
    	boolean exist = false;
    	for(int i = 0; i < lists.length; i++) {
    		String fileName = lists[i].getName();
    		if(fileName.equals(nameConfig + ".config")) {
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
    			configurations = new File("configurations\\" + nameConfig + ".config");
    			configurations.createNewFile();
    		}else {
    			configurations = new File("configurations/" + nameConfig + ".config");
    			configurations.createNewFile();
    		}
    		
    		/*
    		 * Close
    		 */
    		Stage stage = (Stage) textField.getScene().getWindow();
    		stage.close();
    	}
    }

	public String getNameConfig() {
		return nameConfig;
	}
    
}
