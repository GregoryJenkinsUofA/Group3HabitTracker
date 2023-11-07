package model;

public class Activity {
	
	String activityName;
	int day;
	int month;
	int year;
	int dayInYear;
	int duration;
	
	public Activity(String activityName, int day, int month, int year, int duration) {
		this.activityName = activityName;
		this.day = day;
		this.month = month;
		this.year = year;
		this.dayInYear = calculateDayInYear(day, month);
		this.duration = duration;
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
		return activityName;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getDayInYear() {
		return dayInYear;
	}
	
	public int getDuration() {
		return duration;
	}
	


	@Override
	public String toString() {
		return "Activity [activityName=" + activityName + ", date=" + month
				+ "/" + day + "/" + year + ", duration=" + duration + "]";
	}
	
	

}