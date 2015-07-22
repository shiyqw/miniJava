/**
 * 所有声明的类的列表
 */
package minijava.symboltable;

import java.util.Vector;

public class MClasses extends MType {
	public Vector<MClass> mj_classes = new Vector<MClass>(); // 用于存放类

	// 在表中插入类
	public String InsertClass(MClass v_class) { 
		String class_name = v_class.getName();
		if (Repeated(class_name)) // 如已经定义过该类，返回错误信息
			return "Class double declaration " + "\"" + class_name + "\"";
		mj_classes.addElement(v_class);
		return null;
	}

	// 判定是否定义同名的类
	public boolean Repeated(String class_name) {
		int sz = mj_classes.size();
		for (int i = 0; i < sz; i++) {
			String c_name = ((MClass) mj_classes.elementAt(i)).getName();
			if (c_name.equals(class_name))
				return true;
		}
		return false;
	}
}

