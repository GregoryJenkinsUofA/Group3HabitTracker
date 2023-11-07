package view;


import javafx.scene.layout.StackPane;

public class HomePangeView extends StackPane { // choose whatever the pane you want
	private AddingActivitiesView next;
	
	HomePangeView(AddingActivitiesView next){
		this.next = next;
		createGUI();
		setupListener();
	}

	private void createGUI() {
		// TODO set up the element for the pane
		
	}
	
	private void setupListener() {
		// TODO adding event handler
		
	}
	
	private void callNext() {
		getScene().setRoot(next);
	}
}
