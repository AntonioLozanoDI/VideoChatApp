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

	public void startPlayer() {
		if (!isAlive()) {
			start();
		}
		resumeService();
	}

	@Override
	public void status() {
		System.out.println(String.format("%s - paused %s, isStopped %s", getClass().getSimpleName(), pause, isStopped() ));
	}

	public final void pausePlayer() {
		pause = true;
	}

	public final void resumePlayer() {
		pause = false;
	}
}
