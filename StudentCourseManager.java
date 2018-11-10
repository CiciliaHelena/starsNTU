// package starsNTU;

import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

// notes for myself: 
// - add code in Course and Student class to add entries to both TreeMaps when addCourse or addStudent


public class StudentCourseManager {
	private ArrayList<StudentCourse> list = new ArrayList<StudentCourse>();
	private TreeMap<String, LinkedList<Integer>> byCourse = new TreeMap<String, LinkedList<Integer>>();
	// <courseID, [index of records of students taking the course]>
	private TreeMap<String, LinkedList<Integer>> byStudent = new TreeMap<String, LinkedList<Integer>>();
	// <studentID, [index of records of courses taken by student]>
	private String filename = "StudentCourse.dat";
	private Scanner read = new Scanner(System.in);
	private static StudentCourseManager theinstance = null;

	private static CourseManager coursemanager = CourseManager.initiate();
	private static StudentManager studentmanager = StudentManager.initiate();


	private StudentCourseManager()
	{ 
			try	{
				// read from serialized file the list of student records
				// System.out.println("Initializing StudentCourseManager - - -");
				list = (ArrayList) IOE.readSerializedObject(filename);
				// System.out.println("StudentCourse ArrayList created - - -");
				System.out.println(list);
				if(list == null) list = new ArrayList<StudentCourse>();
				
				// from list create two TreeMap: one with courseID as key and another with studentID as key
				// add all courses to byCourse
				for (Course entry: coursemanager.list) {
					byCourse.put(entry.getCourseCode(), new LinkedList<Integer>());
				}
				
				// add all students to byStudent
				for (Student entry: studentmanager.list) {
					byStudent.put(entry.getName(), new LinkedList<Integer>());
				}
				
				// update indices of records
				for (int i = 0; i < list.size(); i++) {
					byCourse.get(list.get(i).getCourseCode()).add(i);
					byStudent.get(list.get(i).getStudentName()).add(i);
				}

		}  catch ( Exception e ) {
		 	System.out.println( "Exception >> " + e.getMessage() );
		}
	}

	public static StudentCourseManager initiate()
	{
		if(theinstance == null)
			theinstance = new StudentCourseManager();
		return theinstance;
	}

	// DONE
	public void registerCourse() {
		System.out.println("Enter student name: ");
		String student = read.nextLine();
		coursemanager.printCourses();
		System.out.println("Enter course code: ");
		String course = read.nextLine();
		
		// check if student and course exists
		if (byCourse.containsKey(course) && byStudent.containsKey(student)) { 
			
			// check if student is already registered for the course
			LinkedList indexList = byCourse.get(course);
			for (int i = 0; i < indexList.size(); i++) {
				// if student is found in the course, cancel registration
				if (list.get((int) indexList.get(i)).getStudentName().equals(student)) { ;
					System.out.println("Student is already registered for this course.");
					return;
				}			
			}
			
			if (coursemanager.getVacancy(course) <= 0) {
				System.out.println("Course has no more vacancy.");
				return;
			}
			
			// student not registered yet, proceed with registration ---
			int choice;
			String tutg = "NA";
			String labg = "NA";
			
			// check for available tutorial groups
			ArrayList<String> tutGroup = coursemanager.checkAvailableTutGroup(course);
			if (!tutGroup.isEmpty()) {
				System.out.println("Select tutorial group");
				for(int i = 0; i < tutGroup.size(); i++) {
		            System.out.println((i+1) + ": " + tutGroup.get(i));
		        }
				choice = IOE.scint();
				tutg = tutGroup.get(choice-1);
			}
			
			// check for available lab groups
			ArrayList<String> labGroup = coursemanager.checkAvailableLabGroup(course);
			if (!labGroup.isEmpty()) {				
				System.out.println("Select lab group");
				for(int i = 0; i < labGroup.size(); i++) {
		            System.out.println((i+1) + ": " + labGroup.get(i));
		        }
				choice = IOE.scint();
				labg = labGroup.get(choice-1);
			}
			
			int n = coursemanager.getNumOfComponent(course);
			
			StudentCourse newSC = new StudentCourse(student, course, tutg, labg, n);
			// update master record
			list.add(newSC); 
			// update both TreeMaps
			byCourse.get(course).add(list.size()-1);
			byStudent.get(student).add(list.size()-1);
			// update dat file
			IOE.writeSerializedObject(filename, list);
			
			// update course vacancy
			coursemanager.updateVacancy(course, tutg, labg);
			
			System.out.println("Student successfully registered!");	
		}
		
		// student or course doesn't exist, cancel registration
		else { 
			System.out.println("Invalid student or course.");
		}
	}
	
	// DONE
	public void printStudentList() {
		System.out.println("Enter course code: ");
		String course = read.nextLine();
		
		if (byCourse.containsKey(course)) {
			String group = "NA";
			LinkedList indexList = byCourse.get(course);
			int size = indexList.size();
			
			System.out.println("Select type of list");
			System.out.println("1: All students in the course");
			if (!coursemanager.getTutGroup(course).isEmpty())
				System.out.println("2: By tutorial group");
			if (!coursemanager.getLabGroup(course).isEmpty())
				System.out.println("3: By lab group");
			
			int choice = IOE.scint();
			String selected;
			switch(choice) {
			// print all
			case 1:
				for (int i = 0; i < size; i++) {
					System.out.println(list.get((int) indexList.get(i)).getStudentName());
				}
				return;
			
			// by tutorial group
			case 2:
				// get a list of tutorial groups
				if (!coursemanager.getTutGroup(course).isEmpty()) {
					Set<String> tutGroup = coursemanager.getTutGroup(course);
					System.out.println("Select tutorial group");
					for (String key : tutGroup) {
						System.out.println(key);
					}
					selected = read.nextLine();
				}
				else {
					System.out.println("Invalid selection.");
					return;
				}
				// print out names
				for (int i = 0; i < size; i++) {
					if (list.get((int) indexList.get(i)).getTutGroup().equals(selected)) {
						System.out.println(list.get((int) indexList.get(i)).getStudentName());
					}
				}
				return;
				
			// by lab group
			case 3:
				// get a list of lab groups
				if (!coursemanager.getLabGroup(course).isEmpty()) {
					Set<String> tutGroup = coursemanager.getLabGroup(course);
					System.out.println("Select lab group");
					for (String key : tutGroup) {
						System.out.println(key);
					}
					selected = read.nextLine();
				}
				else {
					System.out.println("Invalid selection.");
					return;
				}
				// print out names
				for (int i = 0; i < size; i++) {
					if (list.get((int) indexList.get(i)).getLabGroup().equals(selected)) {
						System.out.println(list.get((int) indexList.get(i)).getStudentName());
					}
				}
				return;
				
			default: break;
			}
		}
		else {
			System.out.println("Invalid course ID.");
			return;
		}
	}
	
	// Asks user for student name and course, then lets user input the exam mark
	public void inputExamMark () {
		System.out.println("Enter student name: ");
		String name = read.nextLine();
		
		if (byStudent.containsKey(name)) {
			LinkedList indexList = byStudent.get(name);
			System.out.println("Enter course code: ");
			name = read.nextLine();
			for (int i = 0; i < indexList.size(); i++) {
				if (list.get((int) indexList.get(i)).getCourseCode().equals(name)) {
					System.out.println("Enter exam mark: ");
					int mark = IOE.scint();
					//int w = coursemanager.getExamWeightage(name);
					list.get((int) indexList.get(i)).setExamResult(mark);
					IOE.writeSerializedObject(filename, list);
					System.out.println("Results saved.");
					//System.out.println("test " + list.get((int) indexList.get(i)).getExamResult());
					//System.out.println(list);
					return;
				}
			}	
			// if course not found
			System.out.println("Invalid course");
		}
		else 
			// if student doesn't exist
			System.out.println("Invalid student.");
	}
	
	// Asks user for student name and course, then lets user input marks for each coursework component
	public void inputCourseworkMark () {
		System.out.println("Enter student name: ");
		String student = read.nextLine();
		
		if (byStudent.containsKey(student)) {
			LinkedList indexList = byStudent.get(student);
			System.out.println("Enter course code: ");
			String course = read.nextLine();
			
			for (int i = 0; i < indexList.size(); i++) {
				// find course
				if (list.get((int) indexList.get(i)).getCourseCode().equals(course)) {
					// check if coursework component has been entered
					if (coursemanager.getCourseworkComponent(course) != null) {
						Map<String, Integer> componentWeightage = coursemanager.getCourseworkComponent(course);
						//int w = coursemanager.getExamWeightage(course);
						int[] marks = new int[componentWeightage.size()];
						int j = 0;
						int mark;
						
						// iterate through every component and ask for marks
						for (Map.Entry<String, Integer> entry : componentWeightage.entrySet()) {
						    System.out.println("Enter mark for " + entry.getKey() + ":");
						    mark = IOE.scint();
						    marks[j++] = mark;
						    //marks[j++] = ((mark * (int) entry.getValue() * (100-w))/10000); 
						    //System.out.println("- - -Saved mark = " + marks[j-1]);
						    //System.out.println("- - -Weightage = " + entry.getValue());
						}
						list.get((int) indexList.get(i)).setCourseworkResult(marks);
						IOE.writeSerializedObject(filename, list);
						System.out.println("Results saved.");
						//System.out.println("test " + list.get((int) indexList.get(i)).getCourseworkResult()[0]);
						//System.out.println(list);
						return;
					}
					System.out.println("No information about coursework component");
					return;
				}
			}
			System.out.println("Invalid course");
			return;
		}
		else {
			System.out.println("Invalid student or course");
			return;
		}
	}
	
	// DONE
	public void printCourseStatistics() {
		System.out.println("Enter course code: ");
		String course = read.nextLine();
		int[] count = {0,0,0,0,0}; //[A,B,C,D,F]
		int mark, grade;
		
		if (byCourse.containsKey(course)) {
			LinkedList indexList = byCourse.get(course);
			System.out.println("Select type of statistics");
			System.out.println("1: Overall");
			System.out.println("2: Exams only");
			System.out.println("3: Coursework only");
			int choice = IOE.scint();
			
			switch(choice) {
			case 1: 
				for (int i = 0; i < indexList.size(); i++) {
					mark = calTotalMarks(list.get((int) indexList.get(i)));
					if (mark == -1) return;
					grade = convertToGrade(mark);
					System.out.println("grade-- " + grade);
					if (grade > 0) {
						System.out.println("Grade count before adding-- " + count);
						count[grade-1]++;
						System.out.println("Grade count after adding-- " + count);
					}
				}
				break;
				
			case 2: 
				for (int i = 0; i < indexList.size(); i++) {
					mark = list.get((int) indexList.get(i)).getExamResult();
					if (mark == -1) {
						System.out.println("Exam marks for student not entered yet.");
						return;
					}
					grade = convertToGrade(mark);
					if (grade > 0) {
						System.out.println("Grade count before adding-- " + count);
						count[grade-1]++;
						System.out.println("Grade count after adding-- " + count);
					}
				}
				break;
				
			case 3: 
				int sum = 0;
				for (int i = 0; i < indexList.size(); i++) {
					int[] marks = list.get((int) indexList.get(i)).getCourseworkResult();
					if (marks.length == 0) {
						System.out.println("Coursework marks for student not entered yet.");
						return;
					}
					for (int j = 0; j < marks.length; j++) {
						sum = sum + marks[j];
					}
					grade = convertToGrade(sum);
					if (grade > 0) {
						count[grade-1]++;
					}
				}
				break;
			}
			
			int numOfGrades = count[0] + count[1] + count[2] + count[3] + count[4];
			System.out.println("Statistics for " + course);
			System.out.println("A: " + (count[0]/numOfGrades)*100 + "%");
			System.out.println("B: " + (count[1]/numOfGrades)*100 + "%");
			System.out.println("C: " + (count[2]/numOfGrades)*100 + "%");
			System.out.println("D: " + (count[3]/numOfGrades)*100 + "%");
			System.out.println("F: " + (count[4]/numOfGrades)*100 + "%");
		}
		
		else {
			System.out.println("Invalid course.");
			return;
		}
		
		
	}
	
	// DONE
	public void printStudentTranscript() {
		System.out.println("Enter student name: ");
		String student = read.nextLine();
		int mark;
		String course;
		String[] grade = {"A", "B", "C", "D", "F"};
		StudentCourse sC;
		
		if (byStudent.containsKey(student)) {
			LinkedList indexList = byStudent.get(student);
			System.out.println("----------------------------------------------");
			System.out.println("Student Name: " + student);
			System.out.println("CGPA: " + calCGPA(student));
			
			// find all the courses the student registered for
			for (int i = 0; i < indexList.size(); i++) {
				sC = list.get((int) indexList.get(i));
				System.out.println();
				course = sC.getCourseCode();
				System.out.println("|" + course + ": " + coursemanager.getCourseName(course) + "|");
				mark = calTotalMarks(sC);
				int w = coursemanager.getExamWeightage(course);
				
				if (mark >= 0) {
					System.out.println("Overall Mark: " + mark);
					System.out.println("Grade: " + grade[convertToGrade(mark)-1]);	
					System.out.println();
					System.out.format("%-15s %-10s %-15s%n", "Component", "Mark", "Weightage");
					System.out.format("%-15s %-10s %-15s%n", "Exam", sC.getExamResult(), w);
					
					// for courseworks with sub-components
					if (sC.getCourseworkResult().length > 1) {
						Map<String, Integer> cwW = coursemanager.getCourseworkComponent(sC.getCourseCode());
						int[] cwR = sC.getCourseworkResult();
						int j = 0;
						System.out.println("Coursework");
						for (Map.Entry<String, Integer> entry : cwW.entrySet()) {
							System.out.print("   ");
							System.out.format("%-12s %-10s %-15s%n", entry.getKey(), cwR[j++], entry.getValue()*(100-w)/100);
						}	
					}
					// for courseworks as the main component only
					else {
						System.out.format("%-15s %-10s %-15s%n", "Coursework", sC.getCourseworkResult()[0], (100-w));
					}
				}
				else {
					System.out.println("Results for this course is not available yet");
				}
				System.out.println();
			}
			System.out.println("----------------------------------------------");
		}
		// student not found
		else System.out.println("Invalid student.");
	}
	
	public double calCGPA(String student) {
		double totalGPA = 0;
		int mark;
		int n = 0;
		if (byStudent.containsKey(student)) {
			LinkedList indexList = byStudent.get(student);
			for (int i = 0; i < indexList.size(); i++) {
				mark = calTotalMarks(list.get((int) indexList.get(i)));
				if (mark >= 0) {
					totalGPA = totalGPA + convertToGPA(mark);
					n++;
				}
			}
			return (totalGPA/n);
		}
		return -1;
	}
	
	private int convertToGrade(int mark) {
		//System.out.println("Mark to convert-- " + mark);
		if (mark >= 0 && mark <= 100) { // check if mark is in range
			if (mark >= 80) return 1;
			else if (mark >= 70) return 2;
			else if (mark >= 60) return 3;
			else if (mark >= 50) return 4;
			else return 5;
		}
		return -1; // invalid mark
	}
	
	private double convertToGPA(int mark) {
		if (mark >= 0 && mark <= 100) { // check if mark is in range
			if (mark >= 80) return 5.00;
			else if (mark >= 70) return 4.00;
			else if (mark >= 60) return 3.00;
			else if (mark >= 50) return 2.00;
			else return 0;
		}
		return -1; // invalid mark
	}
	
	// calculates the total marks of a student for a particular course by taking individual component with its weightage
	// return -1 if the marks have not been entered yet
	private int calTotalMarks(StudentCourse sC) {
		//System.out.println("Calculating total marks- - -");
		// check if marks have been entered
		if (sC.getExamResult() != -1 && sC.getCourseworkResult()[0] != -1) {
			int w = coursemanager.getExamWeightage(sC.getCourseCode());
			int totalMarks = (sC.getExamResult() * w)/ 100; // adjust exam mark according to weightage
			int[] cwR = sC.getCourseworkResult();
			int i = 0;
			Map<String, Integer> cwW = coursemanager.getCourseworkComponent(sC.getCourseCode());
			
			for (Map.Entry<String, Integer> entry : cwW.entrySet()) {
				// adjust component marks according to each weightage
				totalMarks = totalMarks + (cwR[i++] * (100-w) * entry.getValue())/10000;
			}
			return totalMarks;
		}
		else { // if not then grade is not calculated 
			//System.out.println("Not enough information to calculate grade.");
			return -1;
		}
	}
	
	public void updateStudentTM(String student) {
		System.out.println("Updating byStudent TreeMap");
		byStudent.put(student, new LinkedList<Integer>());
		System.out.println("byStudent- " + byStudent);
	}
	
	public void updateCourseTM(String course) {
		System.out.println("Updating byCourse TreeMap");
		byCourse.put(course, new LinkedList<Integer>());
		System.out.println("byCourse- " + byCourse);
	}
	
}