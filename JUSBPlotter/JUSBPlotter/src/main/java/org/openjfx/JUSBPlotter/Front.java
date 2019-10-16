package org.openjfx.JUSBPlotter;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.concurrency.Measureing;
import javafx.stage.FileChooser.ExtensionFilter;

public class Front {
	
	/*
	 * Fields
	 */
	private Alert alert;
	private Alert info;
	private Alert warning;
	private File measurments; // Folder
	private File measurement; // File
	private String nameFile;
	
	@FXML
    private Menu recentMenu;

    @FXML
    private LineChart<String, Float> graph;
    
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    
    /*
     * Private fields
     */
	private File configurations;
	private Measureing measuring;
	private FXMLLoader loaderNewMeasurement;
	private FXMLLoader loaderConnection;
	private FXMLLoader loaderPlot;
	private boolean measureingStarted;
	private Parent rootPlot;
	private Parent rootConnection;
	private Parent rootNewMeasurement;
	private Scene scenePlot;
	private Scene sceneConnection;
	private Scene sceneNewMeasurement;
	private Plot plot;
	private Connection connection;
	private FXMLLoader loaderAbout ;
	private Parent rootAbout;
	private Scene sceneAbout;
	private FXMLLoader loaderProtocol;
	private Parent rootProtocol;
	private Scene sceneProtocol;
	
	/*
	 * Got to web site
	 */
    @FXML
    void gitHub(ActionEvent event) {
		info.setTitle("Information!");
		info.setHeaderText("Go to this web page and download software for STM32.");
		info.setContentText("https://github.com/DanielMartensson/JUSBPlotter");
		info.showAndWait();
    }

    /*
     * Show about dialog
     */
    @FXML
    void about(ActionEvent event) {
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(sceneAbout);
		stage.setTitle("About");
		stage.showAndWait();
    	
    }
    
    /*
     * Load new GUI where you connect to USB
     */
    @FXML
    void connect(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(sceneConnection);
		stage.setTitle("Connect USB");
		stage.showAndWait();
    }

    /*
     * Every time you press "File" in menu, then we reload the recent files that have been created
     */
    @FXML
    void loadRecents(ActionEvent event) {
    	File[] measurementsList = measurments.listFiles();
    	if(measurementsList.length == 0) {
    		return;
    	}
    	Arrays.sort(measurementsList, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
    	
    	/*
    	 * Select the first four and add an action to it
    	 */
    	recentMenu.getItems().clear();
    	int length = 0;
    	if(measurementsList.length <= 4) {
    		length = measurementsList.length ;
    	}else {
    		length = 4;
    	}
    	for(int i = 0; i < length; i++) {
    		String nameOfFile = measurementsList[i].getName();
    		File selectedFile = measurementsList[i];
    		MenuItem menuItem = new MenuItem(nameOfFile);
    		menuItem.setOnAction(e->{
    			nameFile = nameOfFile;
    			measurement = selectedFile;
    			/*
    			 * Change the title
    			 */
    			Stage stageFront = (Stage) graph.getScene().getWindow();
    			stageFront.setTitle("JUBSPlotter - " + nameFile);
    			
    			/*
    			 * Set new change date and update
    			 */
    			selectedFile.setLastModified(new Date().getTime());
    			loadRecents(null);

    		});
    		recentMenu.getItems().add(menuItem);
    	}
    }

    /*
     * Open a dialog where program asking for a new name
     */
    @FXML
    void newMeasure(ActionEvent event) throws Exception {
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(sceneNewMeasurement);
		stage.setTitle("New measurement");
		stage.showAndWait();
		
		/*
		 * Now get value
		 */
		NewMeasurement newMeasuremnt = loaderNewMeasurement.getController();
		if(newMeasuremnt.getNameField() != null) {
			nameFile = newMeasuremnt.getNameField() + ".mat";
			measurement = newMeasuremnt.getMeasurement();
			loadRecents(null);
			
			/*
			 * Change the title
			 */
			Stage stageFront = (Stage) graph.getScene().getWindow();
			stageFront.setTitle("JUBSPlotter - " + nameFile);
		}

    }
    
    /*
     * Show the protocol
     */
    @FXML
    void protocol(ActionEvent event) {
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(sceneProtocol);
		stage.setTitle("Protocol UART");
		stage.showAndWait();
    }
    
    /*
     * Open a dialog where program asking for deleting the current selected measurement
     */
    @FXML
    void delete(ActionEvent event) {
    	Stage stage = new Stage();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Mat Files", "*.mat"));
    	File selectedFile = fileChooser.showOpenDialog(stage);
    	
    	/*
    	 * No selected file
    	 */
    	if(selectedFile == null) {
    		return;
    	}
   	
    	/*
    	 * Ask for delete
    	 */
    	Alert question = new Alert(AlertType.CONFIRMATION);
		question.setTitle("Delete");
		question.setHeaderText("Do you want to delete this file?");
		question.setResizable(false);
		question.setContentText("Press OK to delete.");
		Optional<ButtonType> answer = question.showAndWait();
		if(answer.get() == ButtonType.OK) {
			String selectedName = selectedFile.getName();
			if(selectedFile.delete() == true) {
	    		/*
	    		 * Change the title
	    		 */
	    		if(selectedName.equals(nameFile)) {
	    			nameFile = "";
	    			Stage stageFront = (Stage) graph.getScene().getWindow();
	    			stageFront = (Stage) graph.getScene().getWindow();
		    		stageFront.setTitle("JUBSPlotter");
	    		}
	    		
	    	}else {
	    		alert.setTitle("Error!");
				alert.setHeaderText("Cannot delete measurment");
				alert.setContentText("Need to have rights to access this file");
				alert.showAndWait();
	    	}
		}
		
		/*
		 * Load
		 */
		loadRecents(null);
    }

    /*
     * Open file dialog
     */
    @FXML
    void openMeasure(ActionEvent event) {
    	Stage stage = new Stage();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Mat Files", "*.mat"));
    	File selectedFile = fileChooser.showOpenDialog(stage);
    	
    	/*
    	 * No selected file
    	 */
    	if(selectedFile == null) {
    		return;
    	}
    	
    	/*
    	 * Get the name
    	 */
    	nameFile = selectedFile.getName();
    	measurement = selectedFile;
    	selectedFile.setLastModified(new Date().getTime());
    	loadRecents(null);
    	
    	/*
		 * Change the title
		 */
    	Stage stageFront = (Stage) graph.getScene().getWindow();
		stageFront.setTitle("JUBSPlotter - " + nameFile);
    }

    /*
     * Change the plot, x-axis, y-axis, title etc..
     */
    @FXML
    void plot(ActionEvent event) throws IOException {
       	Stage stage = new Stage();
       	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scenePlot);
		stage.setTitle("Change Plot");
		stage.showAndWait();
    }

    /*
     * Quit the program
     */
    @FXML
    void quit(ActionEvent event) {
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

    /*
     * Start measuring
     */
    @FXML
    void start(ActionEvent event) throws IOException {    
    	
    	/*
    	 * Pass the complete controller as argument
    	 */
    	if(measureingStarted == false) {
    		if(nameFile != null) {
    			if( nameFile.endsWith(".mat")) {
	    			measuring = new Measureing(plot, graph, xAxis, yAxis, connection, measurement);
	    	    	measuring.start();
	    	    	measureingStarted = true;
	    	    	Stage stageFront = (Stage) graph.getScene().getWindow();
	    			stageFront.setTitle("JUBSPlotter - " + nameFile + " - Started");
    			}else {
    				alert.setTitle("Error!");
    				alert.setHeaderText("No selected file.");
    				alert.setContentText("You need to select a measurement file first.");
    				alert.showAndWait();
    			}
    		}else {
				alert.setTitle("Error!");
				alert.setHeaderText("No selected file.");
				alert.setContentText("You need to select a measurement file first.");
				alert.showAndWait();
    		}
    	}else {
			warning.setTitle("Error!");
			warning.setHeaderText("Process is already started.");
			warning.setContentText("You need to stop it first, then start it.");
			warning.showAndWait();
    	}
    }

    /*
     * Stop measuring and save data
     */
    @FXML
    void stop(ActionEvent event) {
    	Measureing.active = false;
    	measureingStarted = false;
    	if(nameFile != null) {
    		if(nameFile.endsWith(".mat")) {
	    		Stage stageFront = (Stage) graph.getScene().getWindow();
	    		stageFront.setTitle("JUBSPlotter - " + nameFile + " - Stoped");
    		}else {
    			alert.setTitle("Error!");
    			alert.setHeaderText("No selected file.");
    			alert.setContentText("You have not select any measurement file.");
    			alert.showAndWait();
    		}
    	}else {
			alert.setTitle("Error!");
			alert.setHeaderText("No selected file.");
			alert.setContentText("You have not select any measurement file.");
			alert.showAndWait();
    	}
    }

    /*
     * Check if we haven't created a folder recents, measurements
     */
    @FXML
    void initialize() throws IOException {
    	/*
    	 * Create objects
    	 */
    	measurments = new File("measurements");
    	configurations = new File("configurations");
    	alert = new Alert(AlertType.WARNING);
    	info = new Alert(AlertType.INFORMATION);
    	warning = new Alert(AlertType.WARNING);

    	if(measurments.exists() == false) {
    		if(measurments.mkdir() == false) {
    			/*
    			 * Cannot create folder -Open a error dialog
    			 */
    			alert.setTitle("Error!");
    			alert.setHeaderText("Cannot create measurments folder");
    			alert.setContentText("Need to have rights to access this folder");
    			alert.showAndWait();
    		}
    	}
    	if(configurations.exists() == false) {
    		if(configurations.mkdir() == false) {
    			/*
    			 * Cannot create folder -Open a error dialog
    			 */
    			alert.setTitle("Error!");
    			alert.setHeaderText("Cannot create configurations folder");
    			alert.setContentText("Need to have rights to access this folder");
    			alert.showAndWait();
    		}
    	}
    	
    	/*
    	 * Load recent
    	 */
    	loadRecents(null);
    	
    	/*
    	 * Load FXML
    	 */
    	loaderPlot = new FXMLLoader(getClass().getResource("/org/openjfx/JUSBPlotter/plot.fxml"));
    	loaderConnection = new FXMLLoader(getClass().getResource("/org/openjfx/JUSBPlotter/connection.fxml"));
    	loaderNewMeasurement = new FXMLLoader(getClass().getResource("/org/openjfx/JUSBPlotter/newMeasurement.fxml"));
    	loaderAbout = new FXMLLoader(getClass().getResource("/org/openjfx/JUSBPlotter/about.fxml"));
    	loaderProtocol = new FXMLLoader(getClass().getResource("/org/openjfx/JUSBPlotter/protocol.fxml"));

    	/*
    	 * Create roots
    	 */
    	rootPlot = (Parent) loaderPlot.load();
    	rootConnection = (Parent) loaderConnection.load();
    	rootNewMeasurement = (Parent) loaderNewMeasurement.load();
    	rootAbout = (Parent) loaderAbout.load();
    	rootProtocol = (Parent) loaderProtocol.load();
    	
    	/*
    	 * Create scenes
    	 */
		scenePlot = new Scene(rootPlot);
		sceneConnection = new Scene(rootConnection);
		sceneNewMeasurement = new Scene(rootNewMeasurement);
		sceneAbout = new Scene(rootAbout);
		sceneProtocol = new Scene(rootProtocol);
		
		/*
		 * Get class from controller
		 */
		plot = loaderPlot.getController();
		connection = loaderConnection.getController();
		
    	/*
    	 * This is just because we don't want a null pointer exception
    	 */
    	measuring = new Measureing(plot, graph, xAxis, yAxis, connection, measurement);
    }
}
