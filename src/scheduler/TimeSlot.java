package scheduler;

import java.awt.Point;

import gui.BaseMachine;
import gui.BaseObject;

/*
 * 
 * This class contains the start and end times of a timeslot.
 * 
 * It also has a reference to Order since the toString requires it.
 * 
 */
public class TimeSlot {
	private int start;
	private int end;
	private Order order;
	private BaseObject machine;
	private Assembly assembly;
	
	public TimeSlot(int start, int end, Order order, BaseObject machine, Assembly assembly) {
		this.start = start;
		this.end = end;
		this.order = order;
		this.machine = machine;
		this.assembly = assembly;
		if (order != null)// just schedule unit test uses a null
			order.addTimeSlot(this);  
	}
	
	public String getAssemblyName(){
		
		return assembly.getAssemblyName();
	}
	
	public Assembly getAssembly() {

		return assembly;
	}
	
	public int length() {
		return end-start;
	}
	
	public int getStart() {
		return start;		
	}
	
	public int getEnd() {
		return end;
	}
	
	public BaseObject getMachine() {
		return machine;
	}
	
	// notice it does not call order.tostring() since it may cause a stack error.
	public String toString() {
		return order.getInfo() + " start time: " + start + " end time:" + " " + end;	
	}

	public boolean isTimeSlot(int time) {
		
		return time > start && time < end;
	}

	public Point getLocation(int time, int start) {
		// TODO Auto-generated method stub
		Point location = machine.getLocation(time, start);
		
		return location;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
