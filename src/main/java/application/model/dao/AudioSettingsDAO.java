package application.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.AudioSettingsModel;
import application.model.repository.AudioSettingsRepository;
import application.model.repository.DefaultAudioSettingsRepository;

public class AudioSettingsDAO {

	private static AudioSettingsDAO instance;

	private Map<Integer, AudioSettingsModel> settingsMap;
	
	private DefaultAudioSettingsRepository defaultSettingsRepository;
	
	private AudioSettingsRepository settingsRepository;

	private AudioSettingsDAO() {
		super();
		settingsMap = new HashMap<>();
		defaultSettingsRepository = new DefaultAudioSettingsRepository();
		settingsRepository = new AudioSettingsRepository();
		mapData();
	}

	private void mapData() {
		settingsMap.clear();
		List<AudioSettingsModel> data = settingsRepository.findAll();
		for(AudioSettingsModel audioSetting : data) {
			settingsMap.put(audioSetting.getId(), audioSetting);
		}
	}
	
	public static AudioSettingsDAO getInstance() {
		return instance == null ? instance = new AudioSettingsDAO() : instance;
	}

	public boolean hasConfiguredData() {
		return !settingsMap.isEmpty();
	}

	public AudioSettingsModel getDefault() {
		return settingsMap.get(defaultSettingsRepository.getDefault());
	}
	
	public void setDefault(AudioSettingsModel model) {
		AudioSettingsModel s = settingsMap.get(model.getId());
		if(s != null) 
			defaultSettingsRepository.setDefault(model);
		
	}
	
	public int getLastSettingId() {
		return settingsRepository.findLastSettingId();
	}
	
	public void saveAudioSetting(AudioSettingsModel model) {
		settingsRepository.insert(model);
		mapData();
	}
	
	public void updateAudioSetting(AudioSettingsModel model) {
		settingsRepository.update(model);
		mapData();
	}
	
	public void deleteAudioSetting(AudioSettingsModel model) {
		settingsRepository.delete(model);
		mapData();
	}
	
	public List<AudioSettingsModel> findAll(){
		return settingsRepository.findAll();
	}
}
