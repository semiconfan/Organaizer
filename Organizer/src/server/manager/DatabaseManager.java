package server.manager;

import java.io.IOException;
import java.sql.*;
import shared.messages.*;

public class DatabaseManager {
	private String dbURL = "jdbc:mysql://localhost:3306/organizerDB";
	private String dbUsername = "root";
	private String dbPass = "";
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	
	private LogManager logManager;
	
	private Connection getConnection() {
		return connection;
	}

	private void setConnection(Connection connection) {
		this.connection = connection;
	}

	private LogManager getLogManager() {
		return logManager;
	}

	private void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

	public DatabaseManager(LogManager logManager) {
		
		this.setLogManager(logManager);
		
		try {		
			connection = DriverManager.getConnection(dbURL, dbUsername, dbPass);
			
			if(connection != null) {
				System.out.println("DB_MANAGER:Connected to the database");
				this.getLogManager().writeToLogs("DB_MANAGER:Connected to the database");
			}
			
		} catch(SQLException e) {
			this.getLogManager().writeToLogs("DB_MANAGER:ERROR:Could not connect to the database!!!");
			this.getLogManager().writeToLogs(e.toString());
			System.out.println("DB_MANAGER:ERROR:Could not connect to the database!!!");
			System.out.println(e);
		}
	}
	
	/**
	 * 
	 * @param message information about sign in
	 * @return ID if sign in was successful or -1 of no such record was found
	 * @throws IOException
	 */
	public int tryToGetIdBySignIn(Message message) throws IOException {
		if(this.getConnection() == null) {
			throw new IOException("DABASE_MANAGER:ERROR:Database is not available");
		}
		
		SignInMessage signInMessage = (SignInMessage) message;
		int ID = -1;
		
		try {
			String request = "SELECT ID FROM `users` WHERE login = ? AND password = ?;";	
			
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			preparedStatement.setString(1, signInMessage.getLogin());
			preparedStatement.setString(2, signInMessage.getPassword());
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			// If no such record in database return -1
			if(!resultSet.next()) {
				signInMessage.setResponseData(-1);
				return -1;
			}
			
			ID = resultSet.getInt("ID");
			
			// If there is another record return -1
			if(resultSet.next()) {
				signInMessage.setResponseData(-1);
				return -1;
			}

		} catch (SQLException e) {
			this.getLogManager().writeToLogs("DB_MANAGER:ERROR:Unable to execute SQL request!!!");
			this.getLogManager().writeToLogs(e.toString());
			System.out.println("DB_MANAGER:ERROR:Unable to execute SQL request!!!");
			System.out.println(e);
		}
		return ID;
	}
	
	/**
	 * 
	 * @param message information about sign up
	 * @return boolean true if record was created false else
	 * @throws IOException
	 */
	public boolean tryToCreateSignUpRecord(Message message) throws IOException {
		if(this.getConnection() == null) {
			throw new IOException("DABASE_MANAGER:ERROR:Database is not available");
		}
		
		SignUpMessage signUpMessage = (SignUpMessage) message;
		int ID = -1;
		
		try {
			String request = "INSERT INTO `users` (login, password) VALUES (?, ?)";;	
			
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			preparedStatement.setString(1, signUpMessage.getLogin());
			preparedStatement.setString(2, signUpMessage.getPassword());
			
			int rowsAffected = preparedStatement.executeUpdate();

			// Check if the data was inserted successfully
            if (rowsAffected > 0) {
            	this.getLogManager().writeToLogs("DABASE_MANAGER:Data inserted successfully");
                System.out.println("DABASE_MANAGER:Data inserted successfully");
                return true;
            } else {
            	this.getLogManager().writeToLogs("DABASE_MANAGER:Failed to insert data");
                System.out.println("DABASE_MANAGER:Failed to insert data");
                return false;
            }

		} catch (SQLException e) {
			this.getLogManager().writeToLogs("DB_MANAGER:ERROR:Unable to execute SQL request!!!");
			this.getLogManager().writeToLogs(e.toString());
			System.out.println("DB_MANAGER:ERROR:Unable to execute SQL request!!!");
			System.out.println(e);
		}
		
		return false;
	}
}
