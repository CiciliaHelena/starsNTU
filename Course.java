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

	private int n;
	private String temp;


	public Course()
	{
		Scanner read = new Scanner(System.in);
		System.out.println("Enter course code : ");
		this.courseCode = read.next().toUpperCase(); read.nextLine();
		System.out.println("Enter course name : ");
		this.courseName = read.nextLine().toUpperCase();
		System.out.println("Enter course coordinator");
		this.coordinator = read.nextLine();

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
		boolean okflag;

		okflag = false;
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
					System.out.println("Total vacancy of tutorial group must not less than overall vacancy");
					System.out.println("Repeat the process");
				}
				else okflag = true;
			}
		}

		okflag = false;
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
	}


	public String getCourseCode() {
		return courseCode;
	}

	public int getVacancy() {
		return overallVacancy;
	}

	public String getCorrodinator() {
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

	@Override
	public String toString()
	{
		return courseCode+" : "+courseName;
	}
}
