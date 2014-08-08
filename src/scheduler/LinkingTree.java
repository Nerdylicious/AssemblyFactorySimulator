package scheduler;

import gui.BaseMachine;

import java.util.ArrayList;

/*
 * CLASS: LinkingTree
 * 
 * PURPOSE: This class converts a tree into an array so that 
 * 			the tree can be indexed.
 *
 * 			It also has the capability of making copies.
 * 
 */
public class LinkingTree {
	
	// the rootnode
	private TreeNode rootnode;
	// the array, which has the same nodes as the tree
	private ArrayList<TreeNode> arraylist;
	// the current traversal index
	private int currentindex;
	
	public LinkingTree() {
		rootnode = null;
		currentindex = 0;
	}
	
	public LinkingTree(TreeNode rootnode) {
		this.rootnode = rootnode.cloneTree();
		toarraylist();
		currentindex = 0;
	}
	
	// if we are done the traversal this returns true
	public boolean isComplete() {
		
		if (currentindex == arraylist.size()) {
			return true;
		}
		return false;
	}
	// copies it to an array
	public void toarraylist() {
		
		arraylist = new ArrayList<TreeNode>();
		rootnode.traverseintoarray(arraylist);
	}
	
	// get the parent of the current node
	public TreeNode getCurrentParent() {
		TreeNode temp = arraylist.get(currentindex);
		return temp.getParent();
	}
	
	public TreeNode getCurrentNode() {
		return arraylist.get(currentindex);
	}
	
	
	// create a copy, INCLUDING the current index so the traversal
	//lines up
	public LinkingTree cloneTree() {
		LinkingTree treturn = new LinkingTree(rootnode);
		treturn.currentindex = currentindex;
		return treturn;
	}
	
	// add a machine to the current index
	public void addMachinetoCurrentIndex(BaseMachine machine) {
		
		arraylist.get(currentindex).addMachines(machine);
		
	}
	
	// increment
	public void incrementindex() {
		
		currentindex++;
		
	}
	
	// this copies out the tree to the production list
	public TreeNode getRootNode() {
		
		return rootnode;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
