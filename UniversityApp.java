// package starsNTU;

import java.util.Scanner;

public class UniversityApp
{
	public static String login(String username, String password)
	{
		if(username.equals("admin") && password.equals("admin"))
			return "admin";
		return null;
	}

	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		String profile = null;
		int token;

		System.out.println("Welcome to NTU course registration system");
		while(profile == null)
		{
			System.out.println("Please log in to your account");
			System.out.print("Matric number: ");
			String username = scan.next();
			System.out.print("Password: ");
			profile = login(username, scan.next());
			
			// // ~~~
			// profile = "HI";
			// // ~~~

			if(profile != null)
			{
				do
				{
					System.out.println("Main menu");
					System.out.println("1: Add a student");
					System.out.println("2: Add a course");
					System.out.println("3: Register student for a course");
					System.out.println("4: Check available slot in a class");
					System.out.println("5: Print student list by lecture, tutorial, or laboratory group");
					//System.out.println("6: Enter course assessment component weightage"); // not necessary because entered when add course
					System.out.println("7: Enter coursework mark");
					System.out.println("8: Enter exam mark");
					System.out.println("9: Print course statistics");
					System.out.println("10: Print student transcript");
					System.out.println("11: Log out");
					System.out.println();
					System.out.println("Select an option");
					token = IOE.scint();
					switch(token)
					{
						case 1: StudentManager.addStudent();
								break;
						case 2: CourseManager.addCourse();
								break;
						case 3: StudentCourseManager.registerCourse();
								break;
						case 4: CourseManager.checkVacancy();
								break;
						case 5: StudentCourseManager.printStudentList();
								break;
						case 7: StudentCourseManager.inputCourseworkMark();
								break;
						case 8: StudentCourseManager.inputExamMark();
								break;
						case 9: StudentCourseManager.printCourseStatistics();
								break;
						case 10:StudentCourseManager.printStudentTranscript();
								break;
						case 11:profile = null;
								break;
						default:break;
					}
				}
				while(token != 11);
			}
		}
	}
}