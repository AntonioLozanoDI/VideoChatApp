package application.services.base;

import application.services.Service;

public abstract class AbstractRecordingService extends Service {
		
	protected boolean pause;

	public AbstractRecordingService() {
		super();
		addServiceTask(this::capture);
	}
	
	protected abstract void capture();
	
	public abstract void startRecording();

	public abstract void stopRecording();

	public final void pauseRecording() {
		pause = true;
	}

	public final void resumeRecording() {
		pause = false;
	}
}
