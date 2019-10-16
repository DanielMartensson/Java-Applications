package org.openjfx.JUSBPlotter;

import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class About {

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image4;

    @FXML
    private ImageView image3;
    
    @FXML
    void initialize() throws FileNotFoundException {
    	Image i1 = new Image(getClass().getResource("/org/openjfx/pictures/computer.png").toExternalForm());
    	image1.setImage(i1);
    	
    	Image i2 = new Image(getClass().getResource("/org/openjfx/pictures/stm32.jpg").toExternalForm());
    	image2.setImage(i2);
    	
    	Image i3 = new Image(getClass().getResource("/org/openjfx/pictures/chart.jpeg").toExternalForm());
    	image3.setImage(i3);
    	
    	Image i4 = new Image(getClass().getResource("/org/openjfx/pictures/octave.jpeg").toExternalForm());
    	image4.setImage(i4);
    	
    }

}
