package webapplikation;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SlideEndEvent;

@ManagedBean
@SessionScoped
public class Slider {
	
	private int dac;
	private boolean status;
	private Raspberry raspberry;
	
	public Slider() throws IOException {
		raspberry = new Raspberry();
	}

	public int getDac() {
		return dac;
	}


	public void setDac(int dac) {
		this.dac = dac;
	}


	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
		
	}
	
	public void callResistans() {
		System.out.println("call setResistans with status/dac: " + status + "/" + dac);
		raspberry.setResistans(dac, status);
		System.out.println("Done");
	}
	
	public void onSlideEnd(SlideEndEvent event) {
        dac = event.getValue(); // This method is needed, or else dac = 0 all the time
        callResistans(); // Call now
	}
	
	
	
	
}
