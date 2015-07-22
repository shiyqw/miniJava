package minijava.st;

import java.util.Hashtable;

import minijava.typecheck.PrintError;

// class table informations
public class MyClass extends MyType {
	protected String className; // name
	protected Hashtable<String, MyClass> classes; // global classes information
	protected String parentName; // parent name, if not have, equals "null"
	protected Hashtable<String, MyBasicType> fields; // fields
	protected Hashtable<String, MyMethod> methods; // methods
	protected MethodTable methodTable; // method labels
	protected VarTable fieldTable; 
	
	public MyClass(int lineNumber) {
		super(lineNumber);
		fields = new Hashtable<String, MyBasicType>();
		methods = new Hashtable<String, MyMethod>();
		parentName = new String("null");
		className = new String("null");
		classes = new Hashtable<String, MyClass>();
		methodTable = new MethodTable();
		fieldTable = new VarTable();
	}
	public boolean insertMethod(String methodName, MyMethod method) {
		if(methods.containsKey(methodName)) {
			return false;
		} else {
			method.setName(className);
			methods.put(methodName, method);
		}
		return true;
	}
	public boolean transField(MyMethod method) {
		for(String fieldName : fields.keySet()) {
			method.insertVar(fieldName, fields.get(fieldName));
		}
		return true;
	}
	public MyMethod getMethod(String methodName) {
		if(methods.containsKey(methodName)) return methods.get(methodName);
		return (classes.get(parentName).getMethod(methodName));
	}
	public boolean insertField(String fieldName, MyBasicType fieldType) {
		if(fields.containsKey(fieldName)) {
			return false;
		} else {
			fields.put(fieldName, fieldType);
		}
		return true;
	}
	public MyBasicType getField(String fieldName) {
		if(fields.containsKey(fieldName)) return fields.get(fieldName);
		return (classes.get(parentName).getField(fieldName));
	}
	public boolean hasField(String fieldName) {
		if(fields.containsKey(fieldName)) return true;
		if(parentName.equals("null")) return false;
		return(classes.get(parentName).hasField(fieldName));
	}
	public String getParent() {
		return parentName;
	}
	public boolean setParentName(String parentName) {
		this.parentName = parentName;
		return true;
	}
	public boolean checkType(Hashtable<String, MyClass> classes) {
		for(String fieldName : fields.keySet()) {
			if(!classes.containsKey(fields.get(fieldName).getType()) && !fields.get(fieldName).isBasic()) {
				String errorString = "type not found : " + fields.get(fieldName).getType();
				PrintError.print(fields.get(fieldName).getLine(), errorString);
			}
		}
		for(String methodName : methods.keySet()) {
			//Syster.out.println("checking method type " + methodName);
			if(methodName.equals("main")) continue;
			methods.get(methodName).checkType(classes);
		}
		return true;
	}
	public boolean hasMethod(String methodName) {
		//System.out.println("co here");
		if(methods.containsKey(methodName)) return true;
		if(parentName.equals("null")) return false;
		//System.out.println(parentName);
		return(classes.get(parentName).hasMethod(methodName));
	}
	public boolean checkOverride(MyClass obj) {
		//System.out.println("co here");
		for(String methodName : methods.keySet()) {
			//System.out.println("co here");
			if(!obj.hasMethod(methodName)) continue;
			//System.out.println("co here");
			methods.get(methodName).checkOverride(obj.getMethod(methodName));
		}
		//System.out.println("co there");
		return true;
	}
	public boolean hasClass(String className) {
		return classes.containsKey(className);
	}
	public boolean addClass(String className, MyClass obj) {
		//System.out.println("addc mc");
		classes.put(className, obj);
		//System.out.println(obj.getName());
		return true;
	}
	public MyClass getMyClass(String className) {
		return classes.get(className);
	}
	public boolean transClasses() {
		for(String methodName : methods.keySet()) {
			for(String className : classes.keySet()) {
				methods.get(methodName).addClass(className, classes.get(className));
			}
		}
		return true;
	}
	public String getName() {
		return className;
	}
	public boolean setName(String className) {
		this.className = className;
		return true;
	}
	
	public MethodTable getMethodTable() {
		return methodTable;
	}
	public VarTable getFieldTable() {
		return fieldTable;
	}
	
	public void fillTables() {
		if(parentName.equals("null")) {
			fieldTable = new VarTable();
			methodTable = new MethodTable();
		} else {
			MyClass parentClass = classes.get(parentName);
			parentClass.fillTables();
			methodTable = new MethodTable(parentClass.getMethodTable());
			fieldTable = new VarTable(parentClass.getFieldTable());
		}
		for(String methodName : methods.keySet()) {
			methodTable.addMethod(methodName, className+"_"+methodName);
		}
		for(String fieldName : fields.keySet()) {
			fieldTable.addVar(fieldName);
		}
	}
 }

