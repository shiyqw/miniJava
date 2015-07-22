package util;

public class PrintBoard {
	private static String code = "";
	public static void clear() {
		code = "";
	}
	public static void println(String s) {
		code += s;
		code += "\n";
	}
	public static void print(String s) {
		code += s;
	}
	public static String getCode() {
		return code;
	}
	public static void output() {
		System.out.print(code);
	}
}
