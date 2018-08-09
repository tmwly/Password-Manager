package Controllers;

import client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import password.Password;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Client client;

    @FXML
    ListView<Password> passwordListView;

    @FXML
    Label currentNameLabel;
    @FXML
    Label currentSiteLabel;
    @FXML
    Label currentPasswordLabel;
    @FXML
    Label currentNotesLabel;

    @FXML
    Button editButton;

    @FXML
    MenuItem changePassMenuItem;

    @FXML
    MenuItem changeDatabaseMenuItem;

    Stage primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passwordListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (!(oldValue == null)) {

                oldValue.encrypt(client.getKey());
            }
            setLabels(newValue);

        });
        changePassMenuItem.setOnAction(event -> {
            setPassword();
        });
    }

    public void setClient(Client client) {
        this.client = client;
        passwordListView.setItems(client.getObservableList());
    }

    public void setLabels(Password p) {
        p.decrypt(client.getKey());
        currentNameLabel.setText(p.getName());
        currentSiteLabel.setText(p.getSite());
        currentPasswordLabel.setText(p.getPassword());
        currentNotesLabel.setText(p.getNotes());
    }

    public void setPassword(){
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        PasswordChangerController.show(stage, client);


    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
