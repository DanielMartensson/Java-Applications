package se.danielmartensson.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
 
@ManagedBean
@SessionScoped
public class Counter {
     
    public static int number;
    
    public Counter() {
    	
    }
 
    public int getNumber() {
        return number;
    }
 
    public void reset() {
        number = 0;
    }
    
    
}