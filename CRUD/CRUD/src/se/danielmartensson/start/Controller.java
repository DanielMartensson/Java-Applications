package se.danielmartensson.start;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import se.danielmartensson.login.CheckBox;
import se.danielmartensson.login.Login;
import se.danielmartensson.view.Data;

@ManagedBean
@SessionScoped
public class Controller {
	
	private Login login;
	private Data data;
	private CheckBox checkBox;

	public Controller() {
		checkBox = new CheckBox();
		login = new Login(checkBox); // Create a jdbc object inside login
		data = new Data(login); // Tables need acces to jdbc too
		
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}

	
	
	
}
