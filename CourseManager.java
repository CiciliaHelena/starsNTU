// package starsNTU;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class CourseManager
{
	protected static ArrayList<Course> list = new ArrayList<Course>();
	private static String filename = "course.dat";
	private static Scanner scan = new Scanner(System.in);
	private static String courseCode;

	static
	{
		try
		{
			list = (ArrayList) IOE.readSerializedObject(filename);
			for (int i = 0; i < list.size(); i++)
			{
				Course c = (Course) list.get(i);
				System.out.println(c);
			}
		}
		catch(Exception e){System.out.println( "Exception CourseManager() >> "+e.getMessage());}
	}

	public static void addCourse()
	{
		System.out.print("Adding course. ");
		Course course = new Course();
		courseCode = course.getCourseCode();
		try
		{
			for(Course temp:list)
				if(courseCode.equals(temp.getCourseCode()))
					{System.out.println("Course already exist"); return;}
			list.add(course);
			IOE.writeSerializedObject(filename, list);
			StudentCourseManager.updateCoursetTM(course.getCourseCode()); // (By CY) for StudentCourse pls keep this
			System.out.println("successfully updated course list");
		}
		catch ( Exception e ){System.out.println( "Exception addCourse() >> " + e.getMessage());}
	}

	public static void deleteCourse()
	{
		System.out.print("Enter course code to delete : ");
		courseCode = scan.next().toUpperCase();
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode()))
			{
				list.remove(temp);
				IOE.writeSerializedObject(filename, list);
				return;
			}
		System.out.println("Course not found");
	}

	public static void checkVacancy()
	{
		System.out.print("Enter course code to check the vacancy: ");
		courseCode = scan.next().toUpperCase();
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode()))
				{System.out.println("Vacancy for "+courseCode+" : "+temp.getVacancy()); return;}
		System.out.println("Course not found");
	}
	
	public static ArrayList checkAvailableTutGroup(String courseCode) // for StudentCourse
	{
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())){
				Map<String, Integer> groups = temp.getTutGroup();
				ArrayList<String> keys = new ArrayList();
				for(Map.Entry<String, Integer> e : groups.entrySet()) {
				    if (e.getValue() != 0)			     
				    	keys.add(e.getKey());
				}
				return keys;
			}
		return null;
	}
	
	public static ArrayList checkAvailableLabGroup(String courseCode) // for StudentCourse
	{
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())){
				Map<String, Integer> groups = temp.getLabGroup();
				ArrayList<String> keys = new ArrayList();
				for(Map.Entry<String, Integer> e : groups.entrySet()) {
				    if (e.getValue() != 0)			     
				    	keys.add(e.getKey());
				}
				return keys;
			}
		return null;
	}
	
	public static int getNumOfComponent(String courseCode) { // for StudentCourse
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())){
				return temp.getCourseworkComponent().size();
			}
		return -1;
	}

	public static void showCoordinator()
	{
		System.out.print("Enter course code to show coordinator : ");
		courseCode = scan.next().toUpperCase();
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode()))
				{System.out.println("Coordinator for "+courseCode+" : "+temp.getCorrodinator()); return;}
		System.out.println("Course not found");
	}

	public static void printCourses()
	{
		System.out.println("Print all courses.");
		try
		{
			for(Course temp:list)
				System.out.println(temp);
		}
		catch ( Exception e ){System.out.println( "Exception printCourses() >> " + e.getMessage());}
	}
	
	public static Map getTutGroup (String courseCode) { // for StudentCourse
		for(Course temp: list) {
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getTutGroup();
		}
		return null;
	}

	public static int getExamWeightage(String courseCode) {
		for(Course temp: list) {
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getExamWeightage();
		}
		return -1;
	}
	
	public static Map<String, Integer> getCourseworkComponent(String courseCode) {
		for(Course temp: list) {
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getCourseworkComponent();
		}
		return null;
	}

}
