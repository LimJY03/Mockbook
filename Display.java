package Display;

import java.util.ArrayList;

import AccessControl.RegularUser;

public class Display {


	public static void displaySearchOption()
	{
		System.out.println("Option 1: Search via username");
		System.out.println("Option 2: Search via email");
		System.out.println("Option 3: Search via Age");
		System.out.println("Option 4: Search via Contact");
		System.out.println("Option 5: Search via Gender");
		System.out.println("Option 0: QUIT");
	}
	
	
	
	
	public static void displaySearchResult(ArrayList<RegularUser> list,String type)
	{
		if(list.isEmpty())
		{
			System.out.println("\nSearch result is unavailable\n");
			return;
		}
		
		System.out.printf("\nResult sorted by '%s'.......\n",type);
		System.out.printf("%-25s |%-30s |%-15s |%-15s |%-15s\n","Username", "Email","Contact","Age","Gender");
		System.out.println(String.format("%-55s", "").replace("", "-"));


		for(RegularUser rUser:list)
		{
			System.out.printf("%-25s |%-30s |%-15s |%-15s |%-15s\n",
					rUser.getName(),rUser.getEmail(),rUser.getContact(),rUser.getAge(),rUser.getGender());

		}
		
		System.out.println("\n");
		
	}

}
