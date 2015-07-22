package minijava.minijava2piglet;

import java.util.Vector;

public class Printer {
	public static Vector<String> code;
	public static void print() {
		for(String codeLine : code) {
			System.out.println(codeLine);
		}
	}
	public static void addLine(String codeLine) {
		code.addElement(codeLine);
	}
}
