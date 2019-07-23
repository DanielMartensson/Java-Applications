package se.danielmartensson.hardware.ULN2008;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import se.danielmartensson.hardware.MCP3008.ReadMCP3008;

public class WriteULN2003 {
	
	private ReadMCP3008 readMCP3008;
	private GpioController gpioController;
	private GpioPinDigitalOutput pin29;
	private GpioPinDigitalOutput pin28;
	private GpioPinDigitalOutput pin27;
	private GpioPinDigitalOutput pin26;
	private GpioStepperMotorComponent motor;
	private int pastValue;
	
	
	public WriteULN2003(ReadMCP3008 readMCP3008, GpioController gpioController) {
		this.readMCP3008 = readMCP3008;
		this.gpioController = gpioController;
		
		// Initial value
		pastValue = 0;
				
		// Create the pins
		pin29 = this.gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_29, "29", PinState.LOW);
		pin28 = this.gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_28, "28", PinState.LOW);
		pin27 = this.gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_27, "27", PinState.LOW);
		pin26 = this.gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_26, "26", PinState.LOW);
		
		// Shutdown options
		pin29.setShutdownOptions(true, PinState.LOW);
		pin28.setShutdownOptions(true, PinState.LOW);
		pin27.setShutdownOptions(true, PinState.LOW);
		pin26.setShutdownOptions(true, PinState.LOW);
		
		// Create motor
		GpioPinDigitalOutput[] pins = {pin26, pin27, pin28, pin29};
		motor = new GpioStepperMotorComponent(pins);
		
		byte[] single_step_sequence = new byte[4];
        single_step_sequence[0] = (byte) 0b0001;
        single_step_sequence[1] = (byte) 0b0010;
        single_step_sequence[2] = (byte) 0b0100;
        single_step_sequence[3] = (byte) 0b1000;
        
        motor.setStepInterval(2);
        motor.setStepSequence(single_step_sequence);
        motor.setStepsPerRevolution(2038);
	}
	
	public void write() {
		int step = pastValue - readMCP3008.getX_axis();
		motor.step(step);
		pastValue = readMCP3008.getX_axis();
	}

}
