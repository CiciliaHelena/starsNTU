import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ProfessorManager {
	/**
	 * for pretty formating and standardisation
	 */
	public static final String SEPARATOR = "|";
	
	/**
	 * location of pre-existing data of professors
	 */
	String filename = "professor.txt";
	
	protected ArrayList<Professor> al = new ArrayList<Professor>();
	
	/**
	 * pointer of current thread, initialized to null
	 */
	private static ProfessorManager theinstance = null;

	/**
	 * reads next professor data from file
	 */
	private ProfessorManager() {
		try {
			al = readProfessors(filename);
			if(al == null) al = new ArrayList<Professor>();
		} catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
	}
	
	/**
	 * gets the next data of professor if holding nothing
	 */
	public static ProfessorManager initiate() {
		if(theinstance == null)
			theinstance = new ProfessorManager();
		return theinstance;
	}
	
	/**
	 * prints the list of all professors and their details
	 */
	public void printProfessors () {
		for (int i = 0 ; i < al.size() ; i++) {
			Professor prof = (Professor)al.get(i);
			System.out.println((i+1) + "- " + prof.getName());
		}	
	}
	
	/**
	 * gets a ith Professor object from the file
	 */
	public Professor getProfessor(int i) {
		return al.get(i-1);
	}
	
	/**
	 * gets the ith Professor's name from the file
	 */
	public String getProfName(int i) {
		return al.get(i-1).getName();
	}
	
	/**
	 * prints the details of a professor given the name
	 */
	public void printDetails(String n) {
		for (int i = 0 ; i < al.size() ; i++) {
			Professor cur = al.get(i);
			if (cur.getName().equals(n)) {
				System.out.println("- Course Coordinator: " + cur.getName());
				System.out.println("- Contact Number: " + cur.getContact());
				System.out.println("- Email: " + cur.getEmail());
				System.out.println("- Office: " + cur.getOffice());
				System.out.println();
			}
		}
	}
	
	/**
	 * read file for Professor objects and trims the result
	 */
	public static ArrayList readProfessors(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store Professors data

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				String  name = star.nextToken().trim();	// first token
				String  email = star.nextToken().trim();	// second token
				int  contact = Integer.parseInt(star.nextToken().trim()); // third token
				String office = star.nextToken().trim(); // fourth token
				Professor prof = new Professor(name, email,contact, office);
				// add to Professors list
				alr.add(prof) ;
			}
			return alr ;
	}
	
	/**
	 * basic file reading Professor Object
	 */
	public static List read(String fileName) throws IOException {
		List data = new ArrayList() ;
	    Scanner scanner = new Scanner(new FileInputStream(fileName));
	    try {
	      while (scanner.hasNextLine()){
	        data.add(scanner.nextLine());
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    return data;
	  }
}
