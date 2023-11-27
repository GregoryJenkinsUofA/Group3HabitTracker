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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Activity;

public class AddingActivitiesView extends Stage {
	String chosenActivity = "";
	Text activityText = new Text("Select Activity:");
	ListView<String> list = new ListView<String>();
	ObservableList<String> items =FXCollections.observableArrayList (
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
				
				Activity temp = new Activity(chosenActivity, day, month, year, time);
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
		
		vbox.getChildren().addAll(activityText, selectedActivity, grid, durationText, slider, goButton);
		vbox.setAlignment(Pos.CENTER);
		
		this.setScene(new Scene(vbox));
		
	}
	
	private void addButtons() {
		/*
		FileOutputStream fos;
		ObjectOutputStream oos;
		
		ArrayList<String> activities = new ArrayList<String>();
		activities.add("Running");
		activities.add("Hiking");
		activities.add("Brushing Teeth");
		activities.add("Vaccuming");
		activities.add("Washing Dishes");
		activities.add("Gym");
		
		
		try {
			fos = new FileOutputStream("activities");
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(activities);
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int i = 0;
		int j = 0;
		for (String s : activities) {
			Button temp = new Button(s);
			grid.add(temp, i, j);
			
			i++;
			if (i == 3) {
				i = 0;
				j++;
			}
		}
		
		
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
		*/
		
		
		
		
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
