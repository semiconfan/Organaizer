package shared.messages;

/**
 * Types of request
 */
public enum RequestType {
	
	UNKNOWN {
		@Override
		public String getAsString() {
			return "UNKNOWN";
		}
	},
	CHECK_CONNECTION {
		@Override
		public String getAsString() {
			return "CHECK_CONNECTION";
		}
	}, 
	SIGN_IN {
		@Override
		public String getAsString() {
			return "SIGN_IN";
		}
	},
	SIGN_UP {
		@Override
		public String getAsString() {
			return "SIGN_UP";
		}
	};
	
	public String getAsString() {
		return "UNKNOWN";
	}
}
