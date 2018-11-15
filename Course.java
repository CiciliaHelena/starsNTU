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
	 * name of the course to easily identify the course
	 */
	private String courseName;

	/**
	 * course coordinator name must be a name of a Professor object
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
	
	/**
	 * number of students that could still join the course
	 */
	private int overallVacancy;
	private int initialVacancy;
	private int examWeightage;
	private Map <String, Integer> courseworkComponent = new Hashtable<String, Integer>();  // <type, weightage>


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
	 * get the course code of this course
	 * @return this course's code in string type
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 *get the course name of this course
	 *@return this course's name in string type
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * get the overall available vacancy of this course
	 * @return this course's overall vacancy in integer type
	 */
	public int getOverallVacancy() {
		return overallVacancy;
	}
	
	/**
	 * get the initial vacancy of this course
	 * @return this course's initial vacancy in integer type
	 */
	public int getInitialVacancy() {
		return initialVacancy;
	}

	/**
	 * get the coordinator of this course
	 * @return this course's coordinator name in string type
	 */
	public String getCoordinator() {
		return coordinator;
	}

	/**
	 * get all the tutorial group available of this course
	 * @return this course's list of tutorial group in map data type
	 */
	public Map getTutGroup() {
		return tutGroups;
	}

	/**
	 * get all the lab group available of this course
	 * @return this course's list of lab group in map data type
	 */
	public Map getLabGroup() {
		return labGroups;
	}

	/**
	 * get all the number of both tutorial and lab group available of this course
	 * @return this course's number of group for both lab and tutorial in array of integerdata type
	 */
	public int[] getNumOfGroup() {
		return numOfGroup;
	}

	/**
	 * get the coursework component of this course
	 * @return this course's coursework component int map data type
	 */
	public Map<String, Integer> getCourseworkComponent() {
		return courseworkComponent;
	}

	/**
	 * get the exam weightage of this course
	 * @return this course's exam weitage in integer type
	 */
	public int getExamWeightage() {
		return examWeightage;
	}
	
	/**
	 * set the initial overall vacancy for this course
	 * @param v this course's vacancy in integer type
	 */
	public void setVacancy(int v) {
		overallVacancy = v;
	}
	
	/**
	 * set the tutorial's vacancy of this course
	 * @param t the name of the tutorial group
	 * @param v the vacancy of the tutorial group
	 */
	public void setTutVacancy(String t, int v) {
		tutGroups.put(t, v);
	}
	
	/**
	 * set the lab vacancy of this course
	 * @param l the name of the lab group
	 * @param v the vacacy of the lab group
	 */
	public void setLabVacancy(String l, int v) {
		labGroups.put(l, v);
	}

	/**
	 * @return the course instance in the format of " courseCode : courseName;"
	 */
	@Override
	public String toString(){
		return courseCode+" : "+courseName;
	}
}