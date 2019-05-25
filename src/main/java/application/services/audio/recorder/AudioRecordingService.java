package application.services.audio.recorder;

import javax.sound.sampled.TargetDataLine;

import application.services.audio.AudioHandler;
import application.services.base.AbstractRecordingService;
import utils.logging.LoggingUtils;

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
	public void startRecorder() {
		try {
			pause = false;
			targetLine.start();
			super.startRecorder();
		} catch (Exception e) {
			System.err.println(LoggingUtils.getStackTrace(e));
		}
	}

	@Override
	public void stopRecorder() {
		pause = true;
		if(targetLine != null)
			targetLine.stop();
	}

	@Override
	protected void onStopedService() {
		stopRecorder();
	}
}
