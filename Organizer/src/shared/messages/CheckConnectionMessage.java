package shared.messages;

public class CheckConnectionMessage extends Message{
	
	public CheckConnectionMessage(){
		this.setRequesType(RequestType.CHECK_CONNECTION);
		this.setDataUpdated(false);
	}
}
