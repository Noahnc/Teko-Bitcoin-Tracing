package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import logic.BitcoinHandler;
import logic.MarkedBitcoinService;
import model.MarkedBitcoin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    File LoadingImageFile = new File("src/assets/loadingAnimation.gif");
    Image LoadingImage = new Image(LoadingImageFile.toURI().toString());

    @FXML
    private ImageView MainWindowsImage;

    @FXML
    private Button StartBitcoinTracing;

    @FXML
    private TextField BitcoinAddressValue;

    @FXML
    private VBox TransactionsVBox;

    @FXML
    private Label InputInfo;

    @FXML
    private ComboBox<Integer> LayerDepth;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File image = new File("src/assets/bitcoin.png");
        MainWindowsImage.setImage(new Image(image.toURI().toString()));

        LayerDepth.getItems().addAll(1, 5, 10, 50, 200, 1000);
        LayerDepth.getSelectionModel().selectFirst();
    }

    private void addToTreeView(TreeItem<String> parent, MarkedBitcoin parentModel) {
        for (var mb : parentModel.getChildren()) {
            var child = new TreeItem<>(mb.getAddress());
            parent.getChildren().add(child);

            addToTreeView(child, mb);
        }
    }

    @FXML
    public void StartTracing() throws IOException {
        InputInfo.setText("");
        DisableLoadingAnimation();

        BitcoinHandler Bitcoinhandler = new BitcoinHandler();
        String BitcoinAddress = BitcoinAddressValue.getText();

        if (BitcoinAddress.equals("")) {
            InputInfo.setText("Eingabe darf nicht leer sein!");
        } else {
            if (Bitcoinhandler.CheckIfBitcoinAddressIsValid(BitcoinAddress)) {
                EnableLoadingAnimation();

                Thread thread = new Thread(() -> {
                    try {
                        var result = new MarkedBitcoin(BitcoinAddressValue.getText());
                        new MarkedBitcoinService().getAddress(result.getAddress(), result, LayerDepth.getValue(), 0, new String[0]);

                        Platform.runLater(() -> {
                            DisableLoadingAnimation();

                            var item = new TreeItem<>(result.getAddress());
                            var treeView = new TreeView<String>();

                            treeView.prefHeightProperty().bind(TransactionsVBox.getScene().heightProperty());
                            treeView.prefWidthProperty().bind(TransactionsVBox.getScene().widthProperty());

                            treeView.setRoot(item);
                            addToTreeView(item, result);

                            TransactionsVBox.getChildren().add(treeView);
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                thread.start();
            } else {
                InputInfo.setText("Unter dieser Bitcoin Adresse wurde keine ausgehende Transaktion gefunden.");
            }
        }

    }

    void DisableLoadingAnimation() {
        TransactionsVBox.getChildren().removeAll(TransactionsVBox.getChildren());
        StartBitcoinTracing.setDisable(false);
    }

    void EnableLoadingAnimation() {
        TransactionsVBox.getChildren().add(new ImageView(LoadingImage));
        TransactionsVBox.getChildren().add(new Label("Bitcoins werden gesucht, bitte warten!"));
        StartBitcoinTracing.setDisable(true);
    }

    @FXML public void OnLayerDepthSelectionChnage() {
        switch (LayerDepth.getValue()) {
            case 1:
                LayerDepth.setPrefWidth(60);
                break;
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
