package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import controller.AccountManager;
/**
 * IMPORTANT NOTE:
 * RUNNING THIS UNIT TEST CLEARS OUR THE SAVED ACCOUNTS LIST.
 * BE WARNED.
 * 
 * @author Zongtao Fu
 *
 */
class AccountManagerTest {

	@Test
	void testSignUp() {
		AccountManager hasSomeGarbage = AccountManager.getInstance(false).get();
		assertTrue(hasSomeGarbage.signUp("unittest1", "pass"));
		assertFalse(hasSomeGarbage.signUp("unittest1", "pass"));
	}

	@Test
	void testLogin() {
		AccountManager hasSomeGarbage = AccountManager.getInstance(false).get();
		assertTrue(hasSomeGarbage.signUp("unittest2", "pass"));
		assertFalse(hasSomeGarbage.getLoginStatus());
		
		assertTrue(hasSomeGarbage.login("unittest2", "pass"));
		assertTrue(hasSomeGarbage.getLoginStatus());
		assertFalse(hasSomeGarbage.login("unittest2", "pass"));
		
		hasSomeGarbage.logout();
		assertFalse(hasSomeGarbage.getLoginStatus());
		assertFalse(hasSomeGarbage.login("unittest2", "fail"));
		assertFalse(hasSomeGarbage.getLoginStatus());
	}
	
	@Test
	void testSaveAndLoad() {
		// We should add a toggle somewhere to use a different filepath
		// for this than the main one...
		File testLocation = new File("./data/Accounts.ser");
		testLocation.delete();
		assertTrue(AccountManager.getInstance(true).isEmpty());
		AccountManager hasSomeGarbage = AccountManager.getInstance(false).get();
		hasSomeGarbage.signUp("unittest3", "pass");
		assertTrue(hasSomeGarbage.login("unittest3", "pass"));
		hasSomeGarbage.logout();
		
		assertTrue(hasSomeGarbage.saveAccounts());
		
		AccountManager hasTheSameGarbage = AccountManager.getInstance(true).get();
		assertFalse(hasTheSameGarbage.getLoginStatus());
		assertTrue(hasSomeGarbage.login("unittest3", "pass"));
		hasSomeGarbage.logout();
	}
	
	@Test
	void testLoadAfterGet() {
		AccountManager noLoad = AccountManager.getInstance(false).get();
		assertFalse(noLoad.login("unittest3", "pass"));
		
		AccountManager loadSecond = AccountManager.getInstance(true).get();
		assertTrue(noLoad.login("unittest3", "pass"));
		assertTrue(loadSecond.getLoginStatus());
		loadSecond.logout();
		assertFalse(noLoad.getLoginStatus());
	}
}
