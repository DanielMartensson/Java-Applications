package se.daniel.martensson.web;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Internet {
	private double temperature;
	private Temperature temp;
	
	public Internet() throws IOException {
		temperature = 0;
		temp = new Temperature();
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	
	public void update() throws IOException {
		double ADC = (double) temp.getADC();
		
		// Convert MP9700A at Vd = 3.3 volt
		temperature = ((ADC/1024 * 3.3 - 0.5)*50)/0.5;
	
	}
	
	
}
