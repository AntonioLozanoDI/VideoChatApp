package application.services.video.receiver;

import java.net.DatagramPacket;

import application.services.base.AbstractStreamingReceiverService;

public class VideoStreamingReceiverService extends AbstractStreamingReceiverService{
	
	private static VideoStreamingReceiverService instance;

	private VideoStreamingReceiverService() {
		super();
		port = 48560;
	}

	public static VideoStreamingReceiverService getInstance() {
		return instance == null ? instance = new VideoStreamingReceiverService() : instance;
	}
	
	@Override
	protected void onReceive() {
		if(connected) {    			
			byte[] buf = new byte[23000];
			DatagramPacket capture = new DatagramPacket(buf, buf.length);
			try {
				socketUDP.receive(capture);
				data = capture.getData();
			} catch (Exception e) {
				System.err.println("error receiving image");
			}
		}
	}
}
