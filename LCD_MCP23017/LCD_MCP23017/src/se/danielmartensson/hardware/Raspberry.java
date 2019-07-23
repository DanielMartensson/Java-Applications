package se.danielmartensson.hardware;

import java.io.IOException;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;

import se.danielmartensson.hardware.MCP23017.WriteMCP23017;
import se.danielmartensson.hardware.MCP3008.ReadMCP3008;
import se.danielmartensson.hardware.MCP3008.WriteMCP3008;

/*
 * This class will use MCP3008, MCP41010, MCP23017
 */

public class Raspberry {
	/*
	 * SPI - MCP3008
	 */
	public SpiFactory spiFactory;
	public SpiDevice spiDevice;
	private ReadMCP3008 readMCP3008;
	private WriteMCP3008 writeMCP3008;
	
	/* 
	 * I2C - MCP23017
	 */
	public I2CBus i2CBus;
	private I2CDevice i2CDevice;
	private int SLAVE_ADDRESS = 0x20;
	private WriteMCP23017 writeMCP23017;
	
	
	private String errorMessage;
	
	/* 
	 * Init all libraries such as SPI and i2c
	 */
	public Raspberry() {
		
		try {
			spiDevice = SpiFactory.getInstance(SpiChannel.CS0, SpiDevice.DEFAULT_SPI_SPEED, SpiDevice.DEFAULT_SPI_MODE);
			i2CBus = I2CFactory.getInstance(I2CBus.BUS_1);
			i2CDevice = i2CBus.getDevice(SLAVE_ADDRESS);
		} catch (IOException | UnsupportedBusNumberException e) {
			errorMessage = e.getMessage();
		}
		
		// Init for the MCP3008 functionality
		writeMCP3008 = new WriteMCP3008(spiDevice); // Call the MCP3008
		readMCP3008 = new ReadMCP3008(writeMCP3008); // Read the data MCP3008 give us
		
		// Init for the MCP23018 functionality
		writeMCP23017 = new WriteMCP23017(i2CDevice, readMCP3008);
	}


	public ReadMCP3008 getReadMCP3008() {
		return readMCP3008;
	}


	public void setReadMCP3008(ReadMCP3008 readMCP3008) {
		this.readMCP3008 = readMCP3008;
	}


	public WriteMCP3008 getWriteMCP3008() {
		return writeMCP3008;
	}


	public void setWriteMCP3008(WriteMCP3008 writeMCP3008) {
		this.writeMCP3008 = writeMCP3008;
	}
	

	public WriteMCP23017 getWriteMCP23017() {
		return writeMCP23017;
	}


	public void setWriteMCP23017(WriteMCP23017 writeMCP23017) {
		this.writeMCP23017 = writeMCP23017;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

	
	

}
