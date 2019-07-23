
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
			
			// Create input values
			double number = 20.5;
			input = String.valueOf(number).getBytes();
			
		    // Print what we are going to send
		    System.out.println("We are sending number: " + number);
			
			// Send and then flush
			outputStream.write(input);
			outputStream.flush();
			
			// Done!
			
		}catch(Exception e) {
			
		}
		
	}

	public double getOutput() {
		return output;
	}

	public void setInput(byte[] input) {
		this.input = input;
	}
	
	

}
