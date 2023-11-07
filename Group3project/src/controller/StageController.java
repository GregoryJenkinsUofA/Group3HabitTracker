package controller;

import java.io.IOException;

import javafx.scene.control.Button;

public class StageController {
    private Button button;

    public void openSettings() throws IOException {
        Parent settingsPage = FXMLLoader.load(getClass().getResource("settings.fxml"));
        Scene settingsScene = new Scene(settingsPage);
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(settingsScene);
    }
}
