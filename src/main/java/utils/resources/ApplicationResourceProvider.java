package utils.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import application.Main;
import javafx.scene.image.Image;
import utils.resources.ResourcePathFactory.OriginPathToBin;
import utils.resources.ResourcePathFactory.OriginPathToProjectDir;
import utils.resources.exception.ResourceAccessException;

public final class ApplicationResourceProvider implements ResourceProvider {

	private	String filename;
	private	ApplicationResourceType type;

	private ApplicationResourceProvider(String filename, ApplicationResourceType type) {
		this.filename = filename;
		this.type = type;
	}

	/*******************************************************************************************************
	 * 
	 * 		PUBLIC INSTANCE METHODS
	 * 
	 ********************************************************************************************************/
	
	public File toFile() {
		switch (type) {
		case CSS:
			return new File(ResourcePathFactory.getCSSFilePath(OriginPathToBin.ROOT_DIRECTORY, filename));
		case DATABASE:
			return new File(ResourcePathFactory.getDatabaseFilePath(OriginPathToBin.ROOT_DIRECTORY, filename));
		case FXML:
			return new File(ResourcePathFactory.getFXMLFilePath(OriginPathToBin.ROOT_DIRECTORY, filename));
		case PNG:
			return new File(ResourcePathFactory.getPNGFilePath(OriginPathToBin.ROOT_DIRECTORY, filename));
		case LOGS:
			return new File(ResourcePathFactory.getLogFilePath(OriginPathToProjectDir.ROOT_DIRECTORY, filename));
		case PROPERTIES:
			return new File(ResourcePathFactory.getPropertiesFilePath(OriginPathToBin.ROOT_DIRECTORY, filename));
		}
		return null;
	}

	public URL toURL() {
		switch (type) {
		case CSS:
			return Main.class.getResource(ResourcePathFactory.getCSSFilePath(OriginPathToBin.MAIN_CLASS, filename));
		case DATABASE:
			return Main.class.getResource(ResourcePathFactory.getDatabaseFilePath(OriginPathToBin.MAIN_CLASS, filename));
		case FXML:
			return Main.class.getResource(ResourcePathFactory.getFXMLFilePath(OriginPathToBin.MAIN_CLASS, filename));
		case PNG:
			return Main.class.getResource(ResourcePathFactory.getPNGFilePath(OriginPathToBin.MAIN_CLASS, filename));
		case LOGS:
			return Main.class.getResource(ResourcePathFactory.getLogFilePath(OriginPathToProjectDir.MAIN_CLASS, filename));
		case PROPERTIES:
			return Main.class.getResource(ResourcePathFactory.getPropertiesFilePath(OriginPathToBin.MAIN_CLASS, filename));
		}
		return null;
	}

	public Image toImage() {
		switch (type) {
		case CSS:
		case DATABASE:
		case FXML:
		case LOGS:
		case PROPERTIES:
			throw new ResourceAccessException(type.name(), "javafx.scene.image.Image");
		case PNG:
			return new Image("file:" + ResourcePathFactory.getPNGFilePath(OriginPathToBin.ROOT_DIRECTORY, filename));
		}
		return null;
	}

	public String toString() {
		switch (type) {
		case CSS:
			return ResourcePathFactory.getCSSFilePath(OriginPathToBin.ROOT_DIRECTORY, filename);
		case DATABASE:
			return ResourcePathFactory.getDatabaseFilePath(OriginPathToBin.ROOT_DIRECTORY, filename);
		case FXML:
			return ResourcePathFactory.getFXMLFilePath(OriginPathToBin.ROOT_DIRECTORY, filename);
		case PNG:
			return ResourcePathFactory.getPNGFilePath(OriginPathToBin.ROOT_DIRECTORY, filename);
		case LOGS:
			return ResourcePathFactory.getLogFilePath(OriginPathToProjectDir.ROOT_DIRECTORY, filename);
		case PROPERTIES:
			return ResourcePathFactory.getPropertiesFilePath(OriginPathToBin.ROOT_DIRECTORY, filename);
		}
		return null;
	}

	public InputStream toInputStream() {
		InputStream is = null;
		String filepath = null;
		try {
			switch (type) {
			case CSS:
			case DATABASE:
			case FXML:
			case LOGS:
				throw new ResourceAccessException(type.name(), "java.io.InputStream");
			case PROPERTIES:
				is = new FileInputStream(ResourcePathFactory.getPropertiesFilePath(OriginPathToBin.ROOT_DIRECTORY, filename));
				break;
			case PNG:
				is = new FileInputStream(ResourcePathFactory.getPNGFilePath(OriginPathToBin.ROOT_DIRECTORY, filename));
			}
		} catch (FileNotFoundException e) {
			throw new ResourceAccessException(filepath);
		}
		return is;
	}

	/*******************************************************************************************************
	 * 
	 * 		PUBLIC STATIC METHODS
	 * 
	 ********************************************************************************************************/
	
	public static ResourceProvider getDatabaseFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResourceType.DATABASE);
	}
	
	public static ResourceProvider getFXMLFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResourceType.FXML);
	}
	
	public static ResourceProvider getCSSFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResourceType.CSS);
	}
	
	public static ResourceProvider getLogFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResourceType.LOGS);
	}
	
	public static ResourceProvider getPNGFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResourceType.PNG);
	}
	
	public static ResourceProvider getPropertiesFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResourceType.PROPERTIES);
	}
}
