package db;

import entity.CreditCard;
import entity.Hotel;
import entity.User;
import exceptions.CardInUseException;
import exceptions.InvalidCardException;
import exceptions.InvalidUsernameException;
import exceptions.NoEnoughBalanceException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseExecutor {

	public ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();

		// Aufgabe b
		final String sql = "SELECT User.*, Balance FROM User INNER JOIN CreditCard ON User.CardNumber = CreditCard.CardNumber;";
		try (Statement statement = Database.getConnection().createStatement()) {
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String username = resultSet.getString("Username");
				String cardNumber = resultSet.getString("CardNumber");
				double balance = resultSet.getFloat("Balance");

				CreditCard creditCard = new CreditCard(cardNumber, balance);
				User user = new User(username, creditCard);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	public boolean insertUser(String name, String username, String cardNumber, int vic, String expiryDate) throws Exception {
		boolean success = false;

		// Aufgabe b
		if (!this.isUsernameValid(username)) {
			throw new InvalidUsernameException();
		} else if (!this.isCreditCardValid(cardNumber, vic, expiryDate)) {
			throw new InvalidCardException();
		} else if (!creditCardNotUsed(cardNumber)) {
			throw new CardInUseException();
		}

		final String sql = "INSERT INTO User(Name, Username, CardNumber) VALUES(?,?,?);";
		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, cardNumber);
			success = preparedStatement.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;

	}

	private boolean isUsernameValid(String username) {
		boolean isUsernameValid = false;

		// Aufgabe b
		if (username.length() >= 4) {

			final String sql = "SELECT COUNT(*) FROM User WHERE Username = ?";

			try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
				preparedStatement.setString(1, username);

				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					isUsernameValid = resultSet.getInt(1) == 0;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isUsernameValid;
	}

	private boolean isCreditCardValid(String cardNumber, int vic, String expiryDate) {
		boolean isCardValid = false;

		// Aufgabe b
		final String sql = "SELECT COUNT(*) FROM CreditCard WHERE CardNumber = ? AND VIC = ? AND ExpiryDate = ?";
		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, cardNumber);
			preparedStatement.setInt(2, vic);
			preparedStatement.setString(3, expiryDate);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				isCardValid = resultSet.getInt(1) == 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isCardValid;
	}

	private boolean creditCardNotUsed(String cardNumber) {
		boolean creditCardNotUsed = false;

		// Aufgabe b
		final String sql = "SELECT COUNT(*) FROM CreditCard INNER JOIN USER ON CreditCard.CardNumber = User.CardNumber "
				+ "WHERE CreditCard.CardNumber = ?;";

		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, cardNumber);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				creditCardNotUsed = resultSet.getInt(1) == 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


		return creditCardNotUsed;
	}

	/*public ArrayList<Airport> findAirports(String searchTerm) {
		ArrayList<Airport> airports = new ArrayList<Airport>();

		// Aufgabe c

		return airports;
	}*/

	public ArrayList<Hotel> findHotels(String username, String city, int postalCode, String parking, String internet) {
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();

		// Aufgabe c
		
		return hotels;
	}

	public boolean bookHotel(String username, int hotelId) throws NoEnoughBalanceException {

		boolean success = this.isBalanceEnough(username, hotelId);

		if (success) {
			this.insertUserHotel(username, hotelId);
			this.chargeCard(username, hotelId);
			this.reduceFreeRooms(hotelId);
		} else {
			throw new NoEnoughBalanceException();
		}

		return success;
	}

	private boolean insertUserHotel(String username, int hotelId) {
		boolean success = false;

		// Aufgabe d
		
		return success;
	}

	private boolean chargeCard(String username, int hotelId) {
		boolean success = false;

		// Aufgabe d
		final String sql = "UPDATE CreditCard "
				+ "SET Balance = Balance - (SELECT Price FROM Flight WHERE FlightId = ?) "
				+ "WHERE CardNumber = (SELECT CardNumber FROM User WHERE username = ?);";

		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			preparedStatement.setInt(1, hotelId);
			preparedStatement.setString(2, username);
			success = preparedStatement.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

	private boolean reduceFreeRooms(int hotelId) {
		boolean success = false;
		
		// Aufgabe d
		final String sql = "UPDATE Flight SET FreeRooms = FreeRooms - 1 WHERE hotelId = ?";

		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			preparedStatement.setInt(1, hotelId);
			success = preparedStatement.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}

	private boolean isBalanceEnough(String username, int hotelId) {
		boolean isBalanceEnough = false;

		// Aufgabe d
		final String sql = "Select "
				+ "(Select Balance FROM User INNER JOIN CreditCard on User.cardnumber = CreditCard.cardnumber "
				+ "WHERE username = ?) >= " + "(SELECT Price FROM Flight WHERE FlightId = ?);";

		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, hotelId);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				isBalanceEnough = resultSet.getInt(1) == 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isBalanceEnough;
	}

	public ArrayList<Hotel> getUserHotel(String username) {
		ArrayList<Hotel> userHotel = new ArrayList<Hotel>();

		// Aufgabe d

		return userHotel;
	}
}
