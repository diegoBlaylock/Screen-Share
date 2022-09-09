import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Random;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;




public class MyLib {

	
	public static void changeLookAndFeel(){
		try {
			 UIManager.put("Table.alternateRowColor", new Color(245, 245, 245));
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
			
		} catch (Exception e) {
			System.out.println("hundkfjgd");
			e.printStackTrace();
		}
		}
	public static void changeLookAndFeel(Component comp){
	try {
		 UIManager.put("Table.alternateRowColor", new Color(245, 245, 245));
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
		SwingUtilities.updateComponentTreeUI(comp);
	} catch (Exception e) {
		System.out.println("hundkfjgd");
		e.printStackTrace();
	}
	}
	
	public static BufferedImage[] sprites(BufferedImage img, int width, int height, int rows, int columns){
		
		BufferedImage[] b = new BufferedImage[(rows*columns)-1];
	
		
		
			for(int j = 0; j <(rows * columns) - 1; j++){
				
				BufferedImage bi = img.getSubimage((j %rows)*width, (j %columns)*height, width, height);
				b[j] = bi;
			
			
		}
		
		return b;
	}
	
	public static void changeLookAndFeel(Component comp, String name){
		try {
			
			UIManager.setLookAndFeel(name);
			//UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
			SwingUtilities.updateComponentTreeUI(comp);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		}
	
	public static BufferedImage resizeImage(BufferedImage image, int width, int height){
		
		BufferedImage ge = new BufferedImage(width, height, image.getType());
		Graphics2D g = ge.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
		return ge;
	}
	
	public static Font mc(float size){
		try {
			
			return Font.createFont(Font.TRUETYPE_FONT, new File("src/src/resources/fonts/mc.ttf")).deriveFont(size);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	
	
	public static BufferedImage transposedHBlur(BufferedImage im) {
	    int height = im.getHeight();
	    int width = im.getWidth();
	    // result is transposed, so the width/height are swapped
	    BufferedImage temp =  new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
	    float[] k =  { 0.00598f, 0.060626f, 0.241843f, 0.383103f, 0.241843f, 0.060626f, 0.00598f };
	    // horizontal blur, transpose result
	    for (int y = 0; y < height; y++) {
	        for (int x = 3; x < width - 3; x++) {
	            float r = 0, g = 0, b = 0;
	            for (int i = 0; i < 7; i++) {
	                int pixel = im.getRGB(x + i - 3, y);
	                b += (pixel & 0xFF) * k[i];
	                g += ((pixel >> 8) & 0xFF) * k[i];
	                r += ((pixel >> 16) & 0xFF) * k[i];
	            }
	            int p = (int)b + ((int)g << 8) + ((int)r << 16);
	            // transpose result!
	            temp.setRGB(y, x, p);
	        }
	    }
	    return temp;
	}
	
	static BufferedImage[] randomize( BufferedImage[] ss)
    {
        // Creating a object for Random class
        Random r = new Random();
         
        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = ss.length-1; i > 0; i--) {
             
            // Pick a random index from 0 to i
            int j = r.nextInt(i);
             
            // Swap arr[i] with the element at random index
            BufferedImage temp = ss[i];
            ss[i] = ss[j];
            ss[j] = temp;
        }
        // Prints the random array
        return ss;
    }

	public static int[] randomize(int[] ss) {
		Random r = new Random();
		int[] a = Arrays.copyOf(ss, ss.length);
        
        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = a.length-1; i > 0; i--) {
             
            // Pick a random index from 0 to i
            int j = r.nextInt(i);
             
            // Swap arr[i] with the element at random index
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        // Prints the random array
        return a;
	}
}
