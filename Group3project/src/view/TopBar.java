package view;

import controller.AccountManager;
//import controller.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
/**
 * All the fancy stuff this class does is hiddin in the CSS File.
 * Groups all our buttons in a fake menubar, and puts a real one inside too.
 * 
 * Currently still waiting for the owners of the other menus to put the functions
 * calls to their classes as events for the other buttons.......
 * 
 * @author Joshua Mills
 *
 */
public class TopBar extends HBox{
    MenuItem loginMenu;
    MenuItem createMenu;
    MenuItem logoutMenu;
    Button openInfoPanel;
    Button openStatsPanel;
    Button openSettingsPanel;
    AccountManager loginStatus;

    public TopBar() {
        super();
        this.getStyleClass().add("menu-bar");
        MenuBar accountsSubmenu = createAccountsMenu();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.SOMETIMES);

        openInfoPanel = createInfoButton();
        openStatsPanel = createStatsButton();
        openSettingsPanel = createSettingsButton();
        this.getChildren().addAll(accountsSubmenu, spacer, openInfoPanel, openStatsPanel, openSettingsPanel);

//        loginStatus = AccountManager.getInstance(false).get();
//        loginStatus.addToObservers(this);
        updateVisibility();
    }

    private MenuBar createAccountsMenu() {
        MenuBar toReturn = new MenuBar();

        Menu accountsMenus = new Menu("Accounts");
//		accountsMenus.getStyleClass().add("menu-accounts");

        loginMenu = new MenuItem("Login");
        createMenu = new MenuItem("Create Account");
        logoutMenu = new MenuItem("Logout");
        accountsMenus.getItems().addAll(loginMenu, createMenu, logoutMenu);

        toReturn.getMenus().add(accountsMenus);

        return toReturn;
    }

    private Button createInfoButton() {
        Button toCreate = new Button();
        toCreate.getStyleClass().add("menu-button");
        toCreate.setPickOnBounds(true);

        Region icon = new Region();
        icon.getStyleClass().add("info-icon");
        icon.getStyleClass().add("icon");
        toCreate.setGraphic(icon);

        // add listener to open info panel.

        return toCreate;
    }

    private Button createStatsButton() {
        Button toCreate = new Button();
        toCreate.getStyleClass().add("menu-button");
        toCreate.setPickOnBounds(true);

        Region icon = new Region();
        icon.getStyleClass().add("stats-icon");
        icon.getStyleClass().add("icon");
        toCreate.setGraphic(icon);

        // add listener to open info panel.
        toCreate.setOnMouseClicked(e -> {
            Score score = new Score("testUser");
            ScoreboardPane scoreboardPane = new ScoreboardPane(score);
            scoreboardPane.showWindow();
        });

        return toCreate;
    }

    private Button createSettingsButton() {
        Button toCreate = new Button();
        toCreate.getStyleClass().add("menu-button");
        toCreate.setPickOnBounds(true);

        Region icon = new Region();
        icon.getStyleClass().add("gear-icon");
        icon.getStyleClass().add("icon");
        toCreate.setGraphic(icon);

        // add listener to open info panel.

        return toCreate;
    }

    /**
     * Sets the event listeners for the Account related buttons to
     * the ones specified, since the AccountWindows aren't in our scope
     * (just a GUI class)
     * 
     * @param forLogin - Lambda for Login Button
     * @param forCreate - Lambda for Create Button
     * @param forLogout - Lambda for Logout Button
     */
    public void attachListenersToButtons(EventHandler<ActionEvent> forLogin, EventHandler<ActionEvent> forCreate, EventHandler<ActionEvent> forLogout) {
        loginMenu.setOnAction(forLogin);
        createMenu.setOnAction(forCreate);
        logoutMenu.setOnAction(forLogout);
    }
    
    /**
     * Sets the listers to the icons on the top right of the bar.
     * (Add call show from appropriate menu windows created in main)
     * 
     * @param forHelp - Lambda for '?' Button
     * @param forStats - Lambda for leaderboard Button
     * @param forSettings - Lambda for gear button
     */
    public void attachListenersToIcons(EventHandler<ActionEvent> forHelp, EventHandler<ActionEvent> forStats, EventHandler<ActionEvent> forSettings) {
    	openInfoPanel.setOnAction(forHelp);
    	openStatsPanel.setOnAction(forStats);
    	openSettingsPanel.setOnAction(forSettings);
    }

    /**
     * Refresh to provide only the menus necessary given current
     * login status within the Account Panel
     */
    public void updateVisibility() {
        if (loginStatus.getLoginStatus()) {
            loginMenu.setVisible(false);
            createMenu.setVisible(false);
            logoutMenu.setVisible(true);
            return;
        }
        loginMenu.setVisible(true);
        createMenu.setVisible(true);
        logoutMenu.setVisible(false);
    }

    /**
     * Implements the Observer pattern to hide/show content
     */
    @Override
    public void refresh() {
        updateVisibility();
    }
}
