// package starsNTU;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CourseManager
{
	protected ArrayList<Course> list = new ArrayList<Course>();
	
	/**
	 * points to a pre-existing data of Courses
	 */
	private String filename = "course.dat";
	private Scanner scan = new Scanner(System.in);
	
	/**
	 * courseCode is a string with 2 characters of abbreviation of courseName in front, 
	 * followed by 4 numbers indicating their level and ID
	 */
	private String courseCode;
	
	/**
	 * pointer to read the file, initialized to null
	 */
	private static CourseManager theinstance = null;
	
	/**
	 * initializes ProfessorManager class for use
	 */
	private static ProfessorManager professormanager = ProfessorManager.initiate();

	/**
	 * Initializes CourseManager to load coursedata for use in the future
	 * Initializes the control class
	 */
	private CourseManager()
	{
		System.out.println("Loading course data... Please wait...");
		try
		{
			list = (ArrayList) IOE.readSerializedObject(filename);
			if(list == null) list = new ArrayList<Course>();
			/*for (int i = 0; i < list.size(); i++)
			{
				Course c = (Course) list.get(i);
				System.out.println(c);
			}*/
		}
		catch(Exception e){System.out.println( "Exception CourseManager() >> "+e.getMessage());}
		System.out.println("Load course data, done.\n");
	}

	/**
	 * re-initiates CourseManager to null
	 */
	public static CourseManager initiate()
	{
		if(theinstance == null)
			theinstance = new CourseManager();
		return theinstance;
	}

	/**
	 * this method adds a course by prompting the user for parameters to pass
	 * for the Couse constructor then passing it to the constructor
	 */
	public String addCourse()
	{	
		String courseCode;
		String courseName;
		String coordinator;
		int[] numOfGroup = new int[3];  // [lec, tut, lab]
		Map <String, Integer> tutGroups = null;  // <index, vacancy>
		Map <String, Integer> labGroups = null;  // <index, vacancy>
		int overallVacancy;
		int examWeightage;
		Map <String, Integer> courseworkComponent = new Hashtable<String, Integer>();  // <type, weightage>
		int n;
		String temp;
		
		System.out.print("Adding course. ");
		Scanner read = new Scanner(System.in);
		System.out.println("Enter course code: ");
		courseCode = read.next().toUpperCase(); read.nextLine();
		while(courseCode.length() != 6 || courseCode.charAt(0) != 'C' || courseCode.charAt(1) != 'E')
		{
			System.out.println("Invalid course code. Please enter course code with 6 characters starting with 'CE'.");
			System.out.println("Re-enter course code: ");
			courseCode = read.next().toUpperCase(); read.nextLine();
		}
		System.out.println("Enter course name: ");
		courseName = read.nextLine().toUpperCase();
		System.out.println("Select course coordinator: ");
		professormanager.printProfessors();
		coordinator = professormanager.getProfName(IOE.scint());

		String[] ar = {"lecture", "tutorial", "lab"};
		for(int i = 0; i < 3; i++)
		{
			System.out.println("Does it have "+ar[i]+"? (yes/no)");
			temp = read.next().toLowerCase();
			while(!temp.equals("yes") && !temp.equals("y") && !temp.equals("no") && !temp.equals("n"))
			{
				System.out.print("Try again: ");
				temp = read.next().toLowerCase();
			}
			if(temp.equals("yes") || temp.equals("y")) numOfGroup[i] = 1;
			else numOfGroup[i] = 0;
		}
		
		System.out.print("Enter the total vacancy for this course: ");
		overallVacancy = IOE.scint();

		int k, l;
		boolean okflag;

		if(numOfGroup[1] != 0)
		{
			okflag = false;
			while(!okflag)
			{
				tutGroups = new Hashtable<String, Integer>();  // <index, vacancy>
				k = 0;
				System.out.print("Enter the number of tutorial groups available: ");
				n = IOE.scint();
				numOfGroup[1] = n;
				for(int i = 0; i < n; i++)
				{
					System.out.print("Please enter tutorial group "+(i+1)+" index: ");
					temp = read.next(); read.nextLine();
					System.out.print("Please enter tutorial group "+(i+1)+" vacancy: ");
					l = IOE.scint();
					tutGroups.put(temp.toUpperCase(), l);
					k += l;
				}
				if(k < overallVacancy)
				{
					System.out.println("Total vacancy of lab group must be not less than overall vacancy!");
					System.out.println("Please re-enter.");
				}
				else okflag = true;
			}
		}

		if(numOfGroup[2] != 0)
		{
			okflag = false;
			while(!okflag)
			{
				labGroups = new Hashtable<String, Integer>();  // <index, vacancy>
				k = 0;
				System.out.print("Enter the number of lab groups available: ");
				n = IOE.scint();
				numOfGroup[2] = n;
				for(int i = 0; i < n; i++)
				{
					System.out.print("Please enter lab group "+(i+1)+" index: ");
					temp = read.next(); read.nextLine();
					System.out.print("Please enter lab group "+(i+1)+" vacancy: ");
					l = IOE.scint();
					labGroups.put(temp.toUpperCase(), l);
					k += l;
				}
				if(k < overallVacancy)
				{
					System.out.println("Total vacancy of lab group must not be less than overall vacancy!");
					System.out.println("Please re-enter.");
				}
				else okflag = true;
			}
		}

		int total, lol;
		do
		{
			total = 0;
			System.out.print("Enter the exam weightage: "); 
			examWeightage = IOE.scint();
			while (examWeightage >= 100)
			{
				System.out.println("Exam weightage has to be smaller than 100. Please try again.");
				System.out.print("Enter the exam weightage: "); 
				examWeightage = IOE.scint();
			}
			System.out.println("Enter the number of coursework components in this course: ");
			n = IOE.scint();
			while (n < 1)
			{
				System.out.println("There should be at least one coursework component. Please try again.");
				System.out.println("Enter the number of coursework components in this course: ");
				n = IOE.scint();
			}
			if (n == 1) {
				courseworkComponent.put("Coursework", 100);
				total = 100;
			}
			else
				for(int i = 0 ;i < n; i++)
				{
					System.out.print("Key in the name of component "+(i+1)+": ");
					temp = read.nextLine();
					System.out.print("Key in the weightage of component "+(i+1)+": ");
					lol = IOE.scint();
					courseworkComponent.put(temp, lol);
					total += lol;
				}
			if(total != 100) System.out.print("Total percentage of coursework weightage must be equal to 100!"); 
		}
		while(total != 100);

		Course course = new Course(courseCode, courseName, coordinator, numOfGroup,
		 tutGroups,labGroups, overallVacancy, examWeightage, courseworkComponent);
		courseCode = course.getCourseCode();
		try
		{
			for(Course temp1:list)
				if(courseCode.equals(temp1.getCourseCode()))
					{System.out.println("Course already exist!"); return "NA";}
			list.add(course);
			IOE.writeSerializedObject(filename, list);
			System.out.println("Course succesfully added!");
			return courseCode;
		}
		catch ( Exception e ){System.out.println( "Exception addCourse() >> " + e.getMessage());}
		return "NA";
	}

	/**
	 * this method prompt the user for a coursecode to delete, then deletes it
	 */
	public void deleteCourse()
	{
		System.out.print("Enter course code to delete: ");
		courseCode = scan.next().toUpperCase();
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode()))
			{
				list.remove(temp);
				IOE.writeSerializedObject(filename, list);
				return;
			}
		System.out.println("Course not found!");
	}

	/**
	 * this method prompts the user for a course code, 
	 * then displays the vacancy of that course
	 */
	public void checkVacancy()
	{
		System.out.print("Enter course code to check the vacancy: ");
		courseCode = scan.next().toUpperCase();
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode()))
				{System.out.println("Vacancy for "+courseCode+": "+temp.getVacancy()); return;}
		System.out.println("Course not found!");
	}
	
	/**
	 * gets the vacancy of a course given its coursecode
	 */
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
				try {
					for(Map.Entry<String, Integer> e : groups.entrySet()) {
					    if (e.getValue() != 0)			     
					    	keys.add(e.getKey());
					}
				}
				catch (Exception e) {}
			}
		return keys;
	}
	
	public ArrayList checkAvailableLabGroup(String courseCode) // for StudentCourse
	{
		ArrayList<String> keys = new ArrayList();
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())){
				Map<String, Integer> groups = temp.getLabGroup();
				try {
					for(Map.Entry<String, Integer> e : groups.entrySet()) {
					    if (e.getValue() != 0)			     
					    	keys.add(e.getKey());
					}
				}
				catch (Exception e) {}
			}
		return keys;
	}
	
	public int getNumOfComponent(String courseCode)
	{ // for StudentCourse
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())){
				return temp.getCourseworkComponent().size();
			}
		return -1;
	}

	public void showCoordinator()
	{
		System.out.print("Enter course code to show coordinator: ");
		courseCode = scan.next().toUpperCase();
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode()))
				{System.out.println("Coordinator for "+courseCode+": "+temp.getCoordinator()); return;}
		System.out.println("Course not found!");
	}

	public void printCourses()
	{
		System.out.println("\nAll courses available");
		System.out.println("---------------------");
		try
		{
			for(Course temp:list) {
				System.out.println(temp);
				professormanager.printDetails(temp.getCoordinator());
			}
			System.out.println();
		}
		catch ( Exception e ){System.out.println( "Exception printCourses() >> " + e.getMessage());}
	}
	
	public Set getTutGroup (String courseCode)
	{ // for StudentCourse
		for(Course temp: list) {
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getTutGroup().keySet();
		}
		return null;
	}

	public Set getLabGroup (String courseCode)
	{ // for StudentCourse
		for(Course temp: list) {
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getLabGroup().keySet();
		}
		return null;
	}
	
	public void updateVacancy(String courseCode, String tutGroup, String labGroup)
	{ // for StudentCourse // "NA" for non tut or lab groups
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
	
	public int getExamWeightage(String courseCode)
	{
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getExamWeightage();
		return -1;
	}

	public String getCourseName(String courseCode)
	{
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getCourseName();
		return "No such course.";
	}

	public Map<String, Integer> getCourseworkComponent(String courseCode)
	{
		for(Course temp: list)
			if(courseCode.equals(temp.getCourseCode())) 
				return temp.getCourseworkComponent();
		return null;
	}

}
