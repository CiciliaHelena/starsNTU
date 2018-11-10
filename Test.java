import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Test
{
	public static void main(String[] args)
	{
		CourseManager cm = CourseManager.initiate();
		cm.addCourse();
		cm.printCourses();
		// cm.checkVacancy();
		// System.out.println(cm.list.size());
		// cm.deleteCourse();


		// StudentManager.addStudent();
		// StudentManager.printStudents();
		// System.out.println(StudentManager.list.size());
		// StudentManager.deleteStudent();
	}
}

