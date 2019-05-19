package application.controller.fxml;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import application.services.audio.receiver.AudioStreamingReceiverService;
import application.services.video.receiver.VideoStreamingReceiverService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.constants.Styles;

public class ServerSettingsController {

	enum ConfiguredServer {
		LOCAL, REMOTE, NONE;
	}

	@FXML
	private VBox netAddressesVBox;
	@FXML
	private TextField ipAddressTF;
	@FXML
	private RadioButton clientRadio;
	@FXML
	private RadioButton serverRadio;
	@FXML
	private Label validationLabel;
	@FXML
	private Label serverAddressLabel;
	
	private ConfiguredServer configured = ConfiguredServer.NONE;

	private ToggleGroup radioGroup;

	private Stage stage;
	
	private AudioStreamingReceiverService audioReceiver;
	
	private VideoStreamingReceiverService videoReceiver;

	@FXML
	private void initialize() {
		radioGroup = new ToggleGroup();
		serverRadio.setToggleGroup(radioGroup);
		clientRadio.setToggleGroup(radioGroup);
		radioGroup.selectedToggleProperty().addListener( 
				(observable, oldValue, newValue) -> {	refreshWindow();  });
		ipAddressTF.textProperty().addListener(
				(observable, oldValue, newValue) -> {	refreshValidationLabel();  });
		
		
		ipAddressTF.textProperty().set("192.168.1.133");
//		ipAddressTF.textProperty().set("172.17.251.53");
		
		audioReceiver = AudioStreamingReceiverService.getInstance();
		videoReceiver = VideoStreamingReceiverService.getInstance();
	}
	
	private void refreshWindow() {
		RadioButton radio = (RadioButton) radioGroup.getSelectedToggle();
		ConfiguredServer recentlyConfigured = radio == serverRadio ? ConfiguredServer.LOCAL : ConfiguredServer.REMOTE;
		if (!recentlyConfigured.equals(configured)) {
			switch (recentlyConfigured) {
			case LOCAL:
				addNetAddressesHints();
				ipAddressTF.setDisable(true);
				serverAddressLabel.setDisable(true);
				validationLabel.setVisible(false);
				break;
			case REMOTE:
				removeNetAddressesHints();
				ipAddressTF.setDisable(false);
				serverAddressLabel.setDisable(false);
				validationLabel.setVisible(true);
				break;
			case NONE:
			default:
			}
			configured = recentlyConfigured;
		}
	}

	private void configureRemoteServer() {
		if(isValidIPAddress()) {
			String serverAddress = ipAddressTF.textProperty().get();
			audioReceiver.setServerData(serverAddress);
			videoReceiver.setServerData(serverAddress);
			stage.close();
		}
	}

	private void enableLocalServer() {
		stage.close();
	}

	@FXML
	private void applyChanges() {
		switch (configured) {
		case LOCAL:
			enableLocalServer();
			break;
		case REMOTE:
			configureRemoteServer();
			break;
		case NONE:
		default:
			break;
		}
		stage.close();
	}

	@FXML
	private void discardChanges() {
		stage.close();
	}
	
	private List<String> netAdapterAddresses() throws UnknownHostException{
		return Arrays.asList(Inet4Address.getAllByName(Inet4Address.getLocalHost().getHostName()))
				.stream().filter(addr -> !addr.isLinkLocalAddress())
				.map(addr -> addr.getHostAddress()).collect(Collectors.toList());
	}
	
	private void addNetAddressesHints() {
		try {
			List<Label> labels = new ArrayList<>();
			labels.add(new Label("Direcciones IP del adaptador de red:"));
			List<String> addrs = netAdapterAddresses();
			for (String addr : addrs) {
				labels.add(new Label(String.format("\t%s", addr)));
			}
			netAddressesVBox.getChildren().addAll(labels);
		} catch (UnknownHostException e) {}
	}
	
	private void removeNetAddressesHints() {
		netAddressesVBox.getChildren().clear();
	}
	
	private boolean isValidIPAddress() {
		String str = ipAddressTF.textProperty().get();
		String[] addr = str.split("\\.");
		if(addr.length != 4) {
			return false;
		}
		for(String block : addr) {
			try {
				int intBlock = Integer.parseInt(block);
				if(intBlock < 0 || intBlock > 255)
					return false;	
			} catch (Exception e) {
				return false;
			}
		}
		/* 
		 * si llega este punto se ha validado por completo la dirección,
		 * pero es posible que de como válida una direccion 
		 * con un punto al final (192.168.1.1.)  <-- Ese ultimo punto!!!
		 * para evitar eso está la siguiente línea
		*/
		return !str.endsWith(".");
	}
	
	private void refreshValidationLabel() {
		if (isValidIPAddress()) {
			validationLabel.setId(Styles.ServerSettings.valid);
			validationLabel.setText("Correcto");
		} else {
			validationLabel.setId(Styles.ServerSettings.invalid);
			validationLabel.setText("Incorrecto");
		}
	}

	public void setFormStage(Stage stage) {
		this.stage = stage;
	}
}
