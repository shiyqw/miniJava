package spiglet.spiglet2kanga;

import java.util.Hashtable;

public class AllCFG extends MyType {
	protected Hashtable<String, MethodCFG> methodGraphs;
	public AllCFG() {
		methodGraphs = new Hashtable<String, MethodCFG>();
	}
	public MethodCFG getMethodGraph(String name) {
		return methodGraphs.get(name);
	}
	public void addMethodGraph(MethodCFG methodGraph) {
		String methodName = methodGraph.getName();
		methodGraphs.put(methodName, methodGraph);
	}
}
