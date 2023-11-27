package view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MotivationalPopup extends Stage {

    private ArrayList<String> quotes;
    private Label quoteLabel;
    private Label authorLabel;
    private Label timerLabel = new Label();
    private int[] timeInSeconds = {300}; // Initial time in seconds

    public MotivationalPopup(int initialTimeInSeconds) {
        this.timeInSeconds[0] = initialTimeInSeconds;
        this.quoteLabel = new Label();
        this.authorLabel = new Label();

        quoteLabel.setWrapText(true);

        // Initialize timerLabel
        timerLabel.setStyle("-fx-font-size: 12px;");
        loadQuotesFromFile();
        chooseRandomQuote();

        quoteLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        authorLabel.setStyle("-fx-font-size: 12px;");
        timerLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(quoteLabel);
        borderPane.setCenter(authorLabel);
        borderPane.setBottom(timerLabel);

        updateTimerLabel();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
           // timeInSeconds[0] = timeInSeconds[0] - 1; // Update array element
        	if (timeInSeconds[0] >= 0) {
        	    timeInSeconds[0] = timeInSeconds[0] - 1; // Update array element
        	    updateTimerLabel();
        	} else {
                // Timer ran out, close the stage
                close();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        this.setScene(new Scene(borderPane));
    }

    private void loadQuotesFromFile() {
        quotes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("quotes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                quotes.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(quotes.size() / 2) * 2;
        String quote = quotes.get(index);
        String author = quotes.get(index + 1);

        // Set quote and author labels
        quoteLabel.setText(quote);
        authorLabel.setText("- " + author);
    }

    private void updateTimerLabel() {
        Platform.runLater(() -> {
            int hours = Math.max(timeInSeconds[0] / 3600, 0);
            int minutes = Math.max((timeInSeconds[0] % 3600) / 60, 0);
            int seconds = Math.max(timeInSeconds[0] % 60, 0);

            String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timerLabel.setText("Time remaining: " + formattedTime);
        });
    }

    public Label getQuoteLabel() {
        return quoteLabel;
    }

    public Label getAuthorLabel() {
        return authorLabel;
    }

    public Label getTimerLabel() {
        return timerLabel;
    }
}