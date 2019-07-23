package se.danielmartensson.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import se.danielmartensson.login.Login;

 
@ManagedBean
@SessionScoped
public class Data implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
    private Tables tables;
    private Columns columns;
    
    public Data(Login login) {
    	tables = new Tables(login);
    	columns = new Columns(login, tables);
    }

	public Tables getTables() {
		return tables;
	}

	public void setTables(Tables tables) {
		this.tables = tables;
	}

	public Columns getColumns() {
		return columns;
	}

	public void setColumns(Columns columns) {
		this.columns = columns;
	}
    
    

}