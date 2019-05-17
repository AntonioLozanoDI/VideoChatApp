package application.services.video.player;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import application.services.base.AbstractPlayerService;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.constants.Constants;
import utils.resources.ApplicationResourceProvider;

public class VideoPlayerService extends AbstractPlayerService {

	private static VideoPlayerService instance;

	private ImageView view;

	private byte[] image;

	private final byte[] empty = new byte[1024];

	private final Image black = ApplicationResourceProvider.getPNGFile(Constants.Files.Images.black).toImage();

	private double height;

	private double width;

	private VideoPlayerService() {
		super();
		INTERVAL = 50;
		height = 1000;
		width = 1470;
	}

	public static VideoPlayerService getInstance() {
		return instance == null ? instance = new VideoPlayerService() : instance;
	}

	public void setImageView(ImageView view) {
		this.view = view;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	protected void play() {
		if (!pause && image != null) {
			try {
				if (Arrays.equals(image, empty))
					throw new Exception();

				BufferedImage im = ImageIO.read(new ByteArrayInputStream(image));
				paintImage(SwingFXUtils.toFXImage(im, null));
			} catch (Exception e) {
				paintImage(black);
			}
		} else {
			paintImage(black);
		}
	}

	private void paintImage(Image image) {
		view.setImage(image);
		view.setPreserveRatio(false);
		view.setFitHeight(height);
		view.setFitWidth(width);
	}

	@Override
	public void startPlayer() {
		try {
			if(!isAlive()) {
				start();
			}
			pause =false;
		} catch (Exception e) {}
	}

	@Override
	protected void onStopedService() {
		paintImage(black);
	}
}
