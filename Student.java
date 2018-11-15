// package starsNTU;

import java.io.Serializable;
import java.util.Scanner;

public class Student implements Serializable
{
	/**
	 * a string of identification number
	 * various Student details can be extracted from the identification number
	 * by partitioning it
	 * is not case sensitive
	 */
	private String studentID;	

	/**
	 * case sensitive full name spaced with a single spacebar
	 */
	private String name;		
	
	/**
	 * no criteria for password
	 * case sensitive
	 */
	private String password;
	
	/**
	 * GPA of student,
	 * init to 0 until updated by getCGPA()
	 */
	private double CGPA = 0;

	/**
	 * construcs a Student object with details according to the parameters received
	 */
	public Student(String sID, String n, String p)

	{
		this.studentID = sID;
		this.name = n;
		this.password = p;
	}

	/**
	 * getter of studentID
	 */
	public String getID()
	{
		return this.studentID;
	}

	/**
	 * getter of studentName
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * returns true if the caller and the parameter is equal (has same password)
	 * else false
	 */
	public boolean checkPassword(String password)
	{
		if(this.password.equals(password)) return true;
		return false;
	}

	/**
	 * set a new password for the caller
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * get the CGPA of the caller
	 */
	public double getCGPA()
	{
		return this.CGPA;
	}

	/**
	 * set the CGPA of the caller
	 */
	public void setCGPA(Double cgpa)
	{
		this.CGPA = cgpa;
	}

	/**
	 * returns student details in a format name+" : "+studentID;
	 */
	@Override
	public String toString()
	{
		return name+" : "+studentID;
	}

}
