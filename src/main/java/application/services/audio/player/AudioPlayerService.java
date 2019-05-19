package application.services.audio.player;

import javax.sound.sampled.SourceDataLine;

import application.services.audio.AudioHandler;
import application.services.base.AbstractPlayerService;

public class AudioPlayerService extends AbstractPlayerService implements AudioHandler.Player {
		
	private static AudioPlayerService instance;

	private SourceDataLine sourceLine;

	private byte[] audioData;
	
	private boolean read;

	private AudioPlayerService() {
		super();
	}

	public static AudioPlayerService getInstance() {
		return instance == null ? instance = new AudioPlayerService() : instance;
	}

	@Override
	public void setSourceLine(SourceDataLine line) {
		sourceLine = line;
	}
	
	public synchronized void setData(byte[] data) {
		this.audioData = data;
		read = true;
	}
	
	@Override
	protected synchronized void play() {
		if(!pause && audioData != null && read) { 
			sourceLine.write(audioData, 0, audioData.length);
			read = false;
		}
	}
	
	@Override
	public void startPlayer() {
		try {
			sourceLine.start();
			super.startPlayer();
		} catch (Exception e) {}
	}
	
	@Override
	protected void onStopedService() {
		sourceLine.stop();
		read = false;
	}
}
