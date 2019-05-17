package application.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AudioSettingsModel {

	private int id;

	private StringProperty configNameProperty;

	private FloatProperty inSampleRateProperty;

	private IntegerProperty inBitSizeProperty;

	private IntegerProperty inChannelsProperty;

	private BooleanProperty inSignedProperty;

	private BooleanProperty inBigEndianProperty;

	private FloatProperty outSampleRateProperty;

	private IntegerProperty outBitSizeProperty;

	private IntegerProperty outChannelsProperty;

	private BooleanProperty outSignedProperty;

	private BooleanProperty outBigEndianProperty;

	public AudioSettingsModel() {
		configNameProperty = new SimpleStringProperty();
		
		inSampleRateProperty = new SimpleFloatProperty();
		inBitSizeProperty = new SimpleIntegerProperty();
		inChannelsProperty = new SimpleIntegerProperty();
		inSignedProperty = new SimpleBooleanProperty();
		inBigEndianProperty = new SimpleBooleanProperty();
		
		outSampleRateProperty = new SimpleFloatProperty();
		outBitSizeProperty = new SimpleIntegerProperty();
		outChannelsProperty = new SimpleIntegerProperty();
		outSignedProperty = new SimpleBooleanProperty();
		outBigEndianProperty = new SimpleBooleanProperty();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/***********************************************
	 * Prpoerties
	 ************************************************/

	public StringProperty getConfigNameProperty() {
		return configNameProperty;
	}

	public FloatProperty getInSampleRateProperty() {
		return inSampleRateProperty;
	}

	public IntegerProperty getInBitSizeProperty() {
		return inBitSizeProperty;
	}

	public IntegerProperty getInChannelsProperty() {
		return inChannelsProperty;
	}

	public BooleanProperty getInSignedProperty() {
		return inSignedProperty;
	}

	public BooleanProperty getInBigEndianProperty() {
		return inBigEndianProperty;
	}

	public FloatProperty getOutSampleRateProperty() {
		return outSampleRateProperty;
	}

	public IntegerProperty getOutBitSizeProperty() {
		return outBitSizeProperty;
	}

	public IntegerProperty getOutChannelsProperty() {
		return outChannelsProperty;
	}

	public BooleanProperty getOutSignedProperty() {
		return outSignedProperty;
	}

	public BooleanProperty getOutBigEndianProperty() {
		return outBigEndianProperty;
	}

	/***********************************************
	 * Getters
	 ************************************************/

	public String getConfigName() {
		return configNameProperty.get();
	}

	public float getInSampleRate() {
		return inSampleRateProperty.get();
	}

	public Integer getInBitSize() {
		return inBitSizeProperty.get();
	}

	public Integer getInChannels() {
		return inChannelsProperty.get();
	}

	public Boolean getInSigned() {
		return inSignedProperty.get();
	}

	public Boolean getInBigEndian() {
		return inBigEndianProperty.get();
	}

	public float getOutSampleRate() {
		return outSampleRateProperty.get();
	}

	public Integer getOutBitSize() {
		return outBitSizeProperty.get();
	}

	public Integer getOutChannels() {
		return outChannelsProperty.get();
	}

	public Boolean getOutSigned() {
		return outSignedProperty.get();
	}

	public Boolean getOutBigEndian() {
		return outBigEndianProperty.get();
	}

	/***********************************************
	 * Setters
	 ************************************************/

	public void setConfigName(String value) {
		configNameProperty.set(value);
	}

	public void setInSampleRate(float value) {
		inSampleRateProperty.set(value);
	}

	public void setInBitSize(Integer value) {
		inBitSizeProperty.set(value);
	}

	public void setInChannels(Integer value) {
		inChannelsProperty.set(value);
	}

	public void setInSigned(boolean b) {
		Boolean value = Boolean.valueOf(b);
		inSignedProperty.set(value);
	}

	public void setInBigEndian(boolean b) {
		Boolean value = Boolean.valueOf(b);
		inBigEndianProperty.set(value);
	}

	public void setInSigned(String s) {
		Boolean value = Boolean.valueOf(s);
		inSignedProperty.set(value);
	}

	public void setInBigEndian(String s) {
		Boolean value = Boolean.valueOf(s);
		inBigEndianProperty.set(value);
	}

	public void setOutSampleRate(float value) {
		outSampleRateProperty.set(value);
	}

	public void setOutBitSize(Integer value) {
		outBitSizeProperty.set(value);
	}

	public void setOutChannels(Integer value) {
		outChannelsProperty.set(value);
	}

	public void setOutSigned(boolean b) {
		Boolean value = Boolean.valueOf(b);
		outSignedProperty.set(value);
	}

	public void setOutBigEndian(boolean b) {
		Boolean value = Boolean.valueOf(b);
		outBigEndianProperty.set(value);
	}

	public void setOutSigned(String s) {
		Boolean value = Boolean.valueOf(s);
		outSignedProperty.set(value);
	}

	public void setOutBigEndian(String s) {
		Boolean value = Boolean.valueOf(s);
		outBigEndianProperty.set(value);
	}

	public static AudioSettingsModel fromValues(String configName, float sampleRateIn, int bitSizeIn, int channelsIn,
			boolean signedIn, boolean bigEndianIn, float sampleRateOut, int bitSizeOut, int channelsOut,
			boolean signedOut, boolean bigEndianOut) {

		AudioSettingsModel setting = new AudioSettingsModel();
		setting.setConfigName(configName);

		setting.setInSampleRate(sampleRateIn);
		setting.setInBitSize(bitSizeIn);
		setting.setInChannels(channelsIn);
		setting.setInSigned(signedIn);
		setting.setInBigEndian(bigEndianIn);

		setting.setOutSampleRate(sampleRateOut);
		setting.setOutBitSize(bitSizeOut);
		setting.setOutChannels(channelsOut);
		setting.setOutSigned(signedOut);
		setting.setOutBigEndian(bigEndianOut);

		return setting;
	}
	
	@Override
	public String toString() {
		return getConfigName();
	}
}
