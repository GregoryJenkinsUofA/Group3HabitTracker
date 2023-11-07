package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
/**
 * This is a SINGLETON class that allows readings and writing of an account file,
 * and access to that information in "mildly secure" ways.
 * 
 * only run saveToFile after calling getInstance at least once.
 * 
 * @author Zongtao Fu
 */
public class TimeManagerAccountList implements Serializable {
	/**
	 * Generated on 3/17/23
	 */
	private static final long serialVersionUID = -8877687699267500482L;
	private static TimeManagerAccountList self;
	private HashMap<String, TimeManagerAccount> accountList;
	
	private TimeManagerAccountList() {
		accountList = new HashMap<>();
	}
	
	/**
	 * Gets the current WordleAccountList, or makes one if necessary
	 * 
	 * @return self - Reference to the only WordleAccountList instance
	 */
	public synchronized static TimeManagerAccountList getInstance() {
		if (self == null)
			self = new TimeManagerAccountList();
		return self;
	}
	
	/**
	 * Basic check for key collisions
	 * 
	 * @param toCheck - the username to match
	 * @return true on collision, false if clear
	 */
	public boolean checkUsernameTaken(String toCheck) {
		return accountList.containsKey(toCheck);
	}
	
	/**
	 * Refuse duplicate usernames.
	 * @param toAdd - non-null (ideally) JukeboxAccount
	 * @return true on successful operation, false otherwise.
	 */
	public boolean add(TimeManagerAccount toAdd) {
		if (accountList.containsKey(toAdd.getUsername()))
			return false;
		accountList.put(toAdd.getUsername(), toAdd);
		return true;
	}
	
	/**
	 * Retrieves an Account with matching data.
	 * @param username
	 * @param password
	 * @return the object if successfully matches, null otherwise.
	 */
	public TimeManagerAccount getAccountIfMatches(String username, String password) {
		TimeManagerAccount toAttemptLogin = accountList.get(username);
		if (toAttemptLogin == null)
			return null;
		if (toAttemptLogin.checkPassword(password))
			return toAttemptLogin;
		return null;
	}
	
	/**
	 * Attempts to load a WordleAccountList from the file path specified.
	 * Note: Performing this AFTER getting an instance leaves a stale instance
	 * in that previous variable; either perform this FIRST or getInstance again after.
	 * 
	 * @param fileName - valid path to serialized file
	 * @return true on success, false on errors
	 */
	public static synchronized boolean loadFromFile(String fileName) {
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream accountsIn = new ObjectInputStream(fileIn);
			self = (TimeManagerAccountList) accountsIn.readObject();
			accountsIn.close();
			return true;
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Saves the current WordleAccountList to the specified file path.
	 * This function is safe to use and continue using the class after,
	 * but MAY have strange behavior if another thread attempts to change
	 * the data structure while writing... unsure whether synchronized avoids that.
	 * 
	 * @param fileName - valid path to write to (note: overwrites current contents)
	 * @return true on success, false on errors
	 */
	public static synchronized boolean saveToFile(String fileName) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream accountsOut = new ObjectOutputStream(fileOut);
			accountsOut.writeObject(self);
			accountsOut.close();
			return true;
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
	}
}
