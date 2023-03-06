package gym2;

/**
 * RunProject1 class calls GymManager class in order to run the project.
 * @author Selin Altiparmak, Libby Birenboim
 */
public class RunProject1 {

    /**
     * main method opens a new GymManager instance and calls run() method to execute the GymManager program.
     * @param args String array
     */
    public static void main(String[] args) {
        GymManager m = new GymManager();
        m.run();
    }
}
