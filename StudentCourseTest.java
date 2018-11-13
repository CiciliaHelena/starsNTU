
public class StudentCourseTest {
	
	public static void main (String[] args) {
		StudentCourseManager scMan = StudentCourseManager.initiate();
		//scMan.registerCourse();
		//scMan.printStudentList();
		scMan.inputExamMark();
		scMan.inputCourseworkMark();
		//scMan.printCourseStatistics();
		scMan.printStudentTranscript(null);
	}
	
}
