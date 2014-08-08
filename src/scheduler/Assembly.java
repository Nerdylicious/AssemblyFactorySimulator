package scheduler;
/*
 * 
 * This class contains an assembly.  Keep in mine a product may contains 
 *   many assemblies, some being the final assembly, some being sub assemblies.
 * 
 *  This class mainly contains data and accessors.
 */
public class Assembly {
	private String machinetype;
	private String assemblyname;
	private int assemblytime;
	
	
	public Assembly(String machinetype, String assemblyname, int assemblytime) {
		this.machinetype = machinetype;
		this.assemblytime = assemblytime;
		this.assemblyname = assemblyname;
	}
	public String toString() {
		return machinetype + " " + assemblyname + " " + assemblytime;
	}
	
	public boolean isEquals(String name) {
		if (name.contentEquals(assemblyname)) {
			return true;
		}
		return false;
	}
	
	public int getAssemblyTime() {
		return assemblytime;
	}
	
	public String getMachineType() {
		return machinetype;
	}
	
	public String getAssemblyName() {
		return assemblyname;
	}
}
