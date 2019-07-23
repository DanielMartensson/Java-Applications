package se.danielmartensson.start;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import se.danielmartensson.login.Login;
import se.danielmartensson.view.ScheduleView;

@ManagedBean
@SessionScoped
public class Controller {
	
	private Login login;
	private ScheduleView scheduleView;
	
	public Controller() {
		login = new Login();
		scheduleView = new ScheduleView(login);
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public ScheduleView getScheduleView() {
		return scheduleView;
	}

	public void setScheduleView(ScheduleView scheduleView) {
		this.scheduleView = scheduleView;
	}



	
}
