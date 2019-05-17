package application.services.audio;

import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public interface AudioHandler {

	public interface Player {
		void setSourceLine(SourceDataLine line);
	}
	
	public interface Recorder {
		void setTargetLine(TargetDataLine line);
	}
}
