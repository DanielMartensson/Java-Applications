package se.danielmartensson.web;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;


public class RaspberryPi {
	
	private GpioPinDigitalOutput pinLED;
	private GpioPinDigitalInput pinButton;

	public RaspberryPi() {
		
		// create gpio controller
        GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin
        pinLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);
        
        // provision gpio pin #16 as an input pin
        pinButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_16, PinPullResistance.PULL_DOWN);
        
        pinButton.setShutdownOptions(true);
        
        // init listener for the button
        initButtonListener();
        
        // Reset number
        Counter.number = 0;
	}
	
	
	public void initButtonListener() {
		

		pinButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            	if(event.getState().isHigh()) {
            		Counter.number++;
            	}
            }

        });
        
		
	}


	public void ledON() {
		pinLED.high();
	}
	
	public void ledOFF() {
		pinLED.low();
	}

	
}
