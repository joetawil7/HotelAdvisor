package application;

import db.DatabaseExecutor;
import exceptions.CardInUseException;
import exceptions.InvalidCardException;
import exceptions.InvalidUsernameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.*;

public class SignUpController implements Initializable {

    @FXML
    private TextField fxTestFieldName;

    @FXML
    private TextField fxTextFieldUsername;

    @FXML
    private TextField fxTextFieldCardNumber;

    @FXML
    private TextField fxTextFieldVIC;

    @FXML
    private TextField fxTextFieldExpiryDate;

    @FXML
    private Button fxButtonSignUp;

    @FXML
    private Button fxButtonCancel;

    private DatabaseExecutor databaseExecutor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.databaseExecutor = new DatabaseExecutor();
    }

    @FXML
    void onSignUpButtonAction(ActionEvent event) {
        try {
            boolean success = this.databaseExecutor.insertUser(this.fxTextFieldUsername.getText(),
                    this.fxTextFieldCardNumber.getText(), Integer.parseInt(this.fxTextFieldVIC.getText()),
                    this.fxTextFieldExpiryDate.getText());
            if (success) {
                Main.changeToLoginScene();
            }

        } catch (CardInUseException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Sign up failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (InvalidCardException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Sign up failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (InvalidUsernameException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Sign up failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Sign up failed!");
            alert.setContentText("Check the entered data.");
            alert.showAndWait();
        }
    }

    @FXML
    void onCancelButtonAction(ActionEvent event) {
        Main.changeToLoginScene();
    }


}
