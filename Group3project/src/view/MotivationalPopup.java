package view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    private int timeInSeconds;

    public MotivationalPopup(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
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

        Thread timerThread = new Thread(() -> {
            while (timeInSeconds > 0) {
                try {
                    Thread.sleep(1000);
                    this.timeInSeconds--;
                    updateTimerLabel();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Timer ran out, close the stage
            Platform.runLater(() -> {
                Stage stage = (Stage) quoteLabel.getScene().getWindow();
                stage.close();
                this.close();
            });
        });
        timerThread.start();
        
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
            int hours = timeInSeconds / 3600;
            int minutes = (timeInSeconds % 3600) / 60;
            int seconds = timeInSeconds % 60;

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