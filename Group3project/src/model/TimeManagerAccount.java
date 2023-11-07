package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Responsibility: Store the Account Login Info
 * 	and attached statistics.
 * 
 * Implementation Specific: Be Serializable so that
 * 	we can save these accounts separately.
 * 
 * @author Zongtao Fu
 */
public class TimeManagerAccount implements Serializable {
	/**
	 * Default generated serialVersionUID
	 * Last updated: -1 on 10/9/23
	 */
	private static final long serialVersionUID = -2508211407261443913L;
	private byte[] hashedPass;
	private String username;
	private Database userData;

    public TimeManagerAccount(String username) {
      hashedPass = null;
      this.username = username;
      this.userData = new Database(username);
//      this.userData = null;
    }

    /**
     * Compares between the stored password and the attempted one,
     * but important to note that these both use the HASHED versions;
     * so changing the hash algorithm will cause new attempts to fail
     * on previously generated Accounts, even with same original string.
     * 
     * @param passwordAttempt - String to check
     * @return true if matching, false otherwise
     */
    public boolean checkPassword(String passwordAttempt) {
    	byte[] toCompare = hashAndSalt(passwordAttempt);
    	return MessageDigest.isEqual(hashedPass, toCompare);
    }
    
    /**
     * Takes a newly created Account and sets its password. Could probably
     * just be part of a more complete constructor.
     * 
     * @param newPassword - the String to base the new hash off.
     * @throws SecurityException - if password was already set, do not change
     */
    public void setPassword(String newPassword) throws SecurityException {
    	if (hashedPass != null) {
    		throw new SecurityException("Cannot set password because this account already has a password.");
    	}
    	hashedPass = hashAndSalt(newPassword);
    }
    
    /**
     * Basic getter
     * @return username associated with the account
     */
    public String getUsername() {
    	return username;
    }
    
    public Database getUserData() {
    	return userData;
    }
    
    // Helper Functions
    /**
     * Generates a hashed set of bytes for the String.
     * If the salts or algorithm are changed, the function 
     * will no longer match up with previous iterations.
     * 
     * @param toHash
     * @return a 256 bit unique value.
     */
    private byte[] hashAndSalt(String toHash) {
    	MessageDigest toSHA;
    	try {
			toSHA = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// "Every implementation of the Java platform is required to support
			// the following standard MessageDigest algorithms:" This should 
			// never happen.
			e.printStackTrace();
			return null;
		}
    	String extraSalt = "With more salt comes more security, and a tastier "
    			+ "breakfast.";
    	toSHA.update(extraSalt.getBytes());
    	toSHA.update(toHash.getBytes());
    	extraSalt = "...and then a dash at the end!";
    	toSHA.update(extraSalt.getBytes());
    	return toSHA.digest();
    }
}
