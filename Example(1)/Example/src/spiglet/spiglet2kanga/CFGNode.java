package spiglet.spiglet2kanga;

import java.util.HashSet;
import java.util.Vector;

public class CFGNode extends MyType {
	protected HashSet<Integer> def;
	protected HashSet<Integer> use;
	protected HashSet<Integer> in;
	protected HashSet<Integer> out;
	protected Vector<CFGNode> pre;
	protected Vector<CFGNode> suc;
	protected String label;
	protected String jumpLabel;
	protected String jumpStyle;
	protected MethodCFG method;
	
	public CFGNode(MethodCFG method) {
		def = new HashSet<Integer>();
		use = new HashSet<Integer>();
		in = new HashSet<Integer>();
		out = new HashSet<Integer>();
		pre = new Vector<CFGNode>();
		suc = new Vector<CFGNode>();
		label = new String("null");
		jumpLabel = new String("null");
		jumpStyle = new String("NOJUMP");
		this.method = method;
	}
	public MethodCFG getMethod() {
		return method;
	}
	public void setMethod(MethodCFG method) {
		this.method = method;
	}
	public void setJumpStyle(String style) {
		this.jumpStyle = style;
	}
	public String getJumpStyle() {
		return jumpStyle;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public void setJumpLabel(String label) {
		this.jumpLabel = label;
	}
	public String getJumpLabel() {
		return jumpLabel;
	}
	public void addPre(CFGNode node) {
		pre.addElement(node);
	}
	public void addSuc(CFGNode node) {
		suc.addElement(node);
	}
	public void addUse(int num) {
		use.add(num);
	}
	public void addDef(int num) {
		def.add(num);
	}
	public void addLive(int num) {
		in.add(num);
	}
	public void removeLive(int num) {
		in.remove(num);
	}
	public HashSet<Integer> getUse() {
		return use;
	}
	public HashSet<Integer> getDef() {
		return def;
	}
	public Vector<CFGNode> getPre() {
		return pre;
	}
	public Vector<CFGNode> getSuc() {
		return suc;
	}
	public HashSet<Integer> getIn() {
		return in;
	}
	public HashSet<Integer> getOut() {
		return out;
	}
	public void update() {
		for(CFGNode node : suc) {
			for(int num : node.in) {
				out.add(num);
			}
		}
	}
}
