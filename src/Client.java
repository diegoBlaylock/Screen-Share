import java.awt.AWTException;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Client extends JFrame{

	
	public static int port = 9124;
	public static Socket client;
	public static Socket client2;
	public static boolean connected = false;
	public static DataOutputStream outToServer;
	public static BufferedInputStream in;
	public static int frames = 0;
	
	
	public static void main(String[] args) throws AWTException{
		MyLib.changeLookAndFeel();
		String ip = JOptionPane.showInputDialog("Enter ip for conversation:");
		
		
		if (ip == null) {
			System.exit(0);
		} else if(ip.isEmpty()){
			ip = "localhost";
		}
		
		try {
			//Connects to first port
			client = new Socket(ip, 8123);
			connected = true;
			outToServer = new DataOutputStream(client.getOutputStream());
			
			outToServer.writeBytes("join" + "\n");
			int port = Integer.parseInt((new BufferedReader(new InputStreamReader(client.getInputStream()))).readLine());
			client.close();
			outToServer.close();
			//closes port and opens to new specified port
			System.out.println(port);
			client = new Socket(ip, port);
			client2=new Socket(ip, port+1);
			outToServer = new DataOutputStream(client2.getOutputStream());
			in = new BufferedInputStream(client.getInputStream(),10000);
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			connected = false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Didn't work");
			System.exit(0);
			e1.printStackTrace();
			connected = false;
		}
		
		
		        Client a = new Client();
		        a.setSize(1000, 800);
		        a.addKeyListener(new KeyAdapter(){


					@Override
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						try {
							e.consume();
							outToServer.writeBytes("kp!" + String.valueOf(e.getKeyCode()) + "\n");
							outToServer.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						try {
							e.consume();
							outToServer.writeBytes("kr!" + String.valueOf(e.getKeyCode()) + "\n");
							outToServer.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
		        	
		        });
		        
		        a.addMouseWheelListener(new MouseWheelListener(){

					@Override
					public void mouseWheelMoved(MouseWheelEvent e) {
						// TODO Auto-generated method stub
						try {
							outToServer.writeBytes("mw" + String.valueOf(e.getPreciseWheelRotation()) +"\n");
							outToServer.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
		        	
		        });
		        a.addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						try {
							outToServer.writeBytes("mp!" + String.valueOf(e.getButton()) + "\n");
							outToServer.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						try {
							outToServer.writeBytes("mr!"+ String.valueOf(e.getButton())+"\n");
							outToServer.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
		        	
		        });
		        a.addWindowListener(new WindowListener() {

		

					
					@Override
					public void windowClosing(WindowEvent arg0) {
						// TODO Auto-generated method stub
						try {
							System.out.println("Program closed");
							if(outToServer != null) {	
								outToServer.writeBytes("END\n");
							}
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					
		        	
		        });
		        PictureBox p = new PictureBox(new BufferedImage(10,10, 2));
		        p.setBackground(Color.BLACK);
		        a.setContentPane(p);


		                a.show();
		                a.setDefaultCloseOperation(3);
		                MyLib.changeLookAndFeel(a);
		                
		                
		            
		                ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
						
	                	// and finally, when your program wants to exit
	                	ses.scheduleWithFixedDelay(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								frames = 0;
							}
	                		
	                	}, 1, 1, TimeUnit.SECONDS);
ImageIO.setUseCache(false);
							while(true) {
								BufferedImage bi = null;
									try {
										
										 bi= ImageIO.read(in);
										 
										 
										if(!(bi == null)) {
											frames++;
										p.setImage(bi);
										p.repaint();
										}
									} catch (IOException e) {
										// TODO Auto-generated catch block
										
									}
									
								}
		                		
							
		                
	
	}
		                	
	
						
		            }
		     
		        
	
	

	
