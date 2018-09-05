package Controllers;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import password.Password;
import view.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class EditPasswordController implements Initializable {

    @FXML
    Button newPasswordSaveButton;

    @FXML
    TextField addPasswordNameTextField;
    @FXML
    TextField addPasswordSiteTextField;
    @FXML
    TextField addPasswordPasswordTextField;
    @FXML
    TextField addPasswordNotesTextField;

    private static Password password;



    Client client;
    Stage stage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//            newPasswordSaveButton.setDefaultButton(true);
        newPasswordSaveButton.setOnAction(event -> {
            editPassword();
        });
    }

    private void editPassword() {
        String name = addPasswordNameTextField.getText();
        String site = addPasswordSiteTextField.getText();
        String passwordStr = addPasswordPasswordTextField.getText();
        String notes = addPasswordNotesTextField.getText();

        if(!(name.equals("") || password.equals(""))){
            Password newPass = new Password(name, site, passwordStr, notes);
            password.encrypt(client.getKey());
            client.overwritePassword(password, newPass);
            stage.close();
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please enter a name and password");

            alert.showAndWait();
        }

    }

    public void setClient(Client client) {


        this.client = client;
        //Set existing values
        addPasswordNameTextField.setText(password.getName());
        addPasswordNotesTextField.setText(password.getNotes());
        addPasswordPasswordTextField.setText(password.getPassword());
        addPasswordSiteTextField.setText(password.getSite());


    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void show(Stage stage, Client client, Password p){
        FXMLLoader loader = new FXMLLoader(Window.class.getResource("/EditPassword.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        password = p;

        if(password == null) {
            System.out.println("SHOW PASSWORD IS NULL");
        } else {
            System.out.println(password.deepToString());
        }

        Scene scene = new Scene(parent);
        EditPasswordController controller = loader.getController();
        controller.setClient(client);
        controller.setStage(stage);
        stage.setScene(scene);
        stage.show();


    }
}

