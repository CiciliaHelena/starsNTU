// package starsNTU;

import java.io.Serializable;
import java.util.*;

public class Course implements Serializable
{
	private String courseCode;
	private String courseName;
	private String coordinator;
	private int[] numOfGroup = new int[3];  // [lec, tut, lab]
	private Map <String, Integer> tutGroups = new Hashtable<String, Integer>();  // <index, vacancy>
	private Map <String, Integer> labGroups = new Hashtable<String, Integer>();  // <index, vacancy>
	private int overallVacancy;
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
		this.examWeightage = examWeightage;
		this.courseworkComponent = courseworkComponent;
	}


	public String getCourseCode() {
		return courseCode;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public int getVacancy() {
		return overallVacancy;
	}

	public String getCoordinator() {
		return coordinator;
	}

	public Map getTutGroup() {
		return tutGroups;
	}

	public Map getLabGroup() {
		return labGroups;
	}

	public int[] getNumOfGroup() {
		return numOfGroup;
	}

	public Map<String, Integer> getCourseworkComponent() {
		return courseworkComponent;
	}

	public int getExamWeightage() {
		return examWeightage;
	}
	
	public void setVacancy(int v) {
		overallVacancy = v;
	}
	
	public void setTutVacancy(String t, int v) {
		tutGroups.put(t, v);
	}
	
	public void setLabVacancy(String l, int v) {
		labGroups.put(l, v);
	}

	@Override
	public String toString()
	{
		return courseCode+" : "+courseName;
	}
}