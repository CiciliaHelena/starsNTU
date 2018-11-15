// package starsNTU;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentManager
{ //this is a control class
	protected ArrayList<Student> list = new ArrayList<Student>();
	private String filename = "student.dat";
	private Scanner scan = new Scanner(System.in);
	private String studentID;
	private static StudentManager theinstance = null;

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

	public static StudentManager initiate()
	{
		if(theinstance == null)
			theinstance = new StudentManager();
		return theinstance;
	}

	public String addStudent()
	{
		System.out.print("Adding student. ");
		System.out.println("Enter the student ID: ");
		String sID = scan.next().toUpperCase(); scan.nextLine();
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
		System.out.println("Student not found");
	}
	
	public Student getStudent(String profile)
	{
		studentID = profile;
		if(studentID == null)
		{
			System.out.print("Enter student matric to show info : ");
			studentID = scan.next().toUpperCase();
		}
		for(Student temp: list)
			if(studentID.equals(temp.getID()))
				return temp;
		System.out.println("Student not found");
		return null;
	}
	
	public String getStudentName(String sID)
	{
		for(Student temp: list)
			if(studentID.equals(temp.getID()))
				return temp.getID();
		System.out.println("Student not found");
		return "NA";
	}

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
