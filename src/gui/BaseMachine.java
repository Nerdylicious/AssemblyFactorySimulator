package gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import scheduler.Assembly;
import scheduler.Order;
import scheduler.Schedule;

/*
 * Common code to all Machines.  Note that this is abstract so it can not be created
 *   as a stand alone class.
 * 
 */
public abstract class BaseMachine implements BaseObject{
	// name and type of the machine
	private String name;
	private String type;

	// location and size for the rectangle.
	private Point location;
	private Point size;
	
	private LinkedList <Link> links;

	// used for the enum
	private Iterator <Link> linkIterator;
	private String enumMachineType;
	
	private Schedule schedule;
		
	public BaseMachine(String name) {
		this.name = name;
		setLocation(new Point(0,0));
		this.links = new LinkedList <Link> ();
		this.schedule = new Schedule(this);
	}
	
	public int scheduleSlot(int requiredlength, int minstarttime, Order order, boolean addtimeslot, Assembly assembly) {
		return schedule.scheduleSlot(requiredlength, minstarttime, order, addtimeslot, assembly);
	}
	
	/*
	 * CLASS: Link
	 * 
	 * PURPOSE: The conveyer object and machine that this basemachine
	 * 			connects to
	 * 
	 */
	public class Link{
		
		private BaseMachine machine;
		private ConveyerObject conveyer;
		
		public Link(BaseMachine machine, ConveyerObject conveyer){
			this.machine = machine;
			this.conveyer = conveyer;
			
		}
		
		public BaseMachine getMachine(){
			return machine;
		}
		
		public ConveyerObject getConveyer(){
			
			return conveyer;
		}

	}
	
	/*
	 * PURPOSE: Adds the conveyer and machine this machine is connected to  
	 *          
	 */
	public void addLink(BaseMachine machine, ConveyerObject conveyer){
		
		Link myLink = new Link(machine, conveyer);
		links.add(myLink);
		
	}
	
	/*
	 * PURPOSE: Get the conveyer that this connected to machine and
	 * 			this machine  
	 *          
	 */
	public ConveyerObject getConveyer(BaseMachine machine){
		
		BaseMachine tempmachine = null;
		ConveyerObject tempconveyer = null;
		
		for(Link link : links){

			tempmachine = link.getMachine();
			if(tempmachine.equals(machine)){
				
				tempconveyer = link.getConveyer();
			}
			
			
		}
		return tempconveyer;
		
	}
	
	/*
	 * PURPOSE: Get the location of the order to be drawn
	 *          
	 */
	public Point getLocation(int currenttime, int timeslotstarttime){
		
		Point newLocation = (Point) location.clone();
		
		//draw with an offset
		newLocation.x = newLocation.x + 48;
		newLocation.y = newLocation.y + 20;
		
		return newLocation;
		
		
	}
	
	public void startLinkEnum(String machinetype) {
		
		// first set the iterator
		linkIterator = links.iterator();
		enumMachineType = machinetype;
	}
	
	//returns machine in list that matches type
	public BaseMachine enumLinkedMachines() {
		
		while (linkIterator.hasNext()) {
			
			Link link = linkIterator.next();
			BaseMachine machine = link.machine;
			
			if (machine.getMachineType().contentEquals(enumMachineType)) {
				return machine;
			}
		}
		return null;
	}
		
		
	
	public String getMachineType(){
		
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Point getSize() {
		return size;
	}
	
	protected void setSize(Point size) {
		this.size = size;
	}
	
	public String getName() {
		return name;
	}	
	
	public void setLocation(Point location) {
		this. location = location;
	}
	
	public Point getLocation() {
		return location;
	}	
	
	public Rectangle getRectangle() {
		Rectangle collrect = new Rectangle(location.x,location.y,size.x,size.y);	
		return collrect;
	}
	public boolean isCollision(BaseObject object) {
		Rectangle collrect1 = object.getRectangle();
		Rectangle collrect2 = getRectangle();		
		return collrect1.intersects(collrect2);	
	}

	public String saveObject() {
		return type + " " + name + " " + location.x + " " + location.y;	
	}
	
	public void loadObject(String object) {
		 StringTokenizer st = new StringTokenizer(object);
		 type = st.nextToken();
		 name = st.nextToken();
		 location.x = Integer.parseInt(st.nextToken());
		 location.y = Integer.parseInt(st.nextToken());	
	}
	
	public String toString(){
		
		return "Name: " + name + " " + type + "\n" + schedule.toString() + "\n";
	}
	
}
