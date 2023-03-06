package gym2;

/**
 * Location enum class includes the 5 gym locations (their names, zipcodes, and county) in its constructor.
 * 		this class also checks if a location is valid.
 * @author Selin Altiparmak, Libby Birenboim
 */
public enum Location {
	BRIDGEWATER(0), 
	EDISON(1),
	FRANKLIN(2), 
	PISCATAWAY(3), 
	SOMERVILLE(4);
	public String name;
	public String zipCode;
	public String County;

	/**
	 * Location constructor receives an int value and constructs a Location that correlates with that value.
	 * @param value
	 */
	Location(int value) {
		switch (value) {
		case 0:
			name  = "BRIDGEWATER";
			zipCode = "08807";
			County = "SOMERSET";
			break;
		case 1:
			name  = "EDISON";
			zipCode = "08837";
			County = "MIDDLESEX";
			break;
		case 2:
			name  = "FRANKLIN";
			zipCode = "08873";
			County = "SOMERSET";
			break;
		case 3:
			name  = "PISCATAWAY";
			zipCode = "08854";
			County = "MIDDLESEX";
			break;
		case 4:
			name  = "SOMERVILLE";
			zipCode = "08876";
			County = "SOMERSET";			
			break;
		default:
			break;
		}
	}

	/**
	 * isValid method receives a String s of a location and checks if the location is a valid one.
	 * @param s String
	 * @return boolean true if valid location and false otherwise
	 */
	public static boolean isValid(String s) {
		boolean validLocation = true;
		Location locations[] = Location.values();
		for( Location location : locations ) {
	         if ( location.name.equals(s.toUpperCase()) ) {
	        	 return true;
	         }
	      }
		return false;
	}
}
