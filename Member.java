package gym2;
import java.util.Calendar;
import java.util.Objects;

/**
 * Member class is the class for standard membership and it includes members' first name, last name,
 * 		date of birth, expiration date, and location in its constructors.
 * 		It can print a member's info, check if there is a location or guest restriction for fitness classes,
 * 		check if an object equals the member, compare members, get expiration date, location, date of birth,
 * 		membership fee, decrease and increase available guest passes (super methods for Family and Premium classes)
 * @author Selin Altiparmak, Libby Birenboim
 */
public class Member implements Comparable<Member>{
	private String fname;
	private String lname;
	private Date dob;
	private Date expire;
	private Location location;
	public static final double STANDARD_ONE_TIME_FEE = 29.99;
	public static final double STANDARD_PER_MONTH = 39.99;
	public static final int STANDARD_DURATION = 3;

	/**
	 * Member constructor constructs a member object with a standard membership
	 * @param fname first name of member
	 * @param lname last name of member
	 * @param dob Date of birth of member
	 * @param location Location of membership
	 */
	public Member(String fname, String lname, Date dob, Location location) {
		this.fname = fname;
		this.lname = lname;
		this.dob = dob;
		Calendar today = Calendar.getInstance();
		today.add(Calendar.MONTH, 3);
		
		int day = today.get(Calendar.DAY_OF_MONTH);
		int month = today.get(Calendar.MONTH) + 1;
		int year = today.get(Calendar.YEAR);
		this.expire = new Date(month,day,year);
		this.location = location;
	}
	
	/**
	 * Member constructor receives the member's first name, last name, date of birth, expiration date, and location
	 * 		and creates a member object with these parameters.
	 * @param fname first name of member
	 * @param lname last name of member
	 * @param dob Date of birth of member
	 * @param expire expiration Date of membership
	 * @param location Location of membership
	 */
	public Member(String fname, String lname, Date dob, Date expire, Location location) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.dob = dob;
		this.expire = expire;
		this.location = location;
	}

	/**
	 * toString method prints the member's info.
	 */
	@Override
	public String toString() {
		String fullname = fname + " " + lname;
		String dateOfBirth = "DOB: " + dob.toString();
		String strExpire = "expires ";
		Date today = new Date();
		if (this.expire.compareTo(today) < 0) {
			strExpire = "expired ";
		}
		String memberShip = "Membership " + strExpire  + expire.toString();
		String locationStr = "Location: " + this.location.name + ", " + this.location.zipCode + ", " + this.location.County;

		return fullname + ", " + dateOfBirth + ", " + memberShip + ", " + locationStr;
	}

	/**
	 * isLocationRestriction method receives a location object and checks if the member has a location restriction
	 * 		for the fitness classes
	 * @param location Location of fitness class
	 * @return true if there is a restriction and false otherwise
	 */
	// Standard members are eligible to checkin a class only if the fitness class is at Membership location 
	public boolean isLocationRestriction(Location location) {
		return !( this.location.name == location.name);
	}

	/**
	 * isGuestLocationRestriction method receives a location and checks if the member has a guest location
	 * 		restriction, meaning guest can only take fitness class in host's membership location
	 * @param location Location of fitness class
	 * @return true if there is a restriction and false otherwise
	 */
	public boolean isGuestLocationRestriction(Location location) {
		return true;
	}



	/**
	 * equals method receives an object and checks if that object is equal to a member object.
	 * @param obj Object
	 * @return true if given object is equal to member object and false otherwise.
	 */
	@Override
	public boolean equals(Object obj) { 
		if ( obj == null ) {
			return false;
		}
		if ( obj instanceof Member == false ) {
			return false;
		}
		Member other = (Member)obj;
		
		if ( !this.fname.toLowerCase().equals(other.fname.toLowerCase()) )
			return false;
		if ( !this.lname.toLowerCase().equals(other.lname.toLowerCase()) )
			return false;
		if ( !this.dob.equals(other.dob) )
			return false;
		return true;
	}

	/**
	 * compareTo method receives a member object and compares between that and the original object.
	 * @param member Member
	 * @return int value of -1 if first member is smaller (lexicographically), 0 if members are equal, and
	 * 		1 if first member is bigger (lexicographically) than given member.
	 */
	@Override
	public int compareTo(Member member) { 
		String fullname = this.lname + " " + this.fname;
		String otherFullName = member.lname + " " + member.fname;
		return fullname.compareTo(otherFullName);
	}

	/**
	 * getExpire method returns the expiration date of member's membership
	 * @return expiration Date
	 */
	public Date getExpire() {
		return expire;
	}

	/**
	 * getLocation method returns the location of the gym where the member got their membership
	 * @return Location instance of the location of the gym where member got membership
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * getDob method returns the date of birth of the member.
	 * @return Date dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * membershipFee method returns the member's standard memberhsip fee
	 * @return double membership fee
	 */
	public double membershipFee() { 
		return STANDARD_ONE_TIME_FEE + STANDARD_DURATION* STANDARD_PER_MONTH;
	}
	
	public double membershipFeeNextBill() { 
		return STANDARD_DURATION* STANDARD_PER_MONTH;
	}

	/**
	 * decrementAvailableGuests method is a setter method for Family and Premium classes to decrease the
	 * 		available guest passes by one
	 */
	public void decrementAvailableGuests() {
	};

	/**
	 * incrementAvailableGuests method is a setter method for Family and Premium classes to increase the
	 * 		available guest passes by one
	 */
	public void incrementAvailableGuests() {
	};

	/**
	 * main method is the testbed to test the compareTo() method in Member class.
	 * @param args String array
	 */
	public static void main(String[] args) {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.MONTH, 3);
		
		int day = today.get(Calendar.DAY_OF_MONTH);
		int month = today.get(Calendar.MONTH) +1 ;
		int year = today.get(Calendar.YEAR);
		Date expire = new Date(month,day,year);
		System.out.println(expire);
	}
}
