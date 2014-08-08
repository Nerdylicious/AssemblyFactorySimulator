package scheduler;

import gui.BaseMachine;

import java.util.LinkedList;
import java.util.ListIterator;


/*
 * This class is a schedule.  It contains a linkedlist of timeslots.
 * 
 * 
 * 
 */
public class Schedule {
	LinkedList <TimeSlot>schedule;
	private BaseMachine machine; // just for reference for print outs
	/*
	public void unittest() {		
		System.out.println("Three in a row test");
		// try three in a row
		int scheduledtime = scheduleSlot(10,0,null,true);
		int scheduledendtime = scheduledtime+10;
		System.out.println("Start :" + scheduledtime + " end : " + scheduledendtime);
		
		scheduledtime = scheduleSlot(10,0,null,true);
		scheduledendtime = scheduledtime+10;
		System.out.println("Start :" + scheduledtime + " end : " + scheduledendtime);
		
		scheduledtime = scheduleSlot(10,0,null,true);
		scheduledendtime = scheduledtime+10;
		System.out.println("Start :" + scheduledtime + " end : " + scheduledendtime);
		
		schedule.clear();
		
		System.out.println("Gap test");		
		scheduledtime = scheduleSlot(10,15,null,true);
		scheduledendtime = scheduledtime+10;
		System.out.println("Start :" + scheduledtime + " end : " + scheduledendtime);
		
		scheduledtime = scheduleSlot(10,0,null,true);
		scheduledendtime = scheduledtime+10;
		System.out.println("Start :" + scheduledtime + " end : " + scheduledendtime);
		
		scheduledtime = scheduleSlot(10,0,null,true);
		scheduledendtime = scheduledtime+10;
		System.out.println("Start :" + scheduledtime + " end : " + scheduledendtime);
		
		schedule.clear();		
		System.out.println("second Gap test");				
		scheduledtime = scheduleSlot(10,0,null,true);
		scheduledendtime = scheduledtime+10;
		System.out.println("Start :" + scheduledtime + " end : " + scheduledendtime);
		
		scheduledtime = scheduleSlot(10,15,null,true);
		scheduledendtime = scheduledtime+10;
		System.out.println("Start :" + scheduledtime + " end : " + scheduledendtime);
		
		scheduledtime = scheduleSlot(10,0,null,true);
		scheduledendtime = scheduledtime+10;
		System.out.println("Start :" + scheduledtime + " end : " + scheduledendtime);
		
	}
	*/

	
	public Schedule(BaseMachine machine) {
		schedule = new LinkedList<TimeSlot>();
		this.machine = machine;
	}
	/*
	 * This method is able to place the new timeslot in the first instance even if it is in the middle
	 *   of the list 
	 * 
	 */
	 
	public int scheduleSlot(int requiredlength, int minstarttime, Order order, boolean addtimeslot, Assembly assembly) {
		// go up the list and look for the first free length of the correct size
	
		int minspacing = 1;
		int prevendtime = minstarttime+minspacing;		
		// using iterator so that it can be added in the proper spot in the list
		ListIterator <TimeSlot>listiterator = schedule.listIterator();
		
		boolean rollback = false;
		while (listiterator.hasNext()) {			
			TimeSlot slot = listiterator.next();
			int start = slot.getStart();
			int end = slot.getEnd();
			if (start - prevendtime > requiredlength) {
				rollback = true;
				break;	
			}
			else {
				if (end >= prevendtime)
					prevendtime = end+minspacing;
			}			
		}
		// we need to insert to the previous not to the next so this call is required
		if (rollback)
			listiterator.previous();
		if (addtimeslot)
			listiterator.add(new TimeSlot(prevendtime,prevendtime+requiredlength,order,machine,assembly));		
		return prevendtime;
	}
	
	
	
	public String toString() {
		String treturn = "";
		for (TimeSlot tslot : schedule) {
			treturn += tslot + "\n";
		}		
		return treturn;		
	}

}
