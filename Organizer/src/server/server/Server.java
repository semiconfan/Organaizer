package server.server;

import java.io.*;
import java.net.*;

import server.manager.DatabaseManager;
import server.manager.LogManager;
import shared.messages.*;


public class Server {
	private final short port; // Port that is listening
	private ServerSocket serverSocket; // Server socket that listens for requests
	private Socket socket; // Socket that creates connection between client and server via streams
	private Message message; // Wrapper class for info transferred between client and server
	private ObjectOutputStream objectOut; // Output stream
	private ObjectInputStream objectIn; // Input stream
	private DatabaseManager databaseManager;
	private LogManager logManager;
	
	/**
	 * Creates a server that listens on port and IP which specified in SharedOptions class
	 * @param port a port that this server will be attached to 
	 */
	public Server(short port) {
		this.setLogManager(new LogManager());
		this.getLogManager().writeToLogs("SERVER:Trying to launch a server...");
		System.out.println("SERVER:Trying to launch a server..."); 
		this.port = port; // Set port
		
		try {
			this.setServerSocket(new ServerSocket(this.port)); // Create ServerSocket
			this.getLogManager().writeToLogs("SERVER:Server socket created successfully");
			System.out.println("SERVER:Server socket created successfully");
		} catch (IOException e) {
			this.getLogManager().writeToLogs("SERVER:ERROR:Could not create a server socket!!!");
			this.getLogManager().writeToLogs(e.toString());
			System.out.println("SERVER:ERROR:Could not create a server socket!!!");
			System.out.println(e);
			
		}
		
		databaseManager = new DatabaseManager(this.getLogManager());
		this.getLogManager().writeToLogs("SERVER:Server is launched");
	}
	
	// Getters and setters
	private ServerSocket getServerSocket() {
		return serverSocket;
	}

	private void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	private Socket getSocket() {
		return socket;
	}

	private void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	// End getters and setters
	
	private ObjectOutputStream getObjectOut() {
		return objectOut;
	}

	private void setObjectOut(ObjectOutputStream objectOut) {
		this.objectOut = objectOut;
	}

	private ObjectInputStream getObjectIn() {
		return objectIn;
	}

	private void setObjectIn(ObjectInputStream objectIn) {
		this.objectIn = objectIn;
	}

	private DatabaseManager getDatabaseManager() {
		return databaseManager;
	}

	private void setDatabaseManager(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}

	private LogManager getLogManager() {
		return logManager;
	}

	private void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

	/**
	 * Here server waits for next request
	 * when request received execution continues
	 */
	public void waitForNextRequest() {
		
		try {
			this.setSocket(this.getServerSocket().accept()); // Listen for request and continue execution when request comes
			
		} catch (IOException e) {
			this.getLogManager().writeToLogs("SERVER:ERROR:Could not wait for next request!!!");
			this.getLogManager().writeToLogs(e.toString());
			System.out.println(e);
			System.out.println("SERVER:ERROR:Could not wait for next request!!!");
		} 
	}
	
	/**
	 * When request received execute if possible
	 */
	public void executeRequest() {
		try {
			// Get object from client through sockets getInputStream() and ObjectInputStream
			this.receiveMessageFromClient(); // Get object from client through sockets getInputStream() and ObjectInputStream
			
			// Routing and executing request
			if(this.getMessage() instanceof CheckConnectionMessage) { // Deal with CheckConnectionMessage here
				CheckConnectionMessage message = (CheckConnectionMessage) this.getMessage();
				this.setMessage(message);
				this.getMessage().success();
				
			} else if (this.getMessage() instanceof SignInMessage) { // Deal with SignInMessage here
				System.out.println("SERVER:SignInMessage received");
				this.getLogManager().writeToLogs("SERVER:SignInMessage received");
				SignInMessage message = (SignInMessage) this.getMessage();
				int userID = this.getDatabaseManager().tryToGetIdBySignIn(message);
				if(userID > 0) { // If ID was found in database
					message.setResponseData(userID);
					message.success();
				} else if(userID == -1) { // If no entry in database
					message.setResponseData(userID);
					message.setNoSuchEntry();
				} else { // Unknown state
					message.setResponseData(userID);
					message.error();
				}
				this.setMessage(message);
				this.getLogManager().writeToLogs(message.toString());
			} else if (this.getMessage() instanceof SignUpMessage) { // Deal with SignUpMessage here
				System.out.println("SERVER:SignUpMessage received");
				this.getLogManager().writeToLogs("SERVER:SignUpMessage received");
				SignUpMessage message = (SignUpMessage) this.getMessage();
				
				if(message.getLogin().length() < 3 || message.getPassword().length() < 3) { // If login or password are too small
					message.loginOrPasswordAreTooSmall();
					message.setResponceData(-1);
				} else { // if login or password are normal
					if (this.getDatabaseManager().tryToCreateSignUpRecord(message)) { // Try to create SignUpRecord
						message.success();
						message.setResponceData(1);
					} else { // Could not create record
						message.suchLoginAlreadyExist();
						message.setResponceData(-1);
					}
				} 

				this.setMessage(message);
				this.getLogManager().writeToLogs("SERVER:Message received:");
				this.getLogManager().writeToLogs(message.toString());
			}
			else {
				this.getMessage().error();
				this.getLogManager().writeToLogs("SERVER:ERROR:Unknown message received!!!");
				this.getLogManager().writeToLogs(message.toString());
			}
			//

			this.sendMessageToClient(); // Sending response to request back to client 
			this.close(); // Close streams
			
		} 
		catch (ClassNotFoundException e) {
			this.getMessage().error();
			this.getLogManager().writeToLogs("SERVER:ERROR:Could not serialize an object!!!");
			this.getLogManager().writeToLogs(message.toString());
			this.getLogManager().writeToLogs(e.toString());
			System.out.println("SERVER:ERROR:Could not serialize an object!!!");
			System.out.println(e);
		}
		catch (IOException e) {
			this.getMessage().error();
			this.getLogManager().writeToLogs("SERVER:ERROR:IO error occured!!!");
			this.getLogManager().writeToLogs(message.toString());
			this.getLogManager().writeToLogs(e.toString());
			System.out.println("SERVER:ERROR:IO error occured!!!");
			System.out.println(e);
		}
			
	}
	
	/**
	 * Receives message from client and sets
	 * and makes a shallow copy from servers message
	 * back to message field of this class
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void receiveMessageFromClient() throws IOException, ClassNotFoundException {
		
		if(this.getSocket() == null) {
			throw new IOException("REQUEST_MANAGER:ERROR:Socket is null!!!");
		}
		
		this.setObjectIn(new ObjectInputStream(this.getSocket().getInputStream()));
		this.setMessage((Message) this.getObjectIn().readObject()); // Deserialize object
	}
	
	/**
	 * Sends message field of this class back to client
	 * @throws IOException
	 */
	private void sendMessageToClient() throws IOException {
		if(this.getSocket() == null) {
			throw new IOException("SERVER:ERROR:Socket is null!!!");
		}
		
		if(this.getObjectIn() == null) {
			throw new IOException("REQUEST_MANAGER:ERROR:In stream is closed!!!");
		}
		
		// Serializes and sends message to client
		this.setObjectOut(new ObjectOutputStream(this.getSocket().getOutputStream()));// Open stream to send object to client
		this.getObjectOut().writeObject(this.getMessage()); // Write object to the stream
		this.getObjectOut().flush(); // Send info in stream to the client
		//
	}
	
	/**
	 * Closes all streams
	 * @throws IOException
	 */
	private void close() throws IOException {
		if(this.getSocket() != null) {
			this.getSocket().close();
		}
		
		if(this.getObjectOut() == null) {
			this.getObjectOut().close();
		}
		
		if(this.getObjectIn() == null) {
			this.getObjectIn().close();
		}	
	}
}
