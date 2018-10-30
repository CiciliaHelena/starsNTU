
public class StudentCourseTest {
	
	public static void main (String[] args) {
		StudentCourseManager scMan = new StudentCourseManager();
		//scMan.registerCourse();
		//scMan.printStudentList();
		scMan.inputExamMark(60);
		scMan.inputCourseworkMark();
		//scMan.printCourseStatistics();
		scMan.printStudentTranscript();
	}
	
}
