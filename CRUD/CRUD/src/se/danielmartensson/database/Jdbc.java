package se.danielmartensson.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class Jdbc {
	
	private Connection connection;
	private String databaseName;
	private String username;
	private String loginTable;
	private String pastDate;

	
	/*
	 * Create the connection
	 */
	
	public Jdbc(String serverName, int portNumber, String databaseName, String username, String password) throws SQLException{
		this.databaseName = databaseName;
		this.username = username;
		pastDate = "Mon Jan 1 00:00:00 CEST 1970"; // Initial date
		connection = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + portNumber + "/" + databaseName, username, password);
	}
	
	/*
	 * Check if we can login into the server
	 */
	
	public boolean checkLogin(String loginTable) {
		this.loginTable = loginTable;
		
		boolean checkmark;
		
		try {
			/**
			 * When we login, we set a check mark that we have logged in
			 */
			String loginDate = new Date().toString();
			Statement statement = connection.createStatement();
			String query = "INSERT INTO " + databaseName + "." + loginTable + " (date,username) VALUES ('" + loginDate + "','" + username + "')"; 
	        statement.execute(query); 
	        checkmark = true; // Yes we can write to the server!
		} catch (SQLException e) {
			checkmark = false;
		} catch (NullPointerException e1) {
			checkmark = false;
		}
		
		return checkmark;
	}
	
	
	/**
	 * Get the column names
	 */
	public ArrayList<String> getColumns(String tableName) {
		
		// Create a list who holds all the head names or row items
		ArrayList<String> columnList = new ArrayList<>();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + databaseName + "." + tableName);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
		        String columnName = resultSetMetaData.getColumnName(i);
		        columnList.add(columnName);
		    }
			
		}catch (Exception e) {
			columnList.add("Empty Column");
		}
		
		return columnList;
	}
	
	/**
	 * Get row list
	 */
	
	public ArrayList<ArrayList<String>> getRowList(String tableName) {
		
		ArrayList<ArrayList<String>> rowList = new ArrayList<ArrayList<String>>();
		
		try {
			// Create a statement and get data
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + databaseName + "." + tableName);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();

			while (resultSet.next()) {
				ArrayList<String> row = new ArrayList<String>();
				for (int i = 1; i <= columnCount; i++) {
			        String rowItem = resultSet.getString(i);
			        row.add(rowItem);
			    }
				rowList.add(row);
	    
			}
		}catch (Exception e) {
			
		}
		
		// We need to reverse rowList because the last date need to be at the top
		Collections.reverse(rowList);
		
		return rowList;
	}
	
	/**
	 * Return all the table names from the database we have connected too
	 */
	
	public ArrayList<String> getTableNames(){
		
		// Create a list who holds all the table names from the database
		ArrayList<String> tableList = new ArrayList<>();
		
		// Create statement and load execute the query
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SHOW TABLES");
			
			// Fetch each row from the result set
			while (resultSet.next()) {
			  String tableName = resultSet.getString("Tables_in_" + databaseName);
			  tableList.add(tableName);
			}
		} catch (Exception e) {
			tableList.add("Empty columns"); // Something happen
		}
		
		// Return the list
		return tableList;
	}
	
	/*
	 * Add new row for this table at the bottom
	 */

	public void addNewRow(String tableName) {
		
		// No table selected?
		if(tableName == null) {
			tableName = loginTable;
		}
		
		// Get the current date and compare it with past date just to make sure that we won't 
		// add double rows of the same date
		String currentDate = new Date().toString();
		
		if(!currentDate.contains(pastDate)) {
			pastDate = currentDate; // Not equal - remeber it!
			
			ArrayList<String> columnList = getColumns(tableName);
			
			String columnString = ""; 
			String valueString = ""; 
			
			// We start at index 1
			for(int i = 0; i < columnList.size(); i++) {
				String item = columnList.get(i);
				columnString += item + ",";
				if(i == 0) {
					valueString += "'" + currentDate + "',";
				}else {
					valueString += "'-',";
				}
				
			}
			
			// remove the last , from columnString and valueString
			columnString = columnString.substring(0, columnString.length() - 1);
			valueString = valueString.substring(0, valueString.length() - 1);
			
			try {
				// Create a statement and create new rot
				Statement statement = connection.createStatement();
				String query = "INSERT INTO " + databaseName + "." + tableName + " (" + columnString + ") VALUES (" + valueString + ")";
				statement.execute(query);
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/*
	 * This will update a row in the database
	 */

	public void updateRow(String selectedTable, String dateEdited, ArrayList<String> editedRow, ArrayList<String> columnList) {
		
		/*
		 * Explanation
		 * selectedTable = The table we have slected
		 * dateEdited = The current row we want to edit
		 * editedRow = The row we want to change to
		 * columnList = Name of all columns
		 */
		
		// No table selected?
		if(selectedTable == null) {
			selectedTable = loginTable;
		}
		
		try {
			Statement statement = connection.createStatement();
		
			String query = "UPDATE " + databaseName + "." + selectedTable + " SET ";
			// We begin at index 1, where index 0 = date column 
			for(int i = 1; i < columnList.size(); i++) {
				String columnName = columnList.get(i);
				String data = editedRow.get(i);
				query += columnName + " = " + "'" + data + "',";
			}
			
			// Remove that last "," from query
			query = query.substring(0, query.length() - 1);
			
			// columnList.get(0) is always "date" 
			query += " WHERE " + columnList.get(0) + " = " + "'" + editedRow.get(0) + "'";
			
			// Update the row now!
			statement.execute(query);
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * This will delete the row
	 */

	public void deleteRow(String dateID, String selectedTable) {
		
		// No table selected?
		if(selectedTable == null) {
			selectedTable = loginTable;
		}

		try {
			
			// Create the delete string
			String query = "DELETE FROM " + databaseName + "." + selectedTable + " WHERE date = " + "'" + dateID + "'";
			

			// Create the statement
			Statement statement = connection.createStatement();
			
			// Detete the row now!
			statement.execute(query);
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
