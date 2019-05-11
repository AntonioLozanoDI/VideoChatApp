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

	private	String path;
	private	ApplicationResource type;

	enum ApplicationResource {
		DATABASE, PNG, FXML, PROPERTIES, CSS, LOGS;
	}

	private ApplicationResourceProvider(String path, ApplicationResource type) {
		this.path = path;
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
			return new File(ResourcePathFactory.getCSSFilePath(OriginPathToBin.ROOT_DIRECTORY, path));
		case DATABASE:
			return new File(ResourcePathFactory.getDatabaseFilePath(OriginPathToBin.ROOT_DIRECTORY, path));
		case FXML:
			return new File(ResourcePathFactory.getFXMLFilePath(OriginPathToBin.ROOT_DIRECTORY, path));
		case PNG:
			return new File(ResourcePathFactory.getPNGFilePath(OriginPathToBin.ROOT_DIRECTORY, path));
		case LOGS:
			return new File(ResourcePathFactory.getLogFilePath(OriginPathToProjectDir.ROOT_DIRECTORY, path));
		case PROPERTIES:
			return new File(ResourcePathFactory.getPropertiesFilePath(OriginPathToBin.ROOT_DIRECTORY, path));
		}
		return null;
	}

	public URL toURL() {
		switch (type) {
		case CSS:
			return Main.class.getResource(ResourcePathFactory.getCSSFilePath(OriginPathToBin.MAIN_CLASS, path));
		case DATABASE:
			return Main.class.getResource(ResourcePathFactory.getDatabaseFilePath(OriginPathToBin.MAIN_CLASS, path));
		case FXML:
			return Main.class.getResource(ResourcePathFactory.getFXMLFilePath(OriginPathToBin.MAIN_CLASS, path));
		case PNG:
			return Main.class.getResource(ResourcePathFactory.getPNGFilePath(OriginPathToBin.MAIN_CLASS, path));
		case LOGS:
			return Main.class.getResource(ResourcePathFactory.getLogFilePath(OriginPathToProjectDir.MAIN_CLASS, path));
		case PROPERTIES:
			return Main.class.getResource(ResourcePathFactory.getPropertiesFilePath(OriginPathToBin.MAIN_CLASS, path));
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
			return new Image("file:" + ResourcePathFactory.getPNGFilePath(OriginPathToBin.ROOT_DIRECTORY, path));
		}
		return null;
	}

	public String toString() {
		switch (type) {
		case CSS:
			return ResourcePathFactory.getCSSFilePath(OriginPathToBin.ROOT_DIRECTORY, path);
		case DATABASE:
			return ResourcePathFactory.getDatabaseFilePath(OriginPathToBin.ROOT_DIRECTORY, path);
		case FXML:
			return ResourcePathFactory.getFXMLFilePath(OriginPathToBin.ROOT_DIRECTORY, path);
		case PNG:
			return ResourcePathFactory.getPNGFilePath(OriginPathToBin.ROOT_DIRECTORY, path);
		case LOGS:
			return ResourcePathFactory.getLogFilePath(OriginPathToProjectDir.ROOT_DIRECTORY, path);
		case PROPERTIES:
			return ResourcePathFactory.getPropertiesFilePath(OriginPathToBin.ROOT_DIRECTORY, path);
		}
		return null;
	}

	public InputStream toInputStream() {
		InputStream is = null;
		try {
			switch (type) {
			case CSS:
				is = new FileInputStream(ResourcePathFactory.getCSSFilePath(OriginPathToBin.ROOT_DIRECTORY, path));
			case DATABASE:
				is = new FileInputStream(ResourcePathFactory.getDatabaseFilePath(OriginPathToBin.ROOT_DIRECTORY, path));
			case FXML:
				is = new FileInputStream(ResourcePathFactory.getFXMLFilePath(OriginPathToBin.ROOT_DIRECTORY, path));
			case PNG:
				is = new FileInputStream(ResourcePathFactory.getPNGFilePath(OriginPathToBin.ROOT_DIRECTORY, path));
			case LOGS:
				is = new FileInputStream(ResourcePathFactory.getLogFilePath(OriginPathToProjectDir.ROOT_DIRECTORY, path));
			case PROPERTIES:
				is = new FileInputStream(ResourcePathFactory.getPropertiesFilePath(OriginPathToBin.ROOT_DIRECTORY, path));
			}
		} catch (FileNotFoundException e) {
			throw new ResourceAccessException(path);
		}
		return is;
	}

	/*******************************************************************************************************
	 * 
	 * 		PUBLIC STATIC METHODS
	 * 
	 ********************************************************************************************************/
	
	public static ResourceProvider getDatabaseFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResource.DATABASE);
	}
	
	public static ResourceProvider getFXMLFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResource.FXML);
	}
	
	public static ResourceProvider getCSSFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResource.CSS);
	}
	
	public static ResourceProvider getLogFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResource.LOGS);
	}
	
	public static ResourceProvider getPNGFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResource.PNG);
	}
	
	public static ResourceProvider getPropertiesFile(String fileName) {
		return new ApplicationResourceProvider(Objects.requireNonNull(fileName), ApplicationResource.PROPERTIES);
	}
}
