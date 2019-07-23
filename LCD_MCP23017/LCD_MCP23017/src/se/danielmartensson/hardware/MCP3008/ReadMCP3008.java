package se.danielmartensson.hardware.MCP3008;


public class ReadMCP3008 {
	
	private WriteMCP3008 writeMCP3008;
	private int x_axis;
	private int y_axis;

	public ReadMCP3008(WriteMCP3008 writeMCP3008) {
		this.writeMCP3008 = writeMCP3008;
	}
	
	/*
	 * Read channel 0 and 1 - We need to write first
	 */
	
	public void read() {
		byte[] resultChannel0 = writeMCP3008.getResultChannel0();
		byte[] resultChannel1 = writeMCP3008.getResultChannel1();
		
		/*
		 * Convert the binary to decimal
		 */
		
		x_axis = (resultChannel0[1]<< 8) & 0b1100000000; //merge data[1] & data[2] to get 10-bit result
		x_axis |=  (resultChannel0[2] & 0xff);
		
        y_axis = (resultChannel1[1]<< 8) & 0b1100000000; //merge data[1] & data[2] to get 10-bit result
        y_axis |=  (resultChannel1[2] & 0xff);
	}

	public int getX_axis() {
		return x_axis;
	}

	public void setX_axis(int x_axis) {
		this.x_axis = x_axis;
	}

	public int getY_axis() {
		return y_axis;
	}

	public void setY_axis(int y_axis) {
		this.y_axis = y_axis;
	}

	
	
	
}
