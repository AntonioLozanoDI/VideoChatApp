package application.services.base;

import application.services.Service;

public abstract class AbstractPlayerService extends Service {
	
	protected boolean playerStarted;
	
	protected boolean pause;
	
	protected AbstractPlayerService() {
		super();
		addServiceTask(this::play);
	}

	protected abstract void play();
	
	public abstract void startPlayer();

	public final void pausePlayer() {
		pause = true;
	}
	
	public final void resumePlayer() {
		pause = false;
	}
}
