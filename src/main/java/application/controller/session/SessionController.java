package application.controller.session;

import com.diproject.commons.utils.ws.WebSocketClient;

import application.controller.ws.VideoChatHandler;
import application.model.ProfileModel;

public class SessionController {

	private static SessionController instance;
	
	private ProfileModel loggedOnProfile;
	
	private String serverAddress;
	
	private String selfClientAddress;
	
	private WebSocketClient wsc;

	public static SessionController getInstance() {
		return instance == null ? instance = new SessionController() : instance;
	}

	public void setLoggedUser(ProfileModel loggedOnProfile) {
		this.loggedOnProfile = loggedOnProfile;
	}
	
	public ProfileModel getLoggerUser() {
		return loggedOnProfile;
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

	public String getSelfClientAddress() {
		if(selfClientAddress==null)
			throw new RuntimeException("self address not configured");
		return selfClientAddress;
	}

	public void setSelfClientAddress(String selfClientAddress) {
		this.selfClientAddress = selfClientAddress;
	}
	
	public void setClient(WebSocketClient wsc) {
		this.wsc = wsc;
		VideoChatHandler ph = VideoChatHandler.getInstance();
		ph.setWebSocketClient(wsc);
		wsc.setPayloadHandler(ph);
		wsc.connect();
	}
	
	public WebSocketClient getClient() {
		return wsc;
	}
}
