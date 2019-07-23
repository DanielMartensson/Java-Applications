package webapplikation;

import java.io.IOException;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;

public class Raspberry {
	// SPI device
    public SpiDevice spi = null;

    public Raspberry() {

    	// Declare the SPI
    	try {
			spi = SpiFactory.getInstance(SpiChannel.CS0,
			        SpiDevice.DEFAULT_SPI_SPEED, // default spi speed 1 MHz
			        SpiDevice.DEFAULT_SPI_MODE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("SPI init failed");
		} // default spi mode 0
    	
    	
    }
    
    public void setResistans(int dac, boolean status) {
    	
    	// Set the command
    	byte command = (byte) 0b00100001; // Off
    	if(status) {
    		command = (byte) 0b00010001; // On
    	}
    	
    	// Set the register
    	byte register = (byte) (0b00000000 | dac); // Register 0 to 255
    	
    	
    	byte[] data = new byte[] {
    			command, 
    			register
    	};
    	
    	// Write SPI
        try {
			spi.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("SPI writing failed");
		}
        System.out.println(dac + " - " + status);
    
    }
}


