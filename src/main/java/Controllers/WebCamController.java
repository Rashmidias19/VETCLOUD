package Controllers;

import com.github.sarxos.webcam.Webcam;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;


import static com.github.sarxos.webcam.Webcam.*;

public class WebCamController{
    @FXML
    private AnchorPane dashboardPane;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
    private ImageView camera;

    Image mainiamge;

    public BufferedImage image;

    public void WebCam() throws IOException {
        Webcam webcam = getDefault();
        webcam.open();

        // get image
        image = webcam.getImage();

        // save image to PNG file
        ImageIO.write(image, "JPG", new File("image.jpg"));
    }

    public void captureBtnOnAction(ActionEvent event) throws IOException {
        WebCam();
    }

    public void saveBtnOnAction(ActionEvent event) {
    }
}
