package fitness;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import gym2.ClassSchedule;
import gym2.Date;
import gym2.Family;
import gym2.FitnessClass;
import gym2.Location;
import gym2.Member;
import gym2.MemberDatabase;
import gym2.Premium;
import gym2.Time;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * GymManagerController class is a javaFX class that controls the GUI of Gym Manager
 * 		It initiates buttons and text fields and controls them.
 * @author Selin Altiparmak, Libby Birenboim
 */
public class GymManagerController {
	public static final int CURRENTYEAR = 2022;
	public static final int NOT_FOUND = -1;
	Date todayDate = new Date(); // create a new Date object for today's date
	MemberDatabase mdb = new MemberDatabase (); // create a new MemberDatabase object to store all members' information
	ClassSchedule classSchedule = new ClassSchedule(); 

	@FXML
	private Button memberAdd, memberRemove;

	@FXML
	private TextField memberFirstName, memberLastName, memberLocation;
	@FXML
	private TextField fcClassName, fcInstructor, fcFirstName, fcLastName, fcLocation;

	@FXML
	private DatePicker memberDOB;

	@FXML
	private DatePicker fcDOB;

	@FXML
	private RadioButton  memberStandard, memberFamily, memberPremium;
	@FXML
	private RadioButton  fcMember, fcGuest;

	@FXML
	private TextArea memberOutput;

	@FXML
	private TextArea informationOutput;

	@FXML
	private TextArea fcOutput;

	public GymManagerController() {
		super();
	}

	/**
	 * checkEverythingIsValidBeforeAddingMember method receives date of birth, location, and today's date
	 * 		and makes sure that all values are valid. Method also creates a location object for location.
	 * 		returns the location or null if there was a problem.
	 * @param dob Date of birth
	 * @param location String of location
	 * @param today Date of today
	 * @return locationLoc Location or null if there was a problem.
	 */
	private Location checkEverythingIsValidBeforeAddingMember ( Date dob, String location, Date today ,boolean outputToTab1) {
		if ( !dob.isValid() ) {
			if (outputToTab1) {
				memberOutput.appendText("DOB " + dob + ": invalid calendar date!\n");	
			} else {
				informationOutput.appendText("DOB " + dob + ": invalid calendar date!\n");
			}

			return null;
		}

		if ( !Location.isValid(location) ) {
			if (outputToTab1) {
				memberOutput.appendText(location + ": invalid location!\n");	
			} else {
				informationOutput.appendText(location + ": invalid location!\n");
			}

			return null;
		}

		Location locationLoc = Location.valueOf(location.toUpperCase()); // convert location to Location object
		if ( dob.compareTo(today) >= 0 ) {
			if (outputToTab1) {
				memberOutput.appendText("DOB " + dob + ": cannot be today or a future date!\n");	
			} else {
				informationOutput.appendText("DOB " + dob + ": cannot be today or a future date!\n");
			}

			return null;
		}

		if ( !dob.isEighteenOrOver()) {
			if (outputToTab1) {
				memberOutput.appendText("DOB " + dob + ": must be 18 or older to join!\n");	
			} else {
				informationOutput.appendText("DOB " + dob + ": must be 18 or older to join!\n");
			}
			return null;
		}
		return locationLoc;
	}

	/**
	 * doLM method receives a txt file and loads the list of members from the file to the member database.
	 */
	@FXML
	private void doLM () {
		Scanner sc_file;
		try {
			sc_file = new Scanner(new File("memberList.txt"));
			String line ;
			while(sc_file.hasNextLine()) {
				line = sc_file.nextLine();
				if (line.strip().equals("")) {
					continue;
				}
				StringTokenizer st = new StringTokenizer(line);
				String fname = st.nextToken();
				String lname = st.nextToken();
				String dob = st.nextToken();
				String exp = st.nextToken();
				String location = st.nextToken();

				Date dobDate = new Date(dob); // convert dob to Date object
				Date expDate = new Date(exp); // convert dob to Date object
				Date today = new Date();
				Location locationLoc = checkEverythingIsValidBeforeAddingMember(dobDate, location, today, false);
				if ( locationLoc == null ) { // something was invalid
					return;
				}

				// if everything is valid: (create new member and add them to database)
				Member member = new Member(fname, lname, dobDate, expDate,  locationLoc);
				mdb.add(member);
			}
			// after having loaded (added) all the members into the member database, print:
			informationOutput.appendText("-list of members loaded-\n");
			informationOutput.appendText(mdb.print());
			informationOutput.appendText("-end of list-");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * doP method receives a String line, takes it apart in order to print the member database in its
	 *      original order, using other methods
	 */
	@FXML
	private void doP (  ) { // print list as it is currently in array (without sorting)
		if (!mdb.isEmpty()) {
			informationOutput.appendText("\n");
			informationOutput.appendText("-list of members-\n");
			informationOutput.appendText(mdb.print());
			informationOutput.appendText("-end of list-\n");
			informationOutput.appendText("\n");
		} else {
			informationOutput.appendText("Member database is empty!\n");
		}
	}

	/**
	 * doPC method receives a String line, takes it apart in order to print the member database,
	 *      ordered based on county and zipcode, using other methods
	 */
	@FXML
	private void doPC (  ) { // print list ordered by county names and then zipcodes
		if (!mdb.isEmpty()) {
			informationOutput.appendText("\n");
			informationOutput.appendText("-list of members sorted by county and zipcode-\n");
			informationOutput.appendText(mdb.printByCounty());
			informationOutput.appendText("-end of list-\n");
			informationOutput.appendText("\n");
		} else {
			informationOutput.appendText("Member database is empty!\n");
		}
	}

	/**
	 * doPN method receives a String line, takes it apart in order to print the member database,
	 *      ordered based on members' last and then first names, using other methods
	 */
	@FXML
	private void doPN (  ) { // print list ordered by last name then first name
		if (!mdb.isEmpty()) {
			informationOutput.appendText("\n");
			informationOutput.appendText("-list of members sorted by last name, and first name-\n");
			informationOutput.appendText(mdb.printByName());
			informationOutput.appendText("-end of list-\n");
			informationOutput.appendText("\n");
		} else {
			informationOutput.appendText("Member database is empty!\n");
		}
	}

	/**
	 * doPD method receives a String line, takes it apart in order to print the member database,
	 *      ordered based on member's membership expiration date, using other methods
	 */
	@FXML
	private void doPD (  ) { // print list ordered by expiration dates
		if (!mdb.isEmpty()) {
			informationOutput.appendText("\n");
			informationOutput.appendText("-list of members sorted by membership expiration date-\n");
			informationOutput.appendText(mdb.printByExpirationDate());
			informationOutput.appendText("-end of list-\n");
			informationOutput.appendText("\n");
		} else {
			informationOutput.appendText("Member database is empty!\n");
		}
	}

	/**
	 * doLS method receives a txt file and loads the fitness class schedule from that file into the
	 * 		fitness class schedule in the software system
	 */
	@FXML
	private void doLS ()  {
		Scanner sc_file;
		try {
			sc_file = new Scanner(new File("classSchedule.txt"));
			String line = "";
			while(sc_file.hasNextLine()) {
				line = sc_file.nextLine();
				if (line.strip().equals("")) {
					continue;
				}
				StringTokenizer st = new StringTokenizer(line);
				String className = st.nextToken();
				String instructor = st.nextToken();
				String time = st.nextToken();
				Time timeT = Time.valueOf(time.toUpperCase());

				String place = st.nextToken();
				Location locationLoc = Location.valueOf(place.toUpperCase()); // convert place to a location object
				FitnessClass fitnessClass = new FitnessClass(className, instructor, timeT, locationLoc);
				classSchedule.addFitnessClass(fitnessClass);
			}
			// after completed loading fitness classes from file into software system, print fitness classes list
			informationOutput.appendText("-Fitness classes loaded-\n");
			informationOutput.appendText(classSchedule.printALL());
			informationOutput.appendText("-end of class list.\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * doS method receives a String line, takes it apart in order to display the fitness class schedule
	 *      for today
	 */
	@FXML
	private void doS () { // display fitness class schedule for today
		if (classSchedule.getNumClasses() > 0)
			informationOutput.appendText("-Fitness classes-\n");

		informationOutput.appendText(classSchedule.printALL());

		if (classSchedule.getNumClasses() > 0)
			informationOutput.appendText("-end of class list.\n");
	}

	/**
	 * doAddMember method adds a member with a standard membership to the member database and catches exceptions
	 */
	@FXML
	private void doAddMember () { // add member with standard membership to member database

		//memberFirstName, memberLastName, memberLocation;

		//memberDOB;

		// memberStandard, memberFamily, memberPremium;
		String fname = memberFirstName.getText().trim();
		String lname = memberLastName.getText().trim();
		String location = memberLocation.getText().trim();
		String dob ="";
		boolean validInputs=true;

		try {
			if (fname.equals("")) {
				memberOutput.appendText("Member first name can not be left blank\n");
				validInputs=false;
			}
			
			for (int i=0; i<fname.length(); i++) {
				
				if (!Character.isLetter(fname.charAt(i))) {
					memberOutput.appendText("Member first name must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}
			if (lname.equals("")) {
				memberOutput.appendText("Member last name can not be left blank\n");
				validInputs=false;
			}
			for (int i=0; i<lname.length(); i++) {
				
				if (!Character.isLetter(lname.charAt(i))) {
					memberOutput.appendText("Member last name must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}

			dob = memberDOB.getValue().toString();	
		} catch (NullPointerException e) {
			memberOutput.appendText("Date of birth field can not be left blank\n");
			validInputs=false;
		} finally {
			if (location.equals("")) {
				memberOutput.appendText("Location can not be left blank\n");
				validInputs=false;
			}
			for (int i=0; i<location.length(); i++) {
				
				if (!Character.isLetter(location.charAt(i))) {
					memberOutput.appendText("Member location must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}
		}

		if (!validInputs) {
			return;
		}
		String [] splitted = dob.split("-");
		String year = splitted [0];
		int month = Integer.parseInt(splitted [1]);
		int day = Integer.parseInt(splitted [2]);
		dob = month + "/" + day + "/" + year;

		Date dobDate ;
		try {
			dobDate = new Date(dob); // convert dob to Date object
		} catch (Exception e) {
			memberOutput.appendText("Invalid date\n");
			return;
		}
		Date today = new Date();
		Location locationLoc = checkEverythingIsValidBeforeAddingMember(dobDate, location, today,true);
		if ( locationLoc == null ) { // something was invalid
			return;
		}

		// if everything is valid: (create new member and add them to database)

		boolean result = false;

		if (memberStandard.isSelected()) {
			Member member = new Member(fname, lname, dobDate,  locationLoc);
			result = mdb.add(member);
		} else if (memberFamily.isSelected()) {
			Family member = new Family(fname, lname, dobDate,  locationLoc);
			result = mdb.add(member);
		} else {
			Premium member = new Premium(fname, lname, dobDate,  locationLoc);
			result = mdb.add(member);
		}

		if ( !result ) {
			memberOutput.appendText (fname + " " + lname + " is already in the database.\n");
		} else {
			memberOutput.appendText(fname + " " + lname + " added.\n");	
		}
	}

	/**
	 * doR method receives a String line, takes it apart in order to remove a member using other methods
	 */
	@FXML
	private void doR ( ) { // remove member
		String fname =memberFirstName.getText().trim();
		String lname =memberLastName.getText().trim();

		String dob ="";
		boolean validInputs=true;


		try {
			if (fname.equals("")) {
				memberOutput.appendText("Member name can not be left blank\n");
				validInputs=false;
			}
			for (int i=0; i<fname.length(); i++) {
				
				if (!Character.isLetter(fname.charAt(i))) {
					memberOutput.appendText("Member first name must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}
			if (lname.equals("")) {
				memberOutput.appendText("Member last name can not be left blank\n");
				validInputs=false;
			}
			for (int i=0; i<lname.length(); i++) {
				
				if (!Character.isLetter(lname.charAt(i))) {
					memberOutput.appendText("Member last name must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}

			dob = memberDOB.getValue().toString();	
		} catch (NullPointerException e) {
			memberOutput.appendText("Date of birth field can not be left blank\n");
			validInputs=false;
			
		}

		if (!validInputs) {
			return;
		}

		String [] splitted = dob.split("-");
		String year = splitted [0];
		int month = Integer.parseInt(splitted [1]);
		int day = Integer.parseInt(splitted [2]);
		dob = month + "/" + day + "/" + year;

		Date dobDate ;
		try {
			dobDate = new Date(dob); // convert dob to Date object
		} catch (Exception e) {
			memberOutput.appendText("Invalid date\n");
			return;
		}

		Member member = new Member(fname, lname, dobDate,null,null);
		boolean result = mdb.remove(member);
		if (!result) {
			memberOutput.appendText(fname + " " + lname + " is not in the database.\n");
		} else {
			memberOutput.appendText(fname + " " + lname + " removed.\n");	
		}
	}

	/**
	 * do FitnessAdd class is a javaFX class that adds a member to a fitness class through the GUI and catches exceptions
	 */
	@FXML
	private void doFitnessAdd() {
		String className = fcClassName.getText().trim();
		String instructor = fcInstructor.getText().trim();
		String location = fcLocation.getText().trim();
		String fname = fcFirstName.getText().trim();
		String lname = fcLastName.getText().trim();

		String dob = "";
		boolean validInputs = true;

		try {
			if (className.equals("")) {
				fcOutput.appendText("Class name can not be left blank\n");
				validInputs = false;
			}
			for (int i = 0; i < className.length(); i++) {
				
				if (!Character.isLetter(className.charAt(i))) {
					fcOutput.appendText("Member class name must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}
			if (instructor.equals("")) {
				fcOutput.appendText("Instructor can not be left blank\n");
				validInputs = false;
			}
			for (int i = 0; i < instructor.length(); i++) {
				
				if (!Character.isLetter(instructor.charAt(i))) {
					fcOutput.appendText("Member instructor must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}
			if (location.equals("")) {
				fcOutput.appendText("Location can not be left blank\n");
				validInputs = false;
			}
			for (int i=0; i < location.length(); i++) {
				
				if (!Character.isLetter(location.charAt(i))) {
					fcOutput.appendText("Member location must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}
			if (fname.equals("")) {
				fcOutput.appendText("Member first name can not be left blank\n");
				validInputs = false;
			}
			for (int i = 0; i < fname.length(); i++) {
				
				if (!Character.isLetter(fname.charAt(i))) {
					fcOutput.appendText("Member first name must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}
			if (lname.equals("")) {
				fcOutput.appendText("Member last name can not be left blank\n");
				validInputs = false;
			}
			for (int i = 0; i < lname.length(); i++) {
				
				if (!Character.isLetter(lname.charAt(i))) {
					fcOutput.appendText("Member last name must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}

			dob = fcDOB.getValue().toString();	
		} catch (NullPointerException e) {
			fcOutput.appendText("Date of birth field can not be left blank\n");
			validInputs = false;
		}

		if (!validInputs) {
			return;
		}

		if (fcMember.isSelected()) {
			doC();
		} else {
			doCG();
		}
	}

	/**
	 * doFitnessRemove method is a javaFX method that removes a member from a fitness class thorugh the GUI
	 * 		and catches exceptions.
	 */
	@FXML
	private void doFitnessRemove() {
		String className = fcClassName.getText().trim();
		String instructor = fcInstructor.getText().trim();
		String location = fcLocation.getText().trim();
		String fname = fcFirstName.getText().trim();
		String lname = fcLastName.getText().trim();

		String dob ="";
		boolean validInputs=true;

		try {
			if (className.equals("")) {
				fcOutput.appendText("Class name can not be left blank\n");
				validInputs = false;
			}
			for (int i = 0; i < className.length(); i++) {
				
				if (!Character.isLetter(className.charAt(i))) {
					memberOutput.appendText("Member class name must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}
			if (instructor.equals("")) {
				fcOutput.appendText("Instructor can not be left blank\n");
				validInputs = false;
			}
			for (int i = 0; i < instructor.length(); i++) {
				
				if (!Character.isLetter(instructor.charAt(i))) {
					memberOutput.appendText("Member instructor must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}
			if (location.equals("")) {
				fcOutput.appendText("Location can not be left blank\n");
				validInputs = false;
			}
			for (int i=0; i < location.length(); i++) {
				
				if (!Character.isLetter(location.charAt(i))) {
					memberOutput.appendText("Member location must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}
			if (fname.equals("")) {
				fcOutput.appendText("Member name can not be left blank\n");
				validInputs = false;
			}
			for (int i = 0; i < fname.length(); i++) {
				
				if (!Character.isLetter(fname.charAt(i))) {
					memberOutput.appendText("Member first name must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}
			if (lname.equals("")) {
				fcOutput.appendText("Member last name can not be left blank\n");
				validInputs = false;
			}
			for (int i = 0; i < lname.length(); i++) {
				
				if (!Character.isLetter(lname.charAt(i))) {
					memberOutput.appendText("Member last name must be composed of letters\n");
					validInputs=false;
					break;	
				}
			}

			dob = fcDOB.getValue().toString();	
		} catch (NullPointerException e) {
			fcOutput.appendText("Date of birth field can not be left blank\n");
			validInputs = false;
		}

		if (!validInputs) {
			return;
		}

		if (fcMember.isSelected()) {
			doD();
		} else {
			doDG();
		}
	}

	/**
	 * doC method checks a member into a class
	 */
	private void doC ( ) { // member check-in to classes
		String className = fcClassName.getText();
		String instructor = fcInstructor.getText();
		String location = fcLocation.getText();
		String fname = fcFirstName.getText();
		String lname = fcLastName.getText();
		String dob = fcDOB.getValue().toString();
		String [] splitted = dob.split("-");
		String year = splitted [0];
		int month = Integer.parseInt(splitted [1]);
		int day = Integer.parseInt(splitted [2]);
		dob = month + "/" + day + "/" + year;

		Date dobDate = new Date(dob); // convert dob to Date object

		if ( !dobDate.isValid() ) {
			fcOutput.appendText("DOB " + dob + ": invalid calendar date!\n");
			return;
		}

		if ( !Location.isValid(location) ) {
			fcOutput.appendText(location + " - invalid location.\n");
			return;
		}
		Location locationLoc = Location.valueOf(location.toUpperCase()); // convert location to Location object
		FitnessClass search = new FitnessClass(className,instructor,null,locationLoc);


		if ( !classSchedule.doesClassNameExist(search) ) {
			fcOutput.appendText(className + " - class does not exist.\n");
			return;
		}

		// (check if instructor exists):
		if ( !classSchedule.doesInstructorExist(search) ) {
			fcOutput.appendText(instructor + " - instructor does not exist.\n");
			return;
		}		

		Member member = mdb.getMember(fname, lname, dobDate);
		if ( member == null ) { // member isn't in database therefore does not exist
			fcOutput.appendText(fname + " " + lname + " " + dob + " is not in the database.\n");
			return;
		}

		if ( member.getExpire().compareTo(todayDate) < 0 ) { // membership expired since expiration date is smaller than today's date
			fcOutput.appendText(fname + " " + lname + " " + dob + " membership expired.\n");
			return;
		}

		if ( !classSchedule.doesClassExist(search) ) {
			fcOutput.appendText(className + " by " + instructor + " does not exist at " + location + "\n");
			return;
		}

		// check if member has standard membership then if location is same as their membership location:
		if ( member instanceof Member) {
			if ( member.isLocationRestriction(locationLoc)  ) {
				fcOutput.appendText(fname + " " + lname + " checking in " + locationLoc.name + ", " + locationLoc.zipCode + ", " +
						member.getLocation().County + " - standard membership location restriction.\n");
				return;
			}
		}

		FitnessClass fitnessClass = classSchedule.findFitnessClass(search);

		if (fitnessClass.isAlreadySignedIn(member)) {
			fcOutput.appendText(fname + " " + lname + " already checked in.\n");
			return;		
		}
		for (FitnessClass fc : classSchedule.getAllClasses()) {
			if (fc.isAlreadySignedIn(member) && fc.getTime().equals(fitnessClass.getTime())) {
				fcOutput.appendText("Time conflict - " + fitnessClass.getClassFullInfo() + "\n");
				return;
			}
		}

		// if we got here, all is good and member can check into class
		fitnessClass.checkIn(member);
		fcOutput.appendText(fname + " " + lname + " checked in " + className.toUpperCase() + " - " + instructor.toUpperCase() +
				", " + fitnessClass.getTime() + ", " + fitnessClass.getLocation().toString() + "\n");
		fcOutput.appendText(fitnessClass.getParticipants()+"\n");
	}

	/**
	 * doD method removes a member from a fitness class after they have already checked into the class
	 */
	private void doD ( ) { // drop class after member checked into it earlier (member is done with fitness class and checking out)
		String className = fcClassName.getText();
		String instructor = fcInstructor.getText();
		String location = fcLocation.getText();
		String fname = fcFirstName.getText();
		String lname = fcLastName.getText();
		String dob = fcDOB.getValue().toString();
		String [] splitted = dob.split("-");
		String year = splitted [0];
		int month = Integer.parseInt(splitted [1]);
		int day = Integer.parseInt(splitted [2]);
		dob = month + "/" + day + "/" + year;

		Date dobDate = new Date(dob); // convert date of birth of the Date object

		if ( !dobDate.isValid() ) {
			fcOutput.appendText("DOB " + dob + ": invalid calendar date!\n");
			return;
		}

		if ( !Location.isValid(location) ) {
			fcOutput.appendText(location + " - invalid location.\n");
			return;
		}
		Location locationLoc = Location.valueOf(location.toUpperCase()); // convert location to Location object
		FitnessClass search = new FitnessClass(className, instructor, null, locationLoc);

		// NEW FOR PROJECT 2:
		if ( !classSchedule.doesClassNameExist(search) ) { // assuming we create a method that checks if class exists in FitnessClass class (takes String argument of class name). returns boolean
			fcOutput.appendText(className + " - class does not exist.\n");
			return;
		}

		// (check if instructor exists):
		if ( !classSchedule.doesInstructorExist(search) ) {
			fcOutput.appendText(instructor + " - instructor does not exist.\n");
			return;
		}		

		Member member = mdb.getMember(fname, lname, dobDate);
		if ( member == null ) { // member isn't in database therefore does not exist
			fcOutput.appendText(fname + " " + lname + " " + dob + " is not in the database.\n");
			return;
		}

		if ( member.getExpire().compareTo(todayDate) < 0 ) { // membership expired since expiration date is smaller than today's date
			fcOutput.appendText(fname + " " + lname + " " + dob + " membership expired.\n");
			return;
		}

		if ( !classSchedule.doesClassExist(search) ) { // assuming we create a method that checks if class exists in FitnessClass class (takes String argument of class name). returns boolean
			fcOutput.appendText(className + " by " + instructor + " does not exist at " + location + "\n");
			return;
		}

		FitnessClass fitnessClass = classSchedule.findFitnessClass(search);

		if (!fitnessClass.isAlreadySignedIn(member)) {
			fcOutput.appendText(fname + " " + lname + " did not check in.\n");
			return;		
		}

		// if we got here, all is good and member can check into class
		fitnessClass.remove(member);
		fcOutput.appendText(fname + " " + lname + " done with the class.\n");
	}

	/**
	 * doCG method checks a guest into a fitness class
	 */
	private void doCG ( ) {
		String className = fcClassName.getText();
		String instructor = fcInstructor.getText();
		String location = fcLocation.getText();
		String fname = fcFirstName.getText();
		String lname = fcLastName.getText();
		String dob = fcDOB.getValue().toString();
		String [] splitted = dob.split("-");
		String year = splitted [0];
		int month = Integer.parseInt(splitted [1]);
		int day = Integer.parseInt(splitted [2]);
		dob = month + "/" + day + "/" + year;


		Date dobDate = new Date(dob); // convert date of birth of the Date object

		if ( !dobDate.isValid() ) {
			fcOutput.appendText("DOB " + dob + ": invalid calendar date!\n");
			return;
		}

		if ( !Location.isValid(location) ) {
			fcOutput.appendText(location + " - invalid location.\n");
			return;
		}
		Location locationLoc = Location.valueOf(location.toUpperCase()); // convert location to Location object
		FitnessClass search = new FitnessClass(className, instructor, null, locationLoc);

		// NEW FOR PROJECT 2:
		if ( !classSchedule.doesClassNameExist(search) ) { // assuming we create a method that checks if class exists in FitnessClass class (takes String argument of class name). returns boolean
			fcOutput.appendText(className + " - class does not exist.\n");
			return;
		}

		// (check if instructor exists):
		if ( !classSchedule.doesInstructorExist(search) ) {
			fcOutput.appendText(instructor + " - instructor does not exist.\n");
			return;
		}		

		Member member = mdb.getMember(fname, lname, dobDate);
		if ( member == null ) { // member isn't in database therefore does not exist
			fcOutput.appendText(fname + " " + lname + " " + dob + " is not in the database.\n");
			return;
		}

		if ( member.getExpire().compareTo(todayDate) < 0 ) { // membership expired since expiration date is smaller than today's date
			fcOutput.appendText(fname + " " + lname + " " + dob + " membership expired.\n");
			return;
		}

		if (!(member instanceof Premium) && !(member instanceof Family)) {
			fcOutput.appendText("Standard membership - guest check-in is not allowed.\n");
			return;
		}

		if ( !classSchedule.doesClassExist(search) ) { // assuming we create a method that checks if class exists in FitnessClass class (takes String argument of class name). returns boolean
			fcOutput.appendText(className + " by " + instructor + " does not exist at " + location + "\n");
			return;
		}

		// check if member has standard membership then if location is same as their membership location:

		if ( member.isGuestLocationRestriction(locationLoc)  ) {
			fcOutput.appendText(fname + " " + lname + " Guest checking in " + locationLoc.name + ", " + locationLoc.zipCode + ", " +
					locationLoc.County + " - guest location restriction.\n");
			return;
		}

		if (member instanceof Family) {
			Family f = (Family)member;
			if (f.getGuessPassRemaining()<=0) {
				fcOutput.appendText(fname + " " + lname + " ran out of guest pass.\n");
				return;
			}
		}

		if (member instanceof Premium) {
			Premium pr = (Premium)member;
			if (pr.getGuessPassRemaining()<=0) {
				fcOutput.appendText(fname + " " + lname + " ran out of guest pass.\n");
				return;
			}
		}

		FitnessClass fitnessClass = classSchedule.findFitnessClass(search);

		// if we got here, all is good and member can check into class
		fitnessClass.checkInGuest(member);
		fcOutput.appendText(fname + " " + lname + " (guest) checked in " + className.toUpperCase() + " - " + instructor.toUpperCase() +
				", " + fitnessClass.getTime() + ", " + fitnessClass.getLocation().toString() + "\n");
		fcOutput.appendText(fitnessClass.getParticipants()+"\n");
	}

	/**
	 * doDG method completes a family guest check-out for a fitness class after the guest is done with the class
	 * 		and keeps track of the remaining number of guest passes.
	 */
	private void doDG (  ) {
		String className = fcClassName.getText();
		String instructor = fcInstructor.getText();
		String location = fcLocation.getText();
		String fname = fcFirstName.getText();
		String lname = fcLastName.getText();
		String dob = fcDOB.getValue().toString();
		String [] splitted = dob.split("-");
		String year = splitted [0];
		int month = Integer.parseInt(splitted [1]);
		int day = Integer.parseInt(splitted [2]);
		dob = month + "/" + day + "/" + year;


		Date dobDate = new Date(dob); // convert date of birth of the Date object

		if ( !dobDate.isValid() ) {
			fcOutput.appendText("DOB " + dob + ": invalid calendar date!\n");
			return;
		}

		if ( !Location.isValid(location) ) {
			fcOutput.appendText(location + " - invalid location.\n");
			return;
		}
		Location locationLoc = Location.valueOf(location.toUpperCase()); // convert location to Location object
		FitnessClass search = new FitnessClass(className,instructor,null,locationLoc);

		if ( !classSchedule.doesClassNameExist(search) ) { // assuming we create a method that checks if class exists in FitnessClass class (takes String argument of class name). returns boolean
			fcOutput.appendText(className + " - class does not exist.\n");
			return;
		}

		// (check if instructor exists):
		if ( !classSchedule.doesInstructorExist(search) ) {
			fcOutput.appendText(instructor + " - instructor does not exist.\n");
			return;
		}		

		Member member = mdb.getMember(fname, lname, dobDate);
		if ( member == null ) { // member isn't in database therefore does not exist
			fcOutput.appendText(fname + " " + lname + " " + dob + " is not in the database.\n");
			return;
		}

		if ( member.getExpire().compareTo(todayDate) < 0 ) { // membership expired since expiration date is smaller than today's date
			fcOutput.appendText(fname + " " + lname + " " + dob + " membership expired.\n");
			return;
		}

		if ( !classSchedule.doesClassExist(search) ) { // assuming we create a method that checks if class exists in FitnessClass class (takes String argument of class name). returns boolean
			fcOutput.appendText(className + " by " + instructor + " does not exist at " + location + "\n");
			return;
		}

		FitnessClass fitnessClass = classSchedule.findFitnessClass(search);

		// if we got here, all is good and member can check into class
		fitnessClass.removeGuest(member);
		fcOutput.appendText(fname + " " + lname + " Guest done with the class.\n");
	}

	/**
	 * doPF method prints the list of members and their membership fees in the text area in the GUI
	 */
	@FXML
	private void doPF (  ) {
		if (!mdb.isEmpty()) {
			informationOutput.appendText("\n");
			informationOutput.appendText("-list of members with membership fees-\n");
			informationOutput.appendText(mdb.printWithMembershipFee()); 
			informationOutput.appendText("-end of list-\n");
			informationOutput.appendText("\n");
		} else {
			informationOutput.appendText("Member database is empty!\n");
		}
	}

	/**
	 * doPFNextBill method prints the list of members and their membership fee for next bill in the text area in the GUI
	 */
	@FXML
	private void doPFNextBill (  ) {
		if (!mdb.isEmpty()) {
			informationOutput.appendText("\n");
			informationOutput.appendText("-list of members with membership fees-\n");
			informationOutput.appendText(mdb.printWithMembershipFeeNextBill()); 
			informationOutput.appendText("-end of list-\n");
			informationOutput.appendText("\n");
		} else {
			informationOutput.appendText("Member database is empty!\n");
		}
	}
}
