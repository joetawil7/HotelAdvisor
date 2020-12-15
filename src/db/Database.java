package db;

import org.sqlite.SQLiteConfig;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static Connection connection;

	public static Connection getConnection() {

		if (connection == null) {
			Database.connect();
		}

		return connection;
	}

	public static void disconnect() {
		if (Database.connection != null) {
			try {
				Database.connection.close();
				System.out.println("Connection has been closed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void connect() {
		try {

			final String DRIVER = "org.sqlite.JDBC";
			final String DB_FILE_NAME = System.getProperty("user.dir") + System.getProperty("file.separator")
					+ "resources" + System.getProperty("file.separator") + "HotelBookingSystemDB.db";
			final boolean dbExisted = new File(DB_FILE_NAME).exists();

			SQLiteConfig config = new SQLiteConfig();
			config.enforceForeignKeys(true);

			Class.forName(DRIVER);
			Database.connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE_NAME, config.toProperties());
			System.out.println("Database connection has been established.");

			if (!dbExisted) {
				new InitDatabase();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
