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
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class FindHotelController implements Initializable {

	@FXML
	private ComboBox<Location> fxComboBoxCity;

	@FXML
	private ComboBox<Location> fxComboBoxPostalCode;

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
		this.disableDatePickerPastSelection();
		this.initComboBoxes();
	}

	public void setMainAppController(MainAppController mainAppController) {
		this.mainAppController = mainAppController;
	}

	@FXML
	void onCityComboBoxKeyReleased(KeyEvent event) {
		if (!event.isAltDown() && !event.isControlDown() && !event.isShiftDown()
				&& (event.getCode().isLetterKey() || event.getCode() == KeyCode.BACK_SPACE)) {
			this.fxComboBoxCity.hide();
			this.fxComboBoxCity.getItems().clear();
			String searchTerm = this.fxComboBoxCity.getEditor().getText();
			List<Location> airports = this.databaseExecutor.find(searchTerm);
			this.fxComboBoxCity.getItems().addAll(airports);
			this.fxComboBoxCity.show();
		}
	}

	@FXML
	void onPostalCodeComboBoxKeyReleased(KeyEvent event) {
		if (!event.isAltDown() && !event.isControlDown() && !event.isShiftDown()
				&& (event.getCode().isLetterKey() || event.getCode() == KeyCode.BACK_SPACE)) {
			this.fxComboBoxPostalCode.hide();
			this.fxComboBoxPostalCode.getItems().clear();
			String searchTerm = this.fxComboBoxPostalCode.getEditor().getText();
			List<Location> airports = this.databaseExecutor.findAirports(searchTerm);
			this.fxComboBoxPostalCode.getItems().addAll(airports);
			this.fxComboBoxPostalCode.show();
		}
	}

	@FXML
	void onSearchButtonAction(ActionEvent event) {
		this.fxListViewHotels.getItems().clear();
		if (this.fxComboBoxCity.getValue() != null && this.fx.getValue() != null
				&& this.fxDatePickerOn.getValue() != null) {
			String from = this.fxComboBoxFrom.getSelectionModel().getSelectedItem().getCode();
			String to = this.fxComboBoxTo.getSelectionModel().getSelectedItem().getCode();
			int month = this.fxDatePickerOn.getValue().getMonthValue();
			int dayOfMonth = this.fxDatePickerOn.getValue().getDayOfMonth();
			
			List<Hotel> flights = this.databaseExecutor.findHotels(
					GlobalService.getInstance().getLoggedInUser().getUsername(), from, to, month, dayOfMonth);
			
			this.fxListViewHotels.getItems().addAll(flights);
		}
	}

	@FXML
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

	private void disableDatePickerPastSelection() {
		this.fxDatePickerOn.setDayCellFactory(new Callback<DatePicker, DateCell>() {

			@Override
			public DateCell call(DatePicker arg0) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);
						LocalDate today = LocalDate.now();
						setDisable(empty || date.compareTo(today) < 0);
					}
				};
			}
		});
	}

	private void initComboBoxes() {
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
	}

	@FXML
	void onLogOutButtonAction(ActionEvent event) {
		GlobalService.getInstance().setLoggedInUser(null);
		Main.changeToLoginScene();
	}
}
