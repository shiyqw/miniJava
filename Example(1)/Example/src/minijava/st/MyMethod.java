package minijava.st;

import java.util.Hashtable;
import java.util.Vector;

import minijava.typecheck.PrintError;

// method table
public class MyMethod extends MyType {
	protected Hashtable<String, MyBasicType> vars; // local variables
	protected ParameterList parameters; // parameter list
	protected Hashtable<String, MyClass> classes; // global classes information
	protected MyBasicType returnType; // return type
	protected String className; // inner class name
	protected Vector<String> varInit; // initialized variables
	protected VarTable varTable;
	
	public MyMethod(int lineNumber) {
		super(lineNumber);
		this.parameters = new ParameterList(lineNumber);
		vars = new Hashtable<String, MyBasicType>();
		returnType = new MyBasicType("void");
		className = new String("null");
		classes = new Hashtable<String, MyClass>();
		varInit = new Vector<String>();
		varTable = new VarTable();
	}
	public boolean insertVar(String varName, MyBasicType varType) {
		if(vars.containsKey(varName)) {
			return false;
		} else {
			//System.out.println(varName);
			vars.put(varName, varType);
			varTable.addVar(varName);
		}
		return true;
	}
	public MyBasicType getVar(String varName) {
		if(vars.containsKey(varName)) return vars.get(varName);
		if(classes.get(className).hasField(varName)) return classes.get(className).getField(varName);
		return (classes.get(classes.get(className).getParent()).getField(varName));
	}
	public ParameterList getParas() {
		return parameters;
	}
	public boolean setParas(ParameterList paras) {
		parameters = paras;
		return true;
	}
	public boolean addPara(MyBasicType paraType) {
		parameters.insertPara(paraType);
		return true;
	}
	public MyBasicType getRet() {
		return returnType;
	}
	public boolean setRet(MyBasicType retType) {
		returnType = retType;
		return true;
	}
	public boolean checkType(Hashtable<String, MyClass> classes) {
		//System.out.println(returnType.getType());
		if(!classes.containsKey(returnType.getType()) && !returnType.isBasic()) {
			String errorString = "type not found : " + returnType.getType();
			PrintError.print(line, errorString);
		}
		//System.out.println("inner method checktype");
		for(String varName : vars.keySet()) {
			if(!classes.containsKey(vars.get(varName).getType()) && !vars.get(varName).isBasic()) {
				String errorString = "type not found : " + vars.get(varName).getType();
				PrintError.print(vars.get(varName).getLine(), errorString);
			}
		}
		//System.out.println("outer method checktype");
		return true;
	}
	public boolean checkOverride(MyMethod method) {
		if(!parameters.isSameAs(method.getParas()) || 
				!returnType.getType().equals(method.getRet().getType())) {
			String errorString = "override method";
			PrintError.print(line, errorString);
		}
		return true;
	}
	public boolean hasClass(String className) {
		return classes.containsKey(className);
	}
	public boolean addClass(String className, MyClass obj) {
		classes.put(className, obj);
		return true;
	}
	public MyClass getMyClass(String className) {
		return classes.get(className);
	}
	public boolean hasVar(String varName) {
		if(vars.containsKey(varName)) return true;
		if(classes.get(className).hasField(varName)) return true;
		if(classes.get(className).getParent().equals("null")) return false;
		return (classes.get(classes.get(className).getParent()).hasField(varName));
	}
	public boolean hasLocal(String varName) {
		return (vars.containsKey(varName));
	}
	public boolean subClass(MyBasicType t1, MyBasicType t2) {
		String className = t1.getType();
		while(!className.equals(t2.getType()) && !className.equals("null")) {
			className = classes.get(className).getParent();
		}
		if(className.equals(t2.getType())) return true;
		return false;
	}
	public boolean typeMatch(MyBasicType t1, MyBasicType t2) {
		//System.out.println(t1.getType());
		//System.out.println(t2.getType());
		if(t1.sameType(t2)) return true;
		if(t1.getType().equals("void") || t2.getType().equals("void")) return true;
		if(!(t1 instanceof MyClassType) || !(t2 instanceof MyClassType)) return false;
		if(!(subClass(t1, t2))) return false;
		return true;
	}
	public String getName() {
		return className;
	}
	public boolean setName(String className) {
		this.className = className;
		return true;
	}
	public boolean isInit(String varName) {
		if(!vars.containsKey(varName)) return true;
		return varInit.contains(varName);
	}
	public void insertInit(String varName) {
		varInit.addElement(varName);
	}
	public int getParaNum() {
		return parameters.getNum();
	}
	
	public VarTable getVarTable() {
		return varTable;
	}
	
	public boolean matchType(ParameterList paras) {
		if(parameters.getSize() != paras.getTypes().size()) return false;
		for(int index = 0; index != parameters.getSize(); ++index) {
			if(!typeMatch(parameters.getTypeAt(index), (paras.getTypes().get(index))) 
					&& !typeMatch((paras.getTypes().get(index)), parameters.getTypeAt(index))) {
				return false;
			}
		}
		return true;
		
	}
}
