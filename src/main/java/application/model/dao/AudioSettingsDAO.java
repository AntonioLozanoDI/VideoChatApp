package application.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sp.dialogs.DialogBuilder;

import application.model.AudioSettingsModel;
import application.model.CaptureDeviceModel;
import application.model.repository.AudioCaptureDeviceRepository;
import application.model.repository.AudioSettingsRepository;
import application.model.repository.DefaultAudioSettingsRepository;

public class AudioSettingsDAO {

	private static AudioSettingsDAO instance;

	private Map<Integer, AudioSettingsModel> settingsMap;

	private Map<Integer, String> devicesMap;

	private DefaultAudioSettingsRepository defaultSettingsRepository;

	private AudioSettingsRepository settingsRepository;

	private AudioCaptureDeviceRepository captureDeviceRepository;

	private AudioSettingsDAO() {
		super();
		settingsMap = new HashMap<>();
		devicesMap = new HashMap<>();
		defaultSettingsRepository = new DefaultAudioSettingsRepository();
		settingsRepository = new AudioSettingsRepository();
		captureDeviceRepository = new AudioCaptureDeviceRepository();
		mapData();
	}

	private void mapData() {
		settingsMap.clear();

		List<AudioSettingsModel> data = settingsRepository.findAll();
		for (AudioSettingsModel audioSetting : data) {
			settingsMap.put(audioSetting.getId(), audioSetting);
		}

		List<CaptureDeviceModel> devices = captureDeviceRepository.findAll();
		for (CaptureDeviceModel device : devices) {
			devicesMap.put(device.getConfigId(), device.getDeviceName());
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
		if (s != null)
			defaultSettingsRepository.setDefault(model);

	}

	public int getLastSettingId() {
		return settingsRepository.findLastSettingId();
	}

	public void saveAudioSetting(AudioSettingsModel model) {
		settingsRepository.insert(model);
		model.setId(settingsRepository.findLastSettingId());

		if (!model.getCaptureDevice().isEmpty())
			captureDeviceRepository.insert(model);

		mapData();
	}

	public void updateAudioSetting(AudioSettingsModel model) {
		settingsRepository.update(model);

		String device = devicesMap.get(model.getId());
		if (device == null) {
			if (!model.getCaptureDevice().isEmpty())
				captureDeviceRepository.insert(model);
		} else {
			captureDeviceRepository.update(model);
		}

		mapData();
	}

	public void deleteAudioSetting(AudioSettingsModel model) {
		settingsRepository.delete(model);
		if (devicesMap.get(model.getId()) != null)
			captureDeviceRepository.delete(model);

		mapData();
	}

	public List<AudioSettingsModel> findAll() {
		return settingsRepository.findAll();
	}
}
