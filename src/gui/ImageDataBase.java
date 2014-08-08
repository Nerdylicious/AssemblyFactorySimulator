package gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageDataBase {
	
	private BufferedImage images[];
	private String lut[][];
	static int numimages = 13;
	
	public ImageDataBase() {
		
		images = new BufferedImage[numimages];
		lut = new String[numimages][2];
		
		// the lut has the file name and then the assembly name
		lut[0][0] = "ipadbattery"; 
		lut[0][1] = "ipadbattery.jpg";
		
		lut[1][0] = "iphonebattery"; 
		lut[1][1] = "iphonebattery.jpg";
		
		lut[2][0] = "ishufflebattery"; 
		lut[2][1] = "ishufflebattery.jpg";
		
		lut[3][0] = "itouchpcb"; 
		lut[3][1] = "iphonepcb.jpg";
		
		lut[4][0] = "ishufflepcb"; 
		lut[4][1] = "ishufflepcb.jpg";
		
		lut[5][0] = "iphonepcb"; 
		lut[5][1] = "iphonepcb.jpg";
		
		lut[6][0] = "ipadpcb"; 
		lut[6][1] = "ipadpcb.jpg";
		
		lut[7][0] = "iphonescreen"; 
		lut[7][1] = "iphonescreen.jpg";
		
		lut[8][0] = "ipadscreen"; 
		lut[8][1] = "ipadscreen.jpg";
		
		lut[9][0] = "ipadassembly"; 
		lut[9][1] = "ipad.jpg";
		
		lut[10][0] = "ishuffleassembly"; 
		lut[10][1] = "ishuffle.jpg";
		
		lut[11][0] = "iphoneassembly"; 
		lut[11][1] = "iphone.jpg";
		
		lut[12][0] = "itouchassembly"; 
		lut[12][1] = "itouch.jpg";
		
		try {
			
			// load in the actual files
			for (int ix = 0; ix < numimages; ix++) {
				images[ix] = ImageIO.read(new
				File(lut[ix][1]));
			}
		} 
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	public void renderimage(String assemblyname, Point location, Graphics g) {
		
		// first find the image
		int ix;
		for (ix = 0; ix < numimages; ix++) {
			if (lut[ix][0].equalsIgnoreCase(assemblyname)) {
				break;
			}
		}
		
		Point tlocation = new Point(0,0);
		tlocation.x = location.x - images[ix].getWidth()/2;
		tlocation.y = location.y - images[ix].getHeight()/2;
		g.drawImage(images[ix],tlocation.x,tlocation.y,null);
	}
	
	
	
	
	}
