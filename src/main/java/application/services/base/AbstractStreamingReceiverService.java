package application.services.base;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import application.services.Service;
import application.services.exception.ServiceNotConfiguredException;
import utils.logging.LoggingUtils;

public abstract class AbstractStreamingReceiverService extends Service {

	protected byte[] data;
	protected InetAddress server;
	protected DatagramSocket socketUDP;
	protected int port;
	protected boolean configured;
	protected boolean connected;
	
	
	protected AbstractStreamingReceiverService() {
		super();
		addServiceTask(this::receive);
	}

	protected void receive() {
		try {
			onReceive();
		} catch (Exception e) {
			System.out.println(LoggingUtils.getStackTrace(e));
		}
	}
	
	protected abstract void onReceive();
	
	public final void setServerData(String serverAddress) {
		try {
			server = Inet4Address.getByName(serverAddress);
			configured = port != 0;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private void sendPetition() throws IOException {
		if(!configured)
			throw new ServiceNotConfiguredException();
		
		socketUDP = new DatagramSocket();
		byte[] buff = new byte[1024];
		DatagramPacket peticion = new DatagramPacket(buff, buff.length,server,port);
		socketUDP.send(peticion);
		connected = true;
		System.out.println(getClass().getSimpleName() + " connected to " + server.getHostAddress() + ":" + port);
	}

	public byte[] getData() {
		return data;
	}
	
	public void startReceiving() {
		try {
			sendPetition();
			if(!isAlive()) {
				start();
			}
		} catch (Exception e) {
			System.err.println("error sending");
			System.out.println(LoggingUtils.getStackTrace(e));
		}
	}
	
	@Override
	protected void onStopedService() {
		server = null;
		socketUDP.close();
		socketUDP = null;
		connected = false;
	}
}
