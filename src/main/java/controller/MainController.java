package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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

        LayerDepth.getItems().addAll(1, 2, 3, 4, 5, 10, 50, 200, 1000);
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
        disableLoadingAnimation();

        // Validiert den Input
        var bitcoinAddressValueText = BitcoinAddressValue.getText();
        if (bitcoinAddressValueText == null || bitcoinAddressValueText.equals("")) {
            InputInfo.setText("Eingabe darf nicht leer sein!");
            return;
        }

        // Checkt die Bitcoin Adresse
        if (!new MarkedBitcoinService().CheckIfBitcoinAddressIsValid(bitcoinAddressValueText)) {
            InputInfo.setText("Unter dieser Bitcoin Adresse wurde keine ausgehende Transaktion gefunden.");
            return;
        }

        enableLoadingAnimation();
        Thread thread = new Thread(() -> {
            try {
                var result = new MarkedBitcoin(BitcoinAddressValue.getText());

                var markedBitcoinService = new MarkedBitcoinService();
                markedBitcoinService.calculateMarkedBitcoinsRecursive(result.getAddress(), result, LayerDepth.getValue(), 0, new String[0]);

                System.out.print("Total requests: " + markedBitcoinService.getRequestCount());

                Platform.runLater(() -> {
                    disableLoadingAnimation();

                    var item = new TreeItem<>(result.getAddress());
                    var treeView = new TreeView<String>();

                    treeView.prefHeightProperty().bind(TransactionsVBox.getScene().heightProperty());
                    treeView.prefWidthProperty().bind(TransactionsVBox.getScene().widthProperty());

                    treeView.setRoot(item);
                    addToTreeView(item, result);

                    TransactionsVBox.getChildren().add(treeView);
                });
            } catch (IOException e) {
                InputInfo.setText("Es ist ein unerwarteter Fehler aufgetreten. Versuchen ");
                disableLoadingAnimation();
                e.printStackTrace();
            }
        });

        thread.start();
    }

    void disableLoadingAnimation() {
        TransactionsVBox.getChildren().removeAll(TransactionsVBox.getChildren());
        StartBitcoinTracing.setDisable(false);
    }

    void enableLoadingAnimation() {
        TransactionsVBox.getChildren().add(new ImageView(LoadingImage));
        TransactionsVBox.getChildren().add(new Label("Bitcoins werden gesucht, bitte warten!"));
        StartBitcoinTracing.setDisable(true);
    }

    // Berechnet die breite des Dropdowns je nach grösse der Zahl
    @FXML
    public void OnLayerDepthSelectionChange() {
        switch (LayerDepth.getValue()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                LayerDepth.setPrefWidth(60);
                break;

            case 10:
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
