package se.danielmartensson.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import se.danielmartensson.hardware.Raspberry;

/*
 * This class will host the index.html
 */

@ManagedBean
@SessionScoped
public class Start {
	
	private Raspberry raspberry;
	private String statusMessage = "";
	
	public Start() {
		raspberry = new Raspberry();
	}

	public Raspberry getRaspberry() {
		return raspberry;
	}

	public void setRaspberry(Raspberry raspberry) {
		this.raspberry = raspberry;
	}

	public String getStatusMessage() {
		return statusMessage;
	}	
	
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public void addStatusMessage(String message) {
		statusMessage += message + "\n";
	}
	
}
