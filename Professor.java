
public class Professor {
	/**
	 * professor object details
	 */
	private String name;
	private String email;
	private int contact;
	private String office;

	/**
	 * contructs a professor object based on the parameters passed.
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
	 * returns true if the caller Professor object and the parameter Professor object is equal
	 */
	public boolean equals(Object o) {
		if (o instanceof Professor) {
			Professor p = (Professor)o;
			return (getName().equals(p.getName()));
		}
		return false;
	}
}