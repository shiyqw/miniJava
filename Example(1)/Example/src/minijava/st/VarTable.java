package minijava.st;

import java.util.Hashtable;
import java.util.Vector;

public class VarTable {
	protected Vector<String> list;
	protected Hashtable<String, Integer> offsets;
	
	public VarTable() {
		list = new Vector<String>();
		offsets = new Hashtable<String, Integer>();
	}
	public VarTable(VarTable fieldTable) {
		list = new Vector<String>(fieldTable.getList());
		offsets = new Hashtable<String, Integer>(fieldTable.getOffsets());
	}
	public Vector<String> getList() {
		return list;
	}
	public Hashtable<String, Integer> getOffsets() {
		return offsets;
	}
	public int getOffset(String name) {
		//System.out.println(name);
		return offsets.get(name);
	}
	public int getVarNumber() {
		return list.size();
	}
	public void addVar(String name) {
		//System.out.println("addvar:" + name);
		list.addElement(name);
		if(offsets.containsKey(name)) {
			offsets.remove(name);
		}
		offsets.put(name, list.size()-1);
	}
}
