import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/*
 * GUI component, paints picture supplied to it
 */

public class PictureBox extends JPanel{
	public int X = 0;
	public int Y = 0;
	public BufferedImage image;
	public PictureBox(BufferedImage img){
	
		this.image = img;
	
	}
	
	public PictureBox(int x, int y, int width, int height, BufferedImage img){
		
		this.image = img;
		this.setBounds(x, y, width, height);
		validate();
		X=x;
		Y=y;
	}
	
	public void setImage(BufferedImage img){
		image = img;
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        int h = image.getHeight(this);
        int w = image.getWidth(this);
        int hp = this.getHeight();
        int wp = this.getWidth();
       BufferedImage fimage;
        if((h/(w*1.000)) ==(hp/wp*1.000) ){
        	
        	fimage = MyLib.resizeImage(image,wp ,hp );
        	g.drawImage(image, 0, 0,wp, hp, this);
        }else if((w/(h*1.000)) >=(wp/(hp*1.000)) ){
        
        	g.drawImage(image, 0, 0,wp ,(int)(h*(wp / (w*1.0000))), this);
        }else{
        	
        	g.drawImage(image, 0, 0,(int)(w*(hp / (h*1.0000))) ,hp , this);
        }
        
        
    
       X = this.getLocation().x;
       Y=this.getLocation().y;
    }
	
	public void setX(int x){
		this.setLocation(x, Y);
	}
	
	public void setY(int y){
		this.setLocation(X, y);
	}

}
