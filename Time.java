package gym2;
import java.text.DecimalFormat;

/**
 * Time enum class includes the hours and minutes of the fitness classes' times in its constructor.
 * 		it can also print the time by overriding toString() method.
 * @author Selin Altiparmak, Libby Birenboim
 */
public enum Time {
	MORNING(9,30),
	AFTERNOON(14,0),
	EVENING(18,30);
	private final int hour;
	private final int minute;

	/**
	 * Time constructor receives the integer values of hours and minutes and creates the Time.
	 * @param hour integer
	 * @param minute integer
	 */
	Time( int hour, int minute ) {
		this.hour = hour;
		this.minute = minute;
	}

	/**
	 * toString method prints out the Time in standard time format (hh:mm)
	 * @return String time
	 */
	@Override
	public String toString() {
		String pattern = "00";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		String hourPattern = "##";
		DecimalFormat decimalFormatHour = new DecimalFormat(hourPattern );
		String hourString  = decimalFormatHour .format(hour);
		String minuteString = decimalFormat.format(minute);
		return hourString + ":" + minuteString;
	}
}
