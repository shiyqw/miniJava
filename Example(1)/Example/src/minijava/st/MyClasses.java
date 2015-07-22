package minijava.st;

import java.util.Hashtable;

import minijava.typecheck.PrintError;

public class MyClasses extends MyType {
	protected Hashtable<String, MyClass> classes;
	protected Hashtable<String, String> relations; // relations of inheritance
	public MyClasses() {
		classes = new Hashtable<String, MyClass>();
		relations = new Hashtable<String, String>();
	}
	public boolean insertClass(String className, MyClass obj) {
		//System.out.println(className);
		if(classes.containsKey(className)) {
			return false;
		} else {
			//System.out.println("over");
			obj.setName(className);
			classes.put(className, obj);
		}
		return true;
	}
	public MyClass getMyClass(String className) {
		return classes.get(className);
	}
	public boolean checkLoop() {
		for(String className : relations.keySet()) {
			String temp = relations.get(className);
			while(!temp.equals(className) && !temp.equals("null")) {
				temp = relations.get(temp);
			}
			if(temp.equals(className)) {
				String errorString = "class inheritence loop : begin from class " + className;
				PrintError.print(classes.get(classes).getLine(), errorString);
			}
		}
		return false;
	}
	public void checkType() {
		for(String className : classes.keySet()) {
			classes.get(className).checkType(classes);
		}
	}
	public boolean checkOverride() {
		for(String className : relations.keySet()) {
			if(!relations.get(className).equals("null")) {
				classes.get(className).checkOverride(classes.get(relations.get(className)));
			}
		}
		return false;
	}
	public void update() {
		for(String className : classes.keySet()) {
			if(!classes.containsKey(classes.get(className).getParent()) && 
					!classes.get(className).getParent().equals("null")) {
				String errorString = "type not found : " + classes.get(className).getParent();
				PrintError.print(classes.get(className).getLine(), errorString);
				continue;
			}
			relations.put(className, classes.get(className).getParent());
			transClasses(classes.get(className));
			classes.get(className).transClasses();
		}
		for(String className : classes.keySet()) {
			classes.get(className).fillTables();
		}
	}
	public boolean transClasses(MyClass obj) {
		for(String className : classes.keySet()) {
			//System.out.println(className);
			//System.out.println(classes.get(className));
			obj.addClass(className, classes.get(className));
		}
		return true;
	}
}