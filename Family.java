package gym2;

/**
 * Family class has the number of guest passes remaining for a family member and the name, date of birth, and
 * 		location of membership in its constructor.
 * 		This class implements methods to calculate the membership fee, check if there is a location or guest
 * 		restrictions, and convert the membership toString
 * @author Selin Altiparmak, Libby Birenboim
 */
public class Family extends Member {
	private int guessPassRemaining;
	public static final double FAMILY_ONE_TIME_FEE = 29.99;
	public static final double FAMILY_PER_MONTH = 59.99;
	public static final int FAMILY_DURATION = 3;

	/**
	 * getGuessPassesRemaining method returns the number of guest passes remaining for the family member
	 * @return int guestPassesRemaining
	 */
	public int getGuessPassRemaining() {
		return guessPassRemaining;
	}

	/**
	 * setGuestPassRemaining method receives the number of guest passes remaining and sets the guest passes
	 * 		remaining to that number
	 * @param guessPassRemaining integer
	 */
	public void setGuestPassRemaining(int guessPassRemaining) {
		this.guessPassRemaining = guessPassRemaining;
	}

	/**
	 * decrementAvailableGuests method decreases the amount of guest passes remaining by one
	 */
	public void decrementAvailableGuests() {
		guessPassRemaining -= 1;
	};

	/**
	 * incrementAvailbaleGuests method increaes the amount of available guest passes remaining by one
	 */
	@Override
	public void incrementAvailableGuests() {
		guessPassRemaining += 1;
	};

	/**
	 * Family constructor constructs a family membership given the appropriate values
	 * @param fname String of first name
	 * @param lname String of last name
	 * @param dob Date of date of birth of member
	 * @param location Location of membership
	 */
	public Family(String fname, String lname, Date dob, Location location) {
		super(fname, lname, dob, location);
		// TODO Auto-generated constructor stub
		this.guessPassRemaining = 1;
	}

	/**
	 * Family constructor contructs a family membership with an expiration date based on the received values
	 * @param fname String of first name
	 * @param lname String of last name
	 * @param dob Date object of date of birth
	 * @param expire Date of expiration
	 * @param location Location of membership
	 */
	public Family(String fname, String lname, Date dob, Date expire, Location location) {
		super(fname, lname, dob, expire, location);
		// TODO Auto-generated constructor stub
		this.guessPassRemaining = 1;
	}

	/**
	 * membershipFee method returns the membership fee for the family member by overriding that of the Member
	 * 		class
	 * @return double type of the membership fee
	 */
	@Override
	public double membershipFee() { 
		return FAMILY_ONE_TIME_FEE + FAMILY_DURATION* FAMILY_PER_MONTH;
	}
	
	@Override
	public double membershipFeeNextBill() { 
		return FAMILY_DURATION* FAMILY_PER_MONTH;
	}

	/**
	 * isLocationRestriction method receives a location and checks if this family member has a location
	 * 		restriction for the fitness classes.
	 * @param location Location
	 * @return false since family member doesn't have a location restriction
	 */
	// Family and Premium class members are eligible to checkin any location
	@Override
	public boolean isLocationRestriction(Location location) {
		return false;
	}

	/**
	 * isGuessLocationRestriction method receives a location and checks if the guest who came with the family
	 * 		member has a location restriction for the fitness classes.
	 * @param location Location
	 * @return true if the location of membership is the location received (no restriction) and false otherwise
	 */
	// Family and Premium class members guests are eligible to checkin at membership location only
	@Override
	public boolean isGuestLocationRestriction(Location location) {
		return !( this.getLocation().name == location.name);
	}

	/**
	 * toStringStandard method uses the super toString method from Member class to return the standard membership
	 * @return String of standard membership
	 */
	public String toStringStandard() {
		 return super.toString() ;
	}

	/**
	 * toString method overrides the toString method from Member class and returns the family membership
	 * @return String of family membership (including amount of guest passes remaining)
	 */
	@Override
	public String toString() {
	 return toStringStandard() + ", (Family) guest-pass remaining: " + this.guessPassRemaining;
	}
}
