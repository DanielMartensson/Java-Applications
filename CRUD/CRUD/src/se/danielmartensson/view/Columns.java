package se.danielmartensson.view;


import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import se.danielmartensson.database.Jdbc;
import se.danielmartensson.login.Login;

@ManagedBean
@SessionScoped
public class Columns implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Login login;
	private Tables tables;
	
	// These fields are going to be connected to the data table in table.xhtml
	private ArrayList<String> columnList;
	private ArrayList<ArrayList<String>> rowList;

    
    public Columns(Login login, Tables tables) {
    	this.login = login;
    	this.tables = tables;
    	
    }
    
    public void update() {
    	// Get Jdbc
    	Jdbc jdbc = login.getJdbc();
    	
    	// Get selected table
    	String selectedTable = tables.getSelectedTable();
    	
    	// Get all the names of the columns
    	columnList = jdbc.getColumns(selectedTable);  	
    	
    	// Get the rows
    	rowList = jdbc.getRowList(selectedTable);
    	
    }
    
    public void addRow() {
    	// Get Jdbc
    	Jdbc jdbc = login.getJdbc();
    	
    	// Get selected table
    	String selectedTable = tables.getSelectedTable();
    	
    	// Add new row
    	jdbc.addNewRow(selectedTable);    	
    }
    
    public void editRow(ArrayList<String> editedRow) {
    	// Get Jdbc
    	Jdbc jdbc = login.getJdbc();
    	
    	// Get selected table
    	String selectedTable = tables.getSelectedTable();
    	
    	// Find that row who has the same date
    	for(ArrayList<String> databaseRow : rowList) {
    		String dateDatabase = databaseRow.get(0); // First element is the date element
    		String dateEdited = editedRow.get(0);
    		
    		// Note that if we edited the date column, then it will be no change
    		if(dateDatabase.contains(dateEdited)) {
    			// Call jdbc and ask for a update at the database and then at the data sheet
    			jdbc.updateRow(selectedTable, dateEdited, editedRow, columnList);
    			update();
    		}
    	}
    }
    
    public void deleteRow(ArrayList<String> deleteRow) {
    	// Get Jdbc
    	Jdbc jdbc = login.getJdbc();
    	
    	// Get selected table
    	String selectedTable = tables.getSelectedTable();
    	
    	// Get ID of date
    	String dateID = deleteRow.get(0); // First index is date
    	
    	// Delete row
    	jdbc.deleteRow(dateID, selectedTable);
    	
    	// Update
    	update();
    }
    
	public ArrayList<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(ArrayList<String> columnList) {
		this.columnList = columnList;
	}

	public ArrayList<ArrayList<String>> getRowList() {
		return rowList;
	}

	public void setRowList(ArrayList<ArrayList<String>> rowList) {
		this.rowList = rowList;
	}
    
}
