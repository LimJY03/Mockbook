package MainProgram;


import java.awt.AWTException;
import java.util.InputMismatchException;
import java.util.Scanner;

import SearchFeature.Search;
import TraceBack.TraceBack;
import Display.Display;


public class MainPageFeature extends TraceBack{
    
    public TraceBack Main() throws InterruptedException, AWTException{
        Scanner sc = new Scanner(System.in);    
        TraceBack returnedTraceBack = null;
        String user = MainProgram.GlobalDataStore.username;
        Display.displayWelcomeLines("Main Page", "Main Page", " "+user);
        
        breaker: while(true)    
        {

        	Display.displayUserOption("Your Feed Feature", "Search Feature","");
            String getInt = sc.nextLine();
            switch(getInt)
            {
                case "1":
                    Thread.sleep(500);
                    clearConsole();
//                    returnedTraceBack = new PostFeature();
                    break breaker;
                case "2":
                    Thread.sleep(500);
                    clearConsole();
//                    returnedTraceBack = new Search();
                    break breaker; 
                case "0": 
                    System.out.println("Redirecting To The Entrance page...");
                    Thread.sleep(500);
                    clearConsole();
                    returnedTraceBack = new EnterMockBook();
                    break breaker;
                default:
                    System.out.println("This is NOT One of The Choices Given! Type Again :D");
                    break;
            }
        }
        
        return returnedTraceBack;
    }
}