package application.services.audio.recorder;

import javax.sound.sampled.TargetDataLine;

import application.services.audio.AudioHandler;
import application.services.base.AbstractRecordingService;

public class AudioRecordingService extends AbstractRecordingService implements AudioHandler.Recorder {

	private static AudioRecordingService instance;

	private TargetDataLine targetLine;

	private byte[] audioData;

	private AudioRecordingService() {
		super();
		audioData = new byte[1024];
	}

	public static AudioRecordingService getInstance() {
		return instance == null ? instance = new AudioRecordingService() : instance;
	}

	@Override
	public void setTargetLine(TargetDataLine line) {
		targetLine = line;
	}

	@Override
	protected void capture() {
		byte[] buff = pause ? new byte[1024] : audioData;
		if (!pause)
			targetLine.read(buff, 0, buff.length);

		audioData = buff;
	}

	public byte[] getAudioData() {
		return audioData;
	}

	@Override
	public void startRecording() {
		try {
			targetLine.start();
			
			if(!isAlive()) 
				start();
			pause = false;
		} catch (Exception e) {}
	}

	@Override
	public void stopRecording() {
		pause = false;
	}

	@Override
	protected void onStopedService() {
		targetLine.stop();
	}
}
