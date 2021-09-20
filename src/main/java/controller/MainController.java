package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.text.Font;
import logic.BitcoinHandler;

public class MainController implements Initializable {

    File LoadingImageFile = new File("src/assets/loadingAnimation.gif");
    Image LoadingImage = new Image(LoadingImageFile.toURI().toString());

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File image = new File("src/assets/bitcoin.png");
        MainWindowsImage.setImage(new Image(image.toURI().toString()));
        LayerDepth.getItems().addAll(5, 10, 50, 200, 1000);
        LayerDepth.getSelectionModel().selectFirst();


    }

    @FXML
    public void StartTracing() throws IOException {

        // Zurücksetzen der Labels und Adressen
        InputInfo.setText("");
        ArrayList<String> BitcoinAddresses = null;
        ClearTransactionsVBox();

        Label BitcoinAddressText;
        BitcoinHandler Bitcoinhandler = new BitcoinHandler();
        String BitcoinAddress = BitcoinAddressValue.getText();

        if (BitcoinAddress.equals("")) {
            InputInfo.setText("Eingabe darf nicht leer sein!");
        } else {
            if (Bitcoinhandler.CheckIfBitcoinAddressIsValid(BitcoinAddress)) {
                EnableLoadingAnimation();
                BitcoinAddresses = Bitcoinhandler.getBitcoinAddresses(BitcoinAddress, LayerDepth.getValue());

                ClearTransactionsVBox();
                for (var address : BitcoinAddresses) {
                    BitcoinAddressText = new Label(address);
                    BitcoinAddressText.setFont(new Font("Arial", 20));
                    TransactionsVBox.getChildren().add(BitcoinAddressText);
                }

            } else {
                InputInfo.setText("Unter dieser Bitcoin Adresse wurde keine ausgehende Transaktion gefunden.");
            }
        }

    }

    void ClearTransactionsVBox() {
        TransactionsVBox.getChildren().removeAll(TransactionsVBox.getChildren());
    }

    void EnableLoadingAnimation() {
        TransactionsVBox.getChildren().add(new ImageView(LoadingImage));
        TransactionsVBox.getChildren().add(new Label("Bitcoins werden gesucht"));
    }

    void AddTransactionsToListView(){

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

    @FXML
    protected ComboBox<Integer> LayerDepth;

    @FXML public void OnLayerDepthSelectionChnage() {
        switch (LayerDepth.getValue()) {
            case 5:
                LayerDepth.setPrefWidth(60);
                break;

            case 10:
                LayerDepth.setPrefWidth(65);
                break;
            case 50:
                LayerDepth.setPrefWidth(65);
                break;

            case 200:
                LayerDepth.setPrefWidth(72);
                break;

            case 1000:
                LayerDepth.setPrefWidth(79);
                break;

        }
    }


}
