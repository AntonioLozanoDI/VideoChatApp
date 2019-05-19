package application.services.video.recorder;

import static org.bytedeco.opencv.global.opencv_core.cvFlip;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;

import application.services.base.AbstractRecordingService;

public class VideoRecordingService extends AbstractRecordingService {
	
	private static VideoRecordingService instance;

	private FrameGrabber grabber = null;
	
	private OpenCVFrameConverter.ToIplImage converter = null;

	private Frame lastFrame = null; 

	private boolean recording;
	
	private VideoRecordingService() {
		super();
		INTERVAL = 100;
		converter = new OpenCVFrameConverter.ToIplImage();
		grabber = new VideoInputFrameGrabber(0);
	}

	public static VideoRecordingService getInstance() {
		return instance == null ? instance = new VideoRecordingService() : instance;
	}
	
	@Override
	protected void capture(){
		if(grabber != null) {
			Frame frame = null;
			if(!pause) {
				try {
					frame = grabber.grab();
					Mat mat = converter.convertToMat(frame);
	                mat.convertTo(mat,  -1, 1, -50);
	                IplImage img = converter.convertToIplImage(converter.convert(mat));
	                
	                //the grabbed frame will be flipped, re-flip to make it right
	                cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
	               
	                lastFrame = converter.convert(img).clone();
				} catch (Exception e) {
					System.err.println("unexpected error grabbing image");
				}
			}
		}
	};

	@Override
	public void startRecorder() {
		System.out.println(String.format("BEFORE: pause %s, isAlive %s, recording %s", pause, isAlive(),recording));
		if(!recording){
			try {
				grabber.start();
				recording = true;
				pause =false;
				super.startRecorder();
			} catch (Exception e) {}
		}
		System.out.println(String.format("AFTER: pause %s, isAlive %s, recording %s", pause, isAlive(),recording));
	}

	@Override
	public void stopRecorder() {
		if(recording) {
			try {
				grabber.stop();
				recording = false;
				pause =true;
			} catch (Exception e) {}
		}
	}
	
	public Frame getLastFrame() {
		return lastFrame;
	}	
	
	@Override
	protected void onStopedService() {
		stopRecorder();
	}
}
