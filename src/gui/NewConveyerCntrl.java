package gui;

import java.awt.Point;
import java.awt.event.MouseEvent;


public class NewConveyerCntrl implements BaseController {

	private Model model;
	private ConveyerObject conveyerobject;
	private boolean firstclick = true;
	public NewConveyerCntrl(Model model) {
		this.model = model;
		conveyerobject = new ConveyerObject();
		model.addObject(conveyerobject);		
	}
	
	public String getType() {
		return "New Conveyer Control";
	}
	
	public boolean clickedonobject(Point location) {		
		location = Grid.GridPoint(location);
		// k make sure there is a collision first
		if (model.isCollision(new SquareObject(location,1)))
			return true;
		return false;
	}
		

	public boolean onMouseClick(MouseEvent e) {
		Point location = e.getPoint();		
		if (firstclick == true) {		
			if (clickedonobject(location)) {
				// add this point twice, once for origin, second for preview line
				conveyerobject.addPointtoList(location);
				conveyerobject.addPointtoList(location);	
				firstclick = false;
			}			
			return false;
		}
		else {				
			conveyerobject.addPointtoList(location);
			
			// k we are clicked on an object we can exit
			if (clickedonobject(location)) {
				conveyerobject.removeLastPoint();				
				return true;
			}
			return false;
		}
	}
	
	public void onMouseMove(MouseEvent e) {
		if (firstclick == false) {		
			Point location = e.getPoint();	
			location = Grid.GridPoint(location);
			conveyerobject.removeLastPoint();
			conveyerobject.addPointtoList(location);
		}
		
	}

	public void onCancel() {
		model.removeFromList(conveyerobject);	
		
	}

}
