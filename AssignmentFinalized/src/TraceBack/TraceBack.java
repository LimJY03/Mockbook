package TraceBack;

import java.awt.AWTException;
import java.awt.Robot;

public abstract class TraceBack {
    
    public boolean isPrevious = false;
    
    public TraceBack Main() throws InterruptedException, AWTException{
        return null;
    }
    
    public void clearConsole() throws InterruptedException, AWTException { // press ctrl and l so it can clear the console 
        Robot botty = new Robot();
        Thread.sleep(500); // pauses the code for half a second so we can clear the console safely
        botty.keyPress(17); 
        botty.keyPress(76); 
        botty.keyRelease(76); 
        botty.keyRelease(17); 
        Thread.sleep(500); // pauses the code again so we can make sure the code continues safely after clearing
    }
    
    public TraceBack previous;
    
    public void backTrack() throws InterruptedException, AWTException
    {
        this.previous.Main();
    }
    
    
    
}

