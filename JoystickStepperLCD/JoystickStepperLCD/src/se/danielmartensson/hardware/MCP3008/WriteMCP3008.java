package se.danielmartensson.hardware.MCP3008;

import java.io.IOException;

import com.pi4j.io.spi.SpiDevice;



public class WriteMCP3008 {
	
	private SpiDevice spiDevice;
	private byte[] resultChannel0;
	private byte[] resultChannel1;
	private String errorMessage;
	
	

	public WriteMCP3008(SpiDevice spiDevice) {
		this.spiDevice = spiDevice;
	}
	
	/*
	 * Call two channels - 0 and 1
	 */
	public void write() {
		
		try {
			resultChannel0 = spiDevice.write(data((short) 0));
			resultChannel1 = spiDevice.write(data((short) 1));
		} catch (IOException e) {
			errorMessage = e.getMessage();
		}
		
		
	}
	
	private byte[] data(short channel) {
		
		byte[] data = new byte[] {
                (byte) 0b00000001,                              // first byte, start bit
                (byte)(0b10000000 |( ((channel & 7) << 4))),    // second byte transmitted -> (SGL/DIF = 1, D2=D1=D0=0)
                (byte) 0b00000000                               // third byte transmitted....don't care
        };
		
		return data;
	}

	public byte[] getResultChannel0() {
		return resultChannel0;
	}

	public void setResultChannel0(byte[] resultChannel0) {
		this.resultChannel0 = resultChannel0;
	}

	public byte[] getResultChannel1() {
		return resultChannel1;
	}

	public void setResultChannel1(byte[] resultChannel1) {
		this.resultChannel1 = resultChannel1;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	
	

	
	
	

}
