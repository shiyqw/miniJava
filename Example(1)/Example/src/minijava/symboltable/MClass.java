/**
 * 该类用于表示声明的类
 */
package minijava.symboltable;

import java.util.Vector;

import minijava.typecheck.PrintError;

public class MClass extends MType {
	public MClasses all_classes; // 所有类的列表
	public boolean isDeclared = false; // 是否已声明，用于检查符号表

	public MClass(String v_name, MClasses all, int m_line, int m_column) {
		super(m_line, m_column);
		name = v_name;
		all_classes = all;
	}
}

