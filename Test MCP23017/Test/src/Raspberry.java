
import java.io.IOException;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;


public class Raspberry {
	public static I2CBus i2c;
	public static I2CDevice device;
	
	// Address to the slave
	public static final int SLAVE_ADDRESS = 0x20;
	
	// Read GPIOB
	public static final int READ_ADDRESS_GPIOB = 0x13;
	
	// Write GPIOA
	public static final int WRITE_ADDRESS_GPIOA = 0x12;
	
	// Internal pullups for B side
	private static final int PULLUPS_ADDRESS_GPPUB = 0x0D; 
	
	// Declare the IN-Out direction for A and B
	private static final int IODIRA_REGISTER = 0x00; 
    private static final int IODIRB_REGISTER = 0x01; 
	
	/*
	 * Connect to MCP23017
	 */
	public static void main(String[] args)  {
		try {
			i2c = I2CFactory.getInstance(I2CBus.BUS_1);
			System.out.println("i2c");
			device = i2c.getDevice(SLAVE_ADDRESS);
			System.out.println("device");
			
			device.write(IODIRA_REGISTER, (byte) 0x00);
	        device.write(IODIRB_REGISTER, (byte) 0xFF);
	        device.write(PULLUPS_ADDRESS_GPPUB, (byte) 0xFF);
	        
		} catch (UnsupportedBusNumberException e) {
			System.out.println("UnsupportedBusNumberException in main()");
		} catch (IOException e) {
			System.out.println("IOException in main()");
		}
		
		
		
		start();
	}
	
	/*
	 * Read the button and ligth the LED
	 */
	public static void start() {
		while(true) {
			try {
				// When we push button, GPIOB will be 255 -> 254
				int pin = device.read(READ_ADDRESS_GPIOB);
				if(pin == 254) {
					// Light LED
					device.write(WRITE_ADDRESS_GPIOA, (byte) 0xFF);
					System.out.println("ON :" + pin);
				}else {
					// Close LED
					device.write(WRITE_ADDRESS_GPIOA, (byte) 0x00);
					System.out.println("OFF :" + pin);
				}
			} catch (IOException e) {
				System.out.println("IOException in start()");
			}
			
		}
	}
}