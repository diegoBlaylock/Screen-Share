import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.objectplanet.image.PngEncoder;

/**
 * Make Stop methos that sets volatile running to false
 * @author kids
 *
 */
public class ClientDealer extends Thread{
	//All the variables
	//public Thread t;
	public ServerSocket serve;
	public ServerSocket serve2;
	public Socket client;
	public Socket client2;
	public BufferedReader inFromClient;
	public BufferedOutputStream outToClient;
	public PngEncoder png = new PngEncoder();
	public DataOutputStream bf ;
	
	public int port;
	private volatile boolean running = false;
	


	/**
	 * Connects to Socket
	 * @param s
	 * @param s2 
	 * @param port
	 * @param n
	 * @throws IOException
	 */
	ClientDealer(ServerSocket s,ServerSocket s2, int por, String n) throws IOException{
		serve = s;
		serve2 = s2;
		port = por;
		running = true;
		client = serve.accept();
		client2= serve2.accept();
		outToClient = new BufferedOutputStream(client.getOutputStream(),10000);
		bf = new DataOutputStream(client2.getOutputStream());
	
	}
	
	
	public void run(){
		
		//Strings for input and output
		String clientSentence;
		String capitalizedSentence;
		try {
			//Creates Input and Output
			inFromClient = new BufferedReader(new InputStreamReader(client2.getInputStream()));
			
		while(running){
			//Reads Message
		
			clientSentence = inFromClient.readLine();
			TCPServer.print(clientSentence);
			String[] css = clientSentence.split("!");
			 if(css[0].equalsIgnoreCase("mp")){
			TCPServer.r.mousePress(InputEvent.getMaskForButton(Integer.parseInt(css[1])));
			 }else if (css[0].equalsIgnoreCase("mr")){
					TCPServer.r.mouseRelease(InputEvent.getMaskForButton(Integer.parseInt(css[1])));
			 }else if(css[0].equalsIgnoreCase("kp")){
				 TCPServer.r.keyPress(Integer.parseInt(css[1]));
			 }else if(css[0].equalsIgnoreCase("kr")){
				 TCPServer.r.keyRelease(Integer.parseInt(css[1]));
			 }else if(css[0].equalsIgnoreCase("mw")){
				 TCPServer.r.mouseWheel(Integer.parseInt(css[1]));
		}else if(clientSentence.equalsIgnoreCase("END")){
			System.out.println("Received: " + clientSentence);
			TCPServer.clients.remove(this);
			
			client.close();
			running = false;
		
		System.out.println("Received: " + clientSentence);
		
		//Should send message to all Clients
	
	
	}
		}
		} catch (IOException e) {
			running = false;
			TCPServer.clients.remove(this);
		}
		
	
	
	
				
						
	}		
	
	public void terminate(){
		running = false;
		TCPServer.clients.remove(this);
	}
	
	public void send(BufferedImage str){
		if(running){
			
					// TODO Auto-generated method stub
					try {
						png.setCompression(PngEncoder.BEST_SPEED);
							png.encode(str, outToClient);
							
							//outToClient.flush();
						} catch (IOException e) {
							//terminate();
						}
						
			
		}
	}
	
	public void send(String str){
		if(running){
			
					// TODO Auto-generated method stub
					try {
						
							bf.writeBytes(str);
							
							//outToClient.flush();
						} catch (IOException e) {
							//terminate();
						}
						
			
		}
	}

}
