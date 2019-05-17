package application.services.audio.sender;

import java.io.IOException;
import java.net.DatagramPacket;

import application.services.base.AbstractStreamingSenderService;

public class AudioStreamingSenderService extends AbstractStreamingSenderService {
	
	private static AudioStreamingSenderService instance;
	private boolean read;

	private AudioStreamingSenderService() {// TODO handle multiple clients
		super();
		port = 48550;
		socketUDP = initSocket();
	}

	public static AudioStreamingSenderService getInstance() {
		return instance == null ? instance = new AudioStreamingSenderService() : instance;
	}

	public void setAudioData(byte[] audioData) {
		data = audioData;
		read=false;
	}
	
	protected void publish() {
		if (clientConnected) {
			// Construimos el DatagramPacket para enviar la respuesta
			if (!read) {
				DatagramPacket respuesta = new DatagramPacket(data, data.length, peticion.getAddress(), peticion.getPort());
				try {
					// Enviamos la respuesta
					socketUDP.send(respuesta);
				} catch (IOException e) {
					e.printStackTrace();
				}
				read= true;
			}
		}
	}
}
