package org.openjfx.JUSBPlotter;

import java.util.ArrayList;

import com.fazecast.jSerialComm.SerialPort;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Connection {

	/*
	 * Fields
	 */
	private ArrayList<SerialPort> portArray = new ArrayList<>();
	private SerialPort serialPort;
	private float[] measurements = new float[12];

	@FXML
	private ComboBox<String> comPorts;

	@FXML
	private TextField status;

	@FXML
	private Button connectButton;

	@FXML
	private Button disconnectButton;

	@FXML
	void connect(ActionEvent event) {
		connectButton.setStyle("-fx-background-color: #53ff1a"); // Green
		disconnectButton.setStyle("-fx-background-color: #ff0000"); // Red

		/*
		 * Open port
		 */
		if (portArray.get(0) != null) {
			int index = comPorts.getSelectionModel().getSelectedIndex();
			serialPort = portArray.get(index);
			serialPort.setBaudRate(115200);
			serialPort.openPort();

			/*
			 * Disable
			 */
			connectButton.setDisable(true);
			disconnectButton.setDisable(false);
			comPorts.setDisable(true);

			/*
			 * Set status
			 */
			status.setText("Connected!");

		} else {
			/*
			 * Set status
			 */
			status.setText("No COM ports!");
		}
	}

	@FXML
	void disconnect(ActionEvent event) {
		disconnectButton.setStyle("-fx-background-color: #53ff1a"); // Green
		connectButton.setStyle("-fx-background-color: #ff0000"); // Red

		/*
		 * Close port
		 */
		if (portArray.get(0) != null) {
			int index = comPorts.getSelectionModel().getSelectedIndex();
			serialPort = portArray.get(index);
			serialPort.closePort();

			/*
			 * Enable
			 */
			connectButton.setDisable(false);
			disconnectButton.setDisable(true);
			comPorts.setDisable(false);

			/*
			 * Set status
			 */
			status.setText("Disconnected!");
		} else {
			/*
			 * Set status
			 */
			status.setText("No COM ports!");
		}
	}

	@FXML
	void initialize() {
		/*
		 * Add all connected ports.
		 */
		SerialPort[] ports = SerialPort.getCommPorts();
		for (int i = 0; i < ports.length; i++) {
			comPorts.getItems().add(ports[i].getDescriptivePortName());
			portArray.add(ports[i]);
		}

		/*
		 * if ports.length == 0
		 */
		if (ports.length == 0) {
			portArray.add(null);
		}

		/*
		 * Set the first selected port
		 */
		comPorts.getSelectionModel().selectFirst();

		/*
		 * Initial disconnect
		 */
		disconnect(null);

		/*
		 * Disable the status box
		 */
		status.setDisable(true);
	}

	/*
	 * Get the measurements
	 */
	public float[] getMeasurements() {
		/*
		 * Flush
		 */
		int receivedBytes = serialPort.bytesAvailable();
		if(receivedBytes > 0) {
			//System.out.println(receivedBytes);
			byte[] flush = new byte[receivedBytes];
			serialPort.readBytes(flush, receivedBytes);
		}
		
		/*
		 * Wait for 28 bytes or more.
		 */
		receivedBytes = 0;
		while (receivedBytes < 28) {
			// Just wait when we got 12 bytes or more
			receivedBytes = serialPort.bytesAvailable();
		}
		//System.out.println(receivedBytes);
		
		/*
		 * Create our buffer and read
		 */
		byte[] buffer = new byte[receivedBytes];
		serialPort.readBytes(buffer, buffer.length);
		
		/*
		 * Tune in startPosition so we have two ACK in the buffer array. One at the start and one at the end
		 */
		int startPosition = 0;
		for(int i = 0; i < receivedBytes-13; i++) {
			if(buffer[i] == buffer[i+13]) {
				// OK. Two equal bytes at position i and position 13+i. Can it be an ACK?
				if(buffer[i] == 0x06) {
					startPosition++; // Just jump one step in front so we don't use the ACK
					if(buffer[i+1] == 0x06)
						startPosition++; // If we got 0x06, 0x06 in line
					break; // Yes
				}else {
					startPosition++;
				}
			}else {
				startPosition++;
			}
		}
		int stopPosition = 11 + startPosition; // Important to have a stop position for buffer array
		/*
		System.out.println("startPosition=" + startPosition);
		System.out.println("stopPosition=" + stopPosition);
		for(int i = 0; i < receivedBytes; i++) {
			System.out.println("Byte[" + i + "]=" + buffer[i]);
		}
		*/
		/*
		 * Then convert two uint8 to uint16 and then sum them to float. We need 12 bytes
		 * in total to get 6 values
		 */
		for (byte i = 0; i < measurements.length; i++) {
			measurements[i] = (byteToUint8(buffer[startPosition++]) << 8) | (byteToUint8(buffer[startPosition++]));
			//System.out.println("measurements[" + i + "] = " + measurements[i]);
			if (startPosition >= stopPosition) {
				break; // Can only have 6 measurements of uint16, which means 12 uint8 values
			}
		}

		return measurements;
	}

	/*
	 * Java byte is from -128 to 127. We want it to be from 0 to 255
	 */
	private int byteToUint8(byte b) {
		if (b < 0) {
			return b + 256;
		} else {
			return b;
		}
	}

}
