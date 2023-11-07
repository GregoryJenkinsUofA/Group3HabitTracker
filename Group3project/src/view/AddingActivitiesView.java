package view;

import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Activity;

public class AddingActivitiesView extends Stage {
	String chosenActivity = "";
	Text activityText = new Text("Select Activity:");
	Text selectedActivity = new Text();
	GridPane grid = new GridPane();
	VBox vbox = new VBox();
	
	public AddingActivitiesView() {
		
		
		
		
		
		
		
		grid.setPadding(new Insets(5.0));
		grid.setHgap(5.0);
		grid.setVgap(5.0);
		
		addButtons();
		
		Text durationText = new Text("Select Length:");
		
		Slider slider = new Slider(0, 120, 15);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(15f);
		slider.setBlockIncrement(15);
		
		// Adding Listener to value property.
        slider.valueProperty().addListener(
             new ChangeListener<Number>() {
 
            public void changed(ObservableValue <? extends Number > 
                      observable, Number oldValue, Number newValue)
            {
 
                durationText.setText("Select Length: " + newValue.intValue());
            }
        });
		
		// Button to start activity
		Button goButton = new Button("GO!");
		goButton.setOnAction(new EventHandler<ActionEvent>() {

			// When clicked creates new activity and inserts into database
			@Override
			public void handle(ActionEvent arg0) {
				
				Activity temp = new Activity(chosenActivity, 1, 1, 1, 1);
				
			}

		});
		
		vbox.getChildren().addAll(activityText, selectedActivity, grid, durationText, slider, goButton);
		vbox.setAlignment(Pos.CENTER);
		
		this.setScene(new Scene(vbox));
		
	}
	
	private void addButtons() {
		
		Button activity1 = new Button("Running");
		Button activity2 = new Button("Hiking");
		Button activity3 = new Button("Brushing Teeth");
		Button activity4 = new Button("Vaccuming");
		Button activity5 = new Button("Washing Dishes");
		Button activity6 = new Button("Gym");

		grid.add(activity1, 0, 0);
		grid.add(activity2, 1, 0);
		grid.add(activity3, 2, 0);
		grid.add(activity4, 0, 1);
		grid.add(activity5, 1, 1);
		grid.add(activity6, 2, 1);
		
		activity1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				chosenActivity = activity1.getText();
				selectedActivity.setText(chosenActivity);
				
			}
			
		});
		
		activity2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				chosenActivity = activity2.getText();
				selectedActivity.setText(chosenActivity);				
			}
			
		});
		
		activity3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				chosenActivity = activity3.getText();
				selectedActivity.setText(chosenActivity);				
			}
			
		});
		
		activity4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				chosenActivity = activity4.getText();
				selectedActivity.setText(chosenActivity);				
			}
			
		});
		
		activity5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				chosenActivity = activity5.getText();
				selectedActivity.setText(chosenActivity);				
			}
			
		});
		
		activity6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				chosenActivity = activity6.getText();
				selectedActivity.setText(chosenActivity);				
			}
			
		});
		
	}

}
