
import java.awt.AWTException;
import java.awt.Robot;
import java.util.LinkedList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HuSSon
 */
public abstract class TraceBack {
    
    public void Main(){}
    
    public void clsNetbeans() throws InterruptedException, AWTException {
            Thread.sleep(500);
            Robot pressbot = new Robot();
            pressbot.keyPress(17); // Holds CTRL key.
            pressbot.keyPress(76); // Holds L key.
            pressbot.keyRelease(76); // Releases CTRL key.
            pressbot.keyRelease(17); // Releases L key.
            Thread.sleep(500);
    }
    
    TraceBack previous;
    
    public void backTrack()
    {
        this.previous.Main();
    }
    
    public void Reset(){}
    
    
    
    
}


