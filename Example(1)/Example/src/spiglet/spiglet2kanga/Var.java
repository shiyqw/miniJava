package spiglet.spiglet2kanga;

public class Var {
	protected int tempNum;
	protected int begin;
	protected int end;
	protected int color;
	protected int spillNum;
	public Var(int tempNum) {
		this.tempNum = tempNum;
		begin = 0x7FFFFFFF;
		end = -1;
		color = -1;
		spillNum = -1;
	}
	public int getNum() {
		return tempNum;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getBegin() {
		return begin;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getEnd() {
		return end;
	}
	public void setSpill(int spill) {
		this.spillNum = spill;
	}
	public int getSpill() {
		return spillNum;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public boolean hasColor() {
		return color!=-1;
	}
	public boolean isSpilled() {
		return spillNum!=-1;
	}
}
