/*The parent type of all the type in symbol table*/

package minijava.st;

public class MyType {
//	protected String name; // name
	protected int line = 0; // line

	public MyType() {
	};

	public MyType(int line) {
		this.line = line;
	}
//	public String getName() {
//		return name;
//	}

	public int getLine() {
		return line;
	}

//	public void setName(String name) {
//		this.name = name;
//	}

	public void setLine(int line) {
		this.line = line;
	}
}
