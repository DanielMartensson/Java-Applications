package se.danielmartensson.view;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import se.danielmartensson.login.Login;

@ManagedBean
@SessionScoped
public class Tables implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> dbTables;
    private Login login;
    private String selectedTable;
    
    /*
     * Load initial objects
     */
    
    public Tables(Login login) {
    	this.login = login;
    	// Create list
    	dbTables  = new ArrayList<>();
    }
    
    /*
     * Load all the tables from the databaseName when we load the page
     */
    public void loadTables() {
    	dbTables = login.getJdbc().getTableNames();
    	
    	// If there is first time we use the application
    	if(selectedTable == null) {
    		selectedTable = dbTables.get(0); // Select the first table as default
    	}
    }

	public ArrayList<String> getDbTables() {
		return dbTables;
	}

	public void setDbTables(ArrayList<String> dbTables) {
		this.dbTables = dbTables;
	}

	public String getSelectedTable() {
		return selectedTable;
	}
	
	public void setSelectedTable(String selectedTable) {
		this.selectedTable = selectedTable;
	}
	
	

	
}
