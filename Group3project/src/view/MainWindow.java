package view;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.LinkedList;

import controller.AccountManager;
import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Callback;
import model.Activity;
import model.Database;

public class MainWindow extends Application {
	
	AccountManager accountSystem;
	
	LoginCreateAccountPane loginPane;
	Database db = new Database("db");
	ObservableList<Activity> activitiesList = null;

	@Override
	public void start(Stage Primarystage) {

//		try {
//			db.createTable();
//
//		} catch (SQLException e) {
//			fail("Unable to create database");
//			e.printStackTrace();
//		}
		
		accountSystem = AccountManager.getInstance(false);

		Button addActivityButton = new Button();
		addActivityButton.setText("Add New Activity");
		addActivityButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage dialog = new AddingActivitiesView();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(Primarystage);
				//Scene addActivityScene = createActivityScene();
				//dialog.setScene(addActivityScene);
				dialog.show();
			}
		});

		Button analyticsButton = new Button();
		analyticsButton.setText("Analytics");
		analyticsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(Primarystage);
//				VBox dialogVbox = new VBox(20);
//				dialogVbox.getChildren().add(new Text("Placeholder"));
				Scene dialogScene = createdialogScene();
				dialog.setScene(dialogScene);
				dialog.show();
			}
		});

		// Date Hbox
		HBox hboxNavigationButtons = new HBox(5);
		hboxNavigationButtons.setPadding(new Insets(10, 10, 10, 25));
		hboxNavigationButtons.getChildren().addAll(addActivityButton, analyticsButton);

		// Activities table
		TableView<Activity> tableView = new TableView<Activity>();
		tableView.setPlaceholder(new Label("No recent activities added"));

		TableColumn<Activity, String> categoryColumn = new TableColumn<>("Category");
//		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
		categoryColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Activity, String>, ObservableValue<String>>(){
					public ObservableValue<String> call(CellDataFeatures<Activity, String> param){
						SimpleStringProperty category = new SimpleStringProperty(param.getValue().getActivity());
						return category;
					}
				});

		TableColumn<Activity, String> activityColumn = new TableColumn<>("Activity");
//		activityColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));
		activityColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Activity, String>, ObservableValue<String>>(){
			public ObservableValue<String> call(CellDataFeatures<Activity, String> param){
				SimpleStringProperty activity = new SimpleStringProperty(param.getValue().getActivity());
				return activity;
			}
		});

		TableColumn<Activity, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

		TableColumn<Activity, Integer> durationColumn = new TableColumn<>("Duration");
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

		TableColumn<Activity, Integer> moodColumn = new TableColumn<>("Mood");
		moodColumn.setCellValueFactory(new PropertyValueFactory<>("mood"));

		tableView.getColumns().add(categoryColumn);
		tableView.getColumns().add(activityColumn);
		tableView.getColumns().add(dateColumn);
		tableView.getColumns().add(durationColumn);
		tableView.getColumns().add(moodColumn);

		try {
			activitiesList = db.getObservableDatabase();

			// Add a ListChangeListener that will execute as soon as the contents of the
			// list is changed.
			activitiesList.addListener(new ListChangeListener<Activity>() {

				@Override
				public void onChanged(Change<? extends Activity> arg0) {
					tableView.setItems(activitiesList);

				}
			});

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Creating a vbox to hold the pagination
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 25));
		vbox.getChildren().addAll(hboxNavigationButtons, tableView);
		// Setting the stage
		

		Primarystage.setTitle("TimeManager 1st Ed");
//		loginPane = new LoginCreateAccountPane(accountSystem);
//		Scene scene = new Scene(loginPane, 800, 600);
//		Primarystage.setScene(scene);
//		Primarystage.setResizable(false);
//		Primarystage.show();
		
		Group root = new Group(vbox);
		Scene addActivityScene = new Scene(root, 800, 600, Color.WHITE);
		Primarystage.setScene(addActivityScene);
		Primarystage.setResizable(false);
		Primarystage.show();
	}

	// Deletes the file when closed
	// ONLY FOR TESTING NEED TO CHANGE
	@Override
	public void stop() {
		try {
			db.delete();
		} catch (SQLException e) {
			System.out.println("Unable to delete database");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	private Scene createActivityScene() {
		Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);

		// Category selection
		Label labelCategory = new Label("Select Category:");
		labelCategory.setFont(font);
		String categories[] = { "Health", "Hygiene", "Fitness", "Gardening" };
		ComboBox<String> comboBoxCategory = new ComboBox<String>(FXCollections.observableArrayList(categories));

		// Activity selection
		Label labelActivity = new Label("Select Activity:");
		labelActivity.setFont(font);
		String activities[] = { "Running", "Biking", "Gym" };
		ComboBox<String> comboBoxActivity = new ComboBox<String>(FXCollections.observableArrayList(activities));

		// Date selection
		Label labelDate = new Label("Select Date:");
		labelDate.setFont(font);
		Spinner<Integer> spinnerMonth = new Spinner<Integer>(1, 12, 1);
		spinnerMonth.setEditable(true);
		spinnerMonth.setPrefSize(75, 25);
		Label labelMonth = new Label("Month: ");
		Spinner<Integer> spinnerDay = new Spinner<Integer>(1, 31, 1);
		spinnerDay.setEditable(true);
		spinnerDay.setPrefSize(75, 25);
		Label labelDay = new Label("Day: ");
		Spinner<Integer> spinnerYear = new Spinner<Integer>(1900, 2999, 2023);
		spinnerYear.setEditable(true);
		spinnerYear.setPrefSize(75, 25);
		Label labelYear = new Label("Year: ");

		// Time selection
		Label labelTime = new Label("Select Time:");
		labelTime.setFont(font);
		Spinner<Integer> spinnerHour = new Spinner<Integer>(1, 24, 1);
		spinnerHour.setEditable(true);
		spinnerHour.setPrefSize(75, 25);
		Label labelHour = new Label("Hour: ");
		Spinner<Integer> spinnerMinutes = new Spinner<Integer>(1, 60, 1);
		spinnerMinutes.setEditable(true);
		spinnerMinutes.setPrefSize(75, 25);
		Label labelMinute = new Label("Minutes: ");

		// Duration selection
		Label labelDuration = new Label("Select Duration:");
		labelDuration.setFont(font);
		Spinner<Integer> spinnerDurationHour = new Spinner<Integer>(1, 24, 1);
		spinnerDurationHour.setEditable(true);
		spinnerDurationHour.setPrefSize(75, 25);
		Label labelDurationHour = new Label("Hour: ");
		Spinner<Integer> spinnerDurationMinutes = new Spinner<Integer>(1, 60, 1);
		spinnerDurationMinutes.setEditable(true);
		spinnerDurationMinutes.setPrefSize(75, 25);
		Label labelDurationMinute = new Label("Minutes: ");

		// Duration selection
		Label labelMood = new Label("Select Mood:");
		labelMood.setFont(font);
		Spinner<Integer> spinnerMood = new Spinner<Integer>(1, 5, 1);
		spinnerMood.setEditable(true);
		spinnerMood.setPrefSize(75, 25);

		// Date Hbox
		HBox hboxDate = new HBox(5);
		hboxDate.setPadding(new Insets(10, 10, 10, 25));
		hboxDate.getChildren().addAll(labelMonth, spinnerMonth, labelDay, spinnerDay, labelYear, spinnerYear);

		// Time Hbox
		HBox hboxTime = new HBox(5);
		hboxTime.setPadding(new Insets(10, 10, 10, 25));
		hboxTime.getChildren().addAll(labelHour, spinnerHour, labelMinute, spinnerMinutes);

		// Duration Hbox
		HBox hboxDuration = new HBox(5);
		hboxDuration.setPadding(new Insets(10, 10, 10, 25));
		hboxDuration.getChildren().addAll(labelDurationHour, spinnerDurationHour, labelDurationMinute,
				spinnerDurationMinutes);

		// Add button
		Button addButton = new Button("Add activity");
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			// When clicked creates new activity and inserts into database
			@Override
			public void handle(ActionEvent arg0) {
				String tempCategory = comboBoxCategory.getValue().toString() + "";
				String tempActivity = comboBoxActivity.getValue().toString() + "";
				String tempDateTime = spinnerYear.getValue() + "-" + spinnerMonth.getValue() + "-"
						+ spinnerDay.getValue() + " " + spinnerHour.getValue() + ":" + spinnerMinutes.getValue()
						+ ":00";
				;
				int tempDayOfWeek = 0;
				int tempDuration = ((int) spinnerDurationHour.getValue() * 60)
						+ (int) spinnerDurationMinutes.getValue();
				int tempMood = (int) spinnerMood.getValue();
				Activity temp = new Activity(tempActivity, tempDayOfWeek, tempDuration,
						tempMood, 1);

				System.out.println("Adding " + temp.toString());

				try {
					db.insertActivity(temp);
					activitiesList.add(temp);
				} catch (SQLException e) {
					System.out.println("Unable to insert activity");
					e.printStackTrace();
				}

				Stage stage = (Stage) addButton.getScene().getWindow();
				stage.close();
			}

		});

		// Creating a vbox to hold the pagination
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 25));
		vbox.getChildren().addAll(labelCategory, comboBoxCategory, labelActivity, comboBoxActivity, labelDate, hboxDate,
				labelTime, hboxTime, labelDuration, hboxDuration, labelMood, spinnerMood, addButton);
		// Setting the stage
		Group root = new Group(vbox);
		Scene addActivityScene = new Scene(root, 450, 450, Color.WHITE);

		return addActivityScene;
	}
	
	private Scene createdialogScene(){
		
		PieChart.Data d1 = new PieChart.Data("Running", 40);
		PieChart.Data d2 = new PieChart.Data("Biking", 20);
		PieChart.Data d3 = new PieChart.Data("Gym", 20);
		
		ObservableList<PieChart.Data> datalist = FXCollections.observableArrayList();
		datalist.add(d1);
		datalist.add(d2);
		datalist.add(d3);
		
		PieChart piechart = new PieChart(datalist);
		piechart.setPrefWidth(450);
		piechart.setPrefHeight(450);
		
		GridPane root = new GridPane();
		root.add(piechart, 1, 1);
		
		Scene dialogScene = new Scene(root, 600, 600);
		return dialogScene;
	}

}
