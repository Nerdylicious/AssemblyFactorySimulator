package gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


/*
 * This is just used for the collsion detect to return a rectangle given a point
 *   and size
 * 
 */
public class SquareObject implements BaseObject{
	
	private Rectangle rectangle;
	public SquareObject(Point location, int size) {
		rectangle = new Rectangle(location.x-size,location.y-size,size*2,size*2);		
	}
	
	public void drawObject(Graphics g) {		
	}

	
	public Rectangle getRectangle() {
		// TODO Auto-generated method stub
		return rectangle;
	}

	
	public boolean isCollision(BaseObject object) {
		// not needed since never called at
		return false;
	}

	
	public String saveObject() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void loadObject(String object) {
		// TODO Auto-generated method stub
		
	}

	
	public String getName() {
		
		return null;
	}


	public Point getLocation(int currenttime, int timeslotstarttime) {

		return null;
	}

}
