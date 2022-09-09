import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class clicker {
	
	static {
		System.loadLibrary("user32");
	}
	public static void main(String args[])   {
       
		
		
		
			  
				   MessageBoxA(0, "hello", "bye", 0);
			   
	}
	
	
	   private static native int MessageBoxA(int hwndOwner, String text,
	                                        String title, int fuStyle);

			
	

}
