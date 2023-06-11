package AccessControl;

public abstract class User {

	private String username,password;
	

	User(String u,String p)
	{
		this.username=u;
		this.password=p;
	}
	
	User(){}
	
	public String getUsername()
	{
		return this.username;
	}
	
	
	
	public void setUsername(String username)
	{
		this.username=username;
	}
	
	
	public void setPassword(String password)
	{
		this.password=password;
	}
	
	public String getPassword()
	{
		return this.password;
	}

	
	
}
