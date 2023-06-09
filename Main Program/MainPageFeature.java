
import com.mycompany.project.DB.DBFactory;
import java.awt.AWTException;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HuSSon
 */
public class MainPageFeature extends TraceBack{
    
    public TraceBack Main() throws InterruptedException, AWTException{
        Scanner i = new Scanner(System.in);    
        TraceBack returnedTraceBack = null;
        String user = MainProgram.GlobalDataStore.username;
        System.out.println("****************");
            System.out.println("*   MockBook   *      " + user);
            System.out.println("****************\n");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\t\t\t\t\t|              Main Page            |");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\n\t\t This is The Main Page! You Will be Presented With Mulitiple Options you May Choose From Them!");
        breaker: while(true)    
        {
            System.out.println("\n\t\t\t\t\t-------------------------------------\t\t");
            System.out.println("Type 1 Go to Your Feed Feature\nType 2 to Go to The Search Feature\nType 0 To Go Back!");
            System.out.println("\t\t\t\t\t-------------------------------------\t\t");
            String getInt = i.nextLine();
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
