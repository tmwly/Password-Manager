package Controllers;

import client.Client;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import password.Password;

import java.io.File;
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

        changeDatabaseMenuItem.setOnAction(event -> {
            setDatabase();
        });
    }

    private void setDatabase() {
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open database json file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Json files", "*.json" ) ;
        fileChooser.getExtensionFilters().add(extensionFilter);
        //fileChooser.showOpenDialog(stage);

        File file = fileChooser.showOpenDialog(stage);

        if(file != null) {
            String s = file.getAbsolutePath();
            System.out.println(s);
            try {
                client.setDatabase(s);

            } catch(MismatchedInputException e) {
                //TODO error this
                System.out.println("fucked it");
            }


        }


    }


    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
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
