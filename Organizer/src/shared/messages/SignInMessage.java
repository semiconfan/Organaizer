package shared.messages;

/**
 * Class that keeps data for Sign in 
 */
public class SignInMessage extends Message{
	
	private static final long serialVersionUID = -240121629482442769L;
	
	private String login = ""; 
	private String password = "";
	private int ID = -1; // Returned id of account (must be <=0)
	
	/**
	 * Create sign in message
	 * @param login login to be send to server
	 * @param password password to be send to server
	 */
	public SignInMessage(String login, String password){
		this.setRequesType(RequestType.SIGN_IN);
		this.setDataUpdated(false);
		this.setLogin(login);
		this.setPassword(password);
		this.setID(-1);
	}
	
	/**
	 * @return login login stored in this object
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * @param login login to be set during initialization
	 * should only be called during initialization
	 */
	private void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * @return password password stored in this object
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password password to be set during initialization
	 * should only be called during initialization
	 */
	private void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get id of an account
	 * @return ID of an account -1 if there is no such account
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Set id of an account
	 * @param ID ID of logged in account
	 */
	private void setID(int ID) {
		this.ID = ID;
	}
	
	/**
	 * Set response data for SignInMessage message
	 * @param ID ID of logged in account
	 */
	public void setResponseData(int ID) {
		this.setDataUpdated(true);
		this.setID(ID);
	}
	
	public void setNoSuchEntry() {
		this.setDataUpdated(true);
		this.setResponseState(ResponseState.NO_SUCH_ENTRY_IN_DATABASE);
	}

	@Override
	public void shallowCopy(Message newMessage) {
		this.setDataUpdated(newMessage.isDataUpdated());
		this.setRequesType(newMessage.getRequesType());
		this.setResponseState(newMessage.getResponseState());
		
		if(newMessage instanceof SignInMessage) {
			SignInMessage newMessageCasted = (SignInMessage) newMessage;
			this.setID(newMessageCasted.getID());
			this.setLogin(newMessageCasted.getLogin());
			this.setPassword(newMessageCasted.getPassword());
		} else {
			System.out.println("Error: During copying messages error occured message and newMessage have different types!!!");
		}
	}
	
	@Override
	public String toString() {
		String stringObj = super.toString() + "\n";
		stringObj += "Login:" + this.getLogin() + "\n";
		stringObj += "Password:" + this.getPassword() + "\n";
		stringObj += "ID:" + this.ID;
		return stringObj;
	}
}
