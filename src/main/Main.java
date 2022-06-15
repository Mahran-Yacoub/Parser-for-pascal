package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/userinterface/Main.fxml"));
        Scene scene = new Scene(root, 745, 565);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Parser");
        primaryStage.show();
    }
}
