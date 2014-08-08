package gui;

import java.awt.event.MouseEvent;

/*
 * Used so that we can have many different types of controllers with the 
 *   same framework code in model
 * 
 */
public interface BaseController {
	// called for a mouse click
	// returns true if the controller is finished
	public boolean onMouseClick(MouseEvent e);
	public void onMouseMove(MouseEvent e);
	// called to tell the controller that it is finished
	public void onCancel();
	// for display purposes
	public String getType();
}
