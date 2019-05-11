package application.view;

import java.util.logging.Logger;

import javafx.stage.Stage;
import utils.logging.ApplicationLoggers;

public abstract class ApplicationView {

	protected static Logger logger = ApplicationLoggers.viewLogger;

	protected abstract void buildParentView();

	protected abstract void buildChildrenViews();

	protected abstract void setScene(Stage primaryStage);

	public static final <T extends ApplicationView> T launchView(Class<T> viewClass, Stage primaryStage) {
		T view = null;
		try {
			view = viewClass.newInstance();
			view.buildParentView();
			view.buildChildrenViews();
			view.setScene(primaryStage);

			logger.info(String.format("%s successfully initialized. Showing view ...", viewClass.getSimpleName()));
			
			primaryStage.show();
		} catch (Exception e) {
			String trace = String.format(
					"An error ocurred during %s launch. Message: %s%nParams%n\t- classToLoad: %s%n\t- loadedView: %s%n\t- ExceptionType: %s%n",
					ApplicationView.class.getSimpleName(), e.getLocalizedMessage(), viewClass, view, e.getClass());
			
			logger.severe(trace);
			logger.severe("Exiting from application.");
			
			System.exit(-1);
		}
		return view;
	}
}
