package gym2;

/**
 * Premium class contains the premium membership and extends the family membership.
 * 		This class can compute the membership fee and use toString to return the info of a premium member
 * @author Selin Altiparmak, Libby Birenboim
 */
public class Premium extends Family {
	public static final double PREMIUM_ONE_TIME_FEE = 0;
	public static final double PREMIUM_PER_MONTH = 59.99;
	public static final int PREMIUM_DURATION = 11;

	/**
	 * Premium constructor receives information of the member and creates a premium member using super keyword
	 * @param fname first name of member
	 * @param lname last name of member
	 * @param dob Date of birth of member
	 * @param location Location of membership
	 */
	public Premium(String fname, String lname, Date dob, Location location) {
		super(fname, lname, dob, location);
		// TODO Auto-generated constructor stub
		this.setGuestPassRemaining(3);
	}

	/**
	 * Premium constructor receives info of the member and creates a premium member along with the expiration date
	 * 		of the membership, using super keyword
	 * @param fname first name of member
	 * @param lname last name of member
	 * @param dob Date of birth of member
	 * @param expire Date of expiration of membership
	 * @param location Location of membership
	 */
	public Premium(String fname, String lname, Date dob, Date expire, Location location) {
		super(fname, lname, dob, expire, location);
		// TODO Auto-generated constructor stub
		this.setGuestPassRemaining(3);
	}

	/**
	 * membershipFee method calculates the membership fee for the premium member
	 * @return double membership fee number
	 */
	@Override
	public double membershipFee() { 
		return PREMIUM_ONE_TIME_FEE + PREMIUM_DURATION * PREMIUM_PER_MONTH;
	}
	
	@Override
	public double membershipFeeNextBill() { 
		return  PREMIUM_DURATION * PREMIUM_PER_MONTH;
	}

	/**
	 * toString method overrides the toString from the super class and returns the premium member's info in String form
	 * @return String of premium member's info
	 */
	@Override
	public String toString() {
	 return super.toStringStandard() + ", (Premium) Guest-pass remaining: " + this.getGuessPassRemaining();
	}
}
