import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Program extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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
    }
}
