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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientKeyChangerController implements Initializable {

    @FXML
    public VBox vBox;

    @FXML
    TextField newKeyTextField;

    @FXML
    TextField repeatNewKeyTextField;


    @FXML
    Button changePassSaveButton;

    @FXML
    Label keyChangeErrorLabel;

    Client client;
    Stage stage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        changePassSaveButton.defaultButtonProperty().bind(changePassSaveButton.focusedProperty());
        changePassSaveButton.setOnAction(e -> {
            changeKey();
        });
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void show(Stage stage, Client client){
        FXMLLoader loader = new FXMLLoader(Window.class.getResource("/ClientKeyChanger.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        ClientKeyChangerController controller = loader.getController();
        controller.setClient(client);
        controller.setStage(stage);
        stage.setScene(scene);
        stage.showAndWait();


    }

    private void changeKey(){

            String newKey = newKeyTextField.getText();
            String newKey2 = repeatNewKeyTextField.getText();



            if (newKey.equals(newKey2)){


                client.changeClientKey(newKey);

                stage.close();
            } else {
                keyChangeErrorLabel.setText("Please ensure the two keys match");
            }

        }
}
