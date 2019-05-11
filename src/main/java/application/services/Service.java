package application.services;

import java.util.ArrayList;
import java.util.List;

public abstract class Service extends Thread {
	
	@FunctionalInterface
	public interface ServiceTask {
		public void call();
	}

	protected int INTERVAL;
	
	private List<ServiceTask> serviceTasks;
	
	protected Service() {
		super();
		serviceTasks = new ArrayList<>();
	}

	@Override
	public void run() {
		while(!interrupted()) {
			if(!serviceTasks.isEmpty())
				serviceTasks.forEach(ServiceTask::call);
			try {
				Thread.sleep(INTERVAL);
			} catch (Exception e) {}
		}
	}
}
