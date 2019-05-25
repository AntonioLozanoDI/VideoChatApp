package application.services.base;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import application.services.Service;
import application.services.exception.ServiceNotConfiguredException;
import utils.logging.LoggingUtils;

public abstract class AbstractStreamingReceiverService extends Service {

	public interface ConnectionListener {
		void onClientConnected();
	}

	private List<ConnectionListener> listeners = new ArrayList<>();
	
	protected byte[] data;
	protected InetAddress remote;
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
			remote = Inet4Address.getByName(serverAddress);
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
		
		new Thread(() ->  {
			String trace = null;
			DatagramPacket peticion = new DatagramPacket(buff, buff.length,remote,port);
			try {
				socketUDP.send(peticion);
				connected = true;
				
				listeners.forEach(ConnectionListener::onClientConnected);
				trace = getClass().getSimpleName() + " connected to " + remote.getHostAddress() + ":" + port;
			} catch (IOException e) {
				trace = LoggingUtils.getStackTrace(e);
			}
			System.out.println(trace);
		}).start();
	}

	public byte[] getData() {
		return data == null ? new byte[23000] : data;
	}
	
	public void startReceiver() {
		System.out.println(remote);
		try {
			sendPetition();
			resumeService();
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
		if(socketUDP != null)
			socketUDP.close();
		
		data = null;
		socketUDP = null;
		connected = false;
	}
	
	@Override
	public void status() {
		System.out.println(String.format("%s - data is null %s, connected %s, isStopped %s", getClass().getSimpleName(), data==null, connected, isStopped() ));
	}
	

	public void addConnectionListener(ConnectionListener l) {
		listeners.add(Objects.requireNonNull(l));
	}
}
