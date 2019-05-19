package application.services.base;

import application.services.Service;

public abstract class AbstractRecordingService extends Service {
		
	protected boolean pause;

	public AbstractRecordingService() {
		super();
		addServiceTask(this::capture);
	}
	
	protected abstract void capture();
	
	public void startRecorder() {
		resumeService();
		if(!isAlive()) 
			start();
	}

	public abstract void stopRecorder();

	public final void pauseRecorder() {
		pause = true;
	}

	public final void resumeRecorder() {
		pause = false;
	}
	
	@Override
	public void status() {
		System.out.println(String.format("%s - paused %s, isStopped %s", getClass().getSimpleName(), pause, isStopped() ));
	}
}
