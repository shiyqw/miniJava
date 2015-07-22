package spiglet.spiglet2kanga;

public class MyLabel extends MyType {
	String name;
	public MyLabel() {
		name = new String("");
	}
	public MyLabel(String name) {
		this.name = new String(name);
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
