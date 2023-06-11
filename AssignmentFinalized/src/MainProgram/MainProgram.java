package MainProgram;


import java.awt.AWTException;
import TraceBack.TraceBack;


public class MainProgram{
    
    public static class GlobalDataStore{
        public static String username; 
        
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