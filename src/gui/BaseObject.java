package gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/*
 * All objects must implement this, so we can have many different types of objects
 */
public interface BaseObject {	
	public void drawObject(Graphics g);
	public Rectangle getRectangle();
	public boolean isCollision(BaseObject object);
	public String saveObject();
	public void loadObject(String object);
	public String getName();
	public Point getLocation(int currenttime, int timeslotstarttime);
}
