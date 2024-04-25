package shared.options;

public class Options {
	public static final short PORT_USED = 8000; // Port used for connection between client and server
	public static final String IP_USED = "127.0.0.1"; // Port used for connection to server
	public static final int CHECK_FOR_UPDATED_DATA_INTERVAL = 100; // How often should data be checked for updates in milliseconds
//	CHECK_FOR_UPDATED_DATA_INTERVAL must be < CHECK_FOR_CONNECTION_INTERVAL at least 2 times
	public static final int CHECK_FOR_CONNECTION_INTERVAL = 200; // How often should data be checked for updates in milliseconds
	public static final int MAX_TIME_RESPOND = 10000; // Maximum time for respond before request will be rejected
}
