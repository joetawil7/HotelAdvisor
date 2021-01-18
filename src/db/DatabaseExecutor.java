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
import java.text.DecimalFormat;
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
				int balance = resultSet.getInt("Balance");

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

	public ArrayList<String> showCities() throws SQLException {
		ArrayList<String> cities = new ArrayList<String>();

		// Aufgabe c
		final String sql = "SELECT DISTINCT City FROM Location ORDER BY City;";

		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				String city = resultSet.getString("City");
				cities.add(city);
			}
		}

		return cities;
	}

	public ArrayList<Integer> showPostalCode(String city) throws SQLException {
		ArrayList<Integer> postalCodes = new ArrayList<>();

		// Aufgabe c
		final String sql = "SELECT PostalCode FROM Location WHERE City = ? ORDER BY PostalCode;";

		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, city);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				Integer postalCode = resultSet.getInt("PostalCode");
				postalCodes.add(postalCode);
			}
		}

		return postalCodes;
	}

	public ArrayList<Hotel> findHotels(String city, int postalCode, String parking, String internet) throws SQLException {
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		DecimalFormat df = new DecimalFormat("0.0");
		int k = 0;

		// Aufgabe c
		String sql = "SELECT HotelId,Name,Address,PhoneNumber,Email,Website,Rooms,Stars,Review,Price,Parking,Internet FROM Hotel WHERE City = ? AND PostalCode = ?";
		if(parking != null) {
			sql += " AND Parking = ?";
			k++;
		}
		if(internet != null){
			sql += " AND Internet = ?";
			k++;
		}
		sql += " ORDER BY Price;";
		PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
		preparedStatement.setString(1, city);
		preparedStatement.setInt(2, postalCode);
		if(k == 1){
			preparedStatement.setString(3, "JA");
		}
		if(k == 2){
			preparedStatement.setString(3, "JA");
			preparedStatement.setString(4, "JA");
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			int hotelId = resultSet.getInt("HotelId");
			String name = resultSet.getString("Name");
			String address = resultSet.getString("Address");
			int phoneNumber = resultSet.getInt("PhoneNumber");
			String email = resultSet.getString("Email");
			String website = resultSet.getString("Website");
			int rooms = resultSet.getInt("Rooms");
			int stars = resultSet.getInt("Stars");
			double review = Math.round(resultSet.getDouble("Review") * 100.0) / 100.0;
			int price = resultSet.getInt("Price");
			String parking2 = resultSet.getString("Parking");
			String internet2 = resultSet.getString("Internet");
			Hotel newHotel = new Hotel(hotelId, name, address, postalCode, city, phoneNumber, email, website, rooms, stars, review, price, parking2, internet2);
			hotels.add(newHotel);
		}

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
		final String sql = "INSERT INTO UserHotel(Username, HotelId) VALUES(?,?);";

		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, username);
			preparedStatement.setInt(2, hotelId);
			success = preparedStatement.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}

	private boolean chargeCard(String username, int hotelId) {
		boolean success = false;

		// Aufgabe d
		final String sql = "UPDATE CreditCard "
				+ "SET Balance = Balance - (SELECT Price FROM Hotel WHERE HotelId = ?) "
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
		final String sql = "UPDATE Hotel SET Rooms = Rooms - 1 WHERE hotelId = ?";

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
				+ "WHERE username = ?) >= " + "(SELECT Price FROM Hotel WHERE HotelId = ?);";

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
		String sql = "SELECT * FROM Hotel INNER JOIN UserHotel on Hotel.HotelId = UserHotel.HotelId WHERE Username = ?;";

		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, username);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Hotel hotel = new Hotel();
				hotel.setHotelId(resultSet.getInt("HotelId"));
				hotel.setHotelName(resultSet.getString("Name"));
				hotel.setAddress(resultSet.getString("Address"));
				hotel.setPhoneNumber(resultSet.getInt("PhoneNumber"));
				hotel.setEmail(resultSet.getString("Email"));
				hotel.setWebsite(resultSet.getString("Website"));
				hotel.setFreeRooms(resultSet.getInt("Rooms"));
				hotel.setStars(resultSet.getInt("Stars"));
				hotel.setReview(Math.round(resultSet.getDouble("Review") * 100.0) / 100.0);
				hotel.setPrice(resultSet.getInt("Price"));
				hotel.setParking(resultSet.getString("Parking"));
				hotel.setInternet(resultSet.getString("Internet"));

				userHotel.add(hotel);
			}

			} catch (SQLException e) {
			e.printStackTrace();
		}

		return userHotel;
	}
}
