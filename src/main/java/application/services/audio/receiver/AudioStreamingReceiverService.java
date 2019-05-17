package application.services.audio.receiver;

import java.io.IOException;
import java.net.DatagramPacket;

import application.services.base.AbstractStreamingReceiverService;

public class AudioStreamingReceiverService extends AbstractStreamingReceiverService {

	private static AudioStreamingReceiverService instance;

	private AudioStreamingReceiverService() {
		super();
		port = 48550;
	}

	public static AudioStreamingReceiverService getInstance() {
		return instance == null ? instance = new AudioStreamingReceiverService() : instance;
	}
	
	@Override
	protected void onReceive() {
		if(configured && connected) {
			data = new byte[1024];
			DatagramPacket peticion = new DatagramPacket(data, data.length);
			try {
				socketUDP.receive(peticion);
				data = peticion.getData();
			} catch (IOException e) {
				System.err.println("error receiving data");
			}
		}
	}

}
