package gym2;

/**
 * MemberDatabase class includes list of members and size of list in its constructor.
 * 		it finds members within the list, gets members from the list, grows the member database, add members to
 * 		the database, remove members, and print member database list based on different criteria such as
 * 		non-organized and organized by county, expiration date, and members' names.
 * @author Selin Altiparmak, Libby Birenboim
 */
public class MemberDatabase {
	private Member [] mlist;
	private int size;
	public static final int INITIALSIZE = 4;
	public static final int GROWTH = 4;
	public static final int NOT_FOUND = -1;

	/**
	 * MemberDatabase constructor constructs an array of a list of members registered at the gym.
	 */
	public MemberDatabase() {
		mlist = new Member[INITIALSIZE];
		size = 0;
	}

	/**
	 * find method receives a member object and finds the member within the database.
	 * @param member Member
	 * @return int value of the member's index if found in database and -1 if not found in database.
	 */
	private int find(Member member) { 
		for ( int i = 0; i < size; i++ ) {
			if ( member.equals(mlist[i]) ) {
				return i;
			}
		}
		return NOT_FOUND;
	}

	/**
	 * exists method receives a member object and checks if the member exists in the member database.
	 * @param member Member
	 * @return boolean true if member exists in database and false otherwise
	 */
	public boolean exists(Member member) {
		return find(member) != NOT_FOUND;
	}

	/**
	 * getMember method receives a member's first name, last name, and date of birth and returns the member instance
	 * 		of that member from the database.
	 * @param fname first name of member
	 * @param lname last name of member
	 * @param dobDate Date of birth of member
	 * @return Member object of member
	 */
	public Member getMember(String fname, String lname, Date dobDate) {
		Member member = new Member(fname, lname, dobDate,null);
		int mindex = find(member);
		if ( mindex == NOT_FOUND ) {
			return null;
		} else {
			return mlist[mindex];
		}
	}

	/**
	 * grow method expands the member database array for it to be bigger in order to add more members.
	 */
	private void grow() {
		Member [] glist = new Member[mlist.length + GROWTH];
		for ( int i = 0; i < mlist.length; i++ ) {
			glist[i] = mlist[i];
		}
		mlist = glist;
	}

	/**
	 * add method receives a member object and adds the member to the database.
	 * @param member Member
	 * @return boolean true if member was added successfully and false otherwise
	 */
	public boolean add(Member member) { 
		if ( member == null )
			return false;
		if ( find(member) != NOT_FOUND )
			return false;
		if ( mlist.length == size ) {
			grow();
		}
		mlist[size] = member;
		size += 1;
		return true;
	}

	/**
	 * remove method receives a member object and removes them from the member database list.
	 * @param member Member
	 * @return boolean true if member was removed successfully and false otherwise.
	 */
	public boolean remove(Member member) {
		int index = find(member);
		if ( index == NOT_FOUND )
			return false;
		for ( int i = index; i < size - 1; i++ ) {
			mlist[i] = mlist[i+1];
		}
		size--;
		return true;
	}

	/**
	 * isEmpty method returns true or false if the member database is empty (size is zero)
	 * @return boolean true if member database is empty and false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * print method prints the members in the database list as they are currently presented in the array.
	 */
	public String print () {
		String s="";
		for ( int i = 0; i < size; i++ ) {
			s+= mlist[i] + "\n";
		}
		return s;
	}

	/**
	 * printByCounty method prints the members in the database list ordered by county and then zipcode.
	 */
	public String printByCounty() {
		if (size > 1) {
			for ( int index = 0; index < size - 1; index++ ) {
				Member m = mlist[index];
				// compare the member at index with the min of the rest
				Member minMember = mlist[index+1];
				// find the minimum of the rest
				int minMemberIndex = index+1;
				for ( int i = index + 2; i < size; i++ ) {
					Member current = mlist[i];
					if ( current.getLocation().County.compareTo(minMember.getLocation().County) < 0 ) {
						minMember = current;
						minMemberIndex = i;
					} else if (current.getLocation().County.equals(minMember.getLocation().County)) {
						if ( current.getLocation().zipCode.compareTo(minMember.getLocation().zipCode) < 0 ) {
							minMember = current;
							minMemberIndex = i;
						} 	
					}
				}
				if ( minMember.getLocation().County.compareTo(m.getLocation().County) < 0 ) {
					mlist[index] = minMember;
					mlist[minMemberIndex ] = m;
				} else if (minMember.getLocation().County.equals(m.getLocation().County)) {
					if ( minMember.getLocation().zipCode.compareTo(m.getLocation().zipCode) < 0 ) {
						mlist[index] = minMember;
						mlist[minMemberIndex ] = m;
					} 	
				}
			}
		}
		return print () ;
	}

	/**
	 * printByExpirationDate method prints the members in the database list ordered by the expiration date of
	 * 		their membership.
	 */
	public String printByExpirationDate() { 
		if ( size > 1 ) {
			for ( int index = 0; index < size - 1; index++ ) {
				Member m = mlist[index];
				// compare the member at index with the min of the rest
				Member minMember = mlist[index+1];
				// find the minimum of the rest
				int minMemberIndex = index+1;
				for ( int i = index + 2; i < size; i++ ) {
					Member current = mlist[i];
					if ( current.getExpire().compareTo(minMember.getExpire()) < 0 ) {
						minMember = current;
						minMemberIndex = i;
					}
				}
				if ( minMember.getExpire().compareTo(m.getExpire()) < 0 ) {
					mlist[index] = minMember;
					mlist[minMemberIndex] = m;
				}
			}
		}
		return print () ;
	}

	/**
	 * printByName method prints the members in the database list ordered by their last then first names.
	 */
	public String printByName() { // selection sort
		if ( size > 1 ) {
			for ( int index = 0; index < size - 1; index++ ) {
				Member m = mlist[index];
				// compare the member at index with the min of the rest
				Member minMember = mlist[index + 1];
				// find the minimum of the rest
				int minMemberIndex = index + 1;
				for ( int i = index + 2; i < size; i++ ) {
					Member current = mlist[i];
					if ( current.compareTo(minMember) < 0 ) {
						minMember = current;
						minMemberIndex = i;
					}
				}
				if (minMember .compareTo(m) < 0) {
					mlist[index] = minMember;
					mlist[minMemberIndex] = m;
				}
			}
		}
		return print () ;
	}

	/**
	 * printWithMembershipFee method prints the information of the members in the database including each member's
	 * 		membership fee
	 */
	public String printWithMembershipFee() { // selection sort
		String s="";
		for ( int i = 0; i < size; i++ ) {
			s+= mlist[i] + ", Membership fee: $" + mlist[i].membershipFee() + "\n";
		}
		return s;
		
	}
	
	public String printWithMembershipFeeNextBill() { // selection sort
		String s="";
		for ( int i = 0; i < size; i++ ) {
			s+= mlist[i] + ", Membership fee: $" + mlist[i].membershipFeeNextBill() + "\n";
		}
		return s;
		
	}

	/**
	 * main method tests out all the other methods in this class.
	 * @param args String array
	 */
	public static void main(String[] args) {
		MemberDatabase mdb = new MemberDatabase();
		Member m = new Member("Z","March",new Date("1/20/2004"),Location.BRIDGEWATER);
		mdb.add(m);
		Member m2 = new Member("t","Maarch",new Date("3/31/1990"),Location.PISCATAWAY);
		mdb.add(m2);
		Member m3 = new Member("JJ","March",new Date("3/31/1991"),Location.EDISON);
		Member m4 = new Member("BBA","March",new Date("3/31/1992"),Location.EDISON);
		Member m5 = new Member("BBB","March",new Date("3/31/1993"),Location.EDISON);
		mdb.add(m3);
		mdb.add(m4);
		mdb.add(m5);
		mdb.printByCounty();
	}
}
