package shared.messages;

/**
 * Types of response
 */
public enum ResponseState {
	
	UNKNOWN {
		@Override
		public String getAsString() {
			return "UNKNOWN";
		}
	},
	
	SUCCESS {
		@Override
		public String getAsString() {
			return "SUCCESS";
		}
	}, 
	
	NO_SUCH_ENTRY_IN_DATABASE {
		@Override
		public String getAsString() {
			return "NO_SUCH_ENTRY_IN_DATABASE";
		}
	}, 
	
	LOGIN_AND_PASSWORD_ARE_TOO_SMALL {
		@Override
		public String getAsString() {
			return "LOGIN_AND_PASSWORD_ARE_TOO_SMALL";
		}
	}, 
	
	SUCH_LOGIN_ALREADY_EXIST {
		@Override
		public String getAsString() {
			return "SUCH_LOGIN_ALREADY_EXIST";
		}
	},
	
	ERROR {
		@Override
		public String getAsString() {
			return "ERROR";
		}
	}; 
	
	public String getAsString() {
		return "UNKNOWN";
	}
}
