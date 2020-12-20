package db;

import config.Config;
import io.CSVReader;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InitDatabase {

	public InitDatabase() {
		try {
			Database.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Initializing database...");
		this.createAllTables();
		this.fillAllTables();
		System.out.println("Database has been initialized successfully.");
		try {
			Database.getConnection().commit();
			Database.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createAllTables() {
		System.out.println("Creating tables...");
		this.createUserTable();
		this.createCreditCardTable();
		this.createLocationTable();
		this.createHotelTable();
		System.out.println("All tables have been created.");
	}

	private void fillAllTables() {
		this.fillCreditCardTable();
		this.fillLocationTable();
		this.fillHotelTable();
	}

	private void createUserTable() {
		System.out.println("Creating User table...");

		final String sql = "CREATE TABLE User (Name TEXT NOT NULL, Username TEXT PRIMARY KEY NOT NULL CHECK(length(Username) > 4), CardNumber TEXT NOT NULL,"
				+ "FOREIGN KEY(CardNumber) REFERENCES CreditCard(CardNumber))";
		try (final Statement statement = Database.getConnection().createStatement()) {
			statement.executeUpdate(sql);
			System.out.println("Table User has been created.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createCreditCardTable() {
		System.out.println("Creating CreditCard table...");

		final String sql = "CREATE TABLE CreditCard (CardNumber TEXT NOT NULL PRIMARY KEY, VIC INTEGER NOT NULL, ExpiryDate TEXT NOT NULL, Balance INTEGER NOT NULL)";
		try (final Statement statement = Database.getConnection().createStatement()) {
			statement.executeUpdate(sql);
			System.out.println("Table CreditCard has been created.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createLocationTable() {
		System.out.println("Creating Location table...");

		final String sql = "Create Table Location (City TEXT NOT NULL," + "PostalCode INTEGER PRIMARY KEY NOT NULL);";
		try (final Statement statement = Database.getConnection().createStatement()) {
			statement.executeUpdate(sql);
			System.out.println("Table Location has been created.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createHotelTable() {
		System.out.println("Creating Hotel table...");

		final String sql = "Create Table Hotel (" + "HotelId INTEGER NOT NULL PRIMARY KEY," + "Name TEXT NULL,"
				+ "Address TEXT NOT NULL," + "PostalCode INTEGER NOT NULL," + "City TEXT NOT NULL,"
				+ "PhoneNumber BIGINT NOT NULL," + "Email TEXT NOT NULL," + "Website TEXT NOT NULL,"
				+ "Rooms INTEGER NOT NULL," + "Stars INTEGER NOT NULL," + "Review REAL NOT NULL,"
				+ "Price INTEGER NOT NULL," + "Parking TEXT NOT NULL," + "Internet TEXT NOT NULL);";
		try (final Statement statement = Database.getConnection().createStatement();) {
			statement.executeUpdate(sql);
			System.out.println("Table Flight has been created.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fillCreditCardTable() {
		System.out.println("Reading credit cards file...");

		final String file = Config.RESOURCES_DIR + "CREDIT_CARDS.csv";
		final CSVReader csvReader = new CSVReader();
		ArrayList<String[]> fileLines = csvReader.parseFile(file);

		System.out.println("Number of lines read: " + fileLines.size());
		
		// Aufgabe a
		final String sql = "INSERT INTO CreditCard(CardNumber, VIC, ExpiryDate, Balance) VALUES(?,?,?,?);";
		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			int count = 0;
			for (String[] line : fileLines) {
				preparedStatement.setString(1, line[0]);
				preparedStatement.setInt(2, Integer.parseInt(line[1]));
				preparedStatement.setString(3, line[2]);
				preparedStatement.setFloat(4, Float.parseFloat(line[3]));

				count += preparedStatement.executeUpdate();
			}

			System.out.println(count + " rows has been inserted into CreditCard table.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fillLocationTable() {
		System.out.println("Reading locations file...");

		final String file = Config.RESOURCES_DIR + "LOCATIONS.csv";
		final CSVReader csvReader = new CSVReader();
		ArrayList<String[]> fileLines = csvReader.parseFile(file);

		System.out.println("Number of lines read: " + fileLines.size());
		
		// Aufgabe a
		final String sql = "INSERT INTO Location(City, PostalCode) VALUES(?,?);";
		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			int count = 0;
			for (String[] line : fileLines) {
				preparedStatement.setString(1, line[0]);
				preparedStatement.setInt(2, Integer.parseInt(line[1]));
				count += preparedStatement.executeUpdate();
			}

			System.out.println(count + " rows has been inserted into Location table.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fillHotelTable() {
		System.out.println("Reading hotels file...");

		final String file = Config.RESOURCES_DIR + "HOTEL_DATABASE.csv";
		CSVReader csvReader = new CSVReader();
		ArrayList<String[]> fileLines = csvReader.parseFile(file);

		System.out.println("Number of lines read: " + fileLines.size());

		// Aufgabe a
		final String sql = "INSERT INTO Hotel(HotelId, Name, Address, PostalCode, City, PhoneNumber, Email, Website, Rooms, Stars, Review, Price, Parking, Internet) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		try (PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql)) {
			int count = 0;
			for (String[] line : fileLines) {
				preparedStatement.setInt(1, Integer.parseInt(line[0]));
				preparedStatement.setString(2, line[1]);
				preparedStatement.setString(3, line[2]);
				preparedStatement.setInt(4, Integer.parseInt(line[3]));
				preparedStatement.setString(5, line[4]);
				preparedStatement.setLong(6, Long.parseLong(line[5]));
				preparedStatement.setString(7, line[6]);
				preparedStatement.setString(8, line[7]);
				preparedStatement.setInt(9, Integer.parseInt(line[8]));
				preparedStatement.setInt(10, Integer.parseInt(line[9]));
				preparedStatement.setFloat(11, Float.parseFloat(line[10]));
				preparedStatement.setInt(12, Integer.parseInt(line[11]));
				preparedStatement.setString(13, line[12]);
				preparedStatement.setString(14, line[13]);

				count += preparedStatement.executeUpdate();
			}

			System.out.println(count + " rows has been inserted into Hotel table.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
