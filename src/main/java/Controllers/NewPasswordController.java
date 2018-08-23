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
import view.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


    public class NewPasswordController implements Initializable {

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





        Client client;
        Stage stage;
        @Override
        public void initialize(URL location, ResourceBundle resources) {
//            newPasswordSaveButton.setDefaultButton(true);
            newPasswordSaveButton.setOnAction(event -> {
                addPassword();
            });
        }

        private void addPassword() {


            String name = addPasswordNameTextField.getText();
            String site = addPasswordSiteTextField.getText();
            String password = addPasswordPasswordTextField.getText();
            String notes = addPasswordNotesTextField.getText();

            if(!(name.equals("") || password.equals(""))){

                client.addPassword(name,site,password,notes);
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
        }

        public void setStage(Stage stage) {
            this.stage = stage;
        }

        public static void show(Stage stage, Client client){
            FXMLLoader loader = new FXMLLoader(Window.class.getResource("/NewPassword.fxml"));
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(parent);
            Controllers.NewPasswordController controller = loader.getController();
            controller.setClient(client);
            controller.setStage(stage);
            stage.setScene(scene);
            stage.show();


        }
    }

