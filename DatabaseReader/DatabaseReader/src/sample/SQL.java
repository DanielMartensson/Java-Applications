package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {

    private static Connection connection;
    private static boolean connectionEstablished;

    // Null constructor
    SQL(){
    }

    SQL(String username, String password, String serverIP, String databasename, Sphere connectedSphare) {

        // Load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Alert cannotLoadDriver = new Alert(Alert.AlertType.ERROR, "Cannot load the JDCB driver for MySQL : " + e.getMessage(), ButtonType.OK);
            cannotLoadDriver.show();
        }


        // Driver is loaded - connect to server
        PhongMaterial material = new PhongMaterial();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + serverIP  + "/" + databasename, username, password);
            material.setDiffuseColor(Color.GREEN);
            connectedSphare.setMaterial(material);
            connectionEstablished = true;
        } catch (SQLException e) {
            Alert cannotConnectSQL = new Alert(Alert.AlertType.ERROR, "Cannot connect the SQL server : " + e.getMessage(), ButtonType.OK);
            cannotConnectSQL.show();
            material.setDiffuseColor(Color.RED);
            connectedSphare.setMaterial(material);
            connectionEstablished = false;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean getConnectionEstablished() {
        return connectionEstablished;
    }

    public void setConnectionEstablished(boolean connectionEstablished) {
        this.connectionEstablished = connectionEstablished;
    }
}
