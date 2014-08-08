package gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/*
 * The PCB Machine, nothing different except for the type and the drawObject
 */
public class PCBMachine extends BaseMachine {

	public PCBMachine(String name) {
		super(name);
		setSize(new Point(100,40));		
		setType("PCBMachine");
	}
 
	public void drawObject(Graphics g) {
		Rectangle rect = getRectangle();
		g.drawRect(rect.x, rect.y, rect.width, rect.height);	
		g.drawRect(rect.x, rect.y, rect.width, rect.height-20);			
		g.drawString("PCB", rect.x, rect.y+rect.height-20);
	}

}
