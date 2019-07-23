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


import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;

/**
 * 
 * @author asus Create a database with the name databaseName and columns in the
 *         following order: date,title,startUnixTime,endUnixTime,username
 */

public class Jdbc {

	private Connection connection;
	private String databaseName;
	private String scheduleTable;
	private String username;

	/*
	 * Create the connection
	 */
	public Jdbc(String serverName, String scheduleTable, int portNumber, String databaseName, String username,
			String password) throws SQLException {
		this.databaseName = databaseName;
		this.username = username;
		this.scheduleTable = scheduleTable;
		connection = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + portNumber + "/" + databaseName,
				username, password);
	}

	/*
	 * Check if we can login into the server
	 */

	public boolean checkLogin(String loginTable) {

		boolean checkmark;

		try {
			/**
			 * When we login, we set a check mark that we have logged in
			 */
			String loginDate = new Date().toString();
			Statement statement = connection.createStatement();
			String query = "INSERT INTO " + databaseName + "." + loginTable + " (date,username) VALUES ('" + loginDate
					+ "','" + username + "')";
			statement.execute(query);
			checkmark = true; // Yes we can write to the server!
		} catch (SQLException e) {
			checkmark = false;
		} catch (NullPointerException e1) {
			checkmark = false;
		}

		return checkmark;
	}

	/*
	 * Get the column names
	 */
	public ArrayList<String> getColumns() {

		// Create a list who holds all the head names or row items
		ArrayList<String> columnList = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + databaseName + "." + scheduleTable);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = resultSetMetaData.getColumnName(i);
				columnList.add(columnName);
			}

		} catch (Exception e) {
			columnList.add("Empty Column");
		}

		return columnList;
	}

	/*
	 * Get row list
	 */
	public ArrayList<ScheduleEvent> getRowList() {

		ArrayList<ScheduleEvent> rowList = new ArrayList<>();

		try {
			// Create a statement and get data
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + databaseName + "." + scheduleTable);
			while (resultSet.next()) {
				rowList.add(setEventItems(resultSet));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// We need to reverse rowList because the last date need to be at the top
		Collections.reverse(rowList);
		return rowList;
	}

	/*
	 * Add new row for this table at the bottom
	 */
	public void addEvent(ScheduleEvent event) {

		// Get all the columns
		ArrayList<String> columnList = getColumns();

		// Get the items from event
		ArrayList<String> items = eventItems(event);

		// Create values and columns
		String columnString = "";
		String valueString = "";

		for (int i = 0; i < columnList.size(); i++) {
			String item = columnList.get(i);
			columnString += item + ",";
			valueString += "'" + items.get(i) + "',";
		}

		// remove the last "," from columnString and valueString
		columnString = columnString.substring(0, columnString.length() - 1);
		valueString = valueString.substring(0, valueString.length() - 1);

		try {
			// Create a statement and create new rot
			Statement statement = connection.createStatement();
			String query = "INSERT INTO " + databaseName + "." + scheduleTable + " (" + columnString + ") VALUES ("
					+ valueString + ")";
			statement.execute(query);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * This will update a resize in the database
	 */
	public void updateResize(ScheduleEvent event, ScheduleEntryResizeEvent resizeEvent) {

		// Delete event from database
		deleteEvent(event);

		// Add new event from database
		addEvent(resizeEvent.getScheduleEvent());
	}

	/*
	 * This will update a move in the database
	 */
	public void updateMove(ScheduleEvent event, ScheduleEntryMoveEvent moveEvent) {

		// Delete event from database
		deleteEvent(event);

		// Add new event from database
		addEvent(moveEvent.getScheduleEvent());
	}

	/*
	 * This will delete the row
	 */
	public void deleteEvent(ScheduleEvent event) {

		try {
			// Get all the columns
			ArrayList<String> columnList = getColumns();

			// Get all the info
			ArrayList<String> items = eventItems(event);

			// Create the condition where we focusing on ID
			String condition = "";
			
			// We start at index one due to the date column should not effect our delete statement
			for(int i = 1; i < items.size(); i++) {
				condition += columnList.get(i) + " = " + "'" + items.get(i) + "' AND ";
			}
			
			// remove the last "AND " from columnString and valueString
			condition = condition.substring(0, condition.length() - 4);
			
			// Create the delete string
			String query = "DELETE FROM " + databaseName + "." + scheduleTable + " WHERE " + condition;

			System.out.println(query);

			// Create the statement
			Statement statement = connection.createStatement();

			// Detete the row now!
			statement.execute(query);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}

	}

	/*
	 * Set event items of an array
	 */
	private ScheduleEvent setEventItems(ResultSet resultSet) throws SQLException {
		
		// ResultsSet begins from index 2 due to index 1 = date column
		String title = resultSet.getString(2);

		// Get start dates from Unix time
		Date startDate = new Date(resultSet.getLong(3)*1000L);
		Date endDate = new Date(resultSet.getLong(4)*1000L);
		
		// We don't need index 5 = username

		// Create object and set ID
		ScheduleEvent event = new DefaultScheduleEvent(title, startDate, endDate);
		return event;
	}


	/*
	 * Get event items of an array
	 */
	private ArrayList<String> eventItems(ScheduleEvent event) {
		
		// Get current date when we add or change the row
		// Delete method will not care about the current date
		String currentDate = new Date().toString();
		
		// Get title 
		String title = event.getTitle();

		// Get unix time from date
		String startUnixTime = String.valueOf(event.getStartDate().getTime() / 1000L);
		String endUnixTime = String.valueOf(event.getEndDate().getTime() / 1000L);

		// Return this list
		ArrayList<String> items = new ArrayList<>();
		items.add(currentDate);
		items.add(title);
		items.add(startUnixTime);
		items.add(endUnixTime);
		items.add(username);

		return items;
	}

	
}
