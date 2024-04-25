package client.manager;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import shared.messages.*;
import shared.options.Options;

public class RequestManager extends Thread{
	
	private Message message; // Call back message
	private Socket socket; // Socket to establish connection between client and server
	ObjectOutputStream objectOut; // Output stream
	ObjectInputStream objectIn; // Input stream
	
	public RequestManager(Message message) {
		this.setMessage(message);
	}
	
	@Override
	public void run() {
		/*
		 * For preventing span into console separate CheckConnectionMessage
		 * from others
		 */
		if(message instanceof CheckConnectionMessage) {
			try {
				// Open connection to server via socket
				this.openSocketConnection(Options.IP_USED, Options.PORT_USED); // Open connection to server via socket
				this.sendMessageToServer(); // Serializes and sends message to server				
				this.receiveMessageFromServer(); // Receives serialized object from server and deserializes it
				this.close(); // Close all streams
				//

			} catch (ClassNotFoundException e) {
				this.getMessage().error();
				e.printStackTrace();
				
			} catch (IOException e) {
				this.getMessage().error();
				System.out.println(e);
			}
		} else {		// If message is not CheckConnectionMessage
			try {
				System.out.println("REQUEST_MANAGER:Attempt to send data to server...");
				this.openSocketConnection(Options.IP_USED, Options.PORT_USED); // Open connection to server via socket
				this.sendMessageToServer(); // Serializes and sends message to server
				System.out.println("REQUEST_MANAGER:Object is sent to server:");
				System.out.println("REQUEST_MANAGER:Trying to get answear from server...");
				this.receiveMessageFromServer(); // Receives serialized object from server and deserializes it
				System.out.println("REQUEST_MANAGER:Object successfully received from server");
				System.out.println("REQUEST_MANAGER:Data received from server:");
				System.out.println(this.getMessage()); // Prints to console received from server object
				this.close(); // Close all streams
				//

			} catch (ClassNotFoundException e) {
				System.out.println("Could not send message to server!!!");
				this.getMessage().error();
				e.printStackTrace();
				
			} catch (IOException e) {
				System.out.println("Could not send message to server!!!");
				this.getMessage().error();
				System.out.println(e);
			}
		}
		
	}
	
	// Getters and setters
	private Message getMessage() {
		return message;
	}

	private void setMessage(Message message) {
		this.message = message;
	}
	
	private Socket getSocket() {
		return socket;
	}

	private void setSocket(Socket socket) {
		this.socket = socket;
	}

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
	// End getters and setters
	
	/**
	 * Opens socket connection to server 
	 * @param IP IP of server
	 * @param port IP of server
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void openSocketConnection(String IP, short port) throws UnknownHostException, IOException {
		this.setSocket(new Socket(IP, port));
	}
	
	/**
	 * Sends message field of this class to server
	 * @throws IOException
	 */
	private void sendMessageToServer() throws IOException {
		if(this.getSocket() == null) {
			throw new IOException("REQUEST_MANAGER:ERROR:Socket is null!!!");
		}
		
		// Serializes and sends message to server
		this.setObjectOut(new ObjectOutputStream(this.getSocket().getOutputStream()));// Open stream to send object to server
		this.getObjectOut().writeObject(this.getMessage()); // Write object to the stream
		this.getObjectOut().flush(); // Send info in stream to the server
		//
	}
	
	/**
	 * Receives message back from server and sets
	 * and makes a shallow copy from servers message
	 * back to message field of this class
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void receiveMessageFromServer() throws IOException, ClassNotFoundException {
		if(this.getSocket() == null) {
			throw new IOException("REQUEST_MANAGER:ERROR:Socket is null!!!");
		}
		
		if(this.getObjectOut() == null) {
			throw new IOException("REQUEST_MANAGER:ERROR:Out stream is closed!!!");
		}
		
		this.setObjectIn(new ObjectInputStream(socket.getInputStream()));
		this.getMessage().shallowCopy((Message) this.getObjectIn().readObject());
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
