package application.controller.session;

import application.model.ProfileModel;

public class SessionController {

	private static SessionController instance;
	
	private ProfileModel activeUser;
	
	private String serverAddress;

	public static SessionController getInstance() {
		return instance == null ? instance = new SessionController() : instance;
	}

	public void setLoggerUser(ProfileModel activeUser) {
		this.activeUser = activeUser;
	}
	
	public ProfileModel getLoggerUser() {
		return activeUser;
	}
	
	public boolean isServerConfigured() {
		return serverAddress != null;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
}
