package view;

import controller.AccountManager;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Activity;
import model.Database;


public class LoginCreateAccountPane extends GridPane {

    private AccountManager accountManager;
    private Label statusLabel;

    private GridPane loggedInFields;
    private GridPane loggedOutFields;

    private TextField usernameField;
    private PasswordField passwordInField;
    private PasswordField passwordConfirm;

    public LoginCreateAccountPane(AccountManager accountManager) {
        super();
        this.accountManager = AccountManager.getInstance(false);
        statusLabel = new Label("Log in or create account below:");
        usernameField = new TextField();
        passwordInField = new PasswordField();
        passwordConfirm = new PasswordField();
        setElementProperties();
        arrangeElements();
    }

    private void setElementProperties() {
        this.setPrefWidth(400);
        this.setPrefHeight(200);
        this.getStyleClass().add("gridpane-padded");
        statusLabel.setPrefWidth(400);
        usernameField.setPrefWidth(200);
        passwordInField.setPrefWidth(200);
        passwordConfirm.setPrefWidth(200);
    }

    private void arrangeElements() {
        this.add(statusLabel, 0, 0, 2, 1);

        loggedInFields = new GridPane();
        loggedInFields.getStyleClass().add("gridpane-padded");
        loggedInFields.setPrefSize(400, 60);
        Button logoutButton = createLogoutButton();
        loggedInFields.add(logoutButton, 0, 0);
        this.add(loggedInFields, 0, 1, 2, 1);
        loggedInFields.setVisible(false);

        loggedOutFields = new GridPane();
        loggedOutFields.getStyleClass().add("gridpane-padded");
        loggedOutFields.setPrefSize(400, 60);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setPrefWidth(180);
        loggedOutFields.add(usernameLabel, 0, 0);
        Label passwordLabel = new Label("Password:");
        passwordLabel.setPrefWidth(180);
        loggedOutFields.add(passwordLabel, 0, 1);
        Label confirmLabel = new Label("Confirm:");
        loggedOutFields.add(confirmLabel, 0, 2);
        loggedOutFields.add(usernameField, 1, 0);
        loggedOutFields.add(passwordInField, 1, 1);
        loggedOutFields.add(passwordConfirm, 1, 2);
        loggedOutFields.add(createCreateAccountButton(), 0, 3);
        loggedOutFields.add(createLogInButton(), 1, 3);
        this.add(loggedOutFields, 0, 2, 2, 1);
    }

    private Button createLogoutButton() {
        Button toCreate = new Button("Log Out");
        toCreate.setPrefSize(400, 50);
        toCreate.setTextAlignment(TextAlignment.CENTER);
        toCreate.setOnAction(event -> {
            statusLabel.setText("Log in or create account below");
            loggedInFields.setVisible(false);
            loggedOutFields.setVisible(true);
        });
        return toCreate;
    }

    private Button createCreateAccountButton() {
        Button toCreate = new Button("Create Account");
        toCreate.setPrefSize(180, 10);
        toCreate.setTextAlignment(TextAlignment.CENTER);
        toCreate.setOnAction(event -> {
            if (passwordsEmptyOrNoMatch()) {
                statusLabel.setText("Passwords must be matching and non-empty.");
                return;
            }
            String username = usernameField.getText();
            String attemptPass = passwordInField.getText();
            System.out.println(username + "   " + attemptPass);
            if (!accountManager.signUp(username, attemptPass)) {
                statusLabel.setText("Username already taken.");
                return;
            }
            statusLabel.setText("New account has been created.");
            System.out.println("Created a new account with username: " + username);
            usernameField.setText("");
            passwordInField.setText("");
            passwordConfirm.setText("");
        });
        return toCreate;
    }

    private boolean passwordsEmptyOrNoMatch() {
        String textIn = passwordInField.getText();
        String textConfirm = passwordConfirm.getText();
        return textIn.equals("") || !textIn.equals(textConfirm);
    }

    private Button createLogInButton() {
        Button toCreate = new Button("Log In");
        toCreate.setPrefSize(180, 10);
        toCreate.setTextAlignment(TextAlignment.CENTER);
        toCreate.setOnAction(event -> {
            String username = usernameField.getText();
            String attemptPass = passwordInField.getText();
            passwordInField.setText("");
            passwordConfirm.setText("");
            if (!accountManager.login(username, attemptPass)) {
                statusLabel.setText("Unable to log in, username/password incorrect.");
                return;
            }

            // Updated: Show the main window
            showMainWindow();

            statusLabel.setText("Welcome, " + accountManager.getLoginAccount().getUsername());
            loggedInFields.setVisible(true);
        });
        return toCreate;
    }

    // Add this method to show the main window
    private void showMainWindow() {
        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.close(); // Close the login window

        // Launch the main window
        LoginMain.launchMainWindow();
    }
}