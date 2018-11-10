// package starsNTU;

import java.util.Scanner;

public class UniversityApp
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		String profile = null;
		int token;
		CourseManager coursemanager = CourseManager.initiate();
		StudentManager studentmanager = StudentManager.initiate();
		StudentCourseManager studentcoursemanager = StudentCourseManager.initiate();
		LoginManager loginmanager = LoginManager.initiate();

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("           Welcome to NTU course registration system");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		while(profile == null)
		{
			System.out.println("Please log in to your account");
			System.out.print("Matric number: ");
			String username = scan.next().toUpperCase();
			System.out.print("Password: ");
			profile = loginmanager.login(username, scan.next());

			if(profile != null && profile.equals("admin"))
			{
				System.out.println("------------------------------------------------------------------");
				System.out.println("             You are log-in as an administrator.");
				System.out.println("------------------------------------------------------------------\n");
				do
				{
					System.out.print("\n==================================================================\n");
					System.out.println("Main menu");
					System.out.println("1: Add a new student");
					System.out.println("2: Add a new course");
					System.out.println("3: Register student for a course");
					System.out.println("4: Check available slot in a class");
					System.out.println("5: Print student list by lecture, tutorial, or laboratory group");
					System.out.println("6: Enter coursework mark");
					System.out.println("7: Enter exam mark");
					System.out.println("8: Print course statistics");
					System.out.println("9: Print student transcript");
					System.out.println("10: Log out");
					System.out.println();
					System.out.print("Enter an option: ");
					token = IOE.scint();
					System.out.println("-----------------------------------\n\n");
					switch(token)
					{
						case 1: studentmanager.addStudent();
								break;
						case 2: coursemanager.addCourse();
								break;
						case 3: studentcoursemanager.registerCourse(null);
								break;
						case 4: coursemanager.checkVacancy();
								break;
						case 5: studentcoursemanager.printStudentList();
								break;
						case 6: studentcoursemanager.inputCourseworkMark();
								break;
						case 7: studentcoursemanager.inputExamMark();
								break;
						case 8: studentcoursemanager.printCourseStatistics();
								break;
						case 9:studentcoursemanager.printStudentTranscript(null);
								break;
						case 10:profile = null;
								break;
						default:break;
					}
					System.out.print("\n==================================================================\n");
				}
				while(token != 11);
			}

			if(profile != null)
			{
				System.out.println("------------------------------------------------------------------");
				System.out.println("You are log-in as "+studentmanager.getStudent(profile).getName()+".");
				System.out.println("------------------------------------------------------------------\n");
				do
				{
					System.out.print("\n==================================================================\n");
					System.out.println("Main menu");
					System.out.println("1: Register for a course");
					System.out.println("2: Check available slot in a class");
					System.out.println("3: Print student list by lecture, tutorial, or laboratory group");
					System.out.println("4: Print course statistics");
					System.out.println("5: Print student transcript");
					System.out.println("6: Log out");
					System.out.println();
					System.out.print("Enter an option: ");
					token = IOE.scint();
					System.out.println("-----------------------------------\n\n");
					switch(token)
					{
						case 1: studentcoursemanager.registerCourse(profile);
								break;
						case 2: coursemanager.checkVacancy();
								break;
						case 3: studentcoursemanager.printStudentList();
								break;
						case 4: studentcoursemanager.printCourseStatistics();
								break;
						case 5:studentcoursemanager.printStudentTranscript(profile);
								break;
						case 6:profile = null;
								break;
						default:break;
					}
					System.out.print("\n==================================================================\n");
				}
				while(token != 6);
			}
		}
	}
}