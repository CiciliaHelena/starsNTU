// package starsNTU;

import java.io.Serializable;

public class StudentCourse implements Serializable {
	private String courseCode;
	private String tutGroup; // "NA" to indicate class type not conducted for the course
	private String labGroup; // "NA" to indicate class type not conducted for the course
	private String studentName;
	private int examResult; // -1 to indicate result not available yet
	private int[] courseworkResult; // int[0] = -1 to indicate result not available yet
	
	
	public StudentCourse(String sN, String cC, String tG, String lG, int size) {
		studentName = sN;
		courseCode = cC;
		tutGroup = tG;
		labGroup = lG;
		examResult = -1;
		courseworkResult = new int[size];
		courseworkResult[0] = -1;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	
	public String getStudentName() {
		return studentName;
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
			return (this.courseCode.equals(sc.getCourseCode()) && studentName.equals(sc.getStudentName()));
		}
		return false;
	}
}
