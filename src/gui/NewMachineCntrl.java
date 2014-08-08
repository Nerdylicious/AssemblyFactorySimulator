package gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;


public class NewMachineCntrl implements BaseController {

	private BaseMachine basemachine;
	private String machinetype;
	private Model model;
	
	public String getType() {
		return "New Machine Control";
	}
	
	public NewMachineCntrl(String machinetype,Model model) {
		this.machinetype = machinetype;		
		this.model = model;
		if (machinetype.equals("PCBMachine")) {
			basemachine = new PCBMachine("PCBMachine");		
			model.addObject(basemachine);
		}	
		else if (machinetype.equals("ScreenMachine")) {
			basemachine = new ScreenMachine("ScreenMachine");		
			model.addObject(basemachine);
		}	
		else if (machinetype.equals("BatteryMachine")) {
			basemachine = new BatteryMachine("BatteryMachine");		
			model.addObject(basemachine);
		}	
		else if (machinetype.equals("AssemblyMachine")) {
			basemachine = new AssemblyMachine("AssemblyMachine");		
			model.addObject(basemachine);
		}	
		else if (machinetype.equals("DeliveryMachine")) {
			basemachine = new DeliveryMachine("DeliveryMachine");		
			model.addObject(basemachine);
		}	
	}

	public boolean onMouseClick(MouseEvent e) {		
		return true;		
	}


	public void onMouseMove(MouseEvent e) {
		// TODO Auto-generated method stub
		Point location = e.getPoint();
		Point oldlocation = basemachine.getLocation();
		basemachine.setLocation(location);	
		Rectangle rect = basemachine.getRectangle();
		location = Grid.GridPoint(rect);	
		basemachine.setLocation(location);	
		if (model.isCollision(basemachine) == false) {
			//basemachine.setLocation(location);		
		}
		else {
			basemachine.setLocation(oldlocation);		
		}	
	}

	
	public void onCancel() {
		model.removeFromList(basemachine);		
	}

}
