package gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/*
 * The battery machine, nothing different except for the type and the drawObject
 */
public class BatteryMachine extends BaseMachine {

	public BatteryMachine(String name) {
		super(name);
		setSize(new Point(100,40));		
		setType("BatteryMachine");
	}
 
	public void drawObject(Graphics g) {
		Rectangle rect = getRectangle();
		g.drawRect(rect.x, rect.y, rect.width, rect.height);	
		g.drawRect(rect.x, rect.y, rect.width, rect.height-20);			
		g.drawString("Btm", rect.x, rect.y+rect.height-20);
	}

}
