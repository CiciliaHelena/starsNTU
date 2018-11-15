// package starsNTU;

import java.io.Serializable;
import java.util.*;

public class Course implements Serializable
{
	/**
	 * courseCode is a string with 2 characters of abbreviation of courseName in front, 
	 * followed by 4 numbers indicating their level and ID
	 */
	private String courseCode;
	
	/**
	 * Name of the course
	 */
	private String courseName;
	
	/**
	 * Coordinator of the course
	 */
	private String coordinator;
	
	/**
	 * indicating whether the course has a lecture
	 * has a tutorial
	 * has a lab
	 */
	private int[] numOfGroup = new int[3];  // [lec, tut, lab]
	
	private Map <String, Integer> tutGroups = new Hashtable<String, Integer>();  // <index, vacancy>
	private Map <String, Integer> labGroups = new Hashtable<String, Integer>();  // <index, vacancy>
	private Map <String, Integer> courseworkComponent = new Hashtable<String, Integer>();  // <type, weightage>
	
	/**
	 * number of students that could still join the course
	 */
	private int overallVacancy;
<<<<<<< HEAD
	
	/**
	 * number of components of grade
	 */
=======
	private int initialVacancy;
>>>>>>> d0a4c8aa5ca4574ffb905c90e59ba711e4ae1e4b
	private int examWeightage;
	
	


<<<<<<< HEAD

	/**
	 * Constructor of Course, will contruct based on the parameters passed
	 */
=======
>>>>>>> d0a4c8aa5ca4574ffb905c90e59ba711e4ae1e4b
	public Course(String courseCode,String courseName,String coordinator,int[] numOfGroup,Map <String, Integer> tutGroups,Map <String, Integer> labGroups,int overallVacancy,int examWeightage, Map <String, Integer> courseworkComponent)
	{
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.coordinator = coordinator;
		this.numOfGroup = numOfGroup;
		this.tutGroups = tutGroups;
		this.labGroups = labGroups;
		this.overallVacancy = overallVacancy;
		this.initialVacancy = overallVacancy;
		this.examWeightage = examWeightage;
		this.courseworkComponent = courseworkComponent;
	}

	/**
	 * getter of Course.courseCode()
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 * getter of Course.getCourseName()
	 */
	public String getCourseName() {
		return courseName;
	}

<<<<<<< HEAD
	/**
	 * getter of Course.vacancy()
	 */
	public int getVacancy() {
=======
	public int getOverallVacancy() {
>>>>>>> d0a4c8aa5ca4574ffb905c90e59ba711e4ae1e4b
		return overallVacancy;
	}
	
	public int getInitialVacancy() {
		return initialVacancy;
	}

	/**
	 * getter of Course.coordinator()
	 */
	public String getCoordinator() {
		return coordinator;
	}

	/**
	 * getter of Course.tutGroups()
	 */
	public Map getTutGroup() {
		return tutGroups;
	}

	/**
	 * getter of Course.labGroups()
	 */
	public Map getLabGroup() {
		return labGroups;
	}

	/**
	 * getter of Course.numOfGroup()
	 */
	public int[] getNumOfGroup() {
		return numOfGroup;
	}

	/**
	 * getter of Course.courseworkComponent()
	 */
	public Map<String, Integer> getCourseworkComponent() {
		return courseworkComponent;
	}

	/**
	 * getter of Course.examWeightage()
	 */
	public int getExamWeightage() {
		return examWeightage;
	}
	
	/**
	 * sets vacancy to a new value
	 */
	public void setVacancy(int v) {
		overallVacancy = v;
	}
	
	/**
	 * sets tutorial vacancy of a specific tutorial group to a new value
	 */
	public void setTutVacancy(String t, int v) {
		tutGroups.put(t, v);
	}
	
	/**
	 * sets lab vacancy of a specific lab group to a new value
	 */
	public void setLabVacancy(String l, int v) {
		labGroups.put(l, v);
	}

	/**
	 * overrides the usual toString method for Course class
	 * rturns a partitioned data for better parameter passing
	 */
	@Override
	public String toString(){
		return courseCode+" : "+courseName;
	}
}