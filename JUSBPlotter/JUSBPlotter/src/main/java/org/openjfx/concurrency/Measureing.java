package org.openjfx.concurrency;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.openjfx.JUSBPlotter.Connection;
import org.openjfx.JUSBPlotter.Plot;

public class Measureing extends Thread {

	/*
	 * Objects
	 */
	static public boolean active;
	private Alert alert;
	private Alert info;
	private Alert warning;
	private LineChart<String, Float> graph;
	private XYChart.Series<String, Float> dataSeries1;
	private XYChart.Series<String, Float> dataSeries2;
	private XYChart.Series<String, Float> dataSeries3;
	private XYChart.Series<String, Float> dataSeries4;
	private XYChart.Series<String, Float> dataSeries5;
	private XYChart.Series<String, Float> dataSeries6;
	private SimpleDateFormat timeFormat;
	private Connection connection;
	private File measurement;
	private FileOutputStream outputStream;

	/*
	 * Parameters
	 */
	private boolean legend1;
	private boolean legend2;
	private boolean legend3;
	private boolean legend4;
	private boolean legend5;
	private boolean legend6;
	private String legend1Text;
	private String legend2Text;
	private String legend3Text;
	private String legend4Text;
	private String legend5Text;
	private String legend6Text;
	private boolean animationON;
	private String yMin;
	private String yMax;
	private int maxData;
	private String k1;
	private String k2;
	private String k3;
	private String k4;
	private String k5;
	private String k6;
	private String m1;
	private String m2;
	private String m3;
	private String m4;
	private String m5;
	private String m6;
	private long sampleTime;
	private long amountData;
	private long rows;

	public Measureing(Plot plot, LineChart<String, Float> graph, CategoryAxis xAxis, NumberAxis yAxis, Connection connection, File measurement) throws FileNotFoundException {
		/*
		 * Parameters for the graph
		 */
		this.legend1 = plot.getLegend1().isSelected();
		this.legend2 = plot.getLegend2().isSelected();
		this.legend3 = plot.getLegend3().isSelected();
		this.legend4 = plot.getLegend4().isSelected();
		this.legend5 = plot.getLegend5().isSelected();
		this.legend6 = plot.getLegend6().isSelected();
		this.legend1Text = plot.getLegend1Text().getText();
		this.legend2Text = plot.getLegend2Text().getText();
		this.legend3Text = plot.getLegend3Text().getText();
		this.legend4Text = plot.getLegend4Text().getText();
		this.legend5Text = plot.getLegend5Text().getText();
		this.legend6Text = plot.getLegend6Text().getText();
		this.animationON = plot.getAnimationON().isSelected();
		this.yMin = plot.getyMin().getSelectionModel().getSelectedItem();
		this.yMax = plot.getyMax().getSelectionModel().getSelectedItem();
		this.maxData = plot.getMaxData().getSelectionModel().getSelectedItem();
		this.amountData = plot.getAmountData().getSelectionModel().getSelectedItem();
		this.k1 = plot.getK1().getText();
		this.k2 = plot.getK2().getText();
		this.k3 = plot.getK3().getText();
		this.k4 = plot.getK4().getText();
		this.k5 = plot.getK5().getText();
		this.k6 = plot.getK6().getText();
		this.m1 = plot.getM1().getText();
		this.m2 = plot.getM2().getText();
		this.m3 = plot.getM3().getText();
		this.m4 = plot.getM4().getText();
		this.m5 = plot.getM5().getText();
		this.m6 = plot.getM6().getText();
		this.sampleTime = plot.getSampleTime().getSelectionModel().getSelectedItem();

		/*
		 * Data series
		 */
		dataSeries1 = new XYChart.Series<String, Float>();
		dataSeries2 = new XYChart.Series<String, Float>();
		dataSeries3 = new XYChart.Series<String, Float>();
		dataSeries4 = new XYChart.Series<String, Float>();
		dataSeries5 = new XYChart.Series<String, Float>();
		dataSeries6 = new XYChart.Series<String, Float>();

		/*
		 * Add the graph
		 */
		this.graph = graph;
		this.graph.getData().clear();
		this.graph.setAnimated(animationON);

		/*
		 * Setup the legends and add data series
		 */
		if (this.legend1 == true) {
			this.dataSeries1.setName(this.legend1Text);
			this.graph.getData().add(dataSeries1);
		}
		if (this.legend2 == true) {
			this.dataSeries2.setName(this.legend2Text);
			this.graph.getData().add(dataSeries2);
		}
		if (this.legend3 == true) {
			this.dataSeries3.setName(this.legend3Text);
			this.graph.getData().add(dataSeries3);
		}
		if (this.legend4 == true) {
			this.dataSeries4.setName(this.legend4Text);
			this.graph.getData().add(dataSeries4);
		}
		if (this.legend5 == true) {
			this.dataSeries5.setName(this.legend5Text);
			this.graph.getData().add(dataSeries5);
		}
		if (this.legend6 == true) {
			this.dataSeries6.setName(this.legend6Text);
			this.graph.getData().add(dataSeries6);
		}

		/*
		 * Create axis
		 */
		xAxis.setLabel("Time");
		yAxis.setLabel("Measurements");
		if (this.yMin.equals("Auto") == false) {
			yAxis.setLowerBound(Float.parseFloat(this.yMin)); // Turn to float
			yAxis.setAutoRanging(false);
		}else {
			yAxis.setAutoRanging(true); // Reset
		}
		if (this.yMax.equals("Auto") == false) {
			yAxis.setUpperBound(Float.parseFloat(this.yMax)); // Turn to float
			yAxis.setAutoRanging(false);
		}else {
			yAxis.setAutoRanging(true); // Reset
		}

		/*
		 * Get the time format
		 */
		timeFormat = new SimpleDateFormat("hh:mm:ss:SSS");

		/*
		 * Get the connection, measurement and output stream
		 */
		this.connection = connection;
		this.measurement = measurement;
		if (measurement != null) {
			outputStream = new FileOutputStream(measurement);
		}

		/*
		 * Declare alert boxes
		 */
		alert = new Alert(AlertType.ERROR);
		info = new Alert(AlertType.INFORMATION);
		warning = new Alert(AlertType.WARNING);

	}

	@Override
	public void run() {
		if (measurement != null) {
			if (measurement.exists() == true) {
				if (legend1 || legend2 || legend3 || legend4 || legend5 || legend6) {

					String writeRow = null;
					byte[] buffer = null;
					rows = 0;
					

					writeRow = "# name: " + measurement.getName().replaceFirst("[.][^.]+$", "") + "\n# type: matrix\n# rows: " + amountData + "\n# columns: 10\n"; // Without extension from
																								// the file
					buffer = writeRow.getBytes();
					writeToFile(buffer);

					active = true;
					while (active && rows < amountData) {
													
						/*
						 * Read raw ADC values
						 */
						float[] measurements = connection.getMeasurements();
						
						/*
						 * Compute the calibrated measurement values
						 */
						float measure1 = Float.parseFloat(k1) * measurements[0] + Float.parseFloat(m1);
						float measure2 = Float.parseFloat(k2) * measurements[1] + Float.parseFloat(m2);
						float measure3 = Float.parseFloat(k3) * measurements[2] + Float.parseFloat(m3);
						float measure4 = Float.parseFloat(k4) * measurements[3] + Float.parseFloat(m4);
						float measure5 = Float.parseFloat(k5) * measurements[4] + Float.parseFloat(m5);
						float measure6 = Float.parseFloat(k6) * measurements[5] + Float.parseFloat(m6);
						String timeStamp = timeFormat.format(new Date());
						String[] timeStampSplit = timeStamp.split(":"); // 4 elements

						/*
						 * Write to file as GNU Octave .mat style
						 */
						writeRow = " " + measure1 + " " + measure2 + " " + measure3 + " " + measure4 + " " + measure5
								+ " " + measure6 + " " + timeStampSplit[0] + " " + timeStampSplit[1] + " "
								+ timeStampSplit[2] + " " + timeStampSplit[3] + "\n";
						buffer = writeRow.getBytes();
						writeToFile(buffer);

						/*
						 * Plot to screen
						 */
						Platform.runLater(() -> {
							if (legend1 == true) {
								dataSeries1.getData().add(new Data<String, Float>(timeStamp, measure1));
								if (dataSeries1.getData().size() > maxData) {
									dataSeries1.getData().remove(0); // Delete the first one
								}
							}
							if (legend2 == true) {
								dataSeries2.getData().add(new Data<String, Float>(timeStamp, measure2));
								if (dataSeries2.getData().size() > maxData) {
									dataSeries2.getData().remove(0); // Delete the first one
								}
							}
							if (legend3 == true) {
								dataSeries3.getData().add(new Data<String, Float>(timeStamp, measure3));
								if (dataSeries3.getData().size() > maxData) {
									dataSeries3.getData().remove(0); // Delete the first one
								}
							}
							if (legend4 == true) {
								dataSeries4.getData().add(new Data<String, Float>(timeStamp, measure4));
								if (dataSeries4.getData().size() > maxData) {
									dataSeries4.getData().remove(0); // Delete the first one
								}
							}
							if (legend5 == true) {
								dataSeries5.getData().add(new Data<String, Float>(timeStamp, measure5));
								if (dataSeries5.getData().size() > maxData) {
									dataSeries5.getData().remove(0); // Delete the first one
								}
							}
							if (legend6 == true) {
								dataSeries6.getData().add(new Data<String, Float>(timeStamp, measure6));
								if (dataSeries6.getData().size() > maxData) {
									dataSeries6.getData().remove(0); // Delete the first one
								}
							}
						});

						/*
						 * Sample time as delay
						 */
						try {
							Thread.sleep(sampleTime);
						} catch (Exception e) {
							Platform.runLater(() -> {
								alert.setTitle("Error!");
								alert.setHeaderText("Missing sample time.");
								alert.setContentText("You need to set the sample time in Plot.");
								alert.showAndWait();
								active = false;
							});
						}

						/*
						 * Counting
						 */
						rows++;
					}
					/*
					 * Done - Save
					 */
					try {
						outputStream.close();
						/*
						 * Show that we have success
						 */
						Platform.runLater(() -> {
							if(rows < amountData) {
								warning.setTitle("Information!");
								warning.setHeaderText("Aborted! File written!");
								warning.setContentText("You cnnot load the file inside GNU Octave!");
								warning.showAndWait();
							}else {
								info.setTitle("Information!");
								info.setHeaderText("Success! File written!");
								info.setContentText("You can now load the file inside GNU Octave.");
								info.showAndWait();
							}
						});
					} catch (IOException e) {
						Platform.runLater(() -> {
							alert.setTitle("Error!");
							alert.setHeaderText("Cannot save to file.");
							alert.setContentText("You need to have access to the folder.");
							alert.showAndWait();
							active = false;
						});
					}
				} else {
					Platform.runLater(() -> {
						alert.setTitle("Error!");
						alert.setHeaderText("No selected file.");
						alert.setContentText("You need to select a configuration file first.");
						alert.showAndWait();
						active = false;
					});
				}
			}
			
		} else {
			Platform.runLater(() -> {
				alert.setTitle("Error!");
				alert.setHeaderText("No selected file.");
				alert.setContentText("You need to select a measurement file first.");
				alert.showAndWait();
				active = false;
			});
		}
	}

	/*
	 * This function write to file
	 */
	private void writeToFile(byte[] buffer) {
		try {
			outputStream.write(buffer);
		} catch (IOException e1) {
			alert.setTitle("Error!");
			alert.setHeaderText("Cannot write to file.");
			alert.setContentText("You need to have access to the folder.");
			alert.showAndWait();
			active = false;
		}
	}
}
