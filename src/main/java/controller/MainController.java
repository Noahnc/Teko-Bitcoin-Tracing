package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.text.Font;
import logic.BitcoinHandler;

public class MainController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File image = new File("src/assets/bitcoin.png");
        MainWindowsImage.setImage(new Image(image.toURI().toString()));

    }

    @FXML
    public void StartTracing() {
        InputInfo.setText("");
        ArrayList<String> BitcoinAddresses = null;
        Label BitcoinAddressText;
        TransactionsVBox.getChildren().removeAll(TransactionsVBox.getChildren());
        BitcoinHandler Bitcoinhandler = new BitcoinHandler();
        String BitcoinAddress = BitcoinAddressValue.getText();

        if (BitcoinAddress.equals("")) {
            InputInfo.setText("Eingabe darf nicht leer sein!");
        } else {
            if (Bitcoinhandler.CheckIfBitcoinAddressIsValid(BitcoinAddress)) {
                BitcoinAddresses = Bitcoinhandler.getBitcoinAddresses(BitcoinAddress);

                if (BitcoinAddresses.size() == 0) {
                    InputInfo.setText("Unter dieser Bitcoin Adresse wurde keine ausgehende Transaktion gefunden.");
                } else {
                    for (var address : BitcoinAddresses) {
                        BitcoinAddressText = new Label(address);
                        BitcoinAddressText.setFont(new Font("Arial", 20));
                        TransactionsVBox.getChildren().add(BitcoinAddressText);

                    }
                }

            } else {
                InputInfo.setText("Keine gültige Bitcoin Adresse eingegeben!");
            }
        }

    }

    @FXML
    protected ImageView MainWindowsImage;

    @FXML
    protected Button StartBitcoinTracing;

    @FXML
    protected TextField BitcoinAddressValue;

    @FXML
    protected VBox TransactionsVBox;
    @FXML
    protected Label InputInfo;

}
