package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import model.TimeManagerAccount;
import model.TimeManagerAccountList;

// Necessary to persist through tests.
@TestInstance(Lifecycle.PER_CLASS)
class TimeManagerAccountListTest {
	
	@BeforeAll
	void preloadAccountList() {
		// Should persist through tests?
		TimeManagerAccountList samples = TimeManagerAccountList.getInstance();
		
		// Add some accounts.
		TimeManagerAccount testAcct = new TimeManagerAccount("test1");
		testAcct.setPassword("abcd");
		samples.add(testAcct);
		testAcct = new TimeManagerAccount("test2");
		testAcct.setPassword("1234");
		samples.add(testAcct);
		testAcct = new TimeManagerAccount("TheMostSecure");
		testAcct.setPassword("AReallyLongPasswordNiceAndSafe");
		samples.add(testAcct);
		testAcct = new TimeManagerAccount("taken");
		testAcct.setPassword("");
		samples.add(testAcct);
	}
	
	@Test
	void testUsernameTaken() {
		TimeManagerAccountList preloaded = TimeManagerAccountList.getInstance();
		assertTrue(preloaded.checkUsernameTaken("test1"));
		assertFalse(preloaded.checkUsernameTaken("nothere"));
		assertFalse(preloaded.checkUsernameTaken(null));
		// Case sensitivity? What is desired behavior?
		assertTrue(preloaded.checkUsernameTaken("TheMostSecure"));
		assertFalse(preloaded.checkUsernameTaken("themostsecure"));
	}
	
	@Test
	void testAdd() {
		TimeManagerAccountList preloaded = TimeManagerAccountList.getInstance();
		TimeManagerAccount newUsername = new TimeManagerAccount("Not taken");
		TimeManagerAccount usedUsername = new TimeManagerAccount("taken");
		
		assertTrue(preloaded.add(newUsername));
		assertFalse(preloaded.add(usedUsername));
	}
	
	@Test
	void testGet() {
		TimeManagerAccountList preloaded = TimeManagerAccountList.getInstance();
		
		TimeManagerAccount notHere = preloaded.getAccountIfMatches("nothere", "empty");
		assertEquals(null, notHere);
		
		TimeManagerAccount incorrectPass = preloaded.getAccountIfMatches("test1", "1234");
		assertEquals(null, incorrectPass);
		
		TimeManagerAccount successful = preloaded.getAccountIfMatches("test1", "abcd");
		assertNotNull(successful);
		successful = preloaded.getAccountIfMatches("TheMostSecure", "AReallyLongPasswordNiceAndSafe");
		assertNotNull(successful);
		
		incorrectPass = preloaded.getAccountIfMatches("TheMostSecure", "areallylongpasswordniceandsafe");
		assertEquals(null, incorrectPass);
	}
	
	@Test
	void testSaveAndLoad() {
		TimeManagerAccountList preloaded = TimeManagerAccountList.getInstance();
		// I have no idea how to check if the singleton matches since its overwritten.
		// for now just check some accounts to confirm.
		
		assertTrue(TimeManagerAccountList.saveToFile("./data/testAccounts.txt"));
		// should also print stack trace. Maybe should pass exception up?
		assertFalse(TimeManagerAccountList.saveToFile(null));
		
		// make changes that aren't saved.
		preloaded.add(new TimeManagerAccount("shouldbegone"));
		
		// will also print stack trace. Maybe should pass exception up.
		assertFalse(TimeManagerAccountList.loadFromFile(null));
		assertTrue(TimeManagerAccountList.loadFromFile("./data/testAccounts.txt"));
		TimeManagerAccountList oldReference = preloaded;
		
		preloaded = TimeManagerAccountList.getInstance();
		assertNotEquals(oldReference, preloaded);
		
		assertEquals(false, preloaded.checkUsernameTaken("shouldbegone"));
		assertNotNull(preloaded.getAccountIfMatches("test1", "abcd"));
		assertNotNull(preloaded.getAccountIfMatches("test2", "1234"));
	}

}