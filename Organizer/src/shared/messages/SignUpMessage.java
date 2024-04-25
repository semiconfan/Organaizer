package shared.messages;

/**
 * Class that keeps data for Sign up 
 */
public class SignUpMessage extends Message{
	
	private static final long serialVersionUID = 5012710827456737559L;
	
	private String login = "";
	private String password = "";
	private int ID = -1;
	
	/**
	 * Create sign in message
	 * @param login login to be send to server
	 * @param password password to be send to server
	 */
	public SignUpMessage(String login, String password){
		this.setRequesType(RequestType.SIGN_UP);
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
	 * @return ID of an account -1 if could not create such account
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
	 * Set response data for SignUpMessage message
	 * @param ID ID of logged in account
	 */
	public void setResponceData(int ID) {
		this.setDataUpdated(true);
		this.setID(ID);
	}
	
	public void suchLoginAlreadyExist() {
		this.setDataUpdated(true);
		this.setResponseState(ResponseState.SUCH_LOGIN_ALREADY_EXIST);
	}
	
	public void loginOrPasswordAreTooSmall() {
		this.setDataUpdated(true);
		this.setResponseState(ResponseState.LOGIN_AND_PASSWORD_ARE_TOO_SMALL);
	}

	@Override
	public void shallowCopy(Message newMessage) {
		this.setDataUpdated(newMessage.isDataUpdated());
		this.setRequesType(newMessage.getRequesType());
		this.setResponseState(newMessage.getResponseState());
		
		if(newMessage instanceof SignUpMessage) {
			SignUpMessage newMessageCasted = (SignUpMessage) newMessage;
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
