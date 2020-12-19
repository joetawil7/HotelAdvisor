package application;

import db.DatabaseExecutor;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.GlobalService;

import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField fxTextFieldUsername;

    @FXML
    private Button fxButtonLogin;

    @FXML
    private ComboBox<User> fxComboBoxUsernames;

    @FXML
    private Button fxButtonNewAccount;

    private DatabaseExecutor databaseExecutor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.databaseExecutor = new DatabaseExecutor();
        List<User> users = this.databaseExecutor.getUsers();
        this.fxComboBoxUsernames.getItems().addAll(users);
    }

    @FXML
    public void onLoginButtonAction(ActionEvent event) {
        User user = this.fxComboBoxUsernames.getValue();
        if (user != null) {
            this.login(user);
        }
    }

    private void login(User user) {
        GlobalService.getInstance().setLoggedInUser(user);
        Main.changeToMainAppViewScene();
    }

    @FXML
    public void onNewAccountButtonAction(ActionEvent event) {
        Main.changeToSignUpScene();
    }
}
