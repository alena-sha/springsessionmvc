package conversation;

public class Conversation {
	private String name;
	private String logoutUrl;
	private String switchAccountUrl;

	public Conversation(String name, String logoutUrl, String switchAccountUrl) {
		super();
		this.name = name;
		this.logoutUrl = logoutUrl;
		this.switchAccountUrl = switchAccountUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getSwitchAccountUrl() {
		return switchAccountUrl;
	}

	public void setSwitchAccountUrl(String switchAccountUrl) {
		this.switchAccountUrl = switchAccountUrl;
	}
}
