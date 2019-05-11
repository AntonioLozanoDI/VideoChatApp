package utils.resources.exception;

public class ResourceAccessException extends RuntimeException {

	private static final long serialVersionUID = -462978028757678310L;

	public ResourceAccessException(String fileType, String unsupportedType) {
		super(String.format("Unable to convert %s file to %s", fileType,unsupportedType));
	}
	
	public ResourceAccessException(String filePath) {
		super(String.format("Unable to access resource on path %s", filePath));
	}
}
