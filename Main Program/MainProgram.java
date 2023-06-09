
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
public class MainProgram{
    
    static class GlobalDataStore{
        static String username; 
        
    } 
    
    public static void main(String[] args) throws InterruptedException, AWTException{
        
        
        TraceBack currentTraceBack = new EnterMockBook();
        TraceBack nextTraceBack = null;
        while(true) {
            currentTraceBack.isPrevious = false;   
            nextTraceBack = Run(currentTraceBack);
            
            if (!nextTraceBack.isPrevious){
                nextTraceBack.previous = currentTraceBack;
                nextTraceBack.isPrevious = true;
            }

            currentTraceBack = nextTraceBack;
        }
        
    }
    
    
    static public TraceBack Run(TraceBack tb) throws InterruptedException, AWTException{
        
        TraceBack next = tb.Main();
        return next; 
    }
}
