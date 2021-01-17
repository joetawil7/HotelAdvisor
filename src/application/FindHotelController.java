package application;

import config.Config;
import db.DatabaseExecutor;
import entity.CreditCard;
import entity.Hotel;
import entity.Location;
import exceptions.NoEnoughBalanceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import service.GlobalService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class FindHotelController implements Initializable {

	@FXML
	private ComboBox<String> fxComboBoxCity;

	@FXML
	private ComboBox<Integer> fxComboBoxPostalCode;

	@FXML
	private CheckBox fxCheckBoxParking;

	@FXML
	private CheckBox fxCheckBoxInternet;

	@FXML
	private Button fxButtonSearch;

	@FXML
	private Button fxButtonBook;

	@FXML
	private ListView<Hotel> fxListViewHotels;

	private DatabaseExecutor databaseExecutor;
	private MainAppController mainAppController;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.databaseExecutor = new DatabaseExecutor();
		try {
			this.initComboBox();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setMainAppController(MainAppController mainAppController) {
		this.mainAppController = mainAppController;
	}

	void initComboBox() throws SQLException {
		List<String> cities = this.databaseExecutor.showCities();
		this.fxComboBoxCity.getItems().addAll(cities);
	}

	@FXML
	void fxOnActionComboBoxCity(ActionEvent actionEvent) throws SQLException {
		this.fxComboBoxPostalCode.getItems().clear();
		List<Integer> postalCodes = this.databaseExecutor.showPostalCode(this.fxComboBoxCity.getSelectionModel().getSelectedItem());
		this.fxComboBoxPostalCode.getItems().addAll(postalCodes);
	}

	@FXML
	void onSearchButtonAction(ActionEvent event) {
		this.fxListViewHotels.getItems().clear();
		if (this.fxComboBoxCity.getValue() != null && this.fxComboBoxPostalCode.getValue() != null) {
			List<Hotel> flights = this.databaseExecutor.findHotels(
					GlobalService.getInstance().getLoggedInUser().getUsername(), this.fxComboBoxCity.getSelectionModel().getSelectedItem(), this.fxComboBoxPostalCode.getSelectionModel().getSelectedItem(), this.fxCheckBoxParking.isSelected() ? "JA" : null, this.fxCheckBoxInternet.isSelected() ? "JA" : null);
			this.fxListViewHotels.getItems().addAll(flights);
		}
	}

	/*@FXML
	void onBuyButtonAction(ActionEvent event) {
		Flight selectedFlight = this.fxListViewHotels.getSelectionModel().getSelectedItem();

		if (selectedFlight != null) {
			boolean success;
			try {
				success = this.databaseExecutor.buyFlight(GlobalService.getInstance().getLoggedInUser().getUsername(),
						selectedFlight.getFlightId());
				if (success) {
					CreditCard creditCard = GlobalService.getInstance().getLoggedInUser().getCreditCard();
					creditCard.setBalance(creditCard.getBalance() - selectedFlight.getPrice());
					this.mainAppController.updateBalanceLabel();

					this.fxListViewHotels.getItems().clear();
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Congratulation!");
					alert.setHeaderText("Ticket has been bought successfully");
					alert.showAndWait();

				}
			} catch (NoEnoughBalanceException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Buying ticket failed.");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
	}*/

	@FXML
	void onBackButtonAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(new URL(Config.MAIN_APP_FXML_PATH));
			Main.getMainScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*private void initComboBoxes() {
		StringConverter<Airport> fromComboBoxStringConvertor = new StringConverter<Airport>() {
			@Override
			public String toString(Airport airport) {
				return airport == null ? "" : airport.getName();
			}

			@Override
			public Airport fromString(String string) {
				return fxComboBoxFrom.getValue();
			}
		};

		StringConverter<Airport> toComboBoxStringConvertor = new StringConverter<Airport>() {
			@Override
			public String toString(Airport airport) {
				return airport == null ? "" : airport.getName();
			}

			@Override
			public Airport fromString(String string) {
				return fxComboBoxTo.getValue();
			}
		};

		this.fxComboBoxFrom.setConverter(fromComboBoxStringConvertor);
		this.fxComboBoxTo.setConverter(toComboBoxStringConvertor);
	}*/

	@FXML
	void onLogOutButtonAction(ActionEvent event) {
		GlobalService.getInstance().setLoggedInUser(null);
		Main.changeToLoginScene();
	}

	public void onBookButtonAction(ActionEvent actionEvent) {
	}

}
