package se.danielmartensson.hardware.MCP23017;

import java.io.IOException;

import com.pi4j.io.i2c.I2CDevice;

import se.danielmartensson.hardware.MCP3008.ReadMCP3008;

public class WriteMCP23017 {

	private I2CDevice i2CDevice;
	private ReadMCP3008 readMCP3008;
	private int IODIRA = 0x00;
	private int IODIRB = 0x01;
	private int GPPUA = 0x0C;
	private int GPPUB = 0x0D;
	private int GPIOA = 0x12;
	private int GPIOB = 0x13;
	private long sleepTime;
	private int row; 
	private int column;
	
	public WriteMCP23017(I2CDevice i2CDevice, ReadMCP3008 readMCP3008) {
		this.i2CDevice = i2CDevice;
		this.readMCP3008 = readMCP3008;
		
		// Set B and A side to output and activate pull-up resistors
		try {
			this.i2CDevice.write(IODIRA, (byte) 0x00); // A-side output is low
			this.i2CDevice.write(IODIRB, (byte) 0x00); // B-side output is low
			this.i2CDevice.write(GPPUA, (byte) 0xFF); // Pull up for A side
			this.i2CDevice.write(GPPUB, (byte) 0xFF); // Pull up for B side
			
			/*
			 * Initi the LCD
			 */
			initLCD();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void initLCD() {
		// Set S
		try { 
			
			/*
			 * Initial positions
			 */
			
			row = 0;
			column = 0;
			
			// Set the D7->D0 bits
			/*
			 * 0x30 = Init
			 * 0x38 = Function set
			 * 0x0B = Display off
			 * 0x01 = Clear display
			 * 0x06 = Entry mode set
			 */
			sleepTime = 10;
			byte[] D7_to_D0 = {0x30, 0x30, 0x30, 0x38, 0x0C, 0x01, 0x06};
			for(int i = 0; i < D7_to_D0.length; i++) {
				i2CDevice.write(GPIOA, (byte) D7_to_D0[i]); 
				setEWithRSzero();
			}
			sleepTime = 1; // We need fast LCD
			
			// Go to home
			home(); 
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	/*
	 * Write to LCD
	 */
	public void write() {
		int x_axis = readMCP3008.getX_axis();
		int y_axis = readMCP3008.getY_axis();
		
		// Clear display
		try {
			i2CDevice.write(GPIOA, (byte) 0x01);
			setEWithRSzero();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		writeLCD("X-axis: " + x_axis, 0, 0);
		writeLCD("Y-axis: " + y_axis, 1, 0);
	}

	private void writeLCD(String text, int rowLine, int columnPosition) {
		
		/*
		 * Move to right row line and column position
		 */
		
		if(rowLine == 1 && row != 1) {
			moveToSecondLine();
		}else if(rowLine == 0 && row != 0){
			moveToFirstLine();
		}
		
		// Not the same? You got to move it move it!
		while(columnPosition != column) {
			if(columnPosition > column) {
				moveOneRight();
			}else {
				moveOneLeft();
			}
		}
		
		// Now we are standing on rowLine and columnPosition - Write
		for(int i = 0; i < text.length(); i++) {
			
			try {
				byte a_char = (byte) text.charAt(i); // Get the hex code!
				i2CDevice.write(GPIOA, a_char);
				setEWithRSone();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
	}
	
	/*
	 * Set E from high to low with RS = 0 at the beginning
	 */
	private void setEWithRSzero() {
		try {
			i2CDevice.write(GPIOB, (byte) 0x00); // RS = 0, R/W = 0, E = 0
			Thread.sleep(sleepTime);
			i2CDevice.write(GPIOB, (byte) 0x0C); // RS = 0, R/W = 0, E = 1
			Thread.sleep(sleepTime);
			i2CDevice.write(GPIOB, (byte) 0x00); // RS = 0, R/W = 0, E = 0
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Set E from high to low with RS = 1 at the beginning
	 */
	private void setEWithRSone() {
		try {
			i2CDevice.write(GPIOB, (byte) 0x01); // RS = 1, R/W = 0, E = 0
			Thread.sleep(sleepTime);
			i2CDevice.write(GPIOB, (byte) 0x05); // RS = 1, R/W = 0, E = 1
			Thread.sleep(sleepTime);
			i2CDevice.write(GPIOB, (byte) 0x00); // RS = 0, R/W = 0, E = 0
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * This will move the cursor one step to the right on the current row 
	 */
	private void moveOneRight() {
		try {
			column++;
			i2CDevice.write(GPIOA, (byte) 0x14);
			setEWithRSzero();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	/*
	 * This will move the cursor one step to the left on the current row 
	 */
	private void moveOneLeft() {
		try {
			column--;
			i2CDevice.write(GPIOA, (byte) 0x10);
			setEWithRSzero();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	/*
	 * Go to second line
	 */
	private void moveToSecondLine() {
		try {
			row = 1;
			i2CDevice.write(GPIOA, (byte) 0xC0); // Set RAM address so that the cursor is propositioned at the head of the second line.
			setEWithRSzero();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/*
	 * This will move to home then move column steps to right
	 */
	private void moveToFirstLine() {
		try {
			row = 0;
			// Move home
			i2CDevice.write(GPIOA, (byte) 0x02); 
			setEWithRSzero();
			
			// Move - Column in this case is always a positive number
			for(int i = 0; i < column; i++) {
				i2CDevice.write(GPIOA, (byte) 0x14); // Move to right column times
				setEWithRSzero();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/*
	 * This will us move home
	 */
	private void home() {
		try {
			row = 0;
			// Move home
			i2CDevice.write(GPIOA, (byte) 0x02); 
			setEWithRSzero();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}	
