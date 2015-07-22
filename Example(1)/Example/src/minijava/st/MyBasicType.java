package minijava.st;

public class MyBasicType extends MyType {
	protected String typeName;
	public MyBasicType(String typeName) {
		this.typeName = typeName;
	}
	boolean sameType(MyBasicType type) {
		return this.typeName.equals(type.typeName);
	}
	public String getType() {
		return typeName;
	}
	public boolean isBasic() {
		if(typeName == "int") return true;
		if(typeName == "array") return true;
		if(typeName == "boolean") return true;
		return false;
	}
}
