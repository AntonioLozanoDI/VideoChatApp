package application.controller.session;

import com.diproject.commons.model.User;
import com.diproject.commons.utils.ws.WebSocketClient;

import application.controller.ws.VideoChatHandler;

public class SessionController {

	private static SessionController instance;
	
	private User activeUser;
	
	private String serverAddress;
	
	private String selfClientAddress;
	
	private WebSocketClient wsc;

	public static SessionController getInstance() {
		return instance == null ? instance = new SessionController() : instance;
	}

	public void setLoggerUser(User activeUser) {
		this.activeUser = activeUser;
	}
	
	public User getLoggerUser() {
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

	public String getSelfClientAddress() {
		return selfClientAddress;
	}

	public void setSelfClientAddress(String selfClientAddress) {
		this.selfClientAddress = selfClientAddress;
	}
	
	public void setClient(WebSocketClient wsc) {
		this.wsc = wsc;
		wsc.setPayloadHandler(VideoChatHandler.getInstance());
		wsc.connect();
	}
	
	public WebSocketClient getClient() {
		return wsc;
	}
}
