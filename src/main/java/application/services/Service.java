package application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import utils.logging.LoggingUtils;

public abstract class Service extends Thread {
	
	private static List<Service> services = new ArrayList<>();
	
	@FunctionalInterface
	public interface ServiceTask {
		public void call();
	}

	protected int INTERVAL;
	
	private boolean killed;
	
	private boolean serviceStoped = true;
	
	private List<ServiceTask> serviceTasks;
	
	protected Service() {
		super();
		services.add(this);
		serviceTasks = new ArrayList<>();
		this.setName(getClass().getSimpleName());
	}

	public final void addServiceTask(ServiceTask serviceTask) {
		serviceTasks.add(Objects.requireNonNull(serviceTask));
	}
	
	public final void stopService() {
		try {
			serviceStoped = true;
			onStopedService();
		} catch (Exception e) {
			System.out.println(LoggingUtils.getStackTrace(e));
		}
	}
	
	public final void killService() {
		stopService();
		killed = true;
	}
	
	public final void resumeService() {
		serviceStoped = false;
	}
	
	public boolean isRunning() {
		return isAlive() ? !isInterrupted() && !serviceStoped : false;
	}

	protected final boolean isStopped() {
		return serviceStoped;
	}
	
	@Override
	public void run() {
		while(!isInterrupted()) {
			if(!serviceStoped) {
				try {
					if(!serviceTasks.isEmpty())
					serviceTasks.forEach(ServiceTask::call);
					Thread.sleep(INTERVAL);
				} catch (Exception e) {
					System.err.println(getClass().getSimpleName() +  ": " + LoggingUtils.getStackTrace(e));
				}
			}
			if(killed)
				interrupt();
		}
	}
	
	protected abstract void onStopedService();

	public static void killServices() {
		services.forEach(Service::killService);
	}
	
	public abstract void status();
}
