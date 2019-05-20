package application.controller.fxml;

import java.util.Optional;

import com.diproject.commons.model.message.types.AcceptCall;
import com.diproject.commons.model.message.types.InitCall;
import com.diproject.commons.model.message.types.PauseCall;
import com.diproject.commons.model.message.types.StopCall;
import com.diproject.commons.utils.payload.PayloadFactory;
import com.sp.dialogs.DialogBuilder;

import application.controller.session.SessionController;
import application.controller.ws.VideoChatHandler;
import application.services.VideoChatServiceManager;
import application.services.audio.receiver.AudioStreamingReceiverService;
import application.services.video.receiver.VideoStreamingReceiverService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import utils.constants.Constants;
import utils.logging.LoggingUtils;
import utils.resources.ApplicationResourceProvider;

public class VideoStreamingController {

	@FXML
	private ImageView imageView;
	@FXML
	private AnchorPane glassPane;
	@FXML
	private Label sizeLabel;
	@FXML
	private Button initCallButton;
	@FXML
	private Button micButton;
	@FXML
	private Button speakerButton;
	@FXML
	private Button stopButton;
	@FXML
	private Button pauseButton;

	private ImageView micView = new ImageView();
	private ImageView speakerView = new ImageView();
	private ImageView stopView = new ImageView();
	private ImageView pauseView = new ImageView();

	private final Image unmutedMic = ApplicationResourceProvider.getPNGFile(Constants.Files.Images.unmutedMic).toImage();
	private final Image mutedMic = ApplicationResourceProvider.getPNGFile(Constants.Files.Images.mutedMic).toImage();
	private final Image unmutedSpeaker = ApplicationResourceProvider.getPNGFile(Constants.Files.Images.unmutedSpeaker).toImage();
	private final Image mutedSpeaker = ApplicationResourceProvider.getPNGFile(Constants.Files.Images.mutedSpeaker).toImage();
	private final Image activeVideoCall = ApplicationResourceProvider.getPNGFile(Constants.Files.Images.activeVideoCall).toImage();
	private final Image pauseVideoCall = ApplicationResourceProvider.getPNGFile(Constants.Files.Images.pauseVideoCall).toImage();
	private final Image stopVideoCall = ApplicationResourceProvider.getPNGFile(Constants.Files.Images.stopVideoCall).toImage();

	private boolean mutedMicB;
	private boolean mutedSpeakerB;
	private boolean pausedCallB;
	private boolean moved;
	private boolean enableGlassPane;

	private String connectionContact;
	
	private MainController mainController;

	private VideoChatHandler vch;
	private SessionController sc;

	@FXML
	private void initialize() {
		setupGlassPane();

		micButton.setGraphic(micView);
		speakerButton.setGraphic(speakerView);
		stopButton.setGraphic(stopView);
		pauseButton.setGraphic(pauseView);

		sc = SessionController.getInstance();
		vch = VideoChatHandler.getInstance();

		vch.setOnInitCall((call) -> {
			onInit(call);
		});

		vch.setOnAcceptCall((call) -> {
			onAccept(call);
		});
		
		vch.setOnPauseCall((call) -> {
			pause(false);
		});

		vch.setOnStopCall((call) -> {
			stop(false);
		});

		VideoChatServiceManager.setupPlayer(imageView);
		
		initializeViewComponents();
	}

	private void onInit(InitCall call) {
		initCallButton.setVisible(false);
		
		Optional<ButtonType> btn = DialogBuilder.confirmation()
				.content("Desea aceptar la llamada de " + call.getUser() + " ?.")
				.finish().alert().showAndWait();
		
		if (btn.isPresent() && btn.get().equals(ButtonType.OK)) {
			init(false);
			connectionContact = call.getOrigin();
			AudioStreamingReceiverService.getInstance().setServerData(call.getAddress());
			VideoStreamingReceiverService.getInstance().setServerData(call.getAddress());
			sendAccept(call);
			VideoChatServiceManager.acceptCall();
		} else {
			cancelCall();
		}
	}
	
	private void sendAccept(InitCall call) {
		AcceptCall accept = new AcceptCall();
		accept.acceptCall();
		accept.setAddress(sc.getSelfClientAddress());
		accept.setOrigin(call.getDestination());
		accept.setDestination(call.getOrigin());
		sc.getClient().send(PayloadFactory.create(accept));
	}

	private void cancelCall() {
		enableGlassPane = false;
		initCallButton.setVisible(true);
		showButtons(false);
	}

	private void onAccept(AcceptCall call) {
		if(call.isAccepted()) {
			AudioStreamingReceiverService.getInstance().setServerData(call.getAddress());
			VideoStreamingReceiverService.getInstance().setServerData(call.getAddress());
			VideoChatServiceManager.acceptCall();
		} else {
			cancelCall();
		}
	}

	private void initializeViewComponents() {
		initButtonImages();

		mutedMicB = false;
		mutedSpeakerB = false;
		pausedCallB = false;

		initCallButton.setVisible(true);
		showButtons(false);
	}

	private void showButtons(boolean b) {
		micButton.setVisible(b);
		speakerButton.setVisible(b);
		pauseButton.setVisible(b);
		stopButton.setVisible(b);
	}

	private void initButtonImages() {
		micView.setImage(unmutedMic);
		speakerView.setImage(unmutedSpeaker);
		stopView.setImage(stopVideoCall);
		pauseView.setImage(activeVideoCall);
	}

	@FXML
	public void initCall() {
		init(true);
	}

	@FXML
	private void pauseCall() {
		pause(true);
	}

	@FXML
	private void stopCall() {
		stop(true);
	}

	private void init(boolean ownerOfAction) {
		if (ownerOfAction) {
			sendInitCall();
		}
		enableGlassPane = true;
		VideoChatServiceManager.initCall();
		initCallButton.setVisible(false);
		showButtons(true);
	}

	private void sendInitCall() {
		if(connectionContact != null) {
			InitCall call = new InitCall();
			call.setOrigin(sc.getLoggerUser().getLogin());
			call.setUser(sc.getLoggerUser().getLogin());
			call.setDestination(connectionContact);
			call.setAddress(sc.getSelfClientAddress());
			sc.getClient().send(PayloadFactory.create(call));
		}
	}

	private void pause(boolean ownerOfAction) {
		if (ownerOfAction) {
			sendPauseCall();
		}
		pausedCallB = !pausedCallB;
		enableGlassPane = false;
		VideoChatServiceManager.pauseCall();
		refreshButtons();
	}

	private void sendPauseCall() {
		PauseCall pc = new PauseCall();
		pc.setOrigin(sc.getLoggerUser().getLogin());
		pc.setDestination(connectionContact);
		sc.getClient().send(PayloadFactory.create(pc));
	}

	private void stop(boolean ownerOfAction) {
		if (ownerOfAction) {
			sendStopCall();
		}
		enableGlassPane = false;
		VideoChatServiceManager.stopCall();
		hideVideoScreen();
		initButtonImages();
		initializeViewComponents();
	}

	private void sendStopCall() {
		StopCall stc = new StopCall();
		stc.setOrigin(sc.getLoggerUser().getLogin());
		stc.setDest(connectionContact);
		sc.getClient().send(PayloadFactory.create(stc));
	}

	@FXML
	private void toggleMic() {
		mutedMicB = !mutedMicB;
		VideoChatServiceManager.toggleMic();
		refreshButtons();
	}

	@FXML
	private void toggleSpeaker() {
		mutedSpeakerB = !mutedSpeakerB;
		VideoChatServiceManager.toggleSpeaker();
		refreshButtons();
	}

	private void refreshButtons() {
		micView.setImage(mutedMicB ? mutedMic : unmutedMic);
		speakerView.setImage(mutedSpeakerB ? mutedSpeaker : unmutedSpeaker);
		pauseView.setImage(pausedCallB ? pauseVideoCall : activeVideoCall);
		micButton.setDisable(pausedCallB);
		speakerButton.setDisable(pausedCallB);
	}

	@FXML
	private void mouseMoved() {
		moved = true;
	}

	private void setupGlassPane() {
		Runnable glassPaneTask = () -> {
			double opacity = 1;
			boolean opacityFull = true;
			while (true) {
				try {
					if (enableGlassPane) {
						while (!moved) {
							opacityFull = false;
							if (opacity >= 0.15) {
								opacity -= 0.05;
							} else {
								opacity = 0;
							}
							glassPane.setOpacity(opacity);
							Thread.sleep(20); // SLEEP
						}
						if (!opacityFull) {
							opacity = 1;
							glassPane.setOpacity(opacity);
						}
						Thread.sleep(15000); // SLEEP
						moved = false;
					}
					Thread.sleep(20); // SLEEP //no quitar..
				} catch (Exception e) {
					System.out.println(LoggingUtils.getStackTrace(e));
				}
			}
		};
		new Thread(glassPaneTask).start();
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	private void hideVideoScreen() {
		mainController.hideVideoScreen();
		imageView.setImage(null);
	}

	public void setSelectedContact(String selectedContact) {
		connectionContact = selectedContact;
	}
}
