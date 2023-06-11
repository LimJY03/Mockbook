package MainProgram;


import java.awt.AWTException;
import java.sql.SQLException;
import java.util.Scanner;

import AdminGUI.MainApplication;
import Registration.Login;
import Registration.Registration;
import TraceBack.TraceBack;
import javafx.application.Platform;
import Display.Display;
import AdminGUI.MainApplication;

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
                	
                case "0":
                    System.out.println("Closing ....");
                    MainProgram.sc.close();
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