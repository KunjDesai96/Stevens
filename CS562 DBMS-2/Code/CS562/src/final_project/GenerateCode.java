package final_project;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This is the main class from where program will start.
 * 
 * @author Kunj Desai
 *
 */
public class GenerateCode {

	public static HashMap<String, String> key_dataType = new HashMap<String, String>();

	/**
	 * Load the drivers for connecting to the DB.
	 */
	private void connect() {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.postgresql.Driver"); // Loads the required driver
			System.out.println("Success loading Driver!");
		} catch (Exception exception) {
			System.out.println("Fail loading Driver!");
			exception.printStackTrace();
		}

	}

	/**
	 * Main method - The program will start here.
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File input_file;
		GenerateCode generatecode = new GenerateCode();
		generatecode.connect();
		key_dataType = InformationSchema.getInformationSchema();
		
		System.out.println("\n");
		System.out.println("What type of query do you want to run?\n");
		System.out.println("Type MF for mf queries or EMF for emf queries:");
		
		//Checking for the query type.
		Scanner in = new Scanner(System.in);
		String typeOfQuery = in.nextLine();
		typeOfQuery = typeOfQuery.replace(" ","");
		typeOfQuery = typeOfQuery.toUpperCase();
		
		
		if(typeOfQuery.equals("MF"))
		{
			//input_file = new File("/Users/kunj/Desktop/Stevens/Fall '19/CS562/CS562 Final Project/Inputs/MF Inputs/sampleQuery1.txt");
			//input_file = new File("/Users/kunj/Desktop/Stevens/Fall '19/CS562/CS562 Final Project/Inputs/MF Inputs/sampleQuery2.txt");
			//input_file = new File("/Users/kunj/Desktop/Stevens/Fall '19/CS562/CS562 Final Project/Inputs/MF Inputs/sampleQuery3.txt");
			//input_file = new File("/Users/kunj/Desktop/Stevens/Fall '19/CS562/CS562 Final Project/Inputs/MF Inputs/sampleQuery4.txt");
			input_file = new File("/Users/kunj/Desktop/Stevens/Fall '19/CS562/CS562 Final Project/Inputs/MF Inputs/sampleQuery5.txt");
			ParseVariables.addArguments(input_file);
			MFGenerator.generateMF(key_dataType);
			System.out.println("Generation of MF code is done.");
		}
		else if(typeOfQuery.equals("EMF"))
		{
			input_file = new File("/Users/kunj/Desktop/Stevens/Fall '19/CS562/CS562 Final Project/Inputs/EMF Inputs/sampleQuery1.txt");
			//input_file = new File("/Users/kunj/Desktop/Stevens/Fall '19/CS562/CS562 Final Project/Inputs/EMF Inputs/sampleQuery2.txt");
			//input_file = new File("/Users/kunj/Desktop/Stevens/Fall '19/CS562/CS562 Final Project/Inputs/EMF Inputs/sampleQuery3.txt");
			//input_file = new File("/Users/kunj/Desktop/Stevens/Fall '19/CS562/CS562 Final Project/Inputs/EMF Inputs/sampleQuery4.txt");
			//input_file = new File("/Users/kunj/Desktop/Stevens/Fall '19/CS562/CS562 Final Project/Inputs/EMF Inputs/sampleQuery5.txt");
			ParseVariables.addArguments(input_file);
			EMFGenerator.generateEMF(key_dataType);
			System.out.println("Generation of EMF code is done.");
		}
		else
		{
			System.out.println("Please enter valid option.");
		}
			
	}

}
