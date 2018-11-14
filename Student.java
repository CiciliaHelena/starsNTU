// package starsNTU;

import java.io.Serializable;
import java.util.Scanner;

public class Student implements Serializable
{
	private String studentID;	//matric saved in capital should not case sensitive
	private String name;		//case sensitive
	private String password;
	private double CGPA = 0;

	public Student(String sID, String n, String p)
	{
		this.studentID = sID;
		this.name = n;
		this.password = p;
	}

	public String getID()
	{
		return this.studentID;
	}

	public String getName()
	{
		return this.name;
	}

	public boolean checkPassword(String password)
	{
		if(this.password.equals(password)) return true;
		return false;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public double getCGPA()
	{
		return this.CGPA;
	}

	public void setCGPA(Double cgpa)
	{
		this.CGPA = cgpa;
	}

	@Override
	public String toString()
	{
		return name+" : "+studentID;
	}

}
