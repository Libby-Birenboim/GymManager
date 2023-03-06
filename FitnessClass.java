package gym2;
import java.util.ArrayList;
import java.util.Objects;

/**
 * FitnessClass class includes name, instructor, time, and location in its constructors, as well as a member list
 * 		and a guest list.
 * 		Implements more methods to check if someone is already signed into a fitness class, check members and
 * 		guests into fitness classes, remove members and guests from fitness classes, get the name of class, instructor
 * 		location, and time of fitness class, check if an object is equal to a fitness class, get class info, and get
 * 		participants of the fitness class.
 * @author Selin Altiparmak, Libby Birenboim
 */
public class FitnessClass {
		private String name;
		private String instructor;
		private Time time;
		private Location location;
		private ArrayList<Member> memberList = new ArrayList<Member>();
		private ArrayList<Member> guestList = new ArrayList<Member>();

		/**
	 	* FitnessClass constructor constructs a FitnessClass object with the name of the class, name
		 * 		of instructor, time of class, and location of class.
		 * @param name String of name of class
		 * @param instructor String of name of instructor
		 * @param time Time of class
		 * @param location Location of class
	 	*/
		public FitnessClass(String name, String instructor, Time time, Location location) {
			this.name = name;
			this.instructor = instructor;
			this.time = time;
			this.location = location;
		}

	/**
	 * isAlreadySignedIn method receives a member and checks if they are already signed into the fitness class
	 * @param member Member object
	 * @return true if member is signed in and false otherwise
	 */
	public boolean isAlreadySignedIn(Member member) {
			return memberList.contains(member);
		}

	/**
	 * checkIn method receives a member and checks them into the fitness class assuming all is well
	 * @param m Member object
	 * @return true if successfully checked in and false otherwise
	 */
	public boolean checkIn(Member m) {
		if ( m.isLocationRestriction(location)  ) {
			return false;
		}
		if (isAlreadySignedIn(m)) {
			return false;
		}
		Date todayDate = new Date();
		if (m.getExpire().compareTo(todayDate) < 0) {
			return false;
		}
		memberList.add(m);
		return true;
	}

	/**
	 * checkInGuest method receives a member who brought a guest and checks the guest into the class while decreasing the
	 * 		amount of available guest passes that the member now has
	 * @param m Member
	 * @return true if guest successfully checked in and false otherwise
	 */
	public boolean checkInGuest(Member m) {
		if ( m.isGuestLocationRestriction(location)  ) {
			return false;
		}
		if (m instanceof Family) {
			Family f = (Family)m;
			if (f.getGuessPassRemaining()<=0) {
				return false;
			}
		}
		
		if (m instanceof Premium) {
			Premium pr = (Premium)m;
			if (pr.getGuessPassRemaining()<=0) {
				return false;
			}
		}
		Date todayDate = new Date();
		if (m.getExpire().compareTo(todayDate) < 0) {
			return false;
		}
		guestList.add(m);
		m.decrementAvailableGuests();
		return true;
	}

	/**
	 * remove method receives a member and checks them out of the fitness class assuming all is well
	 * @param m Member
	 * @return true if successfully removed member and false otherwise
	 */
	public boolean remove(Member m) {
		if (memberList.contains(m)) {
			memberList.remove(m);
			return true;	
		} else {
			return false;
		}
		
	}

	/**
	 * removeGuest method receives a member who brought a guest and checks the guest out of the fitness
	 * 		class while increasing the amount of available guest passes.
	 * @param m Member
	 * @return true if successfully removed guest and false otherwise
	 */
	public boolean removeGuest(Member m) {
		if (guestList.contains(m)) {
			guestList.remove(m);
			m.incrementAvailableGuests();
			return true;
		} else {
			return false;	
		}
		
	}

	/**
	 * getName method returns the name of the fitness class
	 * @return String name of fitness class
	 */
	public String getName() { return name; }

	/**
	 * getInstructor method returns the name of the instructor of the fitness class
	 * @return String name of instructor
	 */
	public String getInstructor() { return instructor; }

	/**
	 * getTime method returns the time of the fitness class
	 * @return String time of fitness class
	 */
	public String getTime() { return time.toString(); }

	/**
	 * equals method receives an object and checks if it equals the fitness class object
	 * @param obj Object
	 * @return true if it equals the fitness class and false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		FitnessClass other = (FitnessClass) obj;
		return this.instructor.toLowerCase().equals(other.instructor.toLowerCase()) && this.name.toLowerCase().equals(other.name.toLowerCase()) && this.location == other.location;
	}

	/**
	 * getLocation method returns the location of the fitness class
	 * @return Location instance of the location of the fitness class
	 */
	public Location getLocation() {
			return location;
		}

	/**
	 * getClassInfo method returns a String containing information about the fitness class
	 * @return String info about fitness class
	 */
	public String getClassInfo() {
			String s = this.getName().toUpperCase() + " - " + this.getInstructor().toUpperCase() + ", " + this.getTime().toString() + ", " + this.getLocation().name + "\n";
			return s;
	}

	/**
	 * getClassFullInfo method returns the String containing all information about the fitness class
	 * @return String all info about fitness class (including zipcode and county of class location)
	 */
	public String getClassFullInfo() {
		String s = this.getName().toUpperCase() + " - " + this.getInstructor().toUpperCase() + ", " + this.getTime().toString() + ", " + this.getLocation().name+ ", " + this.getLocation().zipCode + ", " + this.getLocation().County + "\n";
		return s;
	}

	/**
	 * getParticipants method returns all the participants in a fitness class
	 * @return String of participants in the fitness class
	 */
		public String getParticipants() {
			String s = "";
			if ( memberList.size() > 0 ) {
				s += "- Participants -\n";
				for ( Member m: memberList ) {
					s += "   " + m.toString() + "\n";
				}
			}
			if ( guestList.size() > 0 ) {
				s += "- Guests -\n";
				for ( Member m: guestList ) {
					s += "   " + m.toString() + "\n";
				}
			}
			return s;
		}

		/**
		 * main method checks if the other methods in FitnessClass class are working properly (it's a testbed).
		 * @param args String array
		 */
		public static void main(String[] args) {
			
			MemberDatabase mdb = new MemberDatabase();
			Member m = new Member("Z","March",new Date("1/20/2004"),new Date("3/30/2023"),Location.BRIDGEWATER);
			mdb.add(m);
			Member m2 = new Member("t","Maarch",new Date("3/31/1990"),new Date("6/30/2023"),Location.PISCATAWAY);
			mdb.add(m2);
			Member m3 = new Member("JJ","March",new Date("3/31/1991"),new Date("5/30/2023"),Location.EDISON);
			Member m4 = new Member("BBA","March",new Date("3/31/1992"),new Date("5/30/2023"),Location.EDISON);
			Member m5 = new Member("BBB","March",new Date("3/31/1993"),new Date("5/30/2023"),Location.EDISON);
			mdb.add(m3);
			mdb.add(m4);
			mdb.add(m5);
		}
	}
