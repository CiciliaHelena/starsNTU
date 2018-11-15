
public class Professor {
	
	/**
	 * Professor object contains details of professors
	 */
	private String name;
	private String email;
	private int contact;
	private String office;

	
	/**
	 * constructor of Professor gets passed all details of professor
	 * then constructs an object
	 */
	public Professor(String n, String e, int c, String o)  {
		name = n ;
		email = e ;
		contact = c ;
		office = o ;
	}
	
	/**
	 * getters of professor details
	 */
			
	public String getName() { return name ; }
	public int getContact() { return contact ; }
	public String getEmail() { return email ; }
	public String getOffice() { return office ; }

	/**
	 * check if a Professor object is equal to the calling Professor object
	 * 
	 */
	public boolean equals(Object o) {
		if (o instanceof Professor) {
			Professor p = (Professor)o;
			return (getName().equals(p.getName()));
		}
		return false;
	}
}