// package starsNTU;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentManager
{ //this is a control class
	protected ArrayList<Student> list = new ArrayList<Student>();
	
	/**
	 * point to a file with pre-existing data of Students
	 */
	private String filename = "student.dat";
	private Scanner scan = new Scanner(System.in);
	
	/**
	 * a string of identification number
	 * various Student details can be extracted from the identification number
	 * by partitioning it
	 * is not case sensitive
	 */
	private String studentID;
	

	private static StudentManager theinstance = null;

	/**
	 * Load student manager and read files to prepare for future edits
	 */
	private StudentManager()
	{
		System.out.println("Loading student data... Please wait...");
		try
		{
			list = (ArrayList<Student>) IOE.readSerializedObject(filename);
			if(list == null) list = new ArrayList<Student>();
			// for (int i = 0; i < list.size(); i++)
			// {
			// 	Student c = (Student) list.get(i);
			// 	System.out.println(c);
			// }
		}
		catch(Exception e){System.out.println( "Exception StudentManager() >> "+e.getMessage());}
		System.out.println("Load student data, done.\n");
	}

	/**
	 * re-initialize the StudentManager pointer to null
	 */
	public static StudentManager initiate() 
	{
		if(theinstance == null)
			theinstance = new StudentManager();
		return theinstance;
	}

	/**
	 * adds Student, method will ask for inputs for the parameters 
	 * to pass to the Student constructor
	 */
	public String addStudent()
	{
		System.out.print("Adding student. ");
		System.out.println("Enter the student ID: ");
		String sID = scan.next().toUpperCase(); scan.nextLine();
		while (sID.length() < 9 || sID.charAt(0) != 'U')
		{
			System.out.println("Invalid student ID. Please enter ID of at least 9 characters long starting with 'U'.");
			System.out.println("Re-enter student ID: ");
			sID = scan.next().toUpperCase(); scan.nextLine();
		}
		System.out.println("Enter the student name: ");
		String n = scan.nextLine();
		System.out.println("Enter a temporary password: ");
		String p = scan.nextLine();
		Student student = new Student(sID, n, p);
		studentID = student.getID();
		try
		{
			for(Student temp:list)
				if(studentID.equals(temp.getID()))
				{
					System.out.println("Student already exist."); 
					return "NA";
				}
			list.add(student);
			IOE.writeSerializedObject(filename, list);
			System.out.println("Successfully added student.");
			return sID;
		}
		catch ( Exception e ){
			System.out.println( "Exception addStudent() >> " + e.getMessage());
		}
		return "NA";
	}

	/**
	 * delete Student by its ID, if it exists in the list
	 */
	public void deleteStudent()
	{
		System.out.print("Enter student matric to delete: ");
		studentID = scan.next().toUpperCase();
		for(Student temp: list)
			if(studentID.equals(temp.getID()))
			{
				list.remove(temp);
				IOE.writeSerializedObject(filename, list);
				return;
			}
		System.out.println("Student not found.");
	}
	
	/**
	 * get the Student object that has matching studentID with the parameter 
	 */
	public Student getStudent(String profile)
	{
		studentID = profile;
		if(studentID == null)
		{
			System.out.print("Enter student matric to show info: ");
			studentID = scan.next().toUpperCase();
		}
		for(Student temp: list)
			if(studentID.equals(temp.getID()))
				return temp;
		System.out.println("Student not found.");
		return null;
	}
	
	/**
	 * get the Student name that has matching StudentID with the parameter
	 */
	public String getStudentName(String sID)
	{
		for(Student temp: list)
			if(studentID.equals(temp.getID()))
				return temp.getID();
		System.out.println("Student not found.");
		return "NA";
	}

	/**
	 * print the list of all Student with some formatting
	 */
	public void printStudents()
	{
		System.out.println("\nComplete list of students");
		System.out.println("-------------------------");
		try
		{
			for(Student temp:list)
				System.out.println(temp);
			System.out.println();
		}
		catch ( Exception e ){System.out.println( "Exception printStudents() >> " + e.getMessage());}
	}
}
