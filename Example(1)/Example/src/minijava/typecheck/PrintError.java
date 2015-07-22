/**
 * 存放错误信息并统一打印
 */
package minijava.typecheck;

import java.util.Vector;

public class PrintError {
	private static Vector<String> errors = new Vector<String>();

	public static void print(int line, String errorString) {
		String msg = "Line " + line + ": " + errorString;
		errors.addElement(msg); 
	}

	public static boolean hasError() {
		return !errors.isEmpty();
	}
	
	public static void printAll() {
		int sz = errors.size();
		for (int i = 0; i < sz; i++) {
			System.out.println(errors.elementAt(i));
		}
	}
}

