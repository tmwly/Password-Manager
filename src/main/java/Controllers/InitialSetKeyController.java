package Controllers;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitialSetKeyController implements Initializable {


    Client client;
    Stage stage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void show(Stage stage, Client client){
        FXMLLoader loader = new FXMLLoader(Window.class.getResource("/InitialSetKey.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        InitialSetKeyController controller = loader.getController();
        controller.setClient(client);
        controller.setStage(stage);
        stage.setScene(scene);
        stage.show();


    }




}
