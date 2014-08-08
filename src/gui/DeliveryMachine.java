package gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/*
 * The delivery machine, nothing different except for the type and the drawObject
 */
public class DeliveryMachine extends BaseMachine {

	public DeliveryMachine(String name) {
		super(name);
		setSize(new Point(100,40));		
		setType("DeliveryMachine");
	}
 
	public void drawObject(Graphics g) {
		Rectangle rect = getRectangle();
		g.drawRect(rect.x, rect.y, rect.width, rect.height);	
		g.drawRect(rect.x, rect.y, rect.width, rect.height-20);			
		g.drawString("Dlr", rect.x, rect.y+rect.height-20);
	}

}

