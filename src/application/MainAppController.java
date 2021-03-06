package application;

import config.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import service.GlobalService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {

	@FXML
	private Label fxLabelUsername;

	@FXML
	private Label fxLabelBalance;

	@FXML
	private Button fxButtonFindHotel;

	@FXML
	private Button fxButtonBookings;

	@FXML
	private Button fxButtonLogOut;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		String loggedInUsername = GlobalService.getInstance().getLoggedInUser().getUsername() + "!";
		this.fxLabelUsername.setText(loggedInUsername);
		updateBalanceLabel();
	}

	public void updateBalanceLabel() {
		String balance = String.format("%.2f",
				GlobalService.getInstance().getLoggedInUser().getCreditCard().getBalance());
		this.fxLabelBalance.setText(balance);
	}

	@FXML
	void onFindHotelButtonAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(new URL(Config.FIND_HOTEL_FXML_PATH));
			Main.getMainScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onBookingsButtonAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(new URL(Config.BOOKINGS_FXML_PATH));
			Main.getMainScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onLogOutButtonAction(ActionEvent event) {
		GlobalService.getInstance().setLoggedInUser(null);
		Main.changeToLoginScene();
	}

}
