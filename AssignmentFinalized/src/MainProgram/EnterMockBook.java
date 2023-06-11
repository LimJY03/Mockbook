package MainProgram;


import java.awt.AWTException;
import java.util.Scanner;

import Registration.Login;
import Registration.Registration;
import TraceBack.TraceBack;
import Display.Display;


public class EnterMockBook extends TraceBack{
	
    public TraceBack Main() throws InterruptedException, AWTException{
        Scanner sc = new Scanner(System.in);
        Display.displayWelcomeLines("Welcome to MockBook","Entrance","");
        
        TraceBack returnedTraceBack = null;
        Entring: while(true)
        {
        	Display.displayUserOption("Login","Register","Admin");
            String getOption = sc.nextLine();
            switch(getOption)
            {
                case "1":
                    returnedTraceBack = new Login();
                    Thread.sleep(500);
                    clearConsole();
                    break Entring;
                case "2":
                    returnedTraceBack = new Registration();
                    Thread.sleep(500);
                    clearConsole();
                    break Entring;
                case "3": 
                case "0":
                    System.out.println("Closing ....");
                    System.exit(0);
                default:
                    System.out.println("This is NOT One of The Choices Given! Type Again :D");
                    break;
            }
            
        }
        return returnedTraceBack;
    }
    
}