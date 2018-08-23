package view;

import Controllers.MainController;
import client.Client;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Window extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(Window.class.getResource("/Main.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);

        Client client = new Client();
//        client.setKey("thisisakey");
//        client.addPassword("hello","this","is","password");
//        client.addPassword("google", "tings", "yee", "nout");
//        client.addPassword("twitter", "oijoijo", "fan", "toid");
//        client.addPassword("facebook", "bigtiddy", "youknowit", "");
        MainController controller = loader.getController();
        controller.setClient(client);
        controller.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Toms cool password manager");

        primaryStage.setScene(scene);
        primaryStage.show();




    };
}
