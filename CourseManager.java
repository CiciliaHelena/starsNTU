// package starsNTU;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CourseManager
{
	protected ArrayList<Course> list = new ArrayList<Course>();
	private String filename = "course.dat";
	private Scanner scan = new Scanner(System.in);
	private String courseCode;
	private static CourseManager theinstance = null;
	private static StudentCourseManager studentcoursemanager = StudentCourseManager.initiate();


	private CourseManager()
	{

		try
		{
			list = (ArrayList) IOE.readSerializedObject(filename);
			if(list == null) list = new ArrayList<Course>();
			for (int i = 0; i < list.size(); i++)
			{
				Course c = (Course) list.get(i);
				System.out.println(c);
			}
		}
		catch(Exception e){System.out.println( "Exception CourseManager() >> "+e.getMessage());}
	}

	public static CourseManager initiate()
	{
		if(theinstance == null)
			theinstance = new CourseManager();
		return theinstance;
	}

	public void addCourse()
	{	String courseCode;
		String courseName;
		String coordinator;
		int[] numOfGroup = new int[3];  // [lec, tut, lab]
		Map <String, Integer> tutGroups = new Hashtable<String, Integer>();  // <index, vacancy>
		Map <String, Integer> labGroups = new Hashtable<String, Integer>();  // <index, vacancy>
		int overallVacancy;
		int examWeightage;
		Map <String, Integer> courseworkComponent = new Hashtable<String, Integer>();  // <type, weightage>
		int n;
		String temp;
		
		System.out.print("Adding course. ");
		Scanner read = new Scanner(System.in);
		System.out.println("Enter course code : ");
		courseCode = read.next().toUpperCase(); 
		read.nextLine();
		System.out.println("Enter course name : ");
		courseName = read.nextLine().toUpperCase();
		System.out.println("Enter course coordinator");
		coordinator = read.nextLine();

		String[] ar = {"lecture", "tutorial", "lab"};
		for(int i = 0; i < 3; i++)
		{
			System.out.println("does it has "+ar[i]+"? (yes/no)");
			temp = read.next().toLowerCase();
			while(!temp.equals("yes") && !temp.equals("y") && !temp.equals("no") && !temp.equals("n"))
			{
				System.out.print("Try again : ");
				temp = read.next().toLowerCase();
			}
			if(temp.equals("yes") || temp.equals("y")) numOfGroup[i] = 1;
			else numOfGroup[i] = 0;
		}
		
		System.out.print("Enter the total vacancy for this course: ");
		overallVacancy = IOE.scint();

		int k, l;
		boolean okflag = false;

		if(numOfGroup[1] != 0)
		{
			while(!okflag)
			{
				k = 0;
				System.out.print("Enter the number of tutorial group available : ");
				n = IOE.scint();
				numOfGroup[1] = n;
				for(int i = 0; i < n; i++)
				{
					System.out.print("Please enter tutorial group "+(i+1)+" index : ");
					temp = read.next();
					System.out.print("Please enter tutorial group "+(i+1)+" vacancy : ");
					l = IOE.scint();
					tutGroups.put(temp.toUpperCase(), l);
					k += l;
				}
				if(k < overallVacancy)
				{
					System.out.println("Total vacancy of lab group must not less than overall vacancy");
					System.out.println("Repeat the process");
				}
				else okflag = true;
			}
		}

		if(numOfGroup[2] != 0)
		{
			while(!okflag)
			{
				k = 0;
				System.out.print("Enter the number of lab group available : ");
				n = IOE.scint();
				numOfGroup[2] = n;
				for(int i = 0; i < n; i++)
				{
					System.out.print("Please enter lab group "+(i+1)+" index : ");
					temp = read.next();
					System.out.print("Please enter lab group "+(i+1)+" vacancy : ");
					l = IOE.scint();
					labGroups.put(temp.toUpperCase(), l);
					k += l;
				}
				if(k < overallVacancy)
				{
					System.out.println("Total vacancy of lab group must not less than overall vacancy");
					System.out.println("Repeat the process");
				}
				else okflag = true;
			}
		}


		System.out.print("Enter the exam weightage: "); 
		examWeightage = IOE.scint();
		System.out.println("Enter the number of coursework components in this course: ");
		n = IOE.scint();
		read.nextLine();
		if (n == 1) {
			courseworkComponent.put("Coursework", 100);
		}
		else
			for(int i = 0 ;i < n; i++)
			{
				System.out.print("Key in the name of component "+(i+1)+" : ");
				temp = read.nextLine();
				System.out.print("Key in the weightage of component "+(i+1)+" : ");
				courseworkComponent.put(temp, IOE.scint());
			}
		Course course = new Course(courseCode, courseName, coordinator, numOfGroup, tutGroups,labGroups, overallVacancy, examWeightage, courseworkComponent);
		courseCode = course.getCourseCode();
		try
		{
			for(Course temp1:list)
				if(courseCode.equals(temp1.getCourseCode()))
					{System.out.println("Course already exist"); return;}
			list.add(course);
			IOE.writeSerializedObject(filename, list);
			studentcoursemanager.updateCourseTM(course.getCourseCode()); // (By CY) for StudentCourse pls keep this
			System.out.println("successfully updated course list");
		}
		catch ( Exception e ){System.out.println( "Exception addCourse() >> " + e.getMessage());}
	}

	public void deleteCourse()
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

	public void checkVacancy()
	{
		System.out.print("Enter course code to check the vacancy: ");
		courseCode = scan.next().toUpperCase();
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode()))
				{System.out.println("Vacancy for "+courseCode+" : "+temp.getVacancy()); return;}
		System.out.println("Course not found");
	}
	
	public int getVacancy(String courseCode){ // for StudentCourse
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())) { 
				return temp.getVacancy();
			}
		return -1;
	}
	
	public ArrayList checkAvailableTutGroup(String courseCode) // for StudentCourse
	{
		ArrayList<String> keys = new ArrayList();
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())){
				Map<String, Integer> groups = temp.getTutGroup();
				for(Map.Entry<String, Integer> e : groups.entrySet()) {
				    if (e.getValue() != 0)			     
				    	keys.add(e.getKey());
				}
			}
		return keys;
	}
	
	public ArrayList checkAvailableLabGroup(String courseCode) // for StudentCourse
	{
		ArrayList<String> keys = new ArrayList();
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())){
				Map<String, Integer> groups = temp.getLabGroup();				
				for(Map.Entry<String, Integer> e : groups.entrySet()) {
				    if (e.getValue() != 0)			     
				    	keys.add(e.getKey());
				}
			}
		return keys;
	}
	
	public int getNumOfComponent(String courseCode) { // for StudentCourse
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())){
				return temp.getCourseworkComponent().size();
			}
		return -1;
	}

	public void showCoordinator()
	{
		System.out.print("Enter course code to show coordinator : ");
		courseCode = scan.next().toUpperCase();
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode()))
				{System.out.println("Coordinator for "+courseCode+" : "+temp.getCoordinator()); return;}
		System.out.println("Course not found");
	}

	public void printCourses()
	{
		System.out.println("All courses");
		try
		{
			for(Course temp:list)
				System.out.println(temp);
		}
		catch ( Exception e ){System.out.println( "Exception printCourses() >> " + e.getMessage());}
	}
	
	public Set getTutGroup (String courseCode) { // for StudentCourse
		for(Course temp: list) {
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getTutGroup().keySet();
		}
		return null;
	}

	public Set getLabGroup (String courseCode) { // for StudentCourse
		for(Course temp: list) {
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getLabGroup().keySet();
		}
		return null;
	}
	
	public void updateVacancy(String courseCode, String tutGroup, String labGroup) { // for StudentCourse // "NA" for non tut or lab groups
		for(Course temp: list) {
			if(courseCode.equals(temp.getCourseCode())) {
				temp.setVacancy(temp.getVacancy()-1);
				if (!tutGroup.equals("NA")) {
					if (temp.getTutGroup().containsKey(tutGroup)) {
						temp.setTutVacancy(tutGroup, (int) temp.getTutGroup().get(tutGroup)); 
					}
					else System.out.println("Invalid tutorial group.");
				}
				if (!labGroup.equals("NA")) {
					if (temp.getLabGroup().containsKey(labGroup)) {
						temp.setLabVacancy(labGroup, (int) temp.getLabGroup().get(labGroup)); 
					}
					else System.out.println("Invalid lab group.");
				}
				IOE.writeSerializedObject(filename, list);
			}
		}
	}
	
	public int getExamWeightage(String courseCode) {
		for(Course temp: list) {
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getExamWeightage();
		}
		return -1;
	}

		public String getCourseName(String courseCode) {
		for(Course temp: list) {
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getCourseName();
		}
		return "No such course";
	}

	public Map<String, Integer> getCourseworkComponent(String courseCode) {
		for(Course temp: list) {
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getCourseworkComponent();
		}
		return null;
	}

}
