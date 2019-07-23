package se.danielmartensson.login;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import se.danielmartensson.database.Jdbc;

@ManagedBean
@SessionScoped
public class Login implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Special parameters as fields
	private final String databaseName = "BOOK"; 
	private final String loginTable = "loginCRUD"; 
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	
	// Class for database connection
	private Jdbc jdbc;
	
	// Cookie holder
	CheckBox checkBox;
	
	// Parameters that the web-UI are going to use
	private String username;
	private String password;
	private boolean errorLogin;
	private String errorMessage;
	
	
	public Login(CheckBox checkBox) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.checkBox = checkBox;
	}
	
	public String getPage() {
		
		String xhtml = "index";
		// Get connection to the database
		try {
			jdbc = new Jdbc(serverName, portNumber, databaseName, username, password);
		    // Check if we can write a row into databaseName.tableName
		    boolean checkmark = jdbc.checkLogin(loginTable);
		            
		    if(checkmark) {
		    	xhtml = "jsf/table?faces-redirect=true";
		    	errorLogin = false;
		    	
		    	// Check if the check box is marked - Cookie
		    	if(checkBox.isCheckBoxMarked()) {
		    		// Store the username and password
		    		checkBox.setCookie(username,password);
		    	}else {
		    		checkBox.setCookie("",""); // Delete the cookie
		    	}
		    	
		    }else {
		    	errorLogin = true;
				errorMessage = "Cannot make checkmark";
		     }
		} catch(SQLException e) {
			errorLogin = true;
			errorMessage = "Cannot login into SQL";
		}
		
		return xhtml;
	}
	
	/*
	 * This will load the username and password
	 */
	
	public void loadCookie() {
		username = checkBox.getCookie("username");
		password = checkBox.getCookie("password");
	
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isErrorLogin() {
		return errorLogin;
	}

	public void setErrorLogin(boolean errorLogin) {
		this.errorLogin = errorLogin;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getServerName() {
		return serverName;
	}

	public Jdbc getJdbc() {
		return jdbc;
	}

	public String getDatabaseName() {
		return databaseName;
	}
	
}
