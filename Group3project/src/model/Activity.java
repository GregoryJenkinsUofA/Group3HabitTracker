package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Activity {
	
	private final SimpleStringProperty activityName;
	private final SimpleIntegerProperty day;
	private final SimpleIntegerProperty month;
	private final SimpleIntegerProperty year;
	private final SimpleIntegerProperty dayInYear;
	private final SimpleStringProperty date;
	private final SimpleIntegerProperty duration;
	
	public Activity(String activityName, int day, int month, int year, int duration) {
		this.activityName = new SimpleStringProperty(activityName);
		this.day = new SimpleIntegerProperty(day);
		this.month = new SimpleIntegerProperty(month);
		this.year = new SimpleIntegerProperty(year);
		this.dayInYear = new SimpleIntegerProperty(calculateDayInYear(day, month));
		this.date = new SimpleStringProperty(month + "/" + day + "/" + year);
		this.duration = new SimpleIntegerProperty(duration);
	}
	
	
	
	
	public static int calculateDayInYear(int day, int month) {
		int output = 0;
		
		switch(month) {
		case 1:
			output = day;
			break;
		case 2:
			output = 31 + day;
			break;
		case 3:
			output = 59 + day;
			break;
		case 4:
			output = 90 + day;
			break;
		case 5:
			output = 120 + day;
			break;
		case 6:
			output = 151 + day;
			break;
		case 7:
			output = 181 + day;
			break;
		case 8:
			output = 212 + day;
			break;
		case 9:
			output = 243 + day;
			break;
		case 10:
			output = 273 + day;
			break;
		case 11:
			output = 304 + day;
			break;
		case 12:
			output = 334 + day;
			break;
		}
			
		
		return output;
	}




	public String getActivity() {
		return activityName.get();
	}
	
	public int getDay() {
		return day.get();
	}
	
	public int getMonth() {
		return month.get();
	}
	
	public int getYear() {
		return year.get();
	}
	
	public int getDayInYear() {
		return dayInYear.get();
	}
	
	public int getDuration() {
		return duration.get();
	}
	
	public String getDate() {
		return date.get();
	}
	


	@Override
	public String toString() {
		return "Activity [activityName=" + activityName + ", date=" + month
				+ "/" + day + "/" + year + ", duration=" + duration + "]";
	}
	
	

}