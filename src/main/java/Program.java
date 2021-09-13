import apis.BitcoinAPIHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TransactionsByBitcoin;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Program extends Application {

    public static Boolean DebugMode = true;
    public static String BitcoinAddress;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Bitcoin Tracer");

        try {
            var file = new File("src/main/java/views/MainScene.fxml");
            var root = FXMLLoader.load(file.toURI().toURL());
            var scene = new Scene((Parent) root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
            System.out.print(ex);
        }

        if (DebugMode) {

            BitcoinAddress = "1JC41YHmjKEcW1rLH6pmMWEFHkoNwSmhnC";

            BitcoinAPIHandler TestHanlder = new BitcoinAPIHandler();
            TransactionsByBitcoin Transactions = TestHanlder.getBitcoinTransactionsByBitcoinID(BitcoinAddress);

            for (var transaction : Transactions.object.entrySet()) {
                System.out.println(transaction.getKey());
            }


        }
    }
}