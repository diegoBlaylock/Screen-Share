import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import com.objectplanet.image.PngEncoder;
import com.sun.jna.platform.win32.GDI32Util;

// Program for server. 
public class TCPServer extends JFrame{

	
	public static List<ClientDealer>clients = new ArrayList<ClientDealer>(); 
	//Port number that ++ every time someone joins
	public static int port = 9124;
	public static Robot r = null;
	public static BufferedImage mouse;
	public static ServerSocket server;
	
	public static void main(String[] args) throws AWTException, IOException{
		  
		try {
			try {
				r = new Robot();
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
        	   
			ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
			ses.scheduleWithFixedDelay(new Runnable() {	

				@Override
				public void run() {
					
				    /*  r.keyRelease(KeyEvent.VK_F4);
			        r.keyRelease(KeyEvent.VK_ALT);*/
	        		
					BufferedImage bi = AccessController.doPrivileged(
							new PrivilegedAction<BufferedImage >() {
						    
								@Override
								public BufferedImage run(){
									try {
										return GDI32Util.getScreenshot(User32.INSTANCE.GetDesktopWindow());
										//r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()) );
									} catch (HeadlessException e) {
										e.printStackTrace();
									}     
									return null;
								}
						   
							});
						
						/*Graphics2D g = bi.createGraphics();
						try {
							
							//g.drawImage(mouse, MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, null);
						} catch (HeadlessException e) {
							// TODO Auto-generated catch block
						//	e.printStackTrace();
						}
						g.dispose();*/
						
					sendToAll( bi);
	        	}
            }, 0, 4, TimeUnit.MILLISECONDS);
            	          
            
		} catch (Exception e) {
			e.printStackTrace();
		}//nope WHHHYHYHYHHYHYHYYHY
		
		Thread reading = new Thread(()->{
			try {
				System.in.read();
				end();
			} catch (IOException e) {
				end();
			}
		});
		
		server = new ServerSocket();
		print("Server setup! Listening for clients...");
		
		reading.start();
		
		while(!server.isClosed()) {
			//Waits until new socket is connected
			try {
				Socket connectionSocket = server.accept();
				System.out.println("New Client Connected");
				BufferedReader br = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream());
				
				//Waits until input is received which should be name
				String n = String.valueOf(br.readLine());
				System.out.println("Recieved " + n);
			       			
			       			
			       			
	   			//Writes out new port to Connnect to
	   			dos.writeBytes( Integer.toString(port) + "\n");
			       			
	   			//Creates New ClientDealer
	   			ClientDealer o = new ClientDealer(new ServerSocket(port), new ServerSocket(port+1), port, n);
	   			clients.add(o);
	   			o.start();
	   			//Adds 1 to port for next client
	   			port += 2;
			} catch (SocketException ex){
				print("Server Closed");
			}
   		}
		
		
		       		
	}
		       		
   	public static void sendToAll(BufferedImage str){
   		try {
   			if(str != null){
   				for( int i = 0; i < clients.size();i++){
   					try {
   				
   						ClientDealer client = clients.get(i);
   						client.send(str);
   					}catch(Exception e) {}
   			
   				}
   			}
   		}catch(Exception e) {}
   	}
   	
   	public static void end(){
   		if(server!=null) {
   			try {
				server.close();
			} catch (IOException e) {}
   		}
   		System.exit(0);
   	}
		     
		        
	
	public static void print(String str){
		System.out.println(str);
	}
	
	
}
