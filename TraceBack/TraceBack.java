package TraceBack;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public abstract class TraceBack {

	public boolean isPrevious = false;

	public TraceBack Main() throws InterruptedException, AWTException {
		return null;
	}

	public void clearConsole() throws InterruptedException, AWTException { // press ctrl and l so it can clear the
																			// console
		Thread.sleep(500); // pauses the code for half a second so we can clear the console safely

		Robot robbie = new Robot();
        //shows the Console View
        robbie.keyPress(KeyEvent.VK_ALT);
        robbie.keyPress(KeyEvent.VK_SHIFT);
        robbie.keyPress(KeyEvent.VK_Q);
        robbie.keyRelease(KeyEvent.VK_ALT);
        robbie.keyPress(KeyEvent.VK_SHIFT);
        robbie.keyPress(KeyEvent.VK_Q);
        robbie.keyPress(KeyEvent.VK_C);
        robbie.keyRelease(KeyEvent.VK_C);

        //clears the console
        robbie.keyPress(KeyEvent.VK_SHIFT);
        robbie.keyPress(KeyEvent.VK_F10);
        robbie.keyRelease(KeyEvent.VK_SHIFT);
        robbie.keyRelease(KeyEvent.VK_F10);
        robbie.keyPress(KeyEvent.VK_R);
        robbie.keyRelease(KeyEvent.VK_R);

		
		Thread.sleep(500); // pauses the code again so we can make sure the code continues safely after
							// clearing

	}

	public TraceBack previous;

	public void backTrack() throws InterruptedException, AWTException {
		this.previous.Main();
	}

}
