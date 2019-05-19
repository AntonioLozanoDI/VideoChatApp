package application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import com.sp.dialogs.DialogBuilder;

import application.model.AudioSettingsModel;
import application.model.dao.AudioSettingsDAO;
import application.services.audio.player.AudioPlayerService;
import application.services.audio.recorder.AudioRecordingService;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.logging.LoggingUtils;

public class AudioSettingsController {

	@FXML
	private ComboBox<AudioSettingsModel> comboSettings;
	@FXML
	private ComboBox<Mixer.Info> inMixers;
	@FXML
	private Label configLabel;
	@FXML
	private TextField inSampleRate;
	@FXML
	private TextField inBitSize;
	@FXML
	private TextField inChannels;
	@FXML
	private CheckBox inSigned;
	@FXML
	private CheckBox inBigEndian;
	@FXML
	private TextField outSampleRate;
	@FXML
	private TextField outBitSize;
	@FXML
	private TextField outChannels;
	@FXML
	private CheckBox outSigned;
	@FXML
	private CheckBox outBigEndian;

	private AudioPlayerService audioPlayer;

	private AudioRecordingService audioRecorder;

	private AudioSettingsDAO settingsDAO;

	private List<Mixer.Info> info = new ArrayList<>();

	private ObservableList<AudioSettingsModel> settings = FXCollections.observableArrayList();

	private AudioSettingsModel defaultSettings;

	private AudioSettingsModel lastSelectedSetting;
	
	private boolean dataUnsync;

	private Stage stage;

	private Info captureDevice;
	
	private ConfiguredLines lines;	

	@FXML
	private void initialize() {
		inMixers.setCellFactory(new Callback<ListView<Info>, ListCell<Info>>() {
			@Override
			public ListCell<Info> call(ListView<Info> param) {
				return new ListCell<Info>() {
					@Override
					protected void updateItem(Info item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null) {
							setText(null);
							setGraphic(null);
						} else {
							setText(item.getName());
						}
					}
				};
			}
		});
		
		comboSettings.setCellFactory(new Callback<ListView<AudioSettingsModel>, ListCell<AudioSettingsModel>>() {
			@Override
			public ListCell<AudioSettingsModel> call(ListView<AudioSettingsModel> param) {
				return new ListCell<AudioSettingsModel>() {
					@Override
					protected void updateItem(AudioSettingsModel item, boolean empty) {
						super.updateItem(item, empty);
						if (empty || item == null) {
							setText(null);
							setGraphic(null);
						} else {
							setText(item.toString());
						}
					}
				};
			}
		});

		inMixers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				captureDevice = newValue;
			}
		});

		comboSettings.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (!dataUnsync) {
				if (newValue != null) {
					setData(newValue);
				}
			} else {
				// showdialog
			}
		});

		
		audioPlayer = AudioPlayerService.getInstance();
		audioRecorder = AudioRecordingService.getInstance();
		settingsDAO = AudioSettingsDAO.getInstance();
		loadFromData();
		setMixers();
		setupChangesDetection();
	}

	private void loadFromData() {
		try {
			if (settingsDAO.hasConfiguredData()) {
				List<AudioSettingsModel> data = settingsDAO.findAll();
				defaultSettings = settingsDAO.getDefault();
				setDataInView(data);
			}
		} catch (Exception e) {
			System.out.println(LoggingUtils.getStackTrace(e));// TODO: handle exception
		}
	}

	private void setMixers() {
		ObservableList<Mixer.Info> outCaptureMixers = FXCollections.observableArrayList();
		Mixer.Info[] minfoSet = AudioSystem.getMixerInfo();
		for (Info mix : minfoSet) {
			if (mix.getDescription().contains("Capture")) {
				outCaptureMixers.add(mix);
				info.add(mix);
			}
		}
		inMixers.setItems(outCaptureMixers);
		inMixers.getSelectionModel().clearSelection();
	}

	private void setDataInView(List<AudioSettingsModel> l) {
		settings.addAll(l);
		comboSettings.setItems(settings);
		if (defaultSettings != null) {
			comboSettings.getSelectionModel().select(defaultSettings);
		} else {
			comboSettings.getSelectionModel().clearSelection();
		}
	}

	private void setData(AudioSettingsModel setting) {
		lastSelectedSetting = setting;
		
		configLabel.setText(setting.getConfigName());

		inSampleRate.setText(Float.toString(setting.getInSampleRate()));
		inBitSize.setText(String.valueOf(setting.getInBitSize()));
		inChannels.setText(String.valueOf(setting.getInChannels()));
		inSigned.setSelected(setting.getInSigned().booleanValue());
		inBigEndian.setSelected(setting.getInBigEndian().booleanValue());

		outSampleRate.setText(Float.toString(setting.getOutSampleRate()));
		outBitSize.setText(String.valueOf(setting.getOutBitSize()));
		outChannels.setText(String.valueOf(setting.getOutChannels()));
		outSigned.setSelected(setting.getOutSigned().booleanValue());
		outBigEndian.setSelected(setting.getOutBigEndian().booleanValue());
		
		setCaptureDevice(setting);
	}

	private void setCaptureDevice(AudioSettingsModel setting) {
		Optional<Info> device = info.stream().filter(cd -> setting.getCaptureDevice().equals(cd.getName())).findFirst();
		if(device.isPresent())
			inMixers.getSelectionModel().select(device.get());
	}

	@FXML
	private void applyChanges() {
		if(dataUnsync) {
			 Optional<ButtonType> btn = DialogBuilder
					 .confirmation()
					 .header(String.format("¿Desea guardar los cambios realizados en la configuracion de audio %n\t-> (%s)?", lastSelectedSetting.getConfigName()))
					 .finish()
					 .alert()
					 .showAndWait();
			 
			 if(btn.isPresent() && btn.get().equals(ButtonType.OK)) {
				 dataUnsync = saveChanges();
			 }
		}
		if(isAudioDataConfigured() && !dataUnsync) {
			audioPlayer.setSourceLine(lines.source);
			audioRecorder.setTargetLine(lines.target);
			stage.close();
		}
	}

	private boolean saveChanges() {
		
		
		return false;
	}

	@FXML
	private void discardChanges() {
		stage.close();
	}

	@FXML
	private void createConfig() {//TODO
		if(isAudioDataConfigured()) {
			int lastId = settingsDAO.getLastSettingId();
			String configName = "AudioSetting - (" + (lastId) + ")";

			float sampleRateIn = Float.parseFloat(inSampleRate.textProperty().get());
			int bitSizeIn = Integer.parseInt(inBitSize.textProperty().get());
			int channelsIn = Integer.parseInt(inChannels.textProperty().get());
			boolean signedIn = inSigned.isSelected();
			boolean bigEndianIn = inBigEndian.isSelected();

			float sampleRateOut = Float.parseFloat(outSampleRate.textProperty().get());
			int bitSizeOut = Integer.parseInt(outBitSize.textProperty().get());
			int channelsOut = Integer.parseInt(outChannels.textProperty().get());
			boolean signedOut = outSigned.isSelected();
			boolean bigEndianOut = outBigEndian.isSelected();

			AudioSettingsModel newSetting = AudioSettingsModel.fromValues(configName, sampleRateIn, bitSizeIn, channelsIn, signedIn, bigEndianIn, sampleRateOut, bitSizeOut, channelsOut, signedOut, bigEndianOut);
			settingsDAO.saveAudioSetting(newSetting);
			settings.add(newSetting);
			comboSettings.getSelectionModel().select(newSetting);
		}
	}

	@FXML
	private void editConfig() {//TODO
		if(isAudioDataConfigured() && lastSelectedSetting.getId() != 0) {
			float sampleRateIn = Float.parseFloat(inSampleRate.textProperty().get());
			int bitSizeIn = Integer.parseInt(inBitSize.textProperty().get());
			int channelsIn = Integer.parseInt(inChannels.textProperty().get());
			boolean signedIn = inSigned.isSelected();
			boolean bigEndianIn = inBigEndian.isSelected();

			float sampleRateOut = Float.parseFloat(outSampleRate.textProperty().get());
			int bitSizeOut = Integer.parseInt(outBitSize.textProperty().get());
			int channelsOut = Integer.parseInt(outChannels.textProperty().get());
			boolean signedOut = outSigned.isSelected();
			boolean bigEndianOut = outBigEndian.isSelected();
			
			lastSelectedSetting.setInSampleRate(sampleRateIn);
			lastSelectedSetting.setInBitSize(bitSizeIn);
			lastSelectedSetting.setInChannels(channelsIn);
			lastSelectedSetting.setInSigned(signedIn);
			lastSelectedSetting.setInBigEndian(bigEndianIn);

			lastSelectedSetting.setOutSampleRate(sampleRateOut);
			lastSelectedSetting.setOutBitSize(bitSizeOut);
			lastSelectedSetting.setOutChannels(channelsOut);
			lastSelectedSetting.setOutSigned(signedOut);
			lastSelectedSetting.setOutBigEndian(bigEndianOut);
			
			lastSelectedSetting.setCaptureDevice(captureDevice == null ? "" : captureDevice.getName());
			
			settingsDAO.updateAudioSetting(lastSelectedSetting);
		}
		dataUnsync = false;
	}
	
	@FXML
	private void deleteConfig() {//TODO
		settingsDAO.deleteAudioSetting(lastSelectedSetting);
		clearData();
		dataUnsync = false;
	}
	
	public boolean isAudioDataConfigured() {
		try {
			lines = new ConfiguredLines();
			setSource();
			setTarget();
		} catch (Exception e) {
			return false;//TODO logger
		}
		return captureDevice != null && lines.source != null && lines.target != null;
	}

	private void setTarget() throws LineUnavailableException {
		AudioFormat audioFormat = targetFormat();
		Mixer mixer = AudioSystem.getMixer(captureDevice);
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
		lines.target = (TargetDataLine) mixer.getLine(dataLineInfo);
		lines.target.open(audioFormat);
	}

	private void setSource() throws LineUnavailableException {
		AudioFormat audioFormat = sourceFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		lines.source = (SourceDataLine) AudioSystem.getLine(info);
		lines.source.open(audioFormat);
	}

	private AudioFormat sourceFormat() {
		// out
		float sampleRate = Float.parseFloat(outSampleRate.textProperty().get());
		int bitSize = Integer.parseInt(outBitSize.textProperty().get());
		int channels = Integer.parseInt(outChannels.textProperty().get());
		boolean signed = outSigned.isSelected();
		boolean bigEndian = outBigEndian.isSelected();
		return new AudioFormat(sampleRate, bitSize, channels, signed, bigEndian);
	}

	private AudioFormat targetFormat() {
		// in
		float sampleRate = Float.parseFloat(inSampleRate.textProperty().get());
		int bitSize = Integer.parseInt(inBitSize.textProperty().get());
		int channels = Integer.parseInt(inChannels.textProperty().get());
		boolean signed = inSigned.isSelected();
		boolean bigEndian = inBigEndian.isSelected();
		return new AudioFormat(sampleRate, bitSize, channels, signed, bigEndian);
	}

	private void setupChangesDetection() {

		inMixers.getSelectionModel().selectedItemProperty().addListener(listener());
		
		TextField[] tfs = { inBitSize, inChannels, inSampleRate, outBitSize, outChannels, outSampleRate };
		for (TextField tf : tfs) {
			tf.textProperty().addListener(listener());
		}

		CheckBox[] cbs = { inSigned, inBigEndian, outBigEndian, outSigned };
		for (CheckBox cb : cbs) {
			cb.selectedProperty().addListener(listener());
		}
	}
	
	private void clearData() {

		inMixers.getSelectionModel().clearSelection();
		
		TextField[] tfs = { inBitSize, inChannels, inSampleRate, outBitSize, outChannels, outSampleRate };
		for (TextField tf : tfs) {
			tf.clear();
		}

		CheckBox[] cbs = { inSigned, inBigEndian, outBigEndian, outSigned };
		for (CheckBox cb : cbs) {
			cb.setSelected(false);
		}
	}

	private ChangeListener<Object> listener() {
		return (observable, oldValue, newValue) -> {
			dataUnsync = oldValue != newValue;
		};
	}

	public void setFormStage(Stage stage) {
		this.stage = stage;
	}

	public boolean areServicesRunning() {
		return audioPlayer.isRunning() || audioRecorder.isRunning();
	}
	
	class ConfiguredLines {
		SourceDataLine source;
		TargetDataLine target;
	}
}
