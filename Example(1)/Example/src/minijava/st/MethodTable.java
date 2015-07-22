package minijava.st;

import java.util.Hashtable;
import java.util.Vector;

public class MethodTable {
	protected Hashtable<String, String> labels;
	protected Vector<String> list;
	
	public MethodTable() {
		labels = new Hashtable<String, String>();
		list = new Vector<String>();
	}
	public MethodTable(MethodTable methodTable) {
		labels = new Hashtable<String, String>(methodTable.getLabels());
		list = new Vector<String>(methodTable.getList());
	}
	public Hashtable<String, String> getLabels() {
		return labels;
	}
	public String getLabel(String name) {
		return labels.get(name);
	}
	public String getLabel(int index) {
		//System.out.println(list.elementAt(index));
		return labels.get(list.elementAt(index));
	}
	public int getOffset(String name) {
		for(int i = 0; i < list.size(); ++i) {
			if(list.elementAt(i).equals(name)) 
				return i;
		}
		return -1;
	}
	public Vector<String> getList() {
		return list;
	}
	public void addMethod(String name, String labelName) {
		//System.out.println("Addmethod: " + name + " " + labelName);
		if(labels.containsKey(labelName)) {
			labels.remove(name);
			labels.put(name, labelName);
		} else {
			labels.put(name, labelName);
			list.addElement(name);
		}
	}
	public int getMethodNumber() {
		return list.size();
	}
}
