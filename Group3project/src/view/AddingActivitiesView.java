package view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Activity;

public class AddingActivitiesView extends Stage {
	String chosenActivity = "";
	Text activityText = new Text("Select Activity:");
	ListView<String> list = new ListView<String>();
	static ObservableList<String> items =FXCollections.observableArrayList (
		    "Running", "Hiking", "Brushing Teeth", "Vaccuming", "Washing Dishes", "Gym");
	Text selectedActivity = new Text();
	GridPane grid = new GridPane();
	VBox vbox = new VBox();
	
	public AddingActivitiesView() {
		
		
		
		
		
		
		
		grid.setPadding(new Insets(5.0));
		grid.setHgap(5.0);
		grid.setVgap(5.0);
		
		addButtons();
		
		Text durationText = new Text("Select Length:");
		
		int initialValue = 15;
		Slider slider = new Slider(0, 60, initialValue);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(15f);
		slider.setBlockIncrement(15);
		
		final int[] tempDuration = {initialValue};
		
		// Adding Listener to value property.
        slider.valueProperty().addListener(
             new ChangeListener<Number>() {
 
            public void changed(ObservableValue <? extends Number > 
                      observable, Number oldValue, Number newValue)
            {
            	tempDuration[0] = newValue.intValue();
                durationText.setText("Select Length: " + newValue.intValue());
            }
        });
		
		// Button to start activity
		Button goButton = new Button("GO!");
		goButton.setOnAction(new EventHandler<ActionEvent>() {

			// When clicked creates new activity and inserts into database
			@Override
			public void handle(ActionEvent arg0) {
				int time = (int) slider.getValue();
				java.util.Date dt = new java.util.Date();
				int day = dt.getDate();
				int month = dt.getMonth();
				int year = dt.getYear();
				
				chosenActivity = list.getSelectionModel().getSelectedItem();
				selectedActivity.setText(chosenActivity);
				
				Activity temp = new Activity(chosenActivity, 15, month, year, time);
				try {
					MainWindow.db.insertActivity(temp);
					MainWindow.activitiesList.add(temp);
					
					LinkedList<Activity> test = MainWindow.db.getMonth(11);
					for (Activity a : test) {
						System.out.println(a.getDate());
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				final Stage dialog = new MotivationalPopup(tempDuration[0] * 60);
				dialog.initModality(Modality.APPLICATION_MODAL);

				dialog.show();
				close();
				
			}

		});
		
		// Button to start activity
				Button createNewActivity = new Button("Create new activity");
				createNewActivity.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						final Stage newActivity = new Stage();
						Group root = new Group();
						Scene s = new Scene(root, 200, 100, Color.LIGHTBLUE);
						s.getStylesheets().add("/styles.css"); // Load CSS file
						VBox vbox = new VBox();
						vbox.setAlignment(Pos.CENTER);
						
						TextField tf = new TextField();
						
						
						Button add = new Button("Add");
						add.setOnAction(event -> {
							items.add(tf.getText());
				        	newActivity.close();
				        });
						add.getStyleClass().add("button"); // Apply button styling
						
						vbox.getChildren().addAll(tf, add);
						
						root.getChildren().addAll(vbox);
						newActivity.setScene(s);
						newActivity.initModality(Modality.APPLICATION_MODAL);
						newActivity.show();
						
					}
					
				});
		
		//HBox hbox = new HBox(activityText, createNewActivity);
		
		vbox.getChildren().addAll(activityText, createNewActivity, selectedActivity, grid, durationText, slider, goButton);
		vbox.setAlignment(Pos.CENTER);
		
		Scene activityScene = new Scene(vbox, Color.LIGHTBLUE);
		activityScene.getStylesheets().add("/styles.css"); // Load CSS file
		this.setScene(activityScene);
		
	}
	
	private void addButtons() {

		
		
		// Button to start activity
				Button goButton = new Button("GO!");
				goButton.setOnAction(new EventHandler<ActionEvent>() {

					// When clicked creates new activity and inserts into database
					@Override
					public void handle(ActionEvent arg0) {

						
					}

				});
		
		list.setItems(items);
		
		grid.add(list, 0, 0);
		
		
		
		
	}
	
	public String getDateTime() {
		java.util.Date dt = new java.util.Date();

		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String output = sdf.format(dt);
		
		return output;
	}

}
