// package starsNTU;
import java.util.ArrayList;

public class LoginManager
{
	protected ArrayList<Student> list;
	private static LoginManager theinstance = null;
	private static StudentManager studentmanager = StudentManager.initiate();

	private LoginManager()
	{
		list = studentmanager.list; //pointer to the real list
	}

	public static LoginManager initiate()
	{
		if(theinstance == null)
			theinstance = new LoginManager();
		return theinstance;
	}

	public String login(String username, String password)
	{
		if(username.equals("admin") && password.equals("admin"))
			return "admin";
		else
		{
			for(Student temp: list)
				if(username.equals(temp.getID()))
					if(temp.checkPassword(password))
						return temp.getID();
		}
		System.out.println("The username or password you provided is incorrect.");
		System.out.println("Please try again.");
		return null;
	}
}