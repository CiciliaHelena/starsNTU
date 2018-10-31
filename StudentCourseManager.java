// package starsNTU;

import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

// notes for myself: 
// - add code in Course and Student class to add entries to both TreeMaps when addCourse or addStudent


public class StudentCourseManager {
	private static ArrayList<StudentCourse> list = new ArrayList<StudentCourse>();
	private static TreeMap<String, LinkedList<Integer>> byCourse = new TreeMap<String, LinkedList<Integer>>();
	// <courseID, [index of records of students taking the course]>
	private static TreeMap<String, LinkedList<Integer>> byStudent = new TreeMap<String, LinkedList<Integer>>();
	// <studentID, [index of records of courses taken by student]>
	private static String filename = "StudentCourse.dat";
	private static Scanner read = new Scanner(System.in);


	static { 
		// try	{
				// read from serialized file the list of student records
		System.out.println("1");

				list = (ArrayList) IOE.readSerializedObject(filename);
		System.out.println("2");
				if(list == null) list = new ArrayList<StudentCourse>();
				
				// from list create two TreeMap: one with courseID as key and another with studentID as key
				for (int i = 0; i < list.size(); i++) {
					String course = list.get(i).getCourseCode();
					String student = list.get(i).getStudentName();
					
					// byCourse
					if (byCourse.containsKey(course)) {
						byCourse.get(course).add(i);
					}
					else {
						byCourse.put(course, new LinkedList<Integer>());
					}
					
					// byStudent
					if (byStudent.containsKey(student)) {
						byStudent.get(student).add(i);
					}
					else {
						byStudent.put(student, new LinkedList<Integer>());
					}
				}

		// }  catch ( Exception e ) {
		// 	System.out.println( "Exception >> " + e.getMessage() );
		// }
	}
	
	// DONE
	public static void registerCourse() {
		System.out.println("Enter student name: ");
		String student = read.nextLine();
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
			
			// student not registered yet, proceed with registration ---
			int choice;
			String tutg = "NA";
			String labg = "NA";
			
			// check for available tutorial groups
			if (CourseManager.checkAvailableTutGroup(course) != null) {
				ArrayList<String> tutGroup = CourseManager.checkAvailableTutGroup(course);
				System.out.println("Select tutorial group");
				for(int i = 0; i < tutGroup.size(); i++) {
		            System.out.println((i+1) + ": " + tutGroup.get(i));
		        }
				choice = IOE.scint();
				tutg = tutGroup.get(choice-1);
			}
			
			// check for available lab groups
			if (CourseManager.checkAvailableLabGroup(course) != null) {
				ArrayList<String> labGroup = CourseManager.checkAvailableLabGroup(course);
				System.out.println("Select lab group");
				for(int i = 0; i < labGroup.size(); i++) {
		            System.out.println((i+1) + ": " + labGroup.get(i));
		        }
				choice = IOE.scint();
				labg = labGroup.get(choice-1);
			}
			
			int n = CourseManager.getNumOfComponent(course);
			
			StudentCourse newSC = new StudentCourse(student, course, tutg, labg, n);
			// update master record
			list.add(newSC); 
			// update both TreeMaps
			byCourse.get(course).add(list.size()-1);
			byStudent.get(student).add(list.size()-1);
			// update dat file
			IOE.writeSerializedObject(filename, list);
			System.out.println("Student successfully registered!");	
		}
		
		// student or course doesn't exist, cancel registration
		else { 
			System.out.println("Invalid student or course.");
		}
	}
	
	// DONE
	public static void printStudentList() {
		System.out.println("Enter course code: ");
		String course = read.nextLine();
		
		if (byCourse.containsKey(course)) {
			String group = "NA";
			LinkedList indexList = byCourse.get(course);
			int size = indexList.size();
			
			System.out.println("Select type of list");
			System.out.println("1: All students in the course");
			if (CourseManager.checkAvailableTutGroup(course) != null)
				System.out.println("2: By tutorial group");
			if (CourseManager.checkAvailableLabGroup(course) != null)
				System.out.println("3: By lab group");
			
			int choice = IOE.scint();
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
				if (CourseManager.checkAvailableTutGroup(course) != null) {
					ArrayList<String> tutGroup = CourseManager.checkAvailableTutGroup(course);
					System.out.println("Select tutorial group");
					for(int i = 0; i < tutGroup.size(); i++) {
			            System.out.println((i+1) + ": " + tutGroup.get(i));
			        }
					choice = IOE.scint();
					group = tutGroup.get(choice-1);
				}
				else {
					System.out.println("Invalid selection.");
					return;
				}
				// print out names
				for (int i = 0; i < size; i++) {
					if (list.get((int) indexList.get(i)).getTutGroup().equals(group)) {
						System.out.println(list.get((int) indexList.get(i)).getStudentName());
					}
				}
				return;
				
			// by lab group
			case 3:
				// get a list of lab groups
				if (CourseManager.checkAvailableLabGroup(course) != null) {
					ArrayList<String> labGroup = CourseManager.checkAvailableLabGroup(course);
					System.out.println("Select lab group");
					for(int i = 0; i < labGroup.size(); i++) {
			            System.out.println((i+1) + ": " + labGroup.get(i));
			        }
					choice = IOE.scint();
					group = labGroup.get(choice-1);
				}
				else {
					System.out.println("Invalid selection.");
					return;
				}
				// print out names
				for (int i = 0; i < size; i++) {
					if (list.get((int) indexList.get(i)).getLabGroup().equals(group)) {
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
	
	// DONE
	public static void inputExamMark () {
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
					int w = CourseManager.getExamWeightage(name);
					list.get((int) indexList.get(i)).setExamResult(mark * w/100);
					System.out.println("Results saved.");
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
	
	// DONE
	public static void inputCourseworkMark () {
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
					if (CourseManager.getCourseworkComponent(course) != null) {
						Map<String, Integer> componentWeightage = CourseManager.getCourseworkComponent(course);
						int[] marks = new int[componentWeightage.size()];
						int j = 0;
						int mark;
						// iterate through every component and ask for marks
						for (Map.Entry<String, Integer> entry : componentWeightage.entrySet()) {
						    System.out.println("Enter mark for " + entry.getKey() + ":");
						    mark = IOE.scint();
						    marks[j++] = mark * entry.getValue()/100; // multiply by respective component weightage
						}
						list.get((int) indexList.get(i)).setCourseworkResult(marks);
						System.out.println("Results saved.");
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
	public static void printCourseStatistics() {
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
					mark = list.get((int) indexList.get(i)).calTotalMarks();
					grade = convertToGrade(mark);
					if (grade > 0) {
						count[grade-1]++;
					}
				}
				break;
				
			case 2: 
				for (int i = 0; i < indexList.size(); i++) {
					mark = list.get((int) indexList.get(i)).getExamResult();
					grade = convertToGrade(mark);
					if (grade > 0) {
						count[grade-1]++;
					}
				}
				break;
				
			case 3: 
				int sum = 0;
				for (int i = 0; i < indexList.size(); i++) {
					int[] marks = list.get((int) indexList.get(i)).getCourseworkResult();
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
	public static void printStudentTranscript() {
		System.out.println("Enter student name: ");
		String student = read.nextLine();
		int mark;
		String course;
		String[] grade = {"A", "B", "C", "D", "F"};
		
		if (byStudent.containsKey(student)) {
			LinkedList indexList = byStudent.get(student);
			System.out.println("------------------------------------");
			System.out.println("Student Name: " + student);
			for (int i = 0; i < indexList.size(); i++) {
				course = list.get((int) indexList.get(i)).getCourseCode();
				mark = list.get((int) indexList.get(i)).calTotalMarks();
				System.out.println(course + ": " + grade[convertToGrade(mark)-1]);	
			}
			System.out.println();
			System.out.println("CGPA: " + calCGPA(student));
			System.out.println("------------------------------------");
		}
	}
	
	public static double calCGPA(String student) {
		double totalGPA = 0;
		int mark;
		if (byStudent.containsKey(student)) {
			LinkedList indexList = byStudent.get(student);
			for (int i = 0; i < indexList.size(); i++) {
				mark = list.get((int) indexList.get(i)).calTotalMarks();
				totalGPA = totalGPA + convertToGPA(mark);
			}
			return (totalGPA/indexList.size());
		}
		return -1;
	}
	
	private static int convertToGrade(int mark) {
		if (mark >= 0 && mark <= 100) { // check if mark is in range
			if (mark >= 80) return 1;
			else if (mark >= 70) return 2;
			else if (mark >= 60) return 3;
			else if (mark >= 50) return 4;
			else return 5;
		}
		return -1; // invalid mark
	}
	
	private static double convertToGPA(int mark) {
		if (mark >= 0 && mark <= 100) { // check if mark is in range
			if (mark >= 80) return 5.00;
			else if (mark >= 70) return 4.00;
			else if (mark >= 60) return 3.00;
			else if (mark >= 50) return 2.00;
			else return 0;
		}
		return -1; // invalid mark
	}
	
	public static void updateStudentTM(String student) {
		System.out.println("lalala");
		byStudent.put(student, new LinkedList<Integer>());
		System.out.println("la");
	}
	
	public static void updateCoursetTM(String course) {
		byStudent.put(course, new LinkedList<Integer>());
	}
	
}