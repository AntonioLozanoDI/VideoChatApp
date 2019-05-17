package application.controller;

import application.services.VideoChatServiceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import utils.constants.Constants;
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
	private boolean stopedCall;
	private boolean moved;
	
	private MainController mainController;
	
	@FXML
	private void initialize() {
		setupGlassPane();
		initButtonImages();
		VideoChatServiceManager.setupPlayer(imageView);
		
		micButton.setGraphic(micView);
		speakerButton.setGraphic(speakerView);
		stopButton.setGraphic(stopView);
		pauseButton.setGraphic(pauseView);
		
		mutedMicB = false;
		mutedSpeakerB = false;
		pausedCallB = false;
		
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
		VideoChatServiceManager.initCall();
		initCallButton.setVisible(false);
		showButtons(true);
	}

	@FXML
	private void pauseCall() {
		pausedCallB = !pausedCallB;
		VideoChatServiceManager.pauseCall();
		refreshButtons();
	}

	@FXML
	private void stopCall() {
		stopedCall = !stopedCall;
		VideoChatServiceManager.stopCall();
		hideVideoScreen();
		initButtonImages();
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
		speakerView.setImage(mutedSpeakerB ? mutedSpeaker: unmutedSpeaker);
		pauseView.setImage(pausedCallB ? pauseVideoCall: activeVideoCall);
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
				while (!moved) {
					opacityFull = false;
					try {
						if (opacity >= 0.15) {
							opacity -= 0.05;
						} else {
							opacity = 0;
						}
						glassPane.setOpacity(opacity);
						Thread.sleep(20);
					} catch (InterruptedException e) {
					}
				}
				if (!opacityFull) {
					opacity = 1;
					glassPane.setOpacity(opacity);
				}
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
				}
				moved = false;
			}
		};
		new Thread(glassPaneTask).start();
	}
	
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	private void hideVideoScreen() {
		mainController.hideVideoScreen();
	}
}
