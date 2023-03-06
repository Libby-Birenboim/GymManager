package gym2;
import java.util.Calendar;
import java.util.Objects;

/**
 * Date class includes year, month, and day in its constructors.
 * 		implements more methods to return the year, month, day, compare Dates, check if a date
 * 		is valid, print date, and check if two dates are equal.
 * @author Selin Altiparmak, Libby Birenboim
 */
public class Date implements Comparable<Date> {

	private int year;	 
	private int month;	 
	private int day;
	public static final int QUADRENNIAL = 4; 
	public static final int CENTENNIAL = 100; 
	public static final int QUATERCENTENNIAL = 400;

	/**
	 * stringToInteger method converts a string to an integer.
	 * @param value of String type
	 * @return int value of given String value
	 */
	private static int stringToInteger(String value) {
		
		int returnValue = 0;
		int multiplier = 1;
		if (value.charAt(0) == '-') {
			for (int i = value.length() - 1; i > 0; i--) {
				int val = value.charAt(i) - '0';
				returnValue -= val * multiplier;
				multiplier *= 10;
			}	
		} else {
			for (int i = value.length() - 1; i >= 0; i--) {
				int val = value.charAt(i) - '0';
				returnValue += val * multiplier;
				multiplier *= 10;
			}
		}
		return returnValue;
	}

	/**
	 * getYear method returns the year of the date object.
	 * @return int year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * getMonth method returns the month of the date object.
	 * @return int month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * getDay method returns the day of the date object.
	 * @return int day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Date constructor constructs from calendar today's date
	 */
	public Date() { //create an object with today’s date (see Calendar class)
		Calendar today = Calendar.getInstance();
		this.day = today.get(Calendar.DAY_OF_MONTH);
		this.month = today.get(Calendar.MONTH) +1 ;
		this.year = today.get(Calendar.YEAR);
	}

	/**
	 * Date(String date) constructor constructs a date object given a String format of a date
	 */
	public Date(String date) { //take "mm/dd/yyyy" and create a Date object
		this.year = -1;
		this.month = -1;
		this.day = -1;
		String[] splittedDate = date.split("/");

		if ( splittedDate.length == 3 ) {
			String mm = splittedDate[0];
			String dd = splittedDate[1];
			String yyyy = splittedDate[2];

			this.month = stringToInteger(mm);
			this.day = stringToInteger(dd);
			this.year = stringToInteger(yyyy);
		}
	}

	/**
	 * Date constructor receives integer values for month, day, and year and constructs a date object with
	 * 		those values.
	 * @param month integer
	 * @param day integer
	 * @param year integer
	 */
	public Date(int month, int day, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	/**
	 * compareTo method compares two dates.
	 * @return -1 when smaller, 0 when dates equal, and 1 when first date is bigger
	 */
	@Override
	public int compareTo(Date date) {
		if ( this.year > date.year ) {
			return 1;
		} else if ( this.year < date.year ) {
			return -1;
		} else {
			if ( this.month > date.month ) {
				return 1;
			} else if ( this.month < date.month ) {
				return -1;
			} else {
				if ( this.day > date.day ) {
					return 1;
				} else if ( this.day < date.day ) {
					return -1;
				} else {
					return 0;
				}
			}			 
		}
	}

	/**
	 * isValid method checks if a date is a valid calendar date
	 * @return boolean true if valid date and false if invalid date
	 */
	public boolean isValid() { //check if a date is a valid calendar date
		if ( this.year < 1900 ) {
			return false;
		}
		if ( this.month <= 0 || this.month > 12 || this.day <= 0 )
			return false;
		if ( this.month == 1 || this.month == 3 || this.month == 5 || this.month == 7 || this.month == 8 || this.month == 10 || this.month == 12 ) {
			if ( this.day > 31 )
				return false;
		} else if ( this.month == 2 ) {
			boolean isLeapYear = false;
			if ( this.year % QUADRENNIAL == 0 ) {
				if ( this.year % CENTENNIAL == 0 ) {
					if ( this.year %  QUATERCENTENNIAL == 0 ) {
						isLeapYear = true;	 
					}
				} else {
					isLeapYear = true;
				}
			} 
			if ( isLeapYear && this.day > 29 ) {
				return false;
			} else if ( !isLeapYear && this.day > 28 ) {
				return false;
			}
		}
		else {
			if ( this.day > 30 )
				return false;
		}
		return true;
	}

	/**
	 * equals method checks if a given object is a date object
	 * @return boolean true if object is date object and false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Date other = (Date)obj;
		return day == other.day && month == other.month && year == other.year;
	}

	/**
	 * toString method returns the String format of a date object.
	 * @return String format of date.
	 */
	@Override
	public String toString() { return month + "/" + day + "/" + year; }

	/**
	 * is EighteenOver checks if the person is 18 years old or older based on their dob
	 * @return true if date of birth indicates person is 18 or older and false otherwise
	 */
	public boolean isEighteenOrOver() {
		Date today = new Date();
		if (today.year - this.year > 18) {
			return true;
		} else if (today.year - this.year == 18) {
			if (today.month > this.month) {
				return true;
			} else if (today.month == this.month) {
				if (today.day >= this.day) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * main method is the testbed that tests out the isValid() method in Date class.
	 * @param args String array
	 */
	public static void main(String[] args) {
		// testbed code implemented in DateTest class in test package
	}
}
