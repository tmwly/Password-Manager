package Controllers;

import client.Client;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import password.Password;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Client client;
    private Password currentPassword;

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
    Button newPasswordButton;

    @FXML
    MenuItem changePassMenuItem;

    @FXML
    MenuItem changeDatabaseMenuItem;

    @FXML
    MenuItem createDatabaseMenuItem;

    @FXML
    Button editPasswordButton;

    Stage primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passwordListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (!(oldValue == null)) {

                oldValue.encrypt(client.getKey());
            }
            currentPassword = newValue;
            setLabels(newValue);

        });
        changePassMenuItem.setOnAction(event -> {
            setPassword();
        });

        changeDatabaseMenuItem.setOnAction(event -> {
            changeDatabase();
        });

        createDatabaseMenuItem.setOnAction(event -> {
            createDatabase();
        });

        newPasswordButton.setOnAction(event -> {
            newPassword();
        });

        editPasswordButton.setOnAction(event -> {
            editPassword();
        });



    }

    private void createDatabase(){
        Stage stage = new Stage();
        stage.initOwner(primaryStage);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create database json file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Json files", "*.json" ) ;
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(stage);
        try {
            client.createDatabase(file);
        } catch(MismatchedInputException e) {
            System.out.println("FUcked it");
        }

        updateObservableList();

        System.out.println(file.getAbsolutePath());

    }

    private void changeDatabase() {
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
                updateObservableList();

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

    public void updateObservableList() {
        passwordListView.setItems(client.refreshObservableList());
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
        stage.initModality(Modality.WINDOW_MODAL);
        KeyChangerController.show(stage, client);
    }

    public void editPassword(){

        if(!(currentPassword == null)) {

            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            EditPasswordController.show(stage, client, currentPassword);
        }


    }





    public void newPassword(){
        Boolean setFile = true;
        try {
            String location = client.getDatabase().getFile().getAbsolutePath();
        } catch(NullPointerException e) {
            setFile = false;
        }
        String key = client.getKey();

        System.out.println(setFile);

        if(key.equals("") && !setFile) {
            showErrorDialog("Error", "Please set a key and Database before adding a new password");
        } else if(key.equals("")) {
            showErrorDialog("Error", "Please set a key before adding a new password");
        } else if(!setFile) {
            showErrorDialog("Error", "Please set a database before adding a new password");
        } else {
            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            NewPasswordController.show(stage, client);
        }


    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
