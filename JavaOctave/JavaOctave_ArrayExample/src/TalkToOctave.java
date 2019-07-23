
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TalkToOctave extends Thread {
	private ServerSocket serverSocket;
	private Socket socket;
	private final int port = 5000;
	
	// Output - This is what we got from Octave
	private double output;
	
	// Input - This is what we are sending to Octave
	private byte[] input;
	
	// Time out 
	private final int END_TIME = 5000;

	public TalkToOctave() throws IOException {
		serverSocket = new ServerSocket(port);
	}

	@Override
	public void run() {
		
		// Start up the server
		initSocket();
		
		while (true) {

			// Sending to Octave
			System.out.println("Sending");
			sending();
			
			// Get from octave
			System.out.println("Receiving");
			receive();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}

	/*
	 * This will be used when we need to init or re-init the socket connection
	 */
	private void initSocket() {
		try {
			socket = serverSocket.accept();
		} catch (IOException e) {
		}
		
	}

	private void receive() {
		
		try {
			
			// Get the input stream
			InputStream inputStream = socket.getInputStream();
			
			// Clock the start time
			long startTime = System.currentTimeMillis();
			
			// Create a waiter
			while (inputStream.available() <= 0) {
				// Loops until we get data or....
				
				long currentTime = System.currentTimeMillis();
				if(currentTime - startTime > END_TIME) {
					initSocket(); // Lets re-start the server
					return; // Leave the receive method after 5 seconds
				}
			}

			// Create an array
			int size = inputStream.available();
			char[] ascci = new char[size];
			
			// Convert int to char
			for (int i = 0; i < size; i++) {
				ascci[i] = (char) inputStream.read();
			}
			
			// Convert to String -> Double
			output = Double.valueOf(new String(ascci));
			System.out.println("We receiving: " + output);
			// Done!
			
			
		} catch (IOException e) {
			
		}

		
	}



	private void sending() {
		
		try {
			
			// Get output stream
			OutputStream outputStream = socket.getOutputStream();
			
			// Create arbitrary double array
			double[] doubleArray = new double[] { 20.5, 32.13, 602.1};
			
			// Compute the size of how long byte array we need
			int size = computeLengthByteSize(doubleArray);
			System.out.println("We have the size: " + size);
			
			// Create the byte array
			input = createByteArray(doubleArray, size);
			
		    // Print what we are going to send
		    System.out.println("We are sending number");
		    for(int i = 0; i < input.length; i++) {
		    	System.out.println(input[i]);
		    }
			
			// Send and then flush
			outputStream.write(input);
			outputStream.flush();
			
			// Done!
			
		}catch(Exception e) {
			System.out.println("error " + e.getMessage());
		}
		
		System.out.println("Done!");
		
	}
	
	/*
	 * This will convert double array to byte array with start bit and end bit
	 */
	private byte[] createByteArray(double[] doubleArray, int size) {
		
		// First create our byte array
		byte[] byteArray = new byte[size];
		
		// The k-number is a value where we are at element at byteArray
		int k = 0; // Right now, we are at the first element
		
		// Then do the for loop
		for(int i = 0; i < doubleArray.length; i++) {
			
			// Get first the data
			byte[] tempByteArray = String.valueOf(doubleArray[i]).getBytes();
			
			// Do another for loop to load in the data inside byteArray
			for(int j = 0; j < tempByteArray.length; j++) {
				byteArray[j+k] = tempByteArray[j];
			}
			
			// At what element are we standing now
			k += tempByteArray.length;
			
			// Insert the SPACE - ASCII 32
			byteArray[k] = 32;
			
			// Now are standing at element:
			k++;
			
			
		}
		
		return byteArray;
		
	}

	/*
	 * This will compute the total size we need to create a byte array
	 */
	private int computeLengthByteSize(double[] doubleArray) {
		int size = 0;
		for(int i = 0; i < doubleArray.length; i++) {
			size += String.valueOf(doubleArray[i]).getBytes().length; // Data size
			size += 1; // SPACE size 
		}
		return size;
	}

	public double getOutput() {
		return output;
	}

	public void setInput(byte[] input) {
		this.input = input;
	}
	
	

}
