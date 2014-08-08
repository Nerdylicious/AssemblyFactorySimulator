package scheduler;

import gui.BaseMachine;
import gui.ConveyerObject;

import java.util.ArrayList;
import java.util.LinkedList;

/*
 * CLASS: TreeNode
 * 
 * PURPOSE: This class holds a tree structure for each product object.
 * 			The root of the tree is the idelivery part, the second level
 * 			is the main assembly part. The children of the main assembly 
 * 			part are the subassemblies stored in a linked list.
 * 			The tree has 2 levels below the root. Each node also has a list of
 * 			machines that are capable of making the assembly part.
 * 			These machines are added in generatePaths() in the Product class.
 * 
 * 
 */
class TreeNode {
	private Assembly assembly;
	private LinkedList <BaseMachine>machines;		
	private LinkedList <TreeNode>children;
	private TreeNode parent;
	
	private ConveyerObject linkingbelt;
	private int ourmachineendtime;

	public TreeNode(Assembly assembly) {
		this.assembly = assembly;
		machines = new LinkedList<BaseMachine>();
		children = new LinkedList<TreeNode>();	
		
		parent = null;

	}
	public void addChildren(TreeNode treenode) {
		children.add(treenode);			
		treenode.parent = this;
	}
	public void addMachines(BaseMachine machine) {
		machines.add(machine);
	}
	public String getMachineType() {
		return assembly.getMachineType();
	}
	public String toString() {
		String treturn = assembly.toString() + " ";
		//String treturn = "";
		for (BaseMachine machine : machines) {
			treturn += machine + "";
		}
		treturn += "\n";
		for (TreeNode treenode : children) {
			treturn += treenode.toString();
		}
		return treturn;
	}

	
	// this is called by the parent, use the linkingtime with the
	//known time to add
	// a TimeSlot for the conveyerbelt
	public void linkConveyerBelt(int parentstarttime, Order order) {
		
		new TimeSlot(ourmachineendtime,parentstarttime,order,linkingbelt,assembly);
	}
	
	
	// place an order on the earliest available machine and return
	//the end time of that order.
	public int placeOrder(int mintime, Order order, boolean placeorder) {
		
		int assemblytime = assembly.getAssemblyTime();
		int conveyertime = 0;
		ConveyerObject conveyerobject = null;
		BaseMachine earliestmachine = null;
		int earliesttime = 99999;
		
		for (BaseMachine machine : machines) {
			int tearliesttime = machine.scheduleSlot(assemblytime,mintime,order,false,assembly);
			if (tearliesttime < earliesttime) {
				earliesttime = tearliesttime;
				earliestmachine = machine;					
			}				
		}
		
		if (parent != null) {
			// get the conveyer belt
			conveyerobject = parent.getMachine().getConveyer(earliestmachine);
			conveyertime = (int)conveyerobject.getTimeDistance();
		}
		
		// now schedule it in and return the earliest completion
		//time.
		int linkingtime = earliestmachine.scheduleSlot(assemblytime,mintime,order,placeorder,assembly);
		this.linkingbelt = conveyerobject;
		//endtime of machine
		this.ourmachineendtime = linkingtime+assemblytime;
		
		// this
		//is the end time of the machine
		// or the start time of the conveyer
		if (placeorder == true) {
			// we need to add the order for the conveyer from the
			//parent to the child.
			for (TreeNode treenode : children) {
				treenode.linkConveyerBelt(linkingtime,order);
			}
		}
		return linkingtime+conveyertime+assemblytime;
	}
	
	
	
	// does a depth postorder traversal.
	public int placeOrderR(Order order, boolean isSchedule) {
		int mintime = 0;
		if (children.size() == 0) {
			return placeOrder(mintime,order,isSchedule);
		}
		else {
			// first call all the children then place order
			int lowesttime = 0;
			for (TreeNode treenode : children) {
				int tlowesttime = treenode.placeOrderR(order, isSchedule);
				if (tlowesttime > lowesttime) {
					lowesttime = tlowesttime;
				}					

			}
			return placeOrder(lowesttime, order, isSchedule);
		}				
	}
	
	
	// clone a single nodes assembly type, and any linked machines.
	//Children are not cloned
	public TreeNode clone() {
		// only clone the machine links not the children
		TreeNode treturn = new TreeNode(this.assembly);
		
		for (BaseMachine machine: machines) {
			treturn.addMachines(machine);
		}
		return treturn;
	}
	public BaseMachine getMachine() {
		return machines.getFirst();
	}
	
	// clone the current treenode and launch a recursive cloning process
	public TreeNode cloneTree() {
		
		TreeNode treturn = clone();
		cloneTreeR(treturn);
		
	return treturn;
	}
	// for each child of the node, create a new treenode by cloning the
	//child, then
	// add the child to return, then call this method recursively for
	//each child
	public void cloneTreeR(TreeNode copy) {
		// make a copy of the node
		for (TreeNode child: children) {
			TreeNode child1 = child.clone();
			copy.addChildren(child1);
			child.cloneTreeR(child1);
		}
	}
	// do a preorder traversal to place tree into array
	public void traverseintoarray(ArrayList<TreeNode> arraylist) {
		// TODO Auto-generated method stub
		arraylist.add(this);
		for (TreeNode child : children) {
			child.traverseintoarray(arraylist);
		}
	}
	public TreeNode getParent() {
		return parent;
	}
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
