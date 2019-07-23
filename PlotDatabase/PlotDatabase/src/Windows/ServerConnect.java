package Windows;

import CoolFeatures.JDBC;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;

public class ServerConnect extends Application {

    @FXML
    private TextField addressField;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField schemaField;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextField tableField;

    @FXML
    private ChoiceBox<String> choiceBox;

    private static Stage serverConnectStage;


    @Override
    public void start(Stage serverConnectStage) throws Exception {
        this.serverConnectStage = serverConnectStage;
        Parent mainParent = FXMLLoader.load(getClass().getResource("/SceneBuilderFXML/ServerConnect.fxml"));

        this.serverConnectStage.setTitle("Connect to SQL server");
        this.serverConnectStage.setResizable(false);

        this.serverConnectStage.setScene(new Scene(mainParent));
        this.serverConnectStage.show();

    }

    @FXML
    void connetButton() throws Exception{
        String SQL = choiceBox.getSelectionModel().getSelectedItem();
        new JDBC(SQL, addressField.getText(), schemaField.getText(), tableField.getText(), usernameField.getText(), passwordField.getText(), progressBar, statusLabel);
    }

    @FXML
    public void closeButton() {
        serverConnectStage.close();
    }


    @FXML
    public void initialize(){
        choiceBox.getItems().addAll("MySQL", "SQLite");
        choiceBox.setValue("MySQL"); // Default value

        // Listener for the choice box
        choiceBox.getSelectionModel().selectedItemProperty().addListener(e -> {
            String SQL = choiceBox.getSelectionModel().getSelectedItem();

            if(SQL.toLowerCase().equals("sqlite")){
                usernameField.setDisable(true);
                passwordField.setDisable(true);
            }else{
                usernameField.setDisable(false);
                passwordField.setDisable(false);
            }
        });


        // Tool tips
        Tooltip addressTip = new Tooltip("Ex C:\\path\\to\\folder\\ or //ipaddress.com/ - Important with backslash at the end");
        Tooltip schemaTip = new Tooltip("Ex: Database.db or myDatabase.idb - Important with file extension");
        Tooltip tableTip = new Tooltip("Your table name here");
        Tooltip userNameTip = new Tooltip("Your username here");
        Tooltip passwordTip = new Tooltip("Your user name here");

        addressField.setTooltip(addressTip);
        schemaField.setTooltip(schemaTip);
        tableField.setTooltip(tableTip);
        usernameField.setTooltip(userNameTip);
        passwordField.setTooltip(passwordTip);

    }

}
