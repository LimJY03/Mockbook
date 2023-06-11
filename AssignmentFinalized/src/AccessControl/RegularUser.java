package AccessControl;

public class RegularUser extends User {
		
	private String contact,gender,email;
	private int age;
	
	public RegularUser(String name,String email,String contact,int age,String gender,String p)
	{
		super(name,p);
		this.email=email;
		this.contact=contact;
		this.age=age;
		this.gender=gender;
	}
	
	public String getName()
	{
		return super.getUsername();
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getGender()
	{
		return this.gender;
	}
	
	public String getContact()
	{
		return this.contact;
	}
	
	public int getAge()
	{
		return this.age;
	}
}
