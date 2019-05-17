package application.services.video.sender;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import application.services.base.AbstractStreamingSenderService;

public class VideoStreamingSenderService extends AbstractStreamingSenderService {

	private static VideoStreamingSenderService instance;

	private VideoStreamingSenderService() {// TODO handle multiple clients
		super();
		port = 48560;
		socketUDP = initSocket();
		INTERVAL = 80;
	}

	public static VideoStreamingSenderService getInstance() {
		return instance == null ? instance = new VideoStreamingSenderService() : instance;
	}

	public void setImage(Frame frame) {
		if(frame != null) 
			convert(frame);
	}

	private void convert(Frame frame) {
		try {
			BufferedImage im = new Java2DFrameConverter().convert(frame);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(im, "jpg", baos);
				baos.flush();
				data = baos.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
		} catch (BufferUnderflowException e) {}
	}
}
