package org.openjfx.JUSBPlotter;


import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Protocol {

    @FXML
    private ImageView image0;

    @FXML
    void initialize() throws FileNotFoundException {
    	Image i0 = new Image(getClass().getResource("/org/openjfx/pictures/protocol.jpeg").toExternalForm());
    	image0.setImage(i0);
    }
}
