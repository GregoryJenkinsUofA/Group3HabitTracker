package model;
import java.io.File;
import java.sql.*;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
	
	Connection conn = null;
	String tableName;
	
	public Database(String tableName) {
		
		this.tableName = tableName;
		
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:sqlite:" + tableName + ".db");
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
	}
	

	public static String getDateTime() {
		java.util.Date dt = new java.util.Date();

		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String output = sdf.format(dt);
		
		return output;
	}
	
	public void createTable() throws SQLException {
		String createTablesql = "" +
								"CREATE TABLE  " + tableName +
								"( " +
								"Activity TEXT, " +
								"Day INT, " +
								"Month INT, " +
								"Year INT, " +
								"DayInYear INT, " +
								"Duration INT, " +
								"); " +
								"";
		
		Statement stmt = conn.createStatement();
		stmt.execute(createTablesql);
										
	}
	
	/**
	 * Closes the database connection
	 */
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Returns the entire database
	 * 
	 * @return ArrayList of all activities in the database
	 * @throws SQLException
	 */
	public LinkedList<Activity> getDatabase() throws SQLException {
		String selectSQL = "SELECT * from " + tableName;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(selectSQL);
		
		//LinkedList<Activity> output = new LinkedList<Activity>();
		
		return createArrayList(rs);
		
	}
	
	/**
	 * Returns all the recorded activities of a single type
	 * 
	 * @param activity- String name of activity
	 * @return Linked list of activities
	 * @throws SQLException
	 */
	public LinkedList<Activity> getActivity(String activity) throws SQLException {
		String selectSQL = "SELECT * from " + tableName + "WHERE Activity=" + activity;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(selectSQL);
		
		
		return createArrayList(rs);
		
	}
		
		
	/**
	 * Returns all activities in a month
	 * 
	 * @param month- Integer of month
	 * @return ArrayList containing all activities from the month
	 * @throws SQLException
	 */
	public LinkedList<Activity> getMonth(int month) throws SQLException {
		String selectSQL = "SELECT * from " + tableName + "WHERE Month=" + month;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(selectSQL);
		
		
		return createArrayList(rs);
		
	}
	
	/**
	 * Returns all activities in a week
	 * 
	 * @param day- Integer of day
	 * @param month- Integer of month
	 * @return ArrayList containing all activities from the week
	 * @throws SQLException
	 */
	public LinkedList<Activity> getWeek(int day, int month) throws SQLException {
		int date = Activity.calculateDayInYear(day, month);
		String selectSQL = "SELECT * from " + tableName + "WHERE DayInYear BETWEEN" + (date - 7) + " AND " + date;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(selectSQL);
		
		
		return createArrayList(rs);
		
	}
	
	/**
	 * Gets an ArrayList of activities between two dates
	 * 
	 * @param dayStart- Integer of starting day
	 * @param monthStart- Integer of starting month
	 * @param dayEnd- Integer of ending day
	 * @param monthEnd- Integer of ending month
	 * @return ArrayList containing activities between the dates provided
	 * @throws SQLException
	 */
	public LinkedList<Activity> getRange(int dayStart, int monthStart, int dayEnd, int monthEnd) throws SQLException {
		int dateStart = Activity.calculateDayInYear(dayStart, monthStart);
		int dateEnd = Activity.calculateDayInYear(dayEnd, monthEnd);
		String selectSQL = "SELECT * from " + tableName + "WHERE DayInYear BETWEEN" + dateStart + " AND " + dateEnd;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(selectSQL);
		
		
		return createArrayList(rs);
		
	}
	



	/**
	 * Returns an ArrayList from a ResultSet
	 * 
	 * @param rs- ResultSet object
	 * @return ArrayList of activities
	 */
	private LinkedList<Activity> createArrayList(ResultSet rs) {
		
		LinkedList<Activity> output = new LinkedList<Activity>();
		
		try {
			while(rs.next()) {
				Activity temp = new Activity(rs.getString("Activity"), rs.getInt("Day"), rs.getInt("Month"), rs.getInt("Year"), rs.getInt("Duration"));
				output.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}


	/**
	 * Inserts an activity into the database
	 * 
	 * @param input- Activity object being inserted
	 * @throws SQLException
	 */
	public void insertActivity(Activity input) throws SQLException {
		String insertSQL = "INSERT INTO " + tableName + "(Activity, Day, Month, Year, DayInYear, Duration) VALUES(?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(insertSQL);
		pstmt.setString(1, input.getActivity());
		pstmt.setInt(2, input.getDay());
		pstmt.setInt(3, input.getMonth());
		pstmt.setInt(4, input.getYear());
		pstmt.setInt(5, input.getDayInYear());
		pstmt.setInt(6, input.getDuration());

		pstmt.executeUpdate();
		
	}



	/**
	 * Deletes the database
	 * 
	 * @throws SQLException
	 */
	public void delete() throws SQLException {
		String deleteTableSQL = "DROP TABLE " + this.tableName;
		Statement stmt = conn.createStatement();
		stmt.execute(deleteTableSQL);
		stmt.close();
		conn.close();
		
		boolean result = new File(this.tableName + ".db").delete();
		
//		if (result) {
//			System.out.println("Delete successful");
//		} else {
//			System.out.println("Delete failed");
//		}
		
	}
	
	/**
	 * Helper function that returns a linked list from a ResultSet
	 * 
	 * @param rs ResultSet
	 * @return Linked list
	 */
	private ObservableList<Activity> createObservableList(ResultSet rs) {
		
		ObservableList<Activity> output = FXCollections.observableArrayList();
		
		try {
			while(rs.next()) {
				Activity temp = new Activity(rs.getString("Activity"), rs.getInt("Day"), rs.getInt("Month"), rs.getInt("Year"), rs.getInt("Duration"));
				output.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
	/**
	 * Returns the entire database as a linked list
	 * 
	 * @return linked list of database activities
	 * @throws SQLException
	 */
	public ObservableList<Activity> getObservableDatabase() throws SQLException {
		String selectSQL = "SELECT * from " + tableName;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(selectSQL);
		
		//LinkedList<Activity> output = new LinkedList<Activity>();
		
		//ObservableList<Activity> output = FXCollections.observableArrayList();
		
		return createObservableList(rs);
		
	}

}