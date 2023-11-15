package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import model.Activity;
import model.Database;

class DatabaseTest {

	@Test
	void testCreateDatabase() {
		Database test = new Database("Test1");
		try {
			test.createTable();
		} catch (SQLException e) {
			fail("Unable to create database");
			e.printStackTrace();
		}
		
		try {
			test.delete();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void testInsertActivity() {
		Activity test1 = new Activity( "Workout", 1, 1, 1, 1);
		Activity test2 = new Activity("Brush Teeth", 2, 2, 2, 2);
		Activity test3 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test4 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test5 = new Activity("Workout", 1, 1, 1, 1);
		Activity test6 = new Activity("Brush Teeth", 1, 2, 2, 2);
		Activity test7 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test8 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test9 = new Activity("Workout", 1, 1, 1, 1);
		Activity test10 = new Activity("Workout", 1, 1, 1, 1);
		Activity test11 = new Activity("Workout", 1, 1, 1, 1);
		Activity test12 = new Activity("Brush Teeth", 1, 2, 2, 2);
		Activity test13 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test14 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test15 = new Activity("Workout", 1, 1, 1, 1);
		Activity test16 = new Activity("Brush Teeth", 1, 2, 2, 2);
		Activity test17 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test18 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test19 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test20 = new Activity("Trim plants", 1, 2, 2, 4);
		
		Database test = new Database("Test2");
		LinkedList<Activity> activities = null;
		try {
			test.createTable();
			
			
		} catch (SQLException e) {
			fail("Unable to create database");
			e.printStackTrace();
		}
		
		try {
			test.insertActivity(test1);
			test.insertActivity(test2);
			test.insertActivity(test3);
			test.insertActivity(test4);
			test.insertActivity(test5);
			test.insertActivity(test6);
			test.insertActivity(test7);
			test.insertActivity(test8);
			test.insertActivity(test9);
			test.insertActivity(test10);
			test.insertActivity(test11);
			test.insertActivity(test12);
			test.insertActivity(test13);
			test.insertActivity(test14);
			test.insertActivity(test15);
			test.insertActivity(test16);
			test.insertActivity(test17);
			test.insertActivity(test18);
			test.insertActivity(test19);
			test.insertActivity(test20);
		} catch (SQLException e) {
			fail("Unable to insert into database");
			e.printStackTrace();
		}
		
		
		try {
			activities = test.getDatabase();
		} catch (SQLException e) {
			fail("Unable to get activities from database");
			e.printStackTrace();
		}
		
		
		
		for (Activity a : activities) {
			System.out.println(a);
		}
		
		try {
			test.delete();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Test
	void testActivity() {
		Activity test1 = new Activity( "Workout", 1, 1, 1, 1);
		Activity test2 = new Activity("Brush Teeth", 2, 2, 2, 2);
		Activity test3 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test4 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test5 = new Activity("Workout", 1, 1, 1, 1);
		Activity test6 = new Activity("Brush Teeth", 1, 2, 2, 2);
		Activity test7 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test8 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test9 = new Activity("Workout", 1, 1, 1, 1);
		Activity test10 = new Activity("Workout", 1, 1, 1, 1);
		Activity test11 = new Activity("Workout", 1, 1, 1, 1);
		Activity test12 = new Activity("Brush Teeth", 1, 2, 2, 2);
		Activity test13 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test14 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test15 = new Activity("Workout", 1, 1, 1, 1);
		Activity test16 = new Activity("Brush Teeth", 1, 2, 2, 2);
		Activity test17 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test18 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test19 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test20 = new Activity("Trim plants", 1, 2, 2, 4);
		
		Database test = new Database("Test2");
		LinkedList<Activity> activities = null;
		try {
			test.createTable();
			
			
		} catch (SQLException e) {
			fail("Unable to create database");
			e.printStackTrace();
		}
		
		try {
			test.insertActivity(test1);
			test.insertActivity(test2);
			test.insertActivity(test3);
			test.insertActivity(test4);
			test.insertActivity(test5);
			test.insertActivity(test6);
			test.insertActivity(test7);
			test.insertActivity(test8);
			test.insertActivity(test9);
			test.insertActivity(test10);
			test.insertActivity(test11);
			test.insertActivity(test12);
			test.insertActivity(test13);
			test.insertActivity(test14);
			test.insertActivity(test15);
			test.insertActivity(test16);
			test.insertActivity(test17);
			test.insertActivity(test18);
			test.insertActivity(test19);
			test.insertActivity(test20);
		} catch (SQLException e) {
			fail("Unable to insert into database");
			e.printStackTrace();
		}
		
		
		try {
			activities = test.getActivity("Gardening");
		} catch (SQLException e) {
			fail("Unable to get activities from database");
			e.printStackTrace();
		}
		
		
		
		for (Activity a : activities) {
			System.out.println(a);
		}
		
		try {
			test.delete();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Test
	void testMonth() {
		Activity test1 = new Activity( "Workout", 1, 1, 1, 1);
		Activity test2 = new Activity("Brush Teeth", 2, 2, 2, 2);
		Activity test3 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test4 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test5 = new Activity("Workout", 1, 1, 1, 1);
		Activity test6 = new Activity("Brush Teeth", 1, 2, 2, 2);
		Activity test7 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test8 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test9 = new Activity("Workout", 1, 1, 1, 1);
		Activity test10 = new Activity("Workout", 1, 1, 1, 1);
		Activity test11 = new Activity("Workout", 1, 1, 1, 1);
		Activity test12 = new Activity("Brush Teeth", 1, 2, 2, 2);
		Activity test13 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test14 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test15 = new Activity("Workout", 1, 1, 1, 1);
		Activity test16 = new Activity("Brush Teeth", 1, 2, 2, 2);
		Activity test17 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test18 = new Activity("Trim plants", 1, 2, 2, 4);
		Activity test19 = new Activity("Water plants", 1, 1, 1, 3);
		Activity test20 = new Activity("Trim plants", 1, 2, 2, 4);
		
		Database test = new Database("Test2");
		LinkedList<Activity> activities = null;
		try {
			test.createTable();
			
			
		} catch (SQLException e) {
			fail("Unable to create database");
			e.printStackTrace();
		}
		
		try {
			test.insertActivity(test1);
			test.insertActivity(test2);
			test.insertActivity(test3);
			test.insertActivity(test4);
			test.insertActivity(test5);
			test.insertActivity(test6);
			test.insertActivity(test7);
			test.insertActivity(test8);
			test.insertActivity(test9);
			test.insertActivity(test10);
			test.insertActivity(test11);
			test.insertActivity(test12);
			test.insertActivity(test13);
			test.insertActivity(test14);
			test.insertActivity(test15);
			test.insertActivity(test16);
			test.insertActivity(test17);
			test.insertActivity(test18);
			test.insertActivity(test19);
			test.insertActivity(test20);
		} catch (SQLException e) {
			fail("Unable to insert into database");
			e.printStackTrace();
		}
		
		
		try {
			activities = test.getMonth(3);
		} catch (SQLException e) {
			fail("Unable to get activities from database");
			e.printStackTrace();
		}
		
		
		
		for (Activity a : activities) {
			System.out.println(a);
		}
		
		try {
			test.delete();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
//	@Test
//	void testDayOfWeek() {
//		Activity test1 = new Activity( "Workout", 1, 1, 1, 1);
//		Activity test2 = new Activity("Brush Teeth", 2, 2, 2, 2);
//		Activity test3 = new Activity("Water plants", 1, 1, 1, 3);
//		Activity test4 = new Activity("Trim plants", 1, 2, 2, 4);
//		Activity test5 = new Activity("Workout", 1, 1, 1, 1);
//		Activity test6 = new Activity("Brush Teeth", 1, 2, 2, 2);
//		Activity test7 = new Activity("Water plants", 1, 1, 1, 3);
//		Activity test8 = new Activity("Trim plants", 1, 2, 2, 4);
//		Activity test9 = new Activity("Workout", 1, 1, 1, 1);
//		Activity test10 = new Activity("Workout", 1, 1, 1, 1);
//		Activity test11 = new Activity("Workout", 1, 1, 1, 1);
//		Activity test12 = new Activity("Brush Teeth", 1, 2, 2, 2);
//		Activity test13 = new Activity("Water plants", 1, 1, 1, 3);
//		Activity test14 = new Activity("Trim plants", 1, 2, 2, 4);
//		Activity test15 = new Activity("Workout", 1, 1, 1, 1);
//		Activity test16 = new Activity("Brush Teeth", 1, 2, 2, 2);
//		Activity test17 = new Activity("Water plants", 1, 1, 1, 3);
//		Activity test18 = new Activity("Trim plants", 1, 2, 2, 4);
//		Activity test19 = new Activity("Water plants", 1, 1, 1, 3);
//		Activity test20 = new Activity("Trim plants", 1, 2, 2, 4);
//		
//		Database test = new Database("Test2");
//		LinkedList<Activity> activities = null;
//		try {
//			test.createTable();
//			
//			
//		} catch (SQLException e) {
//			fail("Unable to create database");
//			e.printStackTrace();
//		}
//		
//		try {
//			test.insertActivity(test1);
//			test.insertActivity(test2);
//			test.insertActivity(test3);
//			test.insertActivity(test4);
//			test.insertActivity(test5);
//			test.insertActivity(test6);
//			test.insertActivity(test7);
//			test.insertActivity(test8);
//			test.insertActivity(test9);
//			test.insertActivity(test10);
//			test.insertActivity(test11);
//			test.insertActivity(test12);
//			test.insertActivity(test13);
//			test.insertActivity(test14);
//			test.insertActivity(test15);
//			test.insertActivity(test16);
//			test.insertActivity(test17);
//			test.insertActivity(test18);
//			test.insertActivity(test19);
//			test.insertActivity(test20);
//		} catch (SQLException e) {
//			fail("Unable to insert into database");
//			e.printStackTrace();
//		}
//		
//		
//		try {
//			activities = test.getDayOfWeek(0);
//			System.out.println("Printing Sunday activities:");
//			for (Activity a : activities) {
//				System.out.println(a);
//			}
//			
//			activities = test.getDayOfWeek(1);
//			System.out.println("Printing Monday activities:");
//			for (Activity a : activities) {
//				System.out.println(a);
//			}
//			
//			activities = test.getDayOfWeek(2);
//			System.out.println("Printing Tuesday activities:");
//			for (Activity a : activities) {
//				System.out.println(a);
//			}
//			
//			activities = test.getDayOfWeek(3);
//			System.out.println("Printing Wednesday activities:");
//			for (Activity a : activities) {
//				System.out.println(a);
//			}
//			
//			activities = test.getDayOfWeek(4);
//			System.out.println("Printing Thursday activities:");
//			for (Activity a : activities) {
//				System.out.println(a);
//			}
//			
//			activities = test.getDayOfWeek(5);
//			System.out.println("Printing Friday activities:");
//			for (Activity a : activities) {
//				System.out.println(a);
//			}
//			
//			activities = test.getDayOfWeek(6);
//			System.out.println("Printing Saturday activities:");
//			for (Activity a : activities) {
//				System.out.println(a);
//			}
//		} catch (SQLException e) {
//			fail("Unable to get activities from database");
//			e.printStackTrace();
//		}
//		
//		
//		
//		
//		
//		try {
//			test.delete();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
 

}