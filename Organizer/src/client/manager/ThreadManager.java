package client.manager;

import javax.swing.Timer;

import shared.messages.CheckConnectionMessage;
import shared.messages.Message;

public class ThreadManager {
	private long checkConnectionMessageThreadStartTimeInMilliseconds;
	private long messageThreadStartTimeInMilliseconds;
	
	private RequestManager checkConnectionThread;
	private RequestManager messageThread;
	
	
	public ThreadManager(CheckConnectionMessage message) {
		this.sendMessage(message);
	}
	
	private long getCheckConnectionMessageThreadStartTimeInMilliseconds() {
		return checkConnectionMessageThreadStartTimeInMilliseconds;
	}

	private void setCheckConnectionMessageThreadStartTimeInMilliseconds(
			long checkConnectionMessageThreadStartTimeInMilliseconds) {
		this.checkConnectionMessageThreadStartTimeInMilliseconds = checkConnectionMessageThreadStartTimeInMilliseconds;
	}

	private long getMessageThreadStartTimeInMilliseconds() {
		return messageThreadStartTimeInMilliseconds;
	}

	private void setMessageThreadStartTimeInMilliseconds(long messageThreadStartTimeInMilliseconds) {
		this.messageThreadStartTimeInMilliseconds = messageThreadStartTimeInMilliseconds;
	}

	private RequestManager getCheckConnectionThread() {
		return checkConnectionThread;
	}

	private void setCheckConnectionThread(RequestManager checkConnectionThread) {
		this.checkConnectionThread = checkConnectionThread;
	}

	private RequestManager getMessageThread() {
		return messageThread;
	}

	private void setMessageThread(RequestManager messageThread) {
		this.messageThread = messageThread;
	}
	
	public long getCheckConnectionThreadDuration() {
        return System.currentTimeMillis() - getCheckConnectionMessageThreadStartTimeInMilliseconds();
    }
	
	public long getMessageThreadDuration() {
        return System.currentTimeMillis() - getMessageThreadStartTimeInMilliseconds();
    }

	public boolean isCheckConnectionMessageThreadAlive() {
		return this.getCheckConnectionThread().isAlive();
	}
	
	public void sendMessage(Message message) {
		if(message instanceof CheckConnectionMessage) {
			this.setCheckConnectionMessageThreadStartTimeInMilliseconds(System.currentTimeMillis());
			this.setCheckConnectionThread(new RequestManager(message));
			this.getCheckConnectionThread().start();
		} else {
			this.setMessageThreadStartTimeInMilliseconds(System.currentTimeMillis());
			this.setMessageThread(new RequestManager(message));
			this.getMessageThread().start();
		}
	}
	
}
