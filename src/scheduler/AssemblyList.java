package scheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*
 * Load a list of assemblies from a file.
 * 
 * In addition to the accessors and loading it has a method to 
 *   get an assembly given a name.
 * 
 * 
 */

public class AssemblyList {
	
	private LinkedList <Assembly>assemblyList;
	
	public AssemblyList() {
		assemblyList = new LinkedList<Assembly>();
	}
	
	public void loadAssemblyList() {
		String filename = "assembly.txt";
		try {
			FileReader in = new FileReader(filename);    		
			BufferedReader br = new BufferedReader(in);			
			String linein;		
			while((linein = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(linein);
				String typename = st.nextToken();
				int numberof = Integer.parseInt(st.nextToken());
				for (int ix = 0; ix < numberof; ix++) {
					linein = br.readLine();
					st = new StringTokenizer(linein);
					String assemblyname = st.nextToken();
					int assemblytime = Integer.parseInt(st.nextToken());
					Assembly assembly = new Assembly(
							typename,assemblyname,assemblytime);
					assemblyList.add(assembly);							
				}							
			}
		} catch (Exception  e) {
			System.out.println(e);			 
		}				
	}
	public String toString() {
		String treturn = "";
		for (Assembly assembly : assemblyList) {
			treturn += assembly + "\n";
		}
		return treturn;		
	}
	public Assembly getAssembly(String name) {
		for (Assembly assembly : assemblyList) {
			if (assembly.isEquals(name)) {
				return assembly;
			}
		}
		return null;
	}
}
