package CoolFeatures;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBC {
    private static Connection connection;
    private static String table;

    private Thread thread;
    private ProgressBar progressBar;


    public JDBC(){
        // Null constructor
    }

    // This constructor is for non-GUI - We already know that the connection works here
    public JDBC(String SQL, String adress, String schema, String table, String userName, String password){
        this.table = table;

        String connectionString = "";
        try{
            // Create connection strings for different servers
            if (SQL.toLowerCase().equals("sqlite")) {
                connectionString = "jdbc:" + SQL + ":" + adress + schema;
                // Connect now
                connection = DriverManager.getConnection(connectionString);
            }else if(SQL.toLowerCase().equals("mysql")){
                connectionString = "jdbc:" + SQL + ":" + adress + schema;
                // Connect now
                connection = DriverManager.getConnection(connectionString, userName, password);
            }

        }catch (SQLException e){
            Alert notConnected = new Alert(Alert.AlertType.ERROR, connectionString, ButtonType.OK);
            notConnected.show();
        }
    }

    public void disconnect() {
        try {
            connection.close();
        }catch (SQLException e){
            Alert notConnected = new Alert(Alert.AlertType.ERROR, "Cannot connect to the database.", ButtonType.OK);
            notConnected.show();
        }catch (NullPointerException e){
            Alert notConnected = new Alert(Alert.AlertType.INFORMATION, "No connected to any database.", ButtonType.OK);
            notConnected.show();
        }
    }

    // This constructor is for GUI
    public JDBC(String SQL, String adress, String schema, String table, String userName, String password, ProgressBar progressBar, Label statusLabel){
        this.progressBar = progressBar;
        this.table = table;

        // This is for progress bar
        Task<Void> task = new Task<>() {
            @Override public Void call() throws Exception{
                for (int i = 0; i < 5000; i++) {
                    Thread.sleep(1);
                    updateProgress(i + 1, 5000);
                }
                return null;
            }
        };

        // Start the progress bar
        thread = new Thread(task);
        progressBar.progressProperty().bind(task.progressProperty());
        thread.start();

        // Timeout for DriverManager
        Properties properties = new Properties();
        properties.put("connectTimeout", "5000");

        // Connection
        String connectionString = "";
        try {

            // Create connection strings for different servers
            if (SQL.toLowerCase().equals("sqlite")) {
                connectionString = "jdbc:" + SQL + ":" + adress + schema;
                // Connect now
                connection = DriverManager.getConnection(connectionString);
            }else if(SQL.toLowerCase().equals("mysql")){
                connectionString = "jdbc:" + SQL + ":" + adress + schema;
                // Connect now
                connection = DriverManager.getConnection(connectionString, userName, password);
            }

            // Connect now
            //connection = DriverManager.getConnection(connectionString, properties);
            statusLabel.setText("Connection establish");
            statusLabel.setTextFill(Color.GREEN);
            cancleProgressBar(1);

            // Save the connection in the server.ini file
            IniFile ini = new IniFile();
            ini.inertKey(SQL, adress, schema, table, userName, password);

        }catch (SQLException e){
            Alert notConnected = new Alert(Alert.AlertType.ERROR, connectionString, ButtonType.OK);
            notConnected.show();
            statusLabel.setText("Connection fail");
            statusLabel.setTextFill(Color.RED);
            cancleProgressBar(0);
        } catch (IOException e) {
            Alert notFound = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            notFound.show(); // For the IniFile class
        } catch (Exception e) {
            Alert notFound = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            notFound.show(); // For the IniFile class
        }

    }

    public void cancleProgressBar(float process){
        thread.stop();
        progressBar.progressProperty().unbind();
        progressBar.setProgress(process);
    }

    public static Connection getConnection() {
        return connection;
    }

    public static String getTable() {
        return table;
    }
}
