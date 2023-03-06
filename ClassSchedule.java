package gym2;

/**
 * ClassSchedule class contains an array of fitness classes and number of classes in its constructor
 * 		this class implements methods to grow the fitness class list, get number of classes, print the class schedule,
 * 		get the array of classes, add and find fitness classes, and check if classes and instructors exist
 * @author Selin Altiparmak, Libby Birenboim
 */
public class ClassSchedule {
	private FitnessClass [] classes;
	private int numClasses;
	
	public static final int INITIALSIZE = 4;
	public static final int GROWTH = 4;
	public static final int NOT_FOUND = -1;

	/**
	 * ClassSchedule constructor constructs a new ClassScheudle type with an array of the fitness classes
	 * 		along with the number of classes.
	 */
	public ClassSchedule() {
		classes = new FitnessClass [INITIALSIZE];
		numClasses = 0;
	}
	
	/**
	 * grow method makes the fitness class list array bigger.
	 */
	private void grow() {
		FitnessClass [] glist = new FitnessClass[classes.length + GROWTH];
		for ( int i = 0; i < classes.length; i++ ) {
			glist[i] = classes[i];
		}
		classes = glist;
	}

	/**
	 * getNumClasses method returns the number of classes in the schedule
	 * @return integer number of classes in fitness class schedule
	 */
	public int getNumClasses() {
		return numClasses;
	}

	/**
	 * printAll method prints all the fitness classes in the schedule
	 */
	public String printALL() {
		String s="";
		if (numClasses == 0) {
			s = "Fitness class schedule is empty.\n";
			return s;
		}
		
		for (FitnessClass fc:getAllClasses() ) {
			s += fc.getClassInfo();
			s += fc.getParticipants();
		}
		return s;
	}

	/**
	 * getAllClasses method returns the array of the fitness classes
	 * @return FitnessClass array of fitness classes
	 */
	public FitnessClass [] getAllClasses() {
		FitnessClass [] f = new FitnessClass [numClasses];
		for (int i = 0; i < numClasses; i++) {
			f[i] = classes[i];
		}
		return f;
	}

	/**
	 * addFitnessClass method receives a fitnessClass object and adds that class to the schedule
	 * @param fitnessClass FitnessClass object
	 * @return true after adding fitness class successfully
	 */
	public boolean addFitnessClass(FitnessClass fitnessClass ) { 
		
		if ( classes.length == numClasses ) {
			grow();
		}
		classes[numClasses] = fitnessClass;
		numClasses += 1;
		return true;
	}

	/**
	 * findFitnessClass method receives a fitnessClass object and finds the class in the schedule
	 * @param fitnessClass FitnessClass object
	 * @return FitnessClass object of the fitness class found in the schedule or null if not found
	 */
	public FitnessClass findFitnessClass(FitnessClass fitnessClass) {
		for (int i = 0; i < numClasses; i++) {
			if (classes[i].equals(fitnessClass)) {
				return classes[i];
			}
		}
		return null;
	}

	/**
	 * doesClassNameExist method receives a fitnessClass object and checks if the name of the class exists as a
	 * 		fitness class.
	 * @param fitnessClass FitnessClass object
	 * @return true if fitness class name exists and false otherwise
	 */
	public boolean doesClassNameExist(FitnessClass fitnessClass) {
		for (int i = 0; i < numClasses ; i++)
		{
			if (classes[i].getName().toLowerCase().equals(fitnessClass.getName().toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * doesClassExist method receives a fitnessClass object and checks if the class exists as a
	 * 		fitness class.
	 * @param fitnessClass FitnessClass object
	 * @return true if fitness class exists and false otherwise
	 */
	public boolean doesClassExist(FitnessClass fitnessClass) {
		for (int i = 0; i < numClasses ; i++)
		{
			if (classes[i].getName().toLowerCase().equals(fitnessClass.getName().toLowerCase()) && classes[i].getInstructor().toLowerCase().equals(fitnessClass.getInstructor().toLowerCase()) && classes[i].getLocation() == fitnessClass.getLocation()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * doesInstructorExist method receives a fitnessClass object and checks if the instructor of the class exists
	 * 		as an instructor of that specific fitness class.
	 * @param fitnessClass FitnessClass object
	 * @return true if fitness class instructor exists and false otherwise
	 */
	public boolean doesInstructorExist(FitnessClass fitnessClass) {
		for (int i = 0; i < numClasses; i++)
		{
			if ( classes[i].getInstructor().toLowerCase().equals(fitnessClass.getInstructor().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
