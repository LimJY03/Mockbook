package MainProgram;


import java.awt.AWTException;
import java.sql.SQLException;

import AdminGUI.MainApplication;
import Registration.Login;
import Registration.Registration;
import TraceBack.TraceBack;
import javafx.application.Platform;
import Display.Display;
import MockDataCreation.MockDataCreator;

public class EnterMockBook extends TraceBack{
	
    public TraceBack Main() throws InterruptedException, AWTException{
        Display.displayWelcomeLines("Welcome to MockBook","Entrance","");
        
        TraceBack returnedTraceBack = null;
        Entring: while(true)
        {
        	Display.displayUserOption("Login","Register","Admin");
        	System.out.println("Type 4 to Create Mock User");
        	System.out.println("Type 5 to Populate 30 Mock Users");
        	System.out.println("Type 6 to Populate 2 Admin Users");
            String getOption = MainProgram.sc.nextLine();
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
                	 if (MainApplication.mainApp == null) {
                         MainApplication.mainApp = new MainApplication(); // Create a new instance of MainApplication
                         Thread launchThread = new Thread(() -> {
                             MainApplication.launch(MainApplication.class,"args1" );
                         });
                         launchThread.start();
                     } else {
                         Platform.runLater(() -> MainApplication.stage.setOpacity(1));
                     }
                     break;
                case "4":
                	MockDataCreator.createMockUser(false);;
                	break;
                	
                case "5":
                	MockDataCreator.createThirtyMockUser();
                	break;
                
                case "6":
                	MockDataCreator.createTwoAdmin();
                	break;
                
                	
                case "0":
                    System.out.println("Closing ....");
                    MainProgram.sc.close();
                    Platform.exit();
				try {
					MainProgram.connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
                    System.exit(0);
                default:
                    System.out.println("This is NOT One of The Choices Given! Type Again :D");
                    break;
            }
            
        }
        return returnedTraceBack;
    }
    
}