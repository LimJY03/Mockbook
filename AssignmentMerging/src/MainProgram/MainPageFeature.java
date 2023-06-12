package MainProgram;


import java.awt.AWTException;
import java.util.Scanner;

import AccessControl.ConnectionNet;
import AccessControl.RegularUser;
import AdminGUI.MainApplication;
import SearchFeature.Search;
import TraceBack.TraceBack;
import Display.Display;
import PostFeature.PostFeature;


public class MainPageFeature extends TraceBack{
	
    static MainApplication mainApp;

    
    public TraceBack Main() throws InterruptedException, AWTException{  
        TraceBack returnedTraceBack = null;
        String user = MainProgram.GlobalDataStore.username;        
        RegularUser me = ConnectionNet.getAllConnection(user);
        ConnectionNet.buildGraph(me);
        Display.displayWelcomeLines("Main Page", "Main Page", " "+user);
        
        breaker: while(true)    
        {

        	Display.displayUserOption("Your Feed Feature", "Search Feature","");
            String getInt = MainProgram.sc.nextLine();
            switch(getInt)
            {
                case "1":
                    Thread.sleep(500);
                    clearConsole();
                    returnedTraceBack = new PostFeature();
                    break breaker;
                case "2":
                    Thread.sleep(500);
                    clearConsole();
                    returnedTraceBack = new Search();
                    break breaker; 
                case "0": 
                    System.out.println("Redirecting To The Entrance page...");
                    Thread.sleep(500);
                    clearConsole();
                    this.previous.isPrevious = true;
                    returnedTraceBack = this.previous;
                    break breaker;
                default:
                    System.out.println("This is NOT One of The Choices Given! Type Again :D");
                    break;
            }
        }
        
        return returnedTraceBack;
    }
}
