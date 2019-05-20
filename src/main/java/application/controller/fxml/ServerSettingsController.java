package application.controller.fxml;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.diproject.commons.utils.rest.ConfigurationHTTPClient;

import application.controller.session.SessionController;
import application.services.audio.receiver.AudioStreamingReceiverService;
import application.services.video.receiver.VideoStreamingReceiverService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
	private TextField ipAddressTF;
	@FXML
	private RadioButton clientRadio;
	@FXML
	private RadioButton serverRadio;
	@FXML
	private Label validationLabel;
	@FXML
	private Label serverAddressLabel;
	@FXML
	private ComboBox<String> clientAdressesCombo;
	
	private ConfiguredServer configured = ConfiguredServer.NONE;

	private ToggleGroup radioGroup;

	private Stage stage;
	
	private AudioStreamingReceiverService audioReceiver;
	
	private VideoStreamingReceiverService videoReceiver;
	
	private SessionController sc;
	
	private ConfigurationHTTPClient configClient;

	@FXML
	private void initialize() {
		radioGroup = new ToggleGroup();
		serverRadio.setToggleGroup(radioGroup);
		clientRadio.setToggleGroup(radioGroup);
		radioGroup.selectedToggleProperty().addListener( 
				(observable, oldValue, newValue) -> {	refreshWindow();  });
		ipAddressTF.textProperty().addListener(
				(observable, oldValue, newValue) -> {	refreshValidationLabel();  });
		
		List<String> addrs = Collections.emptyList();
		try {
			 addrs = netAdapterAddresses();
		} catch (Exception e) {}
		clientAdressesCombo.setItems(FXCollections.observableArrayList(addrs));
		if(!addrs.isEmpty()) {
			clientAdressesCombo.getSelectionModel().select(0);
		}
		
		ipAddressTF.textProperty().set("192.168.1.133");
//		ipAddressTF.textProperty().set("172.17.251.53");
		

		clientRadio.setSelected(true);
		
		sc = SessionController.getInstance();
		audioReceiver = AudioStreamingReceiverService.getInstance();
		videoReceiver = VideoStreamingReceiverService.getInstance();
		
		configClient = new ConfigurationHTTPClient();
		
		if(sc.isServerConfigured()) {
			ipAddressTF.textProperty().set(sc.getServerAddress());
		}
	}
	
	private void refreshWindow() {
		RadioButton radio = (RadioButton) radioGroup.getSelectedToggle();
		ConfiguredServer recentlyConfigured = radio == serverRadio ? ConfiguredServer.LOCAL : ConfiguredServer.REMOTE;
		if (!recentlyConfigured.equals(configured)) {
			switch (recentlyConfigured) {
			case LOCAL:
				ipAddressTF.setDisable(true);
				serverAddressLabel.setDisable(true);
				validationLabel.setVisible(false);
				clientAdressesCombo.setDisable(true);
				break;
			case REMOTE:
				ipAddressTF.setDisable(false);
				serverAddressLabel.setDisable(false);
				validationLabel.setVisible(true);
				clientAdressesCombo.setDisable(false);
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
			//configClient.configureServer(serverAddress);
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
