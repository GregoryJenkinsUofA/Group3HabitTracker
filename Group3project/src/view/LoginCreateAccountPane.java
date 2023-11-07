package view;


import java.util.Optional;

import controller.AccountManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.TimeManagerAccount;
import model.TimeManagerAccountList;
/**
 * A mixed view object, perhaps mostly within the
 * "controller" scope.
 * 
 * Contains a few fields to perform the direct actions,
 * but can also be used as a pure controller if visible=false.
 * Use getLoginStatus, getCurrentCanPlay, and playFromCurrentAccount
 * to interface with the AccountList.
 * 
 * Currently does not handle loading/saving of the account list.
 * 
 * @author Zongtao Fu
 */
public class LoginCreateAccountPane extends GridPane {
//	private static LoginCreateAccountPane self;

	private AccountManager accountManager;
	private TimeManagerAccount currentAccount;
	private Label statusLabel;
	
	private GridPane loggedInFields;
	private GridPane loggedOutFields;
	
	private TextField usernameField;
	private PasswordField passwordInField;
	private PasswordField passwordConfirm;

	public LoginCreateAccountPane(AccountManager accountManager) {
		super();
		this.accountManager = AccountManager.getInstance(false);
		currentAccount = null;
		statusLabel = new Label("Log in or create account below:");
		usernameField = new TextField();
		passwordInField = new PasswordField();
		passwordConfirm = new PasswordField();
		setElementProperties();
		arrangeElements();
	}

//	public static synchronized LoginCreateAccountPane getInstance() {
//		if (self == null) {
//			self = new LoginCreateAccountPane(accountManager);
//		}
//		return self;
//	}
	
//	public boolean getLoginStatus() {
//		return loginStatus;
//	}
	
	private void setElementProperties() {
		this.setPrefWidth(400);
		this.setPrefHeight(80);
		this.getStyleClass().add("gridpane-padded");
		statusLabel.setPrefWidth(400);
		usernameField.setPrefWidth(200);
		passwordInField.setPrefWidth(200);
		passwordConfirm.setPrefWidth(200);
	}
	
	/**
	 * Also creates any non-interactable child elements and
	 * sets them as children of this element.
	 */
	private void arrangeElements() {
		this.add(statusLabel, 0, 0, 2, 1);
		
		loggedInFields = new GridPane();
		loggedInFields.getStyleClass().add("gridpane-padded");
		loggedInFields.setPrefSize(400, 60);
		Button logoutButton = createLogoutButton();
		loggedInFields.add(logoutButton, 0, 0);
//		loggedInFields.setVisible(loginStatus);
//		this.add(loggedInFields, 0, 1, 2, 1);
		
		loggedOutFields = new GridPane();
		loggedOutFields.getStyleClass().add("gridpane-padded");
		loggedOutFields.setPrefSize(400, 60);
		/**
		 * User   | ----
		 * Pass   | ****
		 * Confirm| ****
		 * Create | Log in
		 */
		// Have to set pref width separately for columns to align.
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
//		loggedOutFields.setVisible(!loginStatus);
		this.add(loggedOutFields, 0, 2, 2, 1);
		
	}

	// Creation, formatting, and event listeners.
	private Button createLogoutButton() {
		Button toCreate = new Button("Log Out");
		toCreate.setPrefSize(400, 50);
		toCreate.setTextAlignment(TextAlignment.CENTER);
		toCreate.setOnAction(event -> {
			this.currentAccount = null;
			this.statusLabel.setText("Log in or create account below");
			this.loggedInFields.setVisible(false);
			this.loggedOutFields.setVisible(true);
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
			System.out.println(username + "   " +attemptPass);
			if (!accountManager.signUp(username, attemptPass)) {
				statusLabel.setText("Username already taken.");
				return;
			}
//			TimeManagerAccount newAccount = new TimeManagerAccount(usernameField.getText());
//			newAccount.setPassword(passwordInField.getText());
//			allAccounts.add(newAccount);
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
		if (textIn.equals("") || textConfirm.equals("")) {
			return true;
		}
		if (textIn.equals(textConfirm)) {
			return false;
		}
		return true;
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
//			currentAccount = attemptedLogin;
//			loginStatus = true;
			statusLabel.setText("Welcome, " + accountManager.getLoginAccount().getUsername());
			this.loggedInFields.setVisible(true);
//			this.loggedOutFields.setVisible(false);
		});
		return toCreate;
	}
	
	public void resetAccountList() {
//		this.currentAccount = null;
//		this.loginStatus = false;
//		statusLabel.setText("Log in or create account to continue");
//		this.loggedInFields.setVisible(false);
//		this.loggedOutFields.setVisible(true);
	}
	
	public void switchStage(Button currentButton) {
        Scene settingsScene = new Scene(self);
        Stage stage = (Stage) currentButton.getScene().getWindow();
        stage.setScene(settingsScene);
	}
}