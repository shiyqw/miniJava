package minijava.minijava2piglet;

import minijava.st.MyMethod;

public class Distributor {
	int tempCount;
	static int labelCount;
	
	public Distributor(MyMethod method) {
		tempCount = method.getVarTable().getVarNumber()+1;
	}
	public Distributor() {
		tempCount = 0;
	}
	public String newTemp() {
		//System.out.println("here");
		String ret = new String("TEMP " + tempCount);
		tempCount++; 
		//System.out.println(ret);
		return ret;
	}
	public String newLabel() {
		String ret = new String("LABEL"+labelCount);
		++labelCount;
		return ret;
	}
}
