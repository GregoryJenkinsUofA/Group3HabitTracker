package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.TimeManagerAccount;

class TimeManagerAccountTest {

	@Test
	void testGetters() {
		TimeManagerAccount blankAccount = new TimeManagerAccount("Name");
		assertEquals("Name", blankAccount.getUsername());
	}

	@Test
	void testSetters() {
		TimeManagerAccount debugOptions = new TimeManagerAccount("Testing");
		debugOptions.setPassword("blank");
		Exception noRepeats = assertThrows(SecurityException.class, () -> {
			debugOptions.setPassword("again");
		});
		String message = noRepeats.getMessage();
		assertTrue(message.contains("already has a password"));
	}

	@Test
	void testPasswordCheck() {
		TimeManagerAccount simplePassword = new TimeManagerAccount("Homer Simpson");
		simplePassword.setPassword("1234");
		assertTrue(simplePassword.checkPassword("1234"));
		assertFalse(simplePassword.checkPassword("2345"));
		assertFalse(simplePassword.checkPassword("2134"));
	}
}
