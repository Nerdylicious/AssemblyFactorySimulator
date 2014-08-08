package gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

import scheduler.AssemblyList;
import scheduler.MachineList;
import scheduler.Order;
import scheduler.ProductList;

/*
 * Stores all the data for the editor, the current controller too.
 * 
 */
public class Model {
	// all objects are stored in here
	private LinkedList <BaseObject>objectList;
	private LinkedList <ConveyerObject>conveyerlist;
		private LinkedList <Order>orderlist;
	
	//machinelist2 contains all machines in gui
	private MachineList machinelist;
	private AssemblyList assemblylist;
	private ProductList productlist;

	
	// stores the current controller, null if there is not one.
	private BaseController controller;
	private int currenttime;
	
	public Model() {
		
		objectList = new LinkedList<BaseObject>();
		conveyerlist = new LinkedList<ConveyerObject>();
		
		machinelist = new MachineList();
		
		assemblylist = new AssemblyList();
		assemblylist.loadAssemblyList();
		
		loadFactory();
		linkMachines();

		productlist = new ProductList(machinelist, assemblylist);
		
		System.out.println("\n\nproductlist:\n\n" + productlist);
		
		orderlist = new LinkedList<Order>();
		runFactory();
		System.out.println(this);
		controller = null;
	}
	
	
	public void incCurrTime() {
		currenttime++;
	}
	
	public void addObject(BaseObject object) {
		objectList.add(object);
	}
	
	public void onMouseMove(MouseEvent evt) {
		if (controller != null) {
			controller.onMouseMove(evt);		
		}		
	}
	
	public void mouseReleased(MouseEvent evt) {
		if (controller != null) {
			if (controller.onMouseClick(evt))
				controller = null;
		}
	}
	
	public void onDeleteObject() {
		if (controller == null) {
			controller = new DeleteCntrl(this);
		}
	}
	
	public void onCancelController() {
		if (controller != null) {
			controller.onCancel();
			controller = null;
		}
	}
	
	public void removeFromList(BaseObject object) {
		objectList.remove(object);
	}
	
	public void onNewMachine(String type) {
		if (controller == null) {
			controller = new NewMachineCntrl(type,this);
		}
	}
	
	public void onNewConveyer() {
		if (controller == null) {
			controller = new NewConveyerCntrl(this);
		}
	}
	
	public void render(Graphics g) {
		for (BaseObject baseobject : objectList) {
			baseobject.drawObject(g);
		}
		
		Grid.render(g);
		
		if (controller != null) {
			g.drawString(controller.getType(), 10, 300);
		}
		
		for(Order order : orderlist){
			order.render(g, currenttime);
		}
		
	}
	
	// for the collision detection that ignores the conveyer objects
	public boolean isCollision(BaseObject colobject) {	
		for (BaseObject object : objectList) {
			if (object != colobject) {	
				if (object.getClass() != ConveyerObject.class) {
					if (object.isCollision(colobject)) {	
						return true;
					}
				}
			}			
		}	
		return false;
	}	
	
	// for the delete which checks for all objects
	public boolean removeIfCollision(BaseObject colobject) {
		for (BaseObject object : objectList) {
			if (object != colobject) {				
				if (object.isCollision(colobject)) {	
					objectList.remove(object);
					return true;
				}
				
			}		
		}			
		return false;
	}
	
	// called by the menu to create a dialog box to load the factory.
	public void loadFactory() {
		/*
    	final JFileChooser fc = new JFileChooser();
    	
    	fc.setDialogType(JFileChooser.OPEN_DIALOG);
    	//In response to a button click:
    	int returnVal = fc.showOpenDialog(this);
    	
    	if (returnVal == JFileChooser.APPROVE_OPTION)  {
    		 File file = fc.getSelectedFile();
    	*/
		 String file = "factory.txt";
		 try {
    		 FileReader in = new FileReader (file);    		
    		 BufferedReader br = new BufferedReader(in);
    		 loadObjects(br);    		   		 
    		 in.close();
		 } catch(Exception e) {
			 System.err.println(e);
		 }    		 
    	//}
    	// repaint();
	}
	
	// this could use Class.newInstance I suppose instead of having multiple if statements.
	 public void loadObjects(BufferedReader br) {		 
		 objectList.clear();
		 String object;
		 BaseObject newobject = null;
		 try {
	  	 while((object = br.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(object);
			String type = tokenizer.nextToken();
			if (type.equals("Conveyer")){
				newobject = new ConveyerObject();	
				conveyerlist.add((ConveyerObject) newobject);
				
			}
			else if (type.equals("BatteryMachine")){
				newobject = new BatteryMachine("bt1");	
				machinelist.addMachine((BaseMachine) newobject);
			}
			else if (type.equals("PCBMachine")){
				newobject = new PCBMachine("bt1");	
				machinelist.addMachine((BaseMachine) newobject);
			}
			else if (type.equals("ScreenMachine")){
				newobject = new ScreenMachine("bt1");
				machinelist.addMachine((BaseMachine) newobject);
			}
			else if (type.equals("AssemblyMachine")){
				newobject = new AssemblyMachine("bt1");
				machinelist.addMachine((BaseMachine) newobject);
			}
			else if (type.equals("DeliveryMachine")){
				newobject = new DeliveryMachine("bt1");	
				machinelist.addMachine((BaseMachine) newobject);
			}
		
			newobject.loadObject(object);
			objectList.add(newobject);
		}
		} catch (Exception e) {
			System.err.println(e);
		}
		 
	 }
	 
	public String getSaveString () {
		String treturn = "";
		for (BaseObject object : objectList) {
			treturn += object.saveObject() + "\n";
		}
		return treturn;		
	}
	
	
	/*
	 * PURPOSE: Runs through all conveyer belts and looks for 
	 *  		BaseMachines that are collided with it. These
	 *  		are added to the links list in BaseMachine.
	 *          
	 */
	public void linkMachines(){
		
		boolean gotFirst = false;
		BaseMachine firstMachine = null;
		BaseMachine secMachine = null;
		
		for(ConveyerObject conveyerobject : conveyerlist){
			machinelist.StartIterate();
			BaseMachine machine = machinelist.iterateMachines();
			while(machine != null){

				//calls isCollision in ConveyerObject class
				//checks if machine collides with conveyer
				if(conveyerobject.isCollision(machine)){
					
					if(gotFirst == false){
						firstMachine = machine;
						gotFirst = true;
					}
					else{
						
						secMachine = machine;
					}
					
				}
				machine = machinelist.iterateMachines();
			}
			gotFirst = false;
			if(firstMachine != null && secMachine != null){
				
				//for each BaseMachine, add the machine and conveyer
				//that connects to it
				firstMachine.addLink(secMachine, conveyerobject);
				secMachine.addLink(firstMachine, conveyerobject);
				System.out.println(firstMachine.getName() + " " +
						secMachine.getName());

				
			}

			firstMachine = null;
			secMachine = null;
			
		}
		
		
		
		
	}
	
	/*
	 * First load the productlist, then
	 * load the file and process each order
	 */
	public void runFactory() {		

		int ordernumber = 1;
		// k load the orders
		String filename = "orders.txt";
		try {
			FileReader in = new FileReader(filename);    		
			BufferedReader br = new BufferedReader(in);			
			String linein;		
			while((linein = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(linein);
				String ordertype = st.nextToken();			
				String ordername = "order #" + ordernumber;
				Order order = productlist.placeOrder(ordertype,ordername);
				orderlist.add(order);
				ordernumber++;
			}
		} catch (Exception  e) {
			System.out.println(e);			 
		}			
	}
	
	
	public String toString() {
		String treturn1 = machinelist.toString();
		String treturn2 = "Orders \n";
		for (Order order : orderlist) {
			treturn2 += order + "\n";
		}
		return treturn1 + "\n" + treturn2;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
