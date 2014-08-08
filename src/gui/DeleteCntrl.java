package gui;

import java.awt.Point;
import java.awt.event.MouseEvent;

/*
 * The delete controller.  It deletes an object if the mouse clicks on something.
 * 
 *  The controller stays valid until it deletes something
 *   
 */
public class DeleteCntrl implements BaseController {
	private Model model;
	
	public String getType() {
		return "Delete Control";
	}
	
	public DeleteCntrl(Model model) {
		this.model = model;
	}
	
	public boolean onMouseClick(MouseEvent e) {
		// if we have a collision delete object
		Point location = e.getPoint();		
		location = Grid.GridPoint(location);
		// k make sure there is a collision first
		if (model.removeIfCollision(new SquareObject(location,3)))
			return true;
		return false;	
	}

	public void onMouseMove(MouseEvent e) {	
	}

	public void onCancel() {		
	}

}
