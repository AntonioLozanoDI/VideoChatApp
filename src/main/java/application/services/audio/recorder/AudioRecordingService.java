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
	public void startRecorder() {//TODO remove traces
		System.out.println(String.format("BEFORE: pause %s, isAlive %s, lineIsRunning %s", pause, isAlive(),targetLine.isRunning()));
		try {
			pause = false;
			targetLine.start();
			super.startRecorder();
		} catch (Exception e) {
			System.err.println(LoggingUtils.getStackTrace(e));
		}
		System.out.println(String.format("AFTER: pause %s, isAlive %s, lineIsRunning %s", pause, isAlive(),targetLine.isRunning()));
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(String.format("AFTER-2: pause %s, isAlive %s, lineIsRunning %s", pause, isAlive(),targetLine.isRunning()));
			}
		}).start();
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
