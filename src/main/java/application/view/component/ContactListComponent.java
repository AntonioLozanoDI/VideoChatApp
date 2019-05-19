package application.view.component;

import javafx.scene.Parent;

public class ContactListComponent<P extends Parent> {

	protected P parent;
	
	public final P getParent() {
		return parent;
	}
}
