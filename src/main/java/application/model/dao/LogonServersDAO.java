package application.model.dao;

import java.util.List;

import application.model.repository.LogonServersRepository;

public class LogonServersDAO {

	private static LogonServersDAO instance;
	
	private LogonServersRepository logonServersRepository;

	private LogonServersDAO() {
		logonServersRepository = new LogonServersRepository();
	}

	public static LogonServersDAO getInstance() {
		return instance == null ? instance = new LogonServersDAO() : instance;
	}
	
	public List<String> findAllServers() {
		return logonServersRepository.findAll();
	}
	
	public void saveServer(String server) {
		if(!logonServersRepository.exists(server))
			logonServersRepository.insert(server);
	}
	
	public void deleteServer(String server) {
		logonServersRepository.delete(server);
	}
}
