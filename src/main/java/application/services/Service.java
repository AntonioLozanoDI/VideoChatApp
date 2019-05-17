package application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.omg.Messaging.SyncScopeHelper;

import utils.logging.LoggingUtils;

public abstract class Service extends Thread {
	
	private static List<Service> services = new ArrayList<>();
	
	@FunctionalInterface
	public interface ServiceTask {
		public void call();
	}

	protected int INTERVAL;
	
	private boolean serviceStoped = true;
	
	private List<ServiceTask> serviceTasks;
	
	protected Service() {
		super();
		services.add(this);
		serviceTasks = new ArrayList<>();
	}

	public final void addServiceTask(ServiceTask serviceTask) {
		serviceTasks.add(Objects.requireNonNull(serviceTask));
	}
	
	public final void stopService() {
		serviceStoped = true;
		onStopedService();
	}
	
	public final void resumeService() {
		serviceStoped = false;
	}
	
	public boolean isRunning() {
		return isAlive() ? !isInterrupted() && !serviceStoped : false;
	}

	@Override
	public void run() {
		serviceStoped = false;
		while(!isInterrupted()) {
			if(!serviceStoped) {
				System.out.println("not stoped");
				try {
					if(!serviceTasks.isEmpty())
					serviceTasks.forEach(ServiceTask::call);
					Thread.sleep(INTERVAL);
				} catch (Exception e) {
					System.err.println(LoggingUtils.getStackTrace(e));
				}
			}
		}
	}
	
	protected abstract void onStopedService();

	protected void kill() {
		interrupt();
	}
	
	public static void stopServices() {
		services.forEach(Service::stopService);
		try {
			Thread.sleep(2000);
		} catch (Exception e) {}
		services.forEach(Service::kill);
		try {
			Thread.sleep(5000);
		} catch (Exception e) {}
		Thread.getAllStackTraces().keySet().forEach(System.out::println);;
	}
}
