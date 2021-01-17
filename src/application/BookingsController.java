package application;

import config.Config;
import db.DatabaseExecutor;
import entity.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import service.GlobalService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookingsController implements Initializable {

	@FXML
	private Label fxLabelUsername;

	@FXML
	private Label fxLabelBalance;

	@FXML
	private ListView<Hotel> fxListViewHotels;

	private DatabaseExecutor databaseExecutor;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.databaseExecutor = new DatabaseExecutor();
		List<Hotel> hotels = this.databaseExecutor
				.getUserHotel(GlobalService.getInstance().getLoggedInUser().getUsername());
		 this.fxListViewHotels.getItems().addAll(hotels);

		this.databaseExecutor = new DatabaseExecutor();
		String loggedInUsername = GlobalService.getInstance().getLoggedInUser().getUsername() + "!";
		this.fxLabelUsername.setText(loggedInUsername);
		this.updateBalanceLabel();
	}

	@FXML
	void onBackButtonAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(new URL(Config.MAIN_APP_FXML_PATH));
			Main.getMainScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateBalanceLabel() {
		String balance = String.format("%.2f",
				GlobalService.getInstance().getLoggedInUser().getCreditCard().getBalance());
		this.fxLabelBalance.setText(balance);
	}
}
