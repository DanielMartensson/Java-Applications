package org.openjfx.JUSBPlotter;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Plot {

    @FXML
    private CheckBox legend1;

    @FXML
    private CheckBox legend3;

    @FXML
    private CheckBox legend2;

    @FXML
    private TextField legend2Text;

    @FXML
    private TextField legend1Text;

    @FXML
    private TextField legend3Text;

    @FXML
    private CheckBox legend4;

    @FXML
    private CheckBox legend5;

    @FXML
    private TextField legend4Text;

    @FXML
    private TextField legend5Text;

    @FXML
    private CheckBox animationON;

    @FXML
    private ComboBox<Integer> sampleTime;

    @FXML
    private ComboBox<String> yMin;

    @FXML
    private ComboBox<String> yMax;

    @FXML
    private ComboBox<Integer> maxData;

    @FXML
    private TextField k1;

    @FXML
    private TextField m1;

    @FXML
    private TextField m2;

    @FXML
    private TextField k2;

    @FXML
    private TextField k3;

    @FXML
    private TextField m3;

    @FXML
    private TextField k4;

    @FXML
    private TextField m4;

    @FXML
    private TextField k5;

    @FXML
    private TextField m5;

    @FXML
    private CheckBox legend6;

    @FXML
    private TextField legend6Text;

    @FXML
    private TextField k6;

    @FXML
    private TextField m6;
    
    @FXML
    private ComboBox<Integer> amountData;

	private Alert alert;
	
	private String nameConfig;
	
    @FXML
    void legend1Action(ActionEvent event) {
    	if(legend1.isSelected()) {
    		legend1Text.setDisable(false);
    		k1.setDisable(false);
    		m1.setDisable(false);
    	}else {
    		legend1Text.setDisable(true);
    		k1.setDisable(true);
    		m1.setDisable(true);
    	}
    }

    @FXML
    void legend2Action(ActionEvent event) {
    	if(legend2.isSelected()) {
    		legend2Text.setDisable(false);
    		k2.setDisable(false);
    		m2.setDisable(false);
    	}else {
    		legend2Text.setDisable(true);
    		k2.setDisable(true);
    		m2.setDisable(true);
    	}
    }

    @FXML
    void legend3Action(ActionEvent event) {
    	if(legend3.isSelected()) {
    		legend3Text.setDisable(false);
    		k3.setDisable(false);
    		m3.setDisable(false);
    	}else {
    		legend3Text.setDisable(true);
    		k3.setDisable(true);
    		m3.setDisable(true);
    	}
    }

    @FXML
    void legend4Action(ActionEvent event) {
    	if(legend4.isSelected()) {
    		legend4Text.setDisable(false);
    		k4.setDisable(false);
    		m4.setDisable(false);
    	}else {
    		legend4Text.setDisable(true);
    		k4.setDisable(true);
    		m4.setDisable(true);
    	}
    }

    @FXML
    void legend5Action(ActionEvent event) {
    	if(legend5.isSelected()) {
    		legend5Text.setDisable(false);
    		k5.setDisable(false);
    		m5.setDisable(false);
    	}else {
    		legend5Text.setDisable(true);
    		k5.setDisable(true);
    		m5.setDisable(true);
    	}
    }
    
    @FXML
    void legend6Action(ActionEvent event) {
    	if(legend6.isSelected()) {
    		legend6Text.setDisable(false);
    		k6.setDisable(false);
    		m6.setDisable(false);
    	}else {
    		legend6Text.setDisable(true);
    		k6.setDisable(true);
    		m6.setDisable(true);
    	}
    }

    @FXML
    void loadConfig(ActionEvent event) throws InvalidFileFormatException, IOException {
    	Stage stage = new Stage();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Config Files", "*.config"));
    	File selectedFile = fileChooser.showOpenDialog(stage);
    	
    	/*
    	 * No selected file
    	 */
    	if(selectedFile == null) {
    		return;
    	}
    	
    	/*
    	 * Load all values
    	 */
    	nameConfig = selectedFile.getName();
    	File configuration = configurationFile(nameConfig);
    	Wini ini = new Wini(configuration);
    	
    	/*
    	 * Legends
    	 */
    	legend1.setSelected(ini.get("legends", "legend1", boolean.class));
    	legend2.setSelected(ini.get("legends", "legend2", boolean.class));
    	legend3.setSelected(ini.get("legends", "legend3", boolean.class));
    	legend4.setSelected(ini.get("legends", "legend4", boolean.class));
    	legend5.setSelected(ini.get("legends", "legend5", boolean.class));
    	legend6.setSelected(ini.get("legends", "legend6", boolean.class));
    	
    	/*
         * Legends text
         */
    	legend1Text.setText(ini.get("legendTexts", "legendText1", String.class));
    	legend2Text.setText(ini.get("legendTexts", "legendText2", String.class));
    	legend3Text.setText(ini.get("legendTexts", "legendText3", String.class));
    	legend4Text.setText(ini.get("legendTexts", "legendText4", String.class));
    	legend5Text.setText(ini.get("legendTexts", "legendText5", String.class));
    	legend6Text.setText(ini.get("legendTexts", "legendText6", String.class));
    	
    	/*
    	 * Calibrations
    	 */
    	k1.setText(ini.get("Calibrations", "K1", String.class));
    	m1.setText(ini.get("Calibrations", "M1", String.class));
    	k2.setText(ini.get("Calibrations", "K2", String.class));
    	m2.setText(ini.get("Calibrations", "M2", String.class));
    	k3.setText(ini.get("Calibrations", "K3", String.class));
    	m3.setText(ini.get("Calibrations", "M3", String.class));
    	k4.setText(ini.get("Calibrations", "K4", String.class));
    	m4.setText(ini.get("Calibrations", "M4", String.class));
    	k5.setText(ini.get("Calibrations", "K5", String.class));
    	m5.setText(ini.get("Calibrations", "M5", String.class));
    	k6.setText(ini.get("Calibrations", "K6", String.class));
    	m6.setText(ini.get("Calibrations", "M6", String.class));
    	
    	/*
    	 * Limits
    	 */
    	yMin.getSelectionModel().select(ini.get("Limits", "yMin", String.class));
    	yMax.getSelectionModel().select(ini.get("Limits", "yMax", String.class));
    	
    	/*
    	 * Other
    	 */        
        animationON.setSelected(ini.get("Other", "AnimationON", boolean.class));
        sampleTime.getSelectionModel().select(ini.get("Other", "SampleTime", Integer.class));
        maxData.getSelectionModel().select(ini.get("Other", "MaximumData", Integer.class));
        amountData.getSelectionModel().select(ini.get("Other", "AmountData", Integer.class));
        
        /*
         * Run all events
         */
        legend1Action(null);
        legend2Action(null);
        legend3Action(null);
        legend4Action(null);
        legend5Action(null);
        legend6Action(null);
    	
    	/*
		 * Change the title
		 */
    	Stage plotStage = (Stage) m1.getScene().getWindow();
    	plotStage.setTitle("Change Plot - " + nameConfig);
    }
    
    @FXML
    void newConfig(ActionEvent event) throws IOException {
    	Stage stage = new Stage();
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.resizableProperty().setValue(Boolean.FALSE);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/JUSBPlotter/newConfiguration.fxml"));
    	Parent root = (Parent) loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("New configuration");
		stage.showAndWait();
		
		/*
		 * Now get value
		 */
		initialize(); // Clear all
		NewConfiguration newConfiguration = loader.getController();
		if(newConfiguration.getNameConfig() != null) {
			nameConfig = newConfiguration.getNameConfig() + ".config";
			/*
			 * Change the title
			 */
			Stage plotStage = (Stage) m1.getScene().getWindow();
			plotStage.setTitle("Change Plot - " + nameConfig);
			
			/*
			 * Now save
			 */
			saveConfig(null);
		}
		
    }
    
    @FXML
    void saveConfig(ActionEvent event) throws InvalidFileFormatException, IOException{    	
    	/*
    	 * Open file
    	 */
    	File configuration = configurationFile(nameConfig);
    	
    	/*
    	 * No selected file
    	 */
    	if(configuration == null) {
    		alert.setTitle("Error!");
			alert.setHeaderText("No selected file.");
			alert.setContentText("You need to have open a file already.");
			alert.showAndWait();
    		return;
    	}
		
    	/*
    	 * Save its parameters
    	 */
    	Wini ini = new Wini(configuration);
    	
    	/*
    	 * Legends
    	 */
    	ini.put("legends", "legend1", legend1.isSelected());
        ini.put("legends", "legend2", legend2.isSelected());
        ini.put("legends", "legend3", legend3.isSelected());
        ini.put("legends", "legend4", legend4.isSelected());
        ini.put("legends", "legend5", legend5.isSelected());
        ini.put("legends", "legend6", legend6.isSelected());
        
        /*
         * Legends text
         */
        ini.put("legendTexts", "legendText1", legend1Text.getText());
        ini.put("legendTexts", "legendText2", legend2Text.getText());
        ini.put("legendTexts", "legendText3", legend3Text.getText());
        ini.put("legendTexts", "legendText4", legend4Text.getText());
        ini.put("legendTexts", "legendText5", legend5Text.getText());
        ini.put("legendTexts", "legendText6", legend6Text.getText());
        
        /*
         * Calibrations
         */
        ini.put("Calibrations", "M1", m1.getText());
        ini.put("Calibrations", "M2", m2.getText());
        ini.put("Calibrations", "M3", m3.getText());
        ini.put("Calibrations", "M4", m4.getText());
        ini.put("Calibrations", "M5", m5.getText());
        ini.put("Calibrations", "M6", m6.getText());
        ini.put("Calibrations", "K1", k1.getText());
        ini.put("Calibrations", "K2", k2.getText());
        ini.put("Calibrations", "K3", k3.getText());
        ini.put("Calibrations", "K4", k4.getText());
        ini.put("Calibrations", "K5", k5.getText());
        ini.put("Calibrations", "K6", k6.getText());
        
        /*
         * Limits
         */
        ini.put("Limits", "yMin", yMin.getSelectionModel().getSelectedItem());
        ini.put("Limits", "yMax", yMax.getSelectionModel().getSelectedItem());
        
        /*
         * Animation, sample time and maximum data
         */
        ini.put("Other", "AnimationON", animationON.isSelected());
        ini.put("Other", "SampleTime", sampleTime.getSelectionModel().getSelectedItem());
        ini.put("Other", "MaximumData", maxData.getSelectionModel().getSelectedItem());
        ini.put("Other", "AmountData", amountData.getSelectionModel().getSelectedItem());
        
        /*
         * Store - save
         */
        ini.store();
        
        
    }

    @FXML
    void saveConfigAs(ActionEvent event) throws InvalidFileFormatException, IOException {
    	Stage stage = new Stage();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Config Files", "*.config"));
    	File selectedFile = fileChooser.showOpenDialog(stage);
    	
    	/*
    	 * No selected file
    	 */
    	if(selectedFile == null) {
    		return;
    	}
    	
    	/*
    	 * If the same is the same as label configName
    	 */
    	String fileName = selectedFile.getName();
    	if(fileName.equals(nameConfig) == false) {
    		Alert question = new Alert(AlertType.CONFIRMATION);
        	question.setTitle("Save as");
        	question.setHeaderText("Do you want save " + nameConfig + " as " + fileName +"?");
        	question.setResizable(true);
        	question.setContentText("Press OK to save as.");
        	Optional<ButtonType> answer = question.showAndWait();
        	if(answer.get() == ButtonType.OK) {
        		File configuration = configurationFile(nameConfig);
    			configuration.renameTo(selectedFile);
    			/*
        		 * Change the title
        		 */
    			nameConfig = fileName;
    			Stage plotStage = (Stage) m1.getScene().getWindow();
    			plotStage.setTitle("Change Plot - " + nameConfig);
    			
    			/*
    			 * Save
    			 */
    			saveConfig(null);
        	}else {
        		return; // Cancel selected
        	}
    	}else {
    		alert.setTitle("Error!");
			alert.setHeaderText("Cannot save as the same file");
			alert.setContentText("Select another file with another name.");    				
			alert.showAndWait();
    	}
    	
    }
    
    @FXML
    void deleteConfig(ActionEvent event) {
    	Stage stage = new Stage();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Config Files", "*.config"));
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
		question.setHeaderText("Do you want to delete file " + selectedFile.getName() + " ?");
		question.setResizable(true);
		question.setContentText("Press OK to delete.");
		Optional<ButtonType> answer = question.showAndWait();
		if(answer.get() == ButtonType.OK) {
			String selectedName = selectedFile.getName();
			if(selectedFile.delete() == true) {
	    		/*
	    		 * Change the title if we deleted the file that we have open
	    		 */
	    		if(selectedName.equals(nameConfig)) {
	    			initialize(); // Reset and change the title
	    			Stage plotStage = (Stage) m1.getScene().getWindow();
	    			plotStage.setTitle("Change Plot");
	    		}
	    		
	    	}else {
	    		alert.setTitle("Error!");
				alert.setHeaderText("Cannot delete measurment");
				alert.setContentText("Need to have rights to access this file");
				alert.showAndWait();
	    	}
		}
    }

    @FXML
    void initialize() {
    	/*
    	 * Initial reset
    	 */
    	nameConfig = "";
    	
        legend1Text.setDisable(true);
        legend2Text.setDisable(true);
        legend3Text.setDisable(true);
        legend4Text.setDisable(true);
        legend5Text.setDisable(true);
        legend6Text.setDisable(true);
        legend1Text.setText("");
        legend2Text.setText("");
        legend3Text.setText("");
        legend4Text.setText("");
        legend5Text.setText("");
        legend6Text.setText("");
        legend1.setSelected(false);
        legend2.setSelected(false);
        legend3.setSelected(false);
        legend4.setSelected(false);
        legend5.setSelected(false);
        legend6.setSelected(false);
        animationON.setSelected(false);
        k1.setText("0.0");
        k2.setText("0.0");
        k3.setText("0.0");
        k4.setText("0.0");
        k5.setText("0.0");
        k6.setText("0.0");
        m1.setText("0.0");
        m2.setText("0.0");
        m3.setText("0.0");
        m4.setText("0.0");
        m5.setText("0.0");
        m6.setText("0.0");
        k1.setDisable(true);
		m1.setDisable(true);
		k2.setDisable(true);
		m2.setDisable(true);
		k3.setDisable(true);
		m3.setDisable(true);
		k4.setDisable(true);
		m4.setDisable(true);
		k5.setDisable(true);
		m5.setDisable(true);
		k6.setDisable(true);
		m6.setDisable(true);
        
        /*
         * Initial deceleration of comboboxes
         */
        sampleTime.getItems().addAll(1, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 150, 200, 250, 300, 400, 500, 600, 700, 800, 900, 1000, 1500, 2000, 2500, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 15000, 20000, 25000, 30000, 40000, 50000, 60000);
        sampleTime.getSelectionModel().select(0);
        
        maxData.getItems().addAll(5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100);
        maxData.getSelectionModel().select(0);
        
        yMax.getItems().addAll("Auto", "5000","4500","4000","3500","3000","2500","2000","1500","1000","900","800","700", "600" ,"500" ,"400" ,"300", "200" ,"150" ,"100", "50" ,"40" , "30","10","5","1", "0.1", "0", "-0.1", "-1", "-5", "-10", "-20", "-30", "-40", "-50", "-100", "-150", "-200", "-300", "-400", "-500", "-1000", "-2000", "-3000", "-4000", "-5000");
        yMax.getSelectionModel().select(0);
        
        yMin.getItems().addAll("Auto", "5000","4500","4000","3500","3000","2500","2000","1500","1000","900","800","700", "600" ,"500" ,"400" ,"300", "200" ,"150" ,"100", "50" ,"40" , "30","10","5","1", "0.1", "0", "-0.1", "-1", "-5", "-10", "-20", "-30", "-40", "-50", "-100", "-150", "-200", "-300", "-400", "-500", "-1000", "-2000", "-3000", "-4000", "-5000");
        yMin.getSelectionModel().select(0);
        
        /*
         * Listener for both yMax and yMin so we can only have Auto on yMax if we select Auto on yMin, vise versa
         */
        yMax.valueProperty().addListener(e ->{
        	if(yMax.getSelectionModel().getSelectedItem().equals("Auto")) {
        		yMin.getSelectionModel().select(0);
        	}else {
        		// But what if...
        		if(yMin.getSelectionModel().getSelectedItem().equals("Auto")) {
        			yMin.getSelectionModel().select(27);
        		}
        	}
        });
        yMin.valueProperty().addListener(e ->{
        	if(yMin.getSelectionModel().getSelectedItem().equals("Auto")) {
        		yMax.getSelectionModel().select(0);
        	}else {
        		// But what if...
        		if(yMax.getSelectionModel().getSelectedItem().equals("Auto")) {
        			yMax.getSelectionModel().select(27);
        		}
        	}
        });
        
        amountData.getItems().addAll(50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000, 11000, 12000, 13000, 14000, 15000, 16000, 17000, 18000, 19000, 20000);
        amountData.getSelectionModel().select(0);
        
        alert = new Alert(AlertType.WARNING);
    }
    
    public ComboBox<Integer> getAmountData() {
		return amountData;
	}

	/*
     * Open the selected file
     */
    private File configurationFile(String fileName) {
    	/*
    	 * Open file location
    	 */
    	String osName = System.getProperty("os.name").toLowerCase();
    	File configuration;
	    if(osName.contains("win")) {
	    	configuration = new File("configurations\\" + fileName);
		}else {
			configuration = new File("configurations/" + fileName);
		}
	    
	    /*
	     * Check if the file exist.
	     */
	    
	    if(configuration.exists() && configuration.isFile()) {
	    	return configuration;
	    }else {
	    	return null;
	    }
    	
    }
    
    

	public CheckBox getLegend1() {
		return legend1;
	}

	public CheckBox getLegend3() {
		return legend3;
	}

	public CheckBox getLegend2() {
		return legend2;
	}

	public TextField getLegend2Text() {
		return legend2Text;
	}

	public TextField getLegend1Text() {
		return legend1Text;
	}

	public TextField getLegend3Text() {
		return legend3Text;
	}

	public CheckBox getLegend4() {
		return legend4;
	}

	public CheckBox getLegend5() {
		return legend5;
	}

	public TextField getLegend4Text() {
		return legend4Text;
	}

	public TextField getLegend5Text() {
		return legend5Text;
	}

	public CheckBox getAnimationON() {
		return animationON;
	}

	public ComboBox<Integer> getSampleTime() {
		return sampleTime;
	}

	public ComboBox<String> getyMin() {
		return yMin;
	}

	public ComboBox<String> getyMax() {
		return yMax;
	}

	public ComboBox<Integer> getMaxData() {
		return maxData;
	}

	public TextField getK1() {
		return k1;
	}

	public TextField getM1() {
		return m1;
	}

	public TextField getM2() {
		return m2;
	}

	public TextField getK2() {
		return k2;
	}

	public TextField getK3() {
		return k3;
	}

	public TextField getM3() {
		return m3;
	}

	public TextField getK4() {
		return k4;
	}

	public TextField getM4() {
		return m4;
	}

	public TextField getK5() {
		return k5;
	}

	public TextField getM5() {
		return m5;
	}

	public CheckBox getLegend6() {
		return legend6;
	}

	public TextField getLegend6Text() {
		return legend6Text;
	}

	public TextField getK6() {
		return k6;
	}

	public TextField getM6() {
		return m6;
	}
    
}
