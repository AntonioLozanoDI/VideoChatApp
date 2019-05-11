package application.view.modal;

import java.util.logging.Logger;

import javafx.stage.Stage;
import utils.logging.ApplicationLoggers;
import utils.logging.LoggingUtils;

public abstract class ApplicationModal {
	
	protected static Logger logger = ApplicationLoggers.viewLogger;
	
	protected abstract void buildModal(Stage owner);
	
	public abstract void showView();
	
	public static final <T extends ApplicationModal> T build(Class<T> modalClass, Stage owner) {
		T view = null;
		try {
			view = modalClass.newInstance();
			view.buildModal(owner);
			
			logger.info(String.format("%s successfully initialized. Showing view ...", modalClass.getSimpleName()));
			
		} catch (InstantiationException ie) {
			String trace = String.format(
					"An error ocurred during %s instantiation. .%nParams%n\t- classToLoad: %s%n\t- loadedView: %s%n\t- ExceptionType: %s%n%s",
					ApplicationModal.class.getSimpleName(), ie.getLocalizedMessage(), modalClass, view, ie.getClass(),LoggingUtils.getStackTrace(ie));
			logger.severe(trace);
			System.exit(-1);
		} catch (Exception e) {
			String trace = String.format(
					"Unexpected error ocurred during %s initialization. .%nParams%n\t- classToLoad: %s%n\t- loadedView: %s%n\t- ExceptionType: %s%n%s",
					ApplicationModal.class.getSimpleName(), e.getLocalizedMessage(), modalClass, view, e.getClass(),LoggingUtils.getStackTrace(e));
			logger.severe(trace);
			System.exit(-1);
		}
		return view;
	}
}
