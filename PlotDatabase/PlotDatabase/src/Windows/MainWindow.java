package Windows;


import CoolFeatures.IniFile;
import CoolFeatures.JDBC;
import CoolFeatures.WindowDynamics;
import Interfaces.FadeDelay;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import org.ini4j.Profile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MainWindow extends Application {

    @FXML
    private TableView tableView;

    @FXML
    private Menu menuRecent;

    // For dialog
    protected Window saveWindow;

    private File pathToCSVFolder;

    private String delimiter = ";"; // Default

    private final int fadeOpen = 3000; // milli seconds
    private final int fadeClose = 3000; // milli seconds
    private static Stage mainStage;

    private PlotWindow plotWindow;

    // This is the main window and it always opens with a fade

    @Override
    public void start(Stage mainStage) throws Exception {
        this.mainStage = mainStage;
        Parent mainParent = FXMLLoader.load(getClass().getResource("/SceneBuilderFXML/MainStage.fxml"));

        // Set fade up
        FadeDelay fadeDelay = new WindowDynamics();
        fadeDelay.FadeParent(fadeOpen, mainParent, true);

        // Window setup
        this.mainStage.setTitle("Plot Database");
        this.mainStage.setScene(new Scene(mainParent, 500, 200));

        this.mainStage.show(); // Show window

        // Set on close request
        this.mainStage.setOnCloseRequest(e -> {
            e.consume(); // Important for else the window will be closed in anyway!
            closeRequest();
        });

    }

    @FXML
    public void quitMenuItem(){
        closeRequest();
    }

    @FXML
    public void ServerConnect() throws Exception{
        new ServerConnect().start(new Stage());
    }

    private void closeRequest(){
        // Interface to get the method inside the class WindowDynamics
        FadeDelay closeDelayFade = new WindowDynamics();

        // Alert Box
        Alert closeOrNot = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to close Plot Database?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> YesOrNo = closeOrNot.showAndWait();
        if(YesOrNo.get().equals(ButtonType.YES)){
            closeDelayFade.CloseStageWithFade(mainStage, fadeClose);
            System.exit(0); // Close all windows for Plot Database
        }
    }

    @FXML
    public void addMenuItemcRecent() throws Exception{
        // When open Recent in Menu -> Recent, read the server.ini file and add MenuItems
        IniFile ini = new IniFile();
        ArrayList listOfKeys = ini.keyList();

        // Remove the items so we don't get duplicate items
        menuRecent.getItems().clear();

        // Add menuitems under Recent
        for(int i = 0; i < listOfKeys.size(); i++){
            MenuItem menuItem = new MenuItem(listOfKeys.get(i).toString());
            String key = listOfKeys.get(i).toString();
            Profile.Section section = ini.getSection(key);

            String SQL = section.get("SQL");
            String adress = section.get("adress");
            String schema = section.get("schema");
            String table = section.get("table");
            String userName = section.get("userName");
            String password = section.get("password");

            // Set functionality to this item
            menuItem.setOnAction(event -> {
                new JDBC(SQL, adress, schema, table, userName, password);// Connect the server
                String titleKey = menuItem.getText(); // For title
                mainStage.setTitle("Plot Database - " + titleKey);
                showTable(); // Update
            });

            menuRecent.getItems().add(menuItem);
        }
    }

    @FXML
    public void showTable(){

        // Get the JDBC
        JDBC jdbc = new JDBC();
        Connection connection = jdbc.getConnection();
        String table = jdbc.getTable();

        // First create a show table query
        String query = "SELECT * FROM " + table;
        // Create
        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        // Clear tableView of columns and items
        tableView.getColumns().clear();
        tableView.getItems().clear();

        // Then call the server
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData columns = resultSet.getMetaData();


            // Add the Row column
            TableColumn rowColumn = new TableColumn("Row");

            rowColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(0).toString());
                }
            });


            tableView.getColumns().addAll(rowColumn);
            tableView.getSelectionModel().setCellSelectionEnabled(true);


            // Collect all the column names and create columns
            for(int i = 0; i < columns.getColumnCount(); i++) {
                final int j = i + 1;
                String columName = resultSet.getMetaData().getColumnName(i+1);
                TableColumn tableColumn = new TableColumn(columName);
                tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                // Add one column after column
                tableView.getColumns().addAll(tableColumn);
            }

            // Then collect cells in a list
            int ID = 1;
            while(resultSet.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(String.valueOf(ID)); // For ID column
                ID++;
                for(int i=1 ; i <= resultSet.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(resultSet.getString(i));
                }
                data.add(row); // Add row
            }

            // Then insert into tableView
            tableView.setItems(data);

            // Add a listener for sorting the columns - When we press a column, then we sould get the name of the column
            ObservableList<TablePosition> selectedCells = tableView.getSelectionModel().getSelectedCells();
            selectedCells.addListener(new ListChangeListener<TablePosition>() {
                @Override
                public void onChanged(Change change) {
                    for (int i = 0; i < selectedCells.size(); i++) {
                        String nameOfColumn = selectedCells.get(i).getTableColumn().getText();
                        plotWindow.setColumnName(nameOfColumn);
                    }
                }
            });

            plotWindow.setTableView(tableView); // For every update, send the tableView to the plotWindow

        } catch (SQLException e) {
            Alert notShow = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            notShow.show();
        }catch (Exception e){
            Alert notShow = new Alert(Alert.AlertType.INFORMATION, "No information to view.", ButtonType.OK);
            notShow.show();
        }

    }

    @FXML
    public void disconnect() {
        new JDBC().disconnect();
        tableView.getColumns().clear();
        tableView.getItems().clear();
        tableView = null; // Total clear
        plotWindow.setTableView(tableView); // Send the null table to PlotWindow.java too
        mainStage.setTitle("Plot Database");
    }

    @FXML
    public void saveListAt(){
        // Select a folder
        FileChooser directoryChooser =  new FileChooser();
        pathToCSVFolder = directoryChooser.showSaveDialog(saveWindow);
        String path = pathToCSVFolder.getAbsolutePath();
        save(path);

    }

    @FXML
    public void saveList(){
        try {
            String path = pathToCSVFolder.getAbsolutePath();
            save(path);
        }catch (Exception e){
            Alert notFound = new Alert(Alert.AlertType.INFORMATION, "No file selected.", ButtonType.OK);
            notFound.show();
        }

    }

    @FXML
    public void setDelimiter(){
        TextInputDialog dialog = new TextInputDialog(delimiter);
        dialog.setTitle("Set the delimiter");
        dialog.setHeaderText("Configuration for the file writter");
        dialog.setContentText("Enter the delimiter:");

        try {
            Optional result = dialog.showAndWait();
            delimiter = result.get().toString();
        }catch (NoSuchElementException e){
            //Alert notFound = new Alert(Alert.AlertType.INFORMATION, "No such delimiter allowed.", ButtonType.OK);
            //notFound.show();
        }
    }

    public void save(String path){
        // Get the data
        ObservableList items = tableView.getItems();
        ObservableList<TableColumn> columns = tableView.getColumns();

        try {
            // Open connection
            FileWriter fileWriter = new FileWriter(path);

            // Write the column names first
            for(int i = 0; i < columns.size(); i++){
                String columName = columns.get(i).getText();
                fileWriter.write(columName + delimiter); // Delimiter is ;
            }
            fileWriter.write("\n"); // New row

            // Write the data
            for(int i = 0; i < items.size(); i++){
                String row = items.get(i).toString();
                List<String> rowItem = Arrays.asList(row.split(","));
                for(int j = 0; j < rowItem.size(); j++){
                    String cellValue = rowItem.get(j).toString().replace("[", "").replace("]", "");
                    fileWriter.write( cellValue + delimiter);
                }
                fileWriter.write("\n"); // New row
            }

            fileWriter.close(); // save

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openPlotWindow() throws Exception{
        plotWindow = new PlotWindow();
        plotWindow.start(new Stage()); // Start PlotWindow.java
        plotWindow.setTableView(tableView);
    }

    @FXML
    public void initialize(){

    }

    @FXML
    public void about(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("This software is licenced under MIT license.\nUse this software to plot data from databases.\nCreated by Daniel Mårtensson September 2018.");
        alert.showAndWait();
    }

    @FXML
    public void troubleshoot(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Troubleshoot");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setHeaderText(null);
        alert.setContentText("-If you not can see any values in the table it probably because that the database contains cells who are null.\n-If you have trouble to read or plot, the database might have a comma sign in a cell.");
        alert.showAndWait();
    }

}
