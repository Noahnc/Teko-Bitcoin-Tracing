package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File image = new File("src/assets/bitcoin.png");
        MainWindowsImage.setImage(new Image(image.toURI().toString()));

    }

    @FXML
    protected ImageView MainWindowsImage;

    @FXML
    protected Button StartBitcoinTracing;

    @FXML
    protected TextField BitcoinAddressValue;

    @FXML
    protected VBox TransactionsVBox;

}
