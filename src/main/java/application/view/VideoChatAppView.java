package application.view;

import javafx.stage.Stage;

public class VideoChatAppView extends ApplicationView {

	/*
	 * Es necesario dejar un constructor por defecto visible, para que se pueda
	 * invocar en el metodo <T extends ApplicationView> T launchView(Class<T>
	 * viewClass, Stage primaryStage) la creacion de una instancia de la vista
	 * deseada T view = viewClass.newInstance() en el tipo ApplicationView.
	 */
	
	public VideoChatAppView() {}

	@Override
	protected void buildParentView() {
		
	}

	@Override
	protected void buildChildrenViews() {
	
	}

	@Override
	protected void setScene(Stage stage) {
		
	}
}
