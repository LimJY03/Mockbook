
import java.awt.AWTException;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HuSSon
 */
public class EnterMockBook extends TraceBack{
    public TraceBack Main() throws InterruptedException, AWTException{
        Scanner i = new Scanner(System.in);
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\t\t\t\t\t|        WELCOME TO MOCKBOCK!        |");
        System.out.println("\t\t\t\t\t-------------------------------------\t\t");
        System.out.println("\n");
        System.out.println("\t\t This is The Entrance! You Will be Presented With Mulitiple Options you May Choose From Them!");
        boolean flag = false;
        String user = "";
        
        TraceBack returnedTraceBack = null;
        Entring: while(true)
        {
            System.out.println("\n\t\t\t\t\t-------------------------------------\t\t");
            System.out.println("Type 1 to Login\nType 2 to Register\nType 3 to Login As Admin\nType 0 To Close The Program!!");
            System.out.println("\t\t\t\t\t-------------------------------------\t\t");
            String getInt = i.nextLine();
            switch(getInt)
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
                    flag = true;
                    System.exit(0);
                default:
                    System.out.println("This is NOT One of The Choices Given! Type Again :D");
                    break;
            }
            
        }
        return returnedTraceBack;
    }
    
}
