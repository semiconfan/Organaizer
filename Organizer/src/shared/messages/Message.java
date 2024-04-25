package shared.messages;

import java.io.Serializable;

/**
 * Main abstract class for all messages
 */
public abstract class Message implements Serializable{
	
	private static final long serialVersionUID = 8160835991630623472L;
	private boolean isDataUpdated = false;

	/**
	 *  Type of request,
	 *  can be used to get String representation of request type
	 */
	private RequestType requesType = RequestType.UNKNOWN;
	
	/**
	 *  State of response, 
	 *  can be used to get String representation of response state
	 */
	private ResponseState responseState = ResponseState.UNKNOWN;
	
	
	/**
	 * Returns type of request for this object
	 * @return RequestType Type of request, depends on what kind of request will be initialized
	 */
	public RequestType getRequesType() {
		return requesType;
	}
	
	/**
	 * Sets RequestType for this object
	 * Be accurate RequestType shall only be changed during creation of new message object
	 * changing RequestType after object is created might lead to problems in interpreting type
	 * of the object
	 * @param requesType
	 */
	protected void setRequesType(RequestType requesType) {
		this.requesType = requesType;
	}
	
	/**
	 * Returns state of response for this object
	 * @return ResponseState State of response, can be UNKNOWN, SUCCESSFULL, ERROR
	 */
	public ResponseState getResponseState() {
		return responseState;
	}
	
	/**
	 * Sets ResponseState for this object
	 * Be accurate ResponseState represents state of request thus 
	 * shall only be changed during execution of request message object
	 * changing ResponseState after object is sent back might lead to problems in catching errors
	 * @param responseState
	 */
	protected void setResponseState(ResponseState responseState) {
		this.responseState = responseState;
	}
	
	/**
	 * Returns update state for data
	 * @return isDataUpdated if data was changed on server returns true
	 */
	public boolean isDataUpdated() {
		return isDataUpdated;
	}
	
	/**
	 * Sets update state for data set to false if data is not needed anymore
	 * @param isDataUpdated value to be set to isDataUpdated field
	 */
	public void setDataUpdated(boolean isDataUpdated) {
		this.isDataUpdated = isDataUpdated;
	}
	
	/**
	 * Sets message into error state
	 * must be called within a catch clause 
	 */
	public void error() {
		this.setDataUpdated(true);
		this.setResponseState(ResponseState.ERROR);
	}
	
	/**
	 * Sets message into success state
	 * must be called after executing request ended successfully
	 */
	public void success() {
		this.setDataUpdated(true);
		this.setResponseState(ResponseState.SUCCESS);
	}
	
	/**
	 * Makes a shallow copy of newMessage parameter into the object
	 * that this function has been called on
	 * @param newMessage Message to be copying data from
	 */
	public void shallowCopy(Message newMessage) {
		this.setDataUpdated(true);
		this.setRequesType(newMessage.getRequesType());
		this.setResponseState(newMessage.getResponseState());
	}
	

	@Override
	public String toString() {
		String stringObj = "Message object:\n";
		stringObj += "Is data updated:" + this.isDataUpdated() + "\n";
		stringObj += "Request type:" + this.getRequesType().getAsString() + "\n";
		stringObj += "Response state:" + this.getResponseState().getAsString();
		return stringObj;
	}
	
}
