package se.danielmartensson.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Start {
	private Button button;
	private RaspberryPi raspberryPi;
	private Counter counter;
	
	
	public Start() {
		raspberryPi = new RaspberryPi();
		button = new Button(raspberryPi);
		counter = new Counter();
	}


	public Button getButton() {
		return button;
	}


	public void setButton(Button button) {
		this.button = button;
	}


	public Counter getCounter() {
		return counter;
	}


	public void setCounter(Counter counter) {
		this.counter = counter;
	}
	
	
}
