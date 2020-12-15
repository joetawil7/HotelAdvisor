package db;

import entity.Hotel;
import entity.User;
import exceptions.NoEnoughBalanceException;

import java.util.ArrayList;

public class DatabaseExecutor {

	public ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();

		// Aufgabe b

		return users;
	}

	public boolean insertUser(String username, String cardNumber, int vic, String expiryDate) throws Exception {
		boolean success = false;

		// Aufgabe b

		return success;
	}

	private boolean isUsernameValid(String username) {
		boolean isUsernameValid = false;

		// Aufgabe b

		return isUsernameValid;
	}

	private boolean isCreditCardValid(String cardNumber, int vic, String expiryDate) {
		boolean isCardValid = false;

		// Aufgabe b

		return isCardValid;
	}

	private boolean creditCardNotUsed(String cardNumber) {
		boolean creditCardNotUsed = false;

		// Aufgabe b

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

		return success;
	}

	private boolean reduceFreeRooms(int hotelId) {
		boolean success = false;
		
		// Aufgabe d
		
		return success;
	}

	private boolean isBalanceEnough(String username, int hotelId) {
		boolean isBalanceEnough = false;

		// Aufgabe d

		return isBalanceEnough;
	}

	public ArrayList<Hotel> getUserHotel(String username) {
		ArrayList<Hotel> userHotel = new ArrayList<Hotel>();

		// Aufgabe d

		return userHotel;
	}
}
