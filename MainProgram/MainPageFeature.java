package MainProgram;


import java.awt.AWTException;

import AccessControl.ConnectionNet;
import AccessControl.EditRegularUserAccount;
import AccessControl.RegularUser;
import AddFriend.AddNewFriend;
import AdminGUI.MainApplication;
import SearchFeature.Search;
import TraceBack.TraceBack;
import Display.Display;
import PostFeature.PostFeature;


public class MainPageFeature extends TraceBack{
	
    static MainApplication mainApp;

    public static RegularUser me;
    
    public TraceBack Main() throws InterruptedException, AWTException{  
        TraceBack returnedTraceBack = null;
        String user = MainProgram.GlobalDataStore.username;
        me = ConnectionNet.getAllConnection(user);
        ConnectionNet.buildGraph(me);
        Display.displayWelcomeLines("Main Page", "Main Page", " "+user);
        
        breaker: while(true)    
        {
        	Display.displayUserOption("Your Feed Feature", "Search Feature", "Add New Friend","Edit Your Account Information");
 

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
                case "3":
                    Thread.sleep(500);
                    clearConsole();
                    returnedTraceBack= new AddNewFriend(me);
                    break breaker;
                case "4":
                	Thread.sleep(500);
                	clearConsole();
                    returnedTraceBack= new EditRegularUserAccount();
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