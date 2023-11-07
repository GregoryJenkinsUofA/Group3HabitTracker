package controller;

import model.TimeManagerAccount;
import model.TimeManagerAccountList;

import java.io.File;
import java.util.Optional;
/**
 * I'm honestly not even sure why we have this class;
 * the majority of its functions are literally just an exact
 * copy of the call and return parameters of functions from
 * TimeManagerAccountList...
 * 
 * @implNote Currently the only Observable, and using default
 * functions from it.
 * 
 * @author Zongtao Fu
 *
 */
public class AccountManager {

	private TimeManagerAccountList accounts;
	private TimeManagerAccount currAccount;
	private static AccountManager accountManager;
	private static final String writeFilePath = "./data/Accounts.ser";

	// Some weird stuff can happen with default values, so
	// lets set them in the constructor instead.
	private AccountManager() {
		accounts = TimeManagerAccountList.getInstance();
		// assure that currAccount == null so we can use that
		// information elsewhere to confirm login status.
		currAccount = null;
	}

	/**
	 * Creates a new AccountManager and gives you it, or gives you the old one.
	 * Has some funky behavior to encapsulate optional loading (for tests)
	 * note: Only call forceLoad once, but forceLoad should not ruin previous
	 * references to AccountManager that were made before loading.
	 * 
	 * @param forceLoad - true to load from default file, false otherwise.
	 * 				 use false when calling additional creates
	 * @return null on fail states, itself otherwise.
	 */
	public static synchronized AccountManager getInstance(boolean forceLoad) {
		if (forceLoad) {
			if (!new File(writeFilePath).exists()) {
				return null;
			}
			TimeManagerAccountList.loadFromFile(writeFilePath);
		}
		if (accountManager == null) {
			accountManager = new AccountManager();
		}
		if (forceLoad) {
			// handle a special case where object reference no longer valid.
			accountManager.accounts = TimeManagerAccountList.getInstance();
		}
		return accountManager;
	}

	/**
	 * Attempts to log in with the given username/password combo
	 * Precondition: Not currently logged in
	 * On failure: return false
	 * On success: return true and notify observers once data is updated
	 * 
	 * @param username - matching account
	 * @param password - matching account
	 * @return true when successfully logged in, false otherwise
	 */
	public boolean login(String username, String password) {
		// Don't allow logins without logging out first.
		if (currAccount != null) {
			return false;
		}
		
		currAccount = accounts.getAccountIfMatches(username, password);
//		this.notifyObservers();
		if (currAccount != null) {
			return true;
		}
		return false;
	}

	/**
	 * Creates a new account (if available) using the given combination.
	 * Notifies observers after attempt, regardless of result.
	 * 
	 * @param username - name to set, cannot repeat one in database
	 * @param password - password to set
	 * @return true on addition to list, false otherwise
	 */
	public boolean signUp(String username, String password) {
		if (accounts.checkUsernameTaken(username)) {
			return false;
		}
		
		TimeManagerAccount account = new TimeManagerAccount(username);
		account.setPassword(password);
		// Notify AFTER state finishes changing.
		boolean toReturn = accounts.add(account);
//		this.notifyObservers();
		return toReturn;
	}

	/**
	 * Gets the currently logged in TimeManagerAccount.
	 * Note: reference does not change if user changes later;
	 * that's up to the other classes to observe and update.
	 * 
	 * @return - TimeManagerAccount if logged in, null otherwise.
	 */
	public TimeManagerAccount getLoginAccount() {
		return currAccount;
	}

	/**
	 * Remove the current TimeManagerAccount if present, and notify
	 * observers
	 */
	public void logout() {
		currAccount = null;
//		this.notifyObservers();
	}

	/**
	 * Basic conversion of state to boolean
	 * @return true if Logged in, false otherwise
	 */
	public boolean getLoginStatus() {
		return currAccount != null;
	}

	// splitting it up so we don't delay other processes on creation
	/**
	 * Helped function which calls the TimeManagerAccountList function
	 * to save accounts, returning the same conditions as it.
	 * 
	 * @return true on success, false otherwise
	 */
	public boolean saveAccounts() {
		return TimeManagerAccountList.saveToFile(writeFilePath);
	}
}
