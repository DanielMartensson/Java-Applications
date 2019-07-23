
package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/*

Before you running this software, you need to first:
1. Install MySQL
2. Create a schema/database
3. Create a table
4. Create columns in that table with the names: ID,employee,wage,company,age,on_vacation,experience
 */

public class Controller extends Main{

    @FXML
    private TextField serverIPtextField;

    @FXML
    private TextField tableNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField databasenameTextField;

    @FXML
    private Sphere connectedSphare;

    @FXML
    private TableView<Person> databaseTableView;

    @FXML
    private TableColumn<Person, String> employeeColumn;

    @FXML
    private TableColumn<Person, String> wageColumn;

    @FXML
    private TableColumn<Person, String> companyColumn;

    @FXML
    private TableColumn<Person, String> ageColumn;

    @FXML
    private TableColumn<Person, String> on_vacationColumn;

    @FXML
    private TableColumn<Person, String> experienceColumn;

    @FXML
    private CheckBox saveToCSVCheckBox;

    @FXML
    private TextField savePathCSVTextField;

    @FXML
    private TextField CSVFileNameTextField;

    @FXML
    private Button folderButton;

    @FXML
    private TextField delimiterTextField;

    // For table
    private ObservableList<Person> tablePerson = FXCollections.observableArrayList();

    // For connection
    private SQL sql;

    // For dialog
    protected Window directoryWindow;

    // For the path to the folder of the CSV file
    private File pathToCSVFolder;

    @FXML
    private void folderButtonOnAction(ActionEvent event) {
        // Select a folder
        DirectoryChooser directoryChooser = new DirectoryChooser();
        pathToCSVFolder = directoryChooser.showDialog(directoryWindow);
        savePathCSVTextField.setText(pathToCSVFolder.getPath());
    }

    @FXML
   private void saveToCSVCheckBoxOnAction() {
        // Uncheck or check the checkbox and see what's happening
        if (saveToCSVCheckBox.isSelected()) {
            savePathCSVTextField.setDisable(false);
            CSVFileNameTextField.setDisable(false);
            folderButton.setDisable(false);
            delimiterTextField.setDisable(false);
        } else {
            savePathCSVTextField.setDisable(true);
            CSVFileNameTextField.setDisable(true);
            folderButton.setDisable(true);
            delimiterTextField.setDisable(true);
        }
    }

    @FXML
    private void editedAgeColumn(TableColumn.CellEditEvent event) {
        // Save the changed value
        Person selectedPerson = databaseTableView.getSelectionModel().getSelectedItem(); // The the object of the row
        String age = event.getNewValue().toString();
        try {
            int ageInt = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            Alert onlyInts = new Alert(Alert.AlertType.INFORMATION, "Only integers as age", ButtonType.OK);
            onlyInts.show();
            return;
        }
        selectedPerson.setAge(age);
        editedCellColumn(selectedPerson); // Update the database
    }

    @FXML
    private void editedCompanyColumn(TableColumn.CellEditEvent event) {
        // Save the changed value
        Person selectedPerson = databaseTableView.getSelectionModel().getSelectedItem(); // The the object of the row
        selectedPerson.setCompany(event.getNewValue().toString());
        editedCellColumn(selectedPerson); // Update the database
    }

    @FXML
    private void editedEmployeeColumn(TableColumn.CellEditEvent event) {
        // Save the changed value
        Person selectedPerson = databaseTableView.getSelectionModel().getSelectedItem(); // The the object of the row
        selectedPerson.setEmployee(event.getNewValue().toString());
        editedCellColumn(selectedPerson); // Update the database
    }

    @FXML
    private void editedExperienceColumn(TableColumn.CellEditEvent event) {
        // Save the changed value
        Person selectedPerson = databaseTableView.getSelectionModel().getSelectedItem(); // The the object of the row
        selectedPerson.setExperience(event.getNewValue().toString());
        editedCellColumn(selectedPerson); // Update the database
    }

    @FXML
    private void editedOn_vacationColumn(TableColumn.CellEditEvent event) {
        // Save the changed value
        Person selectedPerson = databaseTableView.getSelectionModel().getSelectedItem(); // The the object of the row
        selectedPerson.setOn_vacation(event.getNewValue().toString());
        editedCellColumn(selectedPerson); // Update the database
    }

    @FXML
    private void editedWageColumn(TableColumn.CellEditEvent event) {
        // Save the changed value
        Person selectedPerson = databaseTableView.getSelectionModel().getSelectedItem(); // The the object of the row
        String wage = event.getNewValue().toString();
        try {
            float wageFloat = Float.parseFloat(wage);
        } catch (NumberFormatException e) {
            Alert onlyInts = new Alert(Alert.AlertType.INFORMATION, "Only floats as wage. Hint: Use dot . instead of comma ,", ButtonType.OK);
            onlyInts.showAndWait();
            return;
        }
        selectedPerson.setWage(wage);
        editedCellColumn(selectedPerson); // Update the database
    }

    @FXML
    private  void editedCellColumn(Person selectedPerson){
        // Every time we edit a single cell, that row is going to be updated to the database

        int rownumber = databaseTableView.getSelectionModel().getFocusedIndex(); // Get the row number

        // Get the info about that row
        String employee = selectedPerson.getEmployee();
        String wage = selectedPerson.getWage();
        String company = selectedPerson.getCompany();
        String age = selectedPerson.getAge();
        String on_vacation = selectedPerson.getOn_vacation();
        String experience = selectedPerson.getExperience();

        // Update the row
        String databasename = databasenameTextField.getText();
        String tablename = tableNameTextField.getText();

        try {
            Statement statement = sql.getConnection().createStatement();
            String query = "UPDATE " + databasename + "." + tablename + " SET employee = '" + employee + "' , wage = " + wage + " , company = '" + company + "' , age = " + age + " , on_vacation = " + Boolean.valueOf(on_vacation) + " , experience = '" + experience + "' WHERE ID = " + String.valueOf(rownumber);
            statement.execute(query);
            writeToCSV(databasename, tablename);
        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Could not edit a cell inside the SQL server - Please update : " + e.getMessage(), ButtonType.OK);
            error.showAndWait();
        }
    }

    @FXML
    private void addNewRowButton(ActionEvent event) {

        int rownumber = databaseTableView.getItems().size();
        String employee = "New employee";
        String wage = "0.0";
        String company = "none";
        String age = "0";
        String on_vacation = "false";
        String experience = "Nothing";

        // Add empty row
        tablePerson.add(new Person(employee, wage, company, age, on_vacation, experience));
        // Create new  row
        String databasename = databasenameTextField.getText();
        String tablename = tableNameTextField.getText();

        try {
            Statement statement = sql.getConnection().createStatement();
            String query = "INSERT INTO " + databasename + "." + tablename + " (ID,employee,wage,company,age,on_vacation,experience) VALUES (" + String.valueOf(rownumber) +  ",'" + employee + "'," + wage + ",'" + company + "'," + age + "," + Boolean.valueOf(on_vacation) + ",'" + experience + "')";
            statement.execute(query);
            writeToCSV(databasename, tablename);
        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Could not add a row inside the SQL server - Please update : " + e.getMessage(), ButtonType.OK);
            error.showAndWait();
        }
    }

    @FXML
    private void deteteRowButton() {
        int rownumber = databaseTableView.getSelectionModel().getFocusedIndex();
        if (rownumber > -1) {
            tablePerson.remove(rownumber);

            // Remove from SQL
            String databasename = databasenameTextField.getText();
            String tablename = tableNameTextField.getText();

            try {
                Statement statement = sql.getConnection().createStatement();
                String query = "DELETE FROM " + databasename + "." + tablename + " WHERE ID = " + String.valueOf(rownumber);
                statement.execute(query);
                updateIDColumns(databasename, tablename, rownumber);
                writeToCSV(databasename, tablename);
            } catch (SQLException e) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Could not delete the row inside the SQL server - Please update : " + e.getMessage(), ButtonType.OK);
                error.showAndWait();
            }
        }
    }

    private void writeToCSV(String databasename, String tablename){
        // If the check box isn't selected, then don't do anything
        String OS = System.getProperty("os.name");
        if (saveToCSVCheckBox.isSelected()) {
            // Delete file first!
            try {

                // Check if it is linux
                String fileLocation = null;
                if (OS.contains("win")){
                    fileLocation = pathToCSVFolder.getPath().replace("\\", "\\\\") + "\\\\" + CSVFileNameTextField.getText() + ".csv";

                } else{
                    fileLocation = pathToCSVFolder.getPath() + "/" + CSVFileNameTextField.getText() + ".csv"; // Linux
                }

                System.out.println(fileLocation);
                File deleteFile = new File(fileLocation);
                try {
                    deleteFile.delete();
                } catch (Exception e){
                    Alert error = new Alert(Alert.AlertType.ERROR, "Every time you update the table, the CSV file is deleted and created again - Error: Could not delete CSV file. " + e.getMessage(), ButtonType.OK);
                    error.showAndWait();
                }
            } catch (NullPointerException e){
                Alert error = new Alert(Alert.AlertType.ERROR, "You have not selected any path : " + e.getMessage(), ButtonType.OK);
                error.showAndWait();
            }

            try {
                // Check if windows
                String saveLocation = null;
                if (OS.contains("win")){
                    saveLocation = pathToCSVFolder.getPath().replace("\\", "\\\\"); // This change C:\Folder\Path to C:\\Folder\\Path
                } else {
                    saveLocation = pathToCSVFolder.getPath(); // Linux
                }
                System.out.println(saveLocation);
                Statement statement = sql.getConnection().createStatement();
                String query = "SELECT * FROM " + databasename + "." + tablename + " INTO OUTFILE '" + saveLocation + "\\\\" + CSVFileNameTextField.getText() + ".csv' FIELDS TERMINATED BY '" + delimiterTextField.getText() + "' LINES TERMINATED BY '\\n'";
                statement.execute(query);
            } catch (SQLException e) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Could not write the CSV file : " + e.getMessage(), ButtonType.OK);
                System.out.println("Could not write the CSV file : " + e.getMessage());
                error.showAndWait();
            } catch (NullPointerException e) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Could not write the CSV file - Missing path : " + e.getMessage(), ButtonType.OK);
                System.out.println("Could not write the CSV file - Missing path : " + e.getMessage());
                error.showAndWait();
            }
        }
    }

    // This function updates all the ID of all rows at the MySQL database only
    private void updateIDColumns(String databasename, String tablename, int rownumber){
        int maxRow = databaseTableView.getItems().size(); // index from zero

        // If we removed e.g row 7, then we need to change row 8, 9, 10, 11, 12.. etc to 7, 8, 9, 10, 11...
        // We start at rownumber, the same row we removed

        for(int i = rownumber; i <= maxRow; i++){
            try {
                Statement statement = sql.getConnection().createStatement();
                String query = "UPDATE " + databasename + "." + tablename + " SET ID = " + String.valueOf(i) + " WHERE ID = " + String.valueOf(i + 1);
                statement.execute(query);
            } catch (SQLException e) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Could not update the ID at the SQL server : " + e.getMessage(), ButtonType.OK);
                error.show();
            }
        }
    }

    @FXML
    private void mouseClickedTableView() {
    }

    @FXML
    private void connectToSQLserverButton(ActionEvent event){

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String serverIP = serverIPtextField.getText();
        String databasename = databasenameTextField.getText();
        String tablename = tableNameTextField.getText();

        // Connect SQL
        sql = new SQL(username, password, serverIP, databasename, connectedSphare);

        // If connected
        if (sql.getConnectionEstablished() == true) {
            databaseTableView.setDisable(false);
            databaseTableView.setItems(tablePerson);

            // Clear tableView
            tablePerson.clear();

            // Load data to tableView
            try {
                Statement statement = sql.getConnection().createStatement();
                String query = "SELECT * FROM " + databasename + "." + tablename;
                ResultSet resultSet = statement.executeQuery(query);

                // Load row after row from the tablename
                while (resultSet.next())
                {
                    //int ID = resultSet.getInt("ID");
                    String employee = resultSet.getString("employee");
                    String wage = String.valueOf(resultSet.getFloat("wage"));
                    String company = resultSet.getString("company");
                    String age = String.valueOf(resultSet.getInt("age"));
                    String on_vacation = String.valueOf(resultSet.getBoolean("on_vacation"));
                    String experience = resultSet.getString("experience");

                    // Insert them into table list
                    tablePerson.add(new Person(employee, wage, company, age, on_vacation, experience));

                }

                resultSet.close();
            } catch (SQLException e) {
                Alert didNotFound = new Alert(Alert.AlertType.ERROR, "Did not found the table: " + e.getMessage(), ButtonType.OK);
                didNotFound.show();

                // Change the Shpere to red again
                PhongMaterial material = new PhongMaterial();
                material.setDiffuseColor(Color.RED);
                connectedSphare.setMaterial(material);
                sql.setConnectionEstablished(false);;
            }


        } else {
            databaseTableView.setDisable(true);
        }
    }

    @FXML
    private void exitProgramButton(ActionEvent event) {
        closeWindow();
    }

    @FXML
    private void initialize() {
        // Set up table columns
        employeeColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("employee"));
        wageColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("wage"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("company"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("age"));
        on_vacationColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("on_vacation"));
        experienceColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("experience"));

        // Set editable
        databaseTableView.setEditable(true);
        employeeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        wageColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        companyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        on_vacationColumn.setCellFactory(ComboBoxTableCell.forTableColumn("true", "false"));
        experienceColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Set color on sphare
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.RED);
        connectedSphare.setMaterial(material);

        // Set a close request
        this.primaryStage.setOnCloseRequest(e -> {
            e.consume(); // If you pressed the X button on the window, the window should not be closed if you don't want to.
            closeWindow();
        });

        // No connection
        databaseTableView.setDisable(true);

        // No CSV writing
        saveToCSVCheckBoxOnAction();
    }

    private void closeWindow() {

        Alert closeRequest = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to close the database reader?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = closeRequest.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            this.primaryStage.close();
        }

    }

}
