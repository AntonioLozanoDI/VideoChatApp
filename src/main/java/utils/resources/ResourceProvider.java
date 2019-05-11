package utils.resources;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javafx.scene.image.Image;

public interface ResourceProvider {
	
	public File toFile();

	public URL toURL();
	
	public Image toImage();
	
	public String toString();
	
	public InputStream toInputStream();
}
