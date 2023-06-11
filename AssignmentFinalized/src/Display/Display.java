package Display;

import java.util.ArrayList;

import AccessControl.RegularUser;

public class Display {

	public static void displaySearchOption() {
		System.out.println("Option 1: Search via username");
		System.out.println("Option 2: Search via email");
		System.out.println("Option 3: Search via Age");
		System.out.println("Option 4: Search via Contact");
		System.out.println("Option 5: Search via Gender");
		System.out.println("Option 0: QUIT");
	}

	public static void displaySearchResult(ArrayList<RegularUser> list, String type) {
		if (list.isEmpty()) {
			System.out.println("\nSearch result is unavailable\n");
			return;
		}

		System.out.printf("\nResult sorted by '%s'.......\n", type);
		System.out.printf("%-25s |%-30s |%-15s |%-15s |%-15s\n", "Username", "Email", "Contact", "Age", "Gender");
		System.out.println(String.format("%-55s", "").replace("", "-"));

		for (RegularUser rUser : list) {
			System.out.printf("%-25s |%-30s |%-15s |%-15s |%-15s\n", rUser.getName(), rUser.getEmail(),
					rUser.getContact(), rUser.getAge(), rUser.getGender());

		}

		System.out.println("\n");

	}

	public static void displayWelcomeLines(String header,String type,String user) {
		
		System.out.println("****************");
		System.out.println("*   MockBook   *");
		System.out.println("****************\n");
		System.out.println("\t\t\t\t\t-------------------------------------\t\t");
		System.out.println("\t\t\t\t\t           "+header+"!       ");
		System.out.println("\t\t\t\t\t-------------------------------------\t\t");
		System.out.println("\n");
		System.out.println(
				"\t\t Hello"+user+"! This is The "+type+"! You Will be Presented With Mulitiple Options you May Choose From Them!");

	}

	public static void displayUserOption(String type1,String type2,String type3) {
		System.out.println("\n\t\t\t\t\t-------------------------------------\t\t");
		
		if(!type1.equals(""))
			System.out.printf("Type 1 to %s\n",type1);
		
		if(!type2.equals(""))
			System.out.printf("Type 2 to %s\n",type2);
		
		if(!type3.equals(""))
			System.out.printf("Type 3 to %s\n",type3);
		System.out.println("Type 0 To Close The Program!!");

		System.out.println("\n\t\t\t\t\t-------------------------------------\t\t");
	}
	
	
	public static void displayLoginPage(String loginOption)
	{
        System.out.println("****************");
        System.out.println("*   MockBook   *");
        System.out.println("****************\n");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\t\t\t\t\t        Login Using "+loginOption+"       ");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\n");
	}

}
