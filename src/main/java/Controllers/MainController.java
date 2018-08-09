package Controllers;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import password.Password;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Client client;

    @FXML
    ListView<Password> passwordListView;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setClient(Client client) {
        this.client = client;
        passwordListView.setItems(client.getObservableList());
    }
}
