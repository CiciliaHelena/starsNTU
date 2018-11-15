// package starsNTU;

import java.io.Serializable;

public class StudentCourse implements Serializable {
	/**
	 * The coursecode of the course this student is registering for.
	 */
	private String courseCode;

	/**
	 * The tutorial group of this course this student is registering for.
	 * "NA" to indicate that tutorial is not conducted for the course.
	 */
	private String tutGroup;

	/**
	 * The lab group of this course this student is registering for.
	 * "NA" to indicate that lab is not conducted for the course.
	 */
	private String labGroup;
	
	/**
	 * This student's ID.
	 */
	private String studentID;

	/**
	 * The exam result of this student in this course.
	 * -1 to indicate the result has not been entered yet.
	 */
	private int examResult;

	/**
	 * The coursework results of this student in this course in order of the coursework components entered.
	 * First element -1 to indicate the result has not been entered yet.
	 */
	private int[] courseworkResult;
	
	/**
	 * Creates a new StudentCourse entry with the given student ID, course code, 
	 * tutorial group, lab group, and number of coursework components.
	 * @param sID This student's ID.
	 * @param cC The course code this student is registering for.
	 * @param tG The tutorial group this student is registering for ("NA" if course does not have tutorial).
	 * @param lG The lab group this student is registering for ("NA" if course does not have lab).
	 * @param size The number of coursework components. Constructor will create an array courseworkResult of given size.
	 * Constructor will initialize examResult as -1.
	 * Constructor will initialize first element in courseworkResult as -1.
	 */
	public StudentCourse(String sID, String cC, String tG, String lG, int size) {
		studentID = sID;
		courseCode = cC;
		tutGroup = tG;
		labGroup = lG;
		examResult = -1;
		courseworkResult = new int[size];
		courseworkResult[0] = -1;
	}
	
	/**
	 * Gets the course code of this StudentCourse record.
	 * @return This StudentCourse record's course code.
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 * Gets the student ID of this StudentCourse record.
	 * @return
	 */
	public String getStudentID() {
		return studentID;
	} 
	
	public String getTutGroup() {
		return tutGroup;
	}
	
	public String getLabGroup() {
		return labGroup;
	}

	public int[] getCourseworkResult() {
		return courseworkResult;
	}
	
	public int getExamResult() {
		return examResult;
	}
	
	public void setExamResult(int mark) {
		examResult = mark;
	}
	
	public void setCourseworkResult(int[] marks) {
		courseworkResult = marks;
	}
	
	public boolean equals(Object o) {
		if (o instanceof StudentCourse) {
			StudentCourse sc = (StudentCourse)o;
			return (this.courseCode.equals(sc.getCourseCode()) && studentID.equals(sc.getStudentID()));
		}
		return false;
	}
}
