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
import password.Encryptor;
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
    MenuItem setClientKeyMenuItem;
    @FXML
    MenuItem changeDatabaseKeyMenuItem;
    @FXML
    MenuItem changeDatabaseMenuItem;
    @FXML
    MenuItem createDatabaseMenuItem;

    @FXML
    Button editPasswordButton;
    @FXML
    Button deletePasswordButton;


    Stage primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passwordListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!(oldValue == null)) {
                oldValue.encrypt(client.getKey());
            }
            currentPassword = newValue;


            if(!(newValue == null)) {
                setLabels(newValue);
            }

        });

        changeDatabaseKeyMenuItem.setOnAction(event -> {
            changeDatabaseKey();
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

        deletePasswordButton.setOnAction(event -> {
            deletePassword();
        });

        setClientKeyMenuItem.setOnAction(event -> {
            setClientKey();
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

        if(!(file == null)) {
            try {
                client.createDatabase(file);
            } catch(MismatchedInputException e) {
                System.out.println("FUcked it");
            }
            updateObservableList();
        }

    }

    private void changeDatabase() {
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open database json file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Json files", "*.json" ) ;
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(stage);

        if(file != null) {
            String s = file.getAbsolutePath();
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

        String name = p.getName();
        String site = Encryptor.decrypt(p.getSite(), client.getKey());
        String password = Encryptor.decrypt(p.getPassword(), client.getKey());
        String notes = Encryptor.decrypt(p.getNotes(), client.getKey());

        currentNameLabel.setText(name);
        currentSiteLabel.setText(site);
        currentPasswordLabel.setText(password);
        currentNotesLabel.setText(notes);
    }

    public void clearLabels() {
        currentNameLabel.setText("");
        currentSiteLabel.setText("");
        currentPasswordLabel.setText("");
        currentNotesLabel.setText("");
    }

    public void changeDatabaseKey(){
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        DatabaseKeyChangerController.show(stage, client);
        updateObservableList();
    }

    public void setClientKey(){

        if(!(currentPassword == null)) {
            passwordListView.getSelectionModel().getSelectedItem().encrypt(client.getKey());
        }




        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        ClientKeyChangerController.show(stage,client);
        updateObservableList();
    }

    //TODO make gui for this and test it actually works
    // should it prompt?
    public void deletePassword() {

        if(!(currentPassword == null)) {


            client.deletePassword(currentPassword);

//            get labels to reset proper


        }  else {
            showError("Error", "Please select a password to delete");
        }
    }


    public void editPassword(){

        if(!(currentPassword == null)) {

            Password p = currentPassword.decryptAndReturn(client.getKey());

            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);

            EditPasswordController.show(stage, client, p);
        } else {
            showError("Error", "Please select a password to edit");
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

        if(key.equals("") && !setFile) {
            showError("Error", "Please set a key and Database before adding a new password");
        } else if(key.equals("")) {
            showError("Error", "Please set a key before adding a new password");
        } else if(!setFile) {
            showError("Error", "Please set a database before adding a new password");
        } else {
            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            NewPasswordController.show(stage, client);
        }
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
