package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
