package se.danielmartensson.web;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class Button {
 
    private boolean LED;   
    private RaspberryPi raspberryPi;
    
    public Button(RaspberryPi raspberryPi) {
    	LED = false; // Start whith LED OFF
    	System.out.println("Raspberry Pi Init");
    	this.raspberryPi = raspberryPi;
    }
	
	public void state() {
		if(!LED) {
			raspberryPi.ledON();
			System.out.println("LED ON");
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ON"));
			LED = true;
			
		}else {
			raspberryPi.ledOFF();
			System.out.println("LED OFF");
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("OFF"));
			LED = false;
			
		}
	}
 
  

}