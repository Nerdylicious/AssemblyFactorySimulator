package scheduler;

import gui.BaseMachine;

import java.io.BufferedReader;
import java.util.LinkedList;


/*
 * This class stores a product name and contains the scheduling tree
 * 
 * The scheduling tree does most of the work for this assignment
 * 
 * It would be correct (and maybe more so) to place the treenode in a separate file,
 *   since it does not need any protected members.
 * 
 */
public class Product {
	
	private String name;	
	private LinkedList <TreeNode>productionpaths;

	// the root of the product tree.
	private TreeNode productroot;
	
		
	/* we need to send the machine list and assembly list so we have proper access to load the product tree.
	 * 
	 * 
	 * There are several possible ways to structure the loading in of hte tree 
	 */
	
	/*
	 * PURPOSE: Create the product using a tree structure.
	 * 			The root of the tree is the idelivery part (productroot),
	 * 			the second level is the main assembly part (highchild). 
	 * 			The children of the main assembly part are the subassemblies 
	 * 			(lowchild) and make up the third level.
	 * 
	 */
	public Product(String name, int numassemblies, BufferedReader br,MachineList ml, AssemblyList as) {
		
		this.productionpaths = new LinkedList <TreeNode> ();
		
		this.name = name;	
		try {
			
			// we know the first one is the main assembly so load it in
			String assemblyname = br.readLine();		
			
			productroot = new TreeNode(as.getAssembly("idelivery"));
			String machinetype = productroot.getMachineType();

			
			TreeNode highchild = new TreeNode(as.getAssembly(assemblyname));
			productroot.addChildren(highchild);
			
			// load in the children nodes.	
			for (int ix = 1; ix < numassemblies; ix++) {
				String subassemblytype = br.readLine();						
				TreeNode lowchild = new TreeNode(as.getAssembly(subassemblytype));
				highchild.addChildren(lowchild);				
			}		
		} 
		catch (Exception e) {
			
			System.out.println(e);
		}		
		
		generatePaths(ml);
	}


	
	public Order placeOrder(String ordername) {
		
		Order order = new Order(ordername,this);
		// k use the tree to place the order
		

		int tempfinish;
		int earliest = 9999999;
		
		TreeNode temptree = null;

		//check every production path to get it's earliest time
		//choose the best production path
		for(TreeNode tree : productionpaths){

			tempfinish = tree.placeOrderR(order, false);

			if(tempfinish < earliest){
				earliest = tempfinish;
				temptree = tree;
			}
			
		}
		if(temptree != null){

			temptree.placeOrderR(order, true);

		}
		
		
		
		return order;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean MatchName(String name) {
		 return this.name.contentEquals(name);		
	}	
	
	/*	
	public String toString() {
		return name + "\n" + productroot;
	}
	*/
	
	public String toString() {
		
		String buffer = name + "\n" + productroot;
		buffer += "\n" + productionpaths.size() + " production paths\n";
		for (TreeNode treenode: productionpaths) {
			buffer += "-------------------------\n" + treenode + "\n";
		}
		return buffer;
	}
	
	/*
	 * PURPOSE: Generates a tree for each possible path. Starting with
	 * 			DeliveryMachine, find all machines that are connected 
	 * 			to it, then put these machines in each assembly parts
	 * 			machinelist, keep doing this again and again until
	 * 			all paths are made.
	 * 
	 */
	public void generatePaths(MachineList machinelist){
		

		machinelist.StartEnum("DeliveryMachine");
		BaseMachine basemachine = machinelist.enumMachines();
		LinkedList <LinkingTree> currentlist = new LinkedList <LinkingTree> ();
		
		
		//make a copy of root and put delivery machine to it
		while(basemachine != null){
			LinkingTree linktree = new LinkingTree(productroot);
			linktree.addMachinetoCurrentIndex(basemachine);
			linktree.incrementindex();
			currentlist.add(linktree);
			
			basemachine = machinelist.enumMachines();
			
		}
		
		LinkedList <LinkingTree> nextlist = new LinkedList <LinkingTree> ();
		//when newtreeclone.isComplete then we are done
		//currentlist will have nothing
		while(currentlist.size() > 0){

			for(LinkingTree lt : currentlist){
				
				TreeNode current = lt.getCurrentNode();
				TreeNode parent = lt.getCurrentParent();
				
				BaseMachine parentmachine = parent.getMachine();
				String machinetype = current.getMachineType();
				
				parentmachine.startLinkEnum(machinetype);
				BaseMachine enummachine = parentmachine.enumLinkedMachines();
				
				while(enummachine != null){
					
					LinkingTree newtreeclone = lt.cloneTree();
					newtreeclone.addMachinetoCurrentIndex(enummachine);
					newtreeclone.incrementindex();
	
					if(newtreeclone.isComplete() == true){
						
						TreeNode newproduct = newtreeclone.getRootNode();
						productionpaths.add(newproduct);
					}
					else{
						
						
						nextlist.add(newtreeclone);
					}
					
					
					enummachine = parentmachine.enumLinkedMachines();
				}
			}

			//if done currentlist will be size == 0
			//have the currentlist reference the 'next' current list
			currentlist = nextlist;
			nextlist = new LinkedList <LinkingTree> ();
			
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
