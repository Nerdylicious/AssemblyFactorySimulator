package scheduler;

import gui.BaseMachine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*
 * The list of machines.
 * 
 * It loads them from a file
 * 
 * It also has an enumeration that enumerates machines of a given type.
 * 
 * 
 */

public class MachineList {
	private LinkedList <BaseMachine>machines;
	
	// used for the enum
	Iterator machineIterator;
	String enumMachineType;
	

	public MachineList() {
		machines = new LinkedList<BaseMachine>();			
	}	
	
	public void StartEnum(String machinetype)	{
		// first set the iterator		
		machineIterator = machines.iterator();
		enumMachineType = machinetype;				
	}
	
	public BaseMachine enumMachines() {
		while (machineIterator.hasNext()) {
			BaseMachine machine = (BaseMachine)machineIterator.next();
			if (machine.getMachineType().contentEquals(enumMachineType)) {
				return machine;
			}
		}		
		return null;		
	}	
	
	public void StartIterate(){
		
		machineIterator = machines.iterator();
	
	}
	
	/*
	 * PURPOSE: This is for iterating the whole list
	 */
	public BaseMachine iterateMachines(){
		
		if(machineIterator.hasNext()){
			BaseMachine machine = (BaseMachine)machineIterator.next();
			return machine;
			
		}
		return null;
	}
	
	
	
	public void addMachine(BaseMachine machine){
		machines.add(machine);
	}
	
	/*
	private void loadMachineList(String filename) {	
		try {
			FileReader in = new FileReader(filename);    		
			BufferedReader br = new BufferedReader(in);			
			String linein;		
			while((linein = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(linein);
				String typename = st.nextToken();
				int numberof = Integer.parseInt(st.nextToken());
				for (int ix = 0; ix < numberof; ix++) {
					int number = ix+1;
					String machinename = typename + " " + number;
					machines.add(new Machine(machinename,typename));				
				}							
			}
		} catch (Exception  e) {
			System.out.println(e);			 
		}				
	}
	
	public void loadMachineList() {
		loadMachineList("machines.txt");
	}	
	*/
	public String toString() {
		String treturn = "";
		for (BaseMachine machine : machines) {
			treturn += machine + "\n";
		}
			
		treturn += "\nAssemblies:\n";	
	
		return treturn;		
	}
}
