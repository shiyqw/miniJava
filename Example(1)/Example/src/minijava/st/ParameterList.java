package minijava.st;

import java.util.Vector;

public class ParameterList extends MyType {
	public ParameterList(int lineNumber) {
		super(lineNumber);
		paraTypes = new Vector<MyBasicType>();
	}
	public ParameterList() {
		paraTypes = new Vector<MyBasicType>();
	}
	Vector<MyBasicType> paraTypes;
	public void insertPara(MyBasicType paraType) {
		paraTypes.addElement(paraType);
	}
	public void addHead(MyBasicType headType) {
		paraTypes.insertElementAt(headType, 0);
	}
	public MyBasicType getTypeAt(int index) {
		return paraTypes.elementAt(index);
	}
	Vector<MyBasicType> getTypes() {
		return paraTypes;
	}
	public int getSize() {
		return paraTypes.size();
	}
	public boolean isSameAs(ParameterList paras) {
	//	for(MyBasicType type : paraTypes) {
	//		System.out.println(type.getType());
	//	}
	//	for(MyBasicType type : paras.getTypes()) {
	//		System.out.println(type.getType());
	//	}
		if(paraTypes.size() != paras.getTypes().size()) return false;
		for(int index = 0; index != paraTypes.size(); ++index) {
			if(!paraTypes.get(index).sameType(paras.getTypes().get(index))) return false;
		}
		return true;
	}
	public int getNum() {
		return paraTypes.size();
	}
}
