// package starsNTU;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentManager
{ //this is a control class
	protected static ArrayList<Student> list = new ArrayList<Student>();
	private static String filename = "student.dat";
	private static Scanner scan = new Scanner(System.in);
	private static String studentID;

	static
	{
		try
		{
			list = (ArrayList) IOE.readSerializedObject(filename);
			// for (int i = 0; i < list.size(); i++)
			// {
			// 	Student c = (Student) list.get(i);
			// 	System.out.println(c);
			// }
		}
		catch(Exception e){
			//System.out.println( "Exception StudentManager() >> "+e.getMessage());
		}
	}

	public static void addStudent()
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
				//if(studentID.equals(temp.getID())) {
				if(studentID == temp.getID()) {
					System.out.println("Student already exist."); 
					return;
				}
			list.add(student);
			IOE.writeSerializedObject(filename, list);
			StudentCourseManager.updateStudentTM(student.getName()); // (By CY) for StudentCourse pls keep this
			System.out.println("Successfully added student.");
		}
		catch ( Exception e ){
			System.out.println( "Exception addStudent() >> " + e.getMessage());
		}
	}

	public static void deleteStudent()
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
	
	public static Student getStudent()
	{
		System.out.print("Enter student matric to show info : ");
		studentID = scan.next().toUpperCase();
		for(Student temp: list)
			if(studentID.equals(temp.getID()))
				return temp;
		System.out.println("Student not found");
		return null;
	}

	public static void printStudents()
	{
		System.out.println("Print all students.");
		try
		{
			for(Student temp:list)
				System.out.println(temp);
		}
		catch ( Exception e ){System.out.println( "Exception printStudents() >> " + e.getMessage());}
	}
}
