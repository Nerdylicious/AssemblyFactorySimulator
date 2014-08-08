package gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/*
 * The assembly machine, nothing different except for the type and the drawObject
 */
public class AssemblyMachine extends BaseMachine {

	public AssemblyMachine(String name) {
		super(name);
		setSize(new Point(100,40));		
		setType("AssemblyMachine");
	}
 
	public void drawObject(Graphics g) {
		Rectangle rect = getRectangle();
		g.drawRect(rect.x, rect.y, rect.width, rect.height);	
		g.drawRect(rect.x, rect.y, rect.width, rect.height-20);			
		g.drawString("Asm", rect.x, rect.y+rect.height-20);
	}

}

