package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.chart.XYChart;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AnalyticsPopup extends Stage {

    private ObservableList<PieChart.Data> pieChartData;
    private LineChart<Number, Number> lineChart;
    
    private MonthAnalytics month = new MonthAnalytics();
    private WeekAnalytics week = new WeekAnalytics();

    public AnalyticsPopup() {
        this.setTitle("Analytics");
        initializeData();
        showPieChart(month.getMonthPieChart()); // By default, show the pie chart
    }

    private void initializeData() {
        pieChartData = createDummyPieChartData();
        lineChart = createDummyLineChart();
    }

    private ObservableList<PieChart.Data> createDummyPieChartData() {
        return FXCollections.observableArrayList(
                new PieChart.Data("Running", 40),
                new PieChart.Data("Biking", 20),
                new PieChart.Data("Gym", 20)
        );
    }

    private LineChart<Number, Number> createDummyLineChart() {
        final NumberAxis xAxis = new NumberAxis(1, 12, 1);
        xAxis.setLabel("Months");
        final NumberAxis yAxis = new NumberAxis(0.1, 50, 10);
        yAxis.setLabel("Time in Hours");
        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Monthly Activity Data");

        // Create dummy data
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();

        Random random = new Random();

        List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        for (int i = 0; i < months.size(); i++) {
            series1.getData().add(new XYChart.Data<>(i + 1, random.nextDouble() * 30));
            series2.getData().add(new XYChart.Data<>(i + 1, random.nextDouble() * 30));
        }

        lineChart.getData().addAll(series1, series2);

        return lineChart;
    }

    private void showPieChart(PieChart pieChart) {
//        PieChart chart = new PieChart(pieChartData);
//        chart.setTitle("Weekly Analytics");

        Button switchToLineChartButton = new Button("View Line Chart");
        switchToLineChartButton.getStyleClass().add("button"); // Apply button styling
        switchToLineChartButton.setOnAction(event -> showLineChart(month.getMonthLineChart()));
        
        Button switchToMontlyPieChartButton = new Button("Month");
        switchToMontlyPieChartButton.getStyleClass().add("button"); // Apply button styling
        switchToMontlyPieChartButton.setOnAction(event -> showPieChart(month.getMonthPieChart()));
        
        Button switchToWeeklyPieChartButton = new Button("Week");
        switchToWeeklyPieChartButton.getStyleClass().add("button"); // Apply button styling
        switchToWeeklyPieChartButton.setOnAction(event -> showPieChart(week.getWeekPieChart()));


//        PieChart monthPieChart = month.getMonthPieChart();
//        PieChart weekPieChart = week.getWeekPieChart();
        
        VBox vbox = new VBox(switchToLineChartButton, switchToMontlyPieChartButton, switchToWeeklyPieChartButton);
        vbox.setSpacing(5);
        HBox root = new HBox(pieChart, vbox);
        Scene scene = new Scene(root, 650, 500);
        scene.getStylesheets().add("/styles.css"); // Load CSS file for button styling
        this.setScene(scene);
    }

    private void showLineChart(LineChart lineChart) {
    	
        Button switchToPieChartButton = new Button("View Pie Chart");
        switchToPieChartButton.getStyleClass().add("button"); // Apply button styling
        switchToPieChartButton.setOnAction(event -> showPieChart(month.getMonthPieChart()));
        
        
        
        Button switchToMonthlyPieChartButton = new Button("Month");
        switchToMonthlyPieChartButton.getStyleClass().add("button"); // Apply button styling
        switchToMonthlyPieChartButton.setOnAction(event -> {
        	showLineChart(month.getMonthLineChart());
        });
        
        Button switchToWeeklyPieChartButton = new Button("Week");
        switchToWeeklyPieChartButton.getStyleClass().add("button"); // Apply button styling
        switchToWeeklyPieChartButton.setOnAction(event -> {
        	showLineChart(week.getWeekLineChart());
        });
        
//        LineChart monthLineChart = month.getMonthLineChart();
//        LineChart weekLineChart = week.getWeekLineChart();

        VBox vbox = new VBox(switchToPieChartButton, switchToMonthlyPieChartButton, switchToWeeklyPieChartButton);
        vbox.setSpacing(5);
        HBox root = new HBox(lineChart, vbox);
        Scene scene = new Scene(root, 650, 500);
        scene.getStylesheets().add("/styles.css"); // Load CSS file for button styling
        this.setScene(scene);
    }
}