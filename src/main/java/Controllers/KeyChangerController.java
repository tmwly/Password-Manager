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

public class KeyChangerController implements Initializable {

    @FXML
    public Label oldPasswordLabel;
    @FXML
    public VBox vBox;
    @FXML
    TextField oldPasswordTextField;

    @FXML
    TextField newPasswordTextField;

    @FXML
    TextField repeatNewPasswordTextField;


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
        if(client.getKey().equals("")){
            vBox.getChildren().removeAll(oldPasswordTextField, oldPasswordLabel);
            newPasswordTextField.requestFocus();
        } else {
            oldPasswordTextField.requestFocus();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void show(Stage stage, Client client){
        FXMLLoader loader = new FXMLLoader(Window.class.getResource("/KeyChanger.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        KeyChangerController controller = loader.getController();
        controller.setClient(client);
        controller.setStage(stage);
        stage.setScene(scene);
        stage.show();


    }

    private void changeKey(){
        String oldKey = oldPasswordTextField.getText();
        if(oldKey.equals(client.getKey())){
            String newKey = newPasswordTextField.getText();
            String newKey2 = repeatNewPasswordTextField.getText();

            if (newKey.equals(newKey2)){
                client.changeKey(newKey);
                stage.close();
            } else {
                keyChangeErrorLabel.setText("Please ensure the two passwords match");
            }
        } else {
            keyChangeErrorLabel.setText("Please enter the correct key");
        }


    }


}
