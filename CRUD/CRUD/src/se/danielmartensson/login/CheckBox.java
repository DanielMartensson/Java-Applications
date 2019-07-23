package se.danielmartensson.login;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class CheckBox implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean checkBoxMarked;  
   
    public boolean isCheckBoxMarked() {
		return checkBoxMarked;
	}

	public void setCheckBoxMarked(boolean checkBoxMarked) {
		this.checkBoxMarked = checkBoxMarked;
	}

	public void addMessage() {
        String summary; 
        if(checkBoxMarked) {
        	summary = "Checked";
        	
        }else {
        	summary = "Unchecked";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
    
    public void setCookie(String username, String password) {
    	// Also set if the checkBox is marked
    }

	public String getCookie(String string) {
		// Also get the check box if marked
		
		return null;
	}
    

}
