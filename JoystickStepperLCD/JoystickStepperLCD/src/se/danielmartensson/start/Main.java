package se.danielmartensson.start;

import se.danielmartensson.hardware.Raspberry;

public class Main {
	
	private static Raspberry raspberry;

	public static void main(String[] args) {
		
		// Create the Raspberry Pi object
		raspberry = new Raspberry();
		
		// When we press CTRL+C
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
			    System.out.println("Exited!");
			    raspberry.getGpioController().shutdown();
			}
	    });
		
		while(true) {
			raspberry.getWriteMCP3008().write(); // Write to the ADC
			raspberry.getReadMCP3008().read(); // Read the ADC
			raspberry.getWriteMCP23017().write(); // Write to the LCD
			raspberry.getWriteULN2003().write(); // Control the stepper motor
		}
		
	}

}
