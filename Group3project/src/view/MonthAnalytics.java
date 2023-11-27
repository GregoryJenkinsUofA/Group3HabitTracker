package view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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

public class MonthAnalytics extends Stage {
	GridPane grid = new GridPane();
	HBox hbox = new HBox();
	
	public MonthAnalytics() {
		
		java.util.Date dt = new java.util.Date();
		int month = dt.getMonth();
		LinkedList<Activity> monthActivities = null;
		
		try {
			
			monthActivities = MainWindow.db.getMonth(month);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashMap<String, LinkedList<Activity>> hm = new HashMap<String, LinkedList<Activity>>();
		
		for (Activity a : monthActivities) {
			if (hm.containsKey(a.getActivity())) {
				LinkedList<Activity> temp = hm.get(a.getActivity());
				temp.add(a);
			} else {
				LinkedList<Activity> temp = new LinkedList<Activity>();
				temp.add(a);
				hm.put(a.getActivity(), temp);
			}
		}
		
		//Defining X axis  
		NumberAxis xAxis = new NumberAxis(1, getDaysInMonth(month), 1); 
		xAxis.setLabel("Day"); 
		        
		//Defining y axis 
		NumberAxis yAxis = new NumberAxis(0, 10, 1); 
		yAxis.setLabel("Activities Completed");
		
		LineChart linechart = new LineChart(xAxis, yAxis);
		linechart.setCreateSymbols(false);
		
		//Preparing ObservbleList object         
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		
		for (HashMap.Entry<String, LinkedList<Activity>> entry : hm.entrySet()) {
			String activityName = entry.getKey();
			LinkedList<Activity> list = entry.getValue();
			
			XYChart.Series series = new XYChart.Series(); 
			series.setName(activityName);
			
			int days[] = new int[getDaysInMonth(month)];
			
			for (Activity a : list) {
				days[a.getDay()]++;
			}

			for (int i = 0; i < days.length; i++) {
				series.getData().add(new XYChart.Data(i, days[i]));
			}
			
			pieChartData.add(new PieChart.Data(activityName, list.size()));
			linechart.getData().add(series);
		}
		
		
		//Creating a Pie chart 
		PieChart pieChart = new PieChart(pieChartData);
		pieChart.setData(pieChartData);
		
		
		
		
		grid.setPadding(new Insets(5.0));
		grid.setHgap(5.0);
		grid.setVgap(5.0);
		
		
		Text topText = new Text("Past Month");
		
		

		
		hbox.getChildren().addAll(topText, linechart, pieChart);
		hbox.setAlignment(Pos.CENTER);
		
		this.setScene(new Scene(hbox));
		
	}
	
	private static int getDaysInMonth(int month) {
		int days = 0;
		
		switch(month) {
		case 1:
			days = 31;
			break;
		case 2:
			days = 28;
			break;
		case 3:
			days = 31;
			break;
		case 4:
			days = 30;
			break;
		case 5:
			days = 31;
			break;
		case 6:
			days = 30;
			break;
		case 7:
			days = 31;
			break;
		case 8:
			days = 31;
			break;
		case 9:
			days = 30;
			break;
		case 10:
			days = 31;
			break;
		case 11:
			days = 30;
			break;
		case 12:
			days = 31;
			break;
		}
		
		return days;
	}

}
