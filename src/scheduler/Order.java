package scheduler;

import gui.ImageDataBase;

import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;


/*
 * This class is an order.  It contains a name,a list of TimeSlots for every
 *   machine it is scheduled in and a link to product
 * 
 */
public class Order {
	private String name;
	LinkedList <TimeSlot> timeslots;
	Product product;
	static ImageDataBase imagedatabase = new ImageDataBase();
	
	public String getName() {
		return name;
	}
	public Order(String name, Product product) {
		this.name = name;
		this.product = product;
		timeslots = new LinkedList<TimeSlot>();
	}

	
	// notice that this does not call other tostrings to avoid a stack error.
	public String toString() {
		String tbuffer = name + " is a: " + "\"" + product.getName() + "\"" + "\n";	
		for (TimeSlot timeslot : timeslots) {
			tbuffer += timeslot.getMachine().getName() + " " + timeslot.getAssemblyName() + ": starttime : " + timeslot.getStart() + " endtime: "  + timeslot.getEnd() + "\n";;
		}
		return tbuffer;
	}
	
	// this is used by timeslot in order to avoid a stack error
	public String getInfo() {
		return name + " is a: " + "\"" + product.getName() + "\"";	
	}
	
	public void addTimeSlot(TimeSlot timeslot) {
		timeslots.add(timeslot);
	}
	
	
	/*
	 * PURPOSE: Draw the orders moving along the conveyer belt
	 *          
	 */
	/*
	public void render(Graphics g, int time) {
		
		// render everything that is a timeslot
		for (TimeSlot timeslot : timeslots) {
			
			if (timeslot.isTimeSlot(time)) {
				// find its location and draw it
				Point getlocation = timeslot.getLocation(time,timeslot.getStart());
				g.drawRect(getlocation.x,getlocation.y,5,5);
			}
		}
	}
	*/
	
	public void render(Graphics g, int time) {
		
		// render everything that is a timeslot
		for (TimeSlot timeslot : timeslots) {
			
			if (timeslot.isTimeSlot(time)) {
				
				TimeSlot ttimeslot = null;
				// find its location and draw it
				Point getlocation = timeslot.getLocation(time,timeslot.getStart());
				ttimeslot = timeslot;
				
				if(ttimeslot.getAssembly().getAssemblyName().equalsIgnoreCase("idelivery")) {
					//get the last thing which is the main assembly 
					//we want to draw main assembly, not deliveries
					ttimeslot = timeslots.get(timeslots.size()-1);
				}
				imagedatabase.renderimage(ttimeslot.getAssembly().getAssemblyName(),getlocation, g);
				// g.drawRect(getlocation.x,getlocation.y,5,5);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
