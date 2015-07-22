package spiglet.spiglet2kanga;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

public class MethodCFG extends MyType{
	protected String name;
	protected int paraNum;
	protected Vector<CFGNode> nodeList;
	protected Hashtable<String, Integer> labelIndex;
	protected Hashtable<Integer, Var> varList;
	public static int MAX_COLOR = 7;
	protected Vector<Boolean> colorList;
	protected Vector<Boolean> usedList;
	protected int maxCallParaNum;
	protected int spillNum;
	
	public MethodCFG(String name, int paraNum) {
		this.name = name;
		this.paraNum = paraNum;
		nodeList = new Vector<CFGNode>();
		varList = new Hashtable<Integer, Var>();
		labelIndex = new Hashtable<String, Integer>();
		colorList = new Vector<Boolean>();
		colorList.setSize(MAX_COLOR);
		for(int i = 0; i < MAX_COLOR; ++i) {
			colorList.setElementAt(false, i);
		}
		usedList = new Vector<Boolean>();
		usedList.setSize(MAX_COLOR);
		for(int i = 0; i < MAX_COLOR; ++i) {
			usedList.setElementAt(false, i);
		}
		if(paraNum > 4) {
			spillNum = paraNum - 4;
		} else {
			spillNum = 0;
		}
		spillNum += MAX_COLOR;
		maxCallParaNum = 0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParaNum() {
		return paraNum;
	}
	public int getSpillNum() {
		return spillNum;
	}
	public int getMaxCallParaNum() {
		return maxCallParaNum;
	}
	public void updateMaxCallParaNum(int paraNum) {
		if(paraNum > maxCallParaNum) maxCallParaNum = paraNum;
	}
	public void addNode(CFGNode node) {
		nodeList.addElement(node);
		if(node.getLabel() != null) {
			labelIndex.put(node.getLabel(), nodeList.size()-1);
		}
	}
	public CFGNode getNode(String labelName) {
		return nodeList.elementAt(labelIndex.get(labelName));
	}
	public CFGNode getNodeAt(int i) {
		return nodeList.elementAt(i);
	}
	public CFGNode getFirstNode() {
		return nodeList.elementAt(0);
	}
	public CFGNode getLastNode() {
		return nodeList.elementAt(nodeList.size()-1);
	}
	public int getUsedColorNum() {
		int ret = 0;
		for(int i = 0; i < MAX_COLOR; ++i) {
			if(usedList.elementAt(i) == true) ++ret;
		}
		return ret;
	}
	public boolean checkColor(int color) {
		return usedList.elementAt(color);
	}
	public Var getVar(int tempNum) {
		return varList.get(tempNum);
	}
	
	public void update() {
		// TODO Auto-generated method stub
		connectAll();
		livenessAnalysis();
		linearScan();
	}
	private void connectAll() {
		for(int i = 0; i < nodeList.size(); ++i) {
			CFGNode node = nodeList.elementAt(i);
			if(!node.getJumpStyle().equals("NOJUMP")) {
				//System.out.println(node.getJumpLabel());
				CFGNode target = getNode(node.getJumpLabel());
				target.addPre(node);
				node.addSuc(target);
			}
			if(!node.getJumpStyle().equals("JUMP") && i < nodeList.size()-1) {
				CFGNode next = nodeList.elementAt(i+1);
				node.addSuc(next);
				next.addPre(node);
			}
		}
	}
	public void livenessAnalysis() {
		CFGNode lastNode = nodeList.elementAt(nodeList.size()-1);
		for(int tempNum : lastNode.getUse()) {
			varList.put(tempNum, new Var(tempNum));
			lastNode.getIn().add(tempNum);
		}
		
		boolean flag = false;
		while(!flag) {
			flag = true;
			for(int i = nodeList.size()-1; i >= 0; --i) {
				CFGNode node = nodeList.elementAt(i);
				HashSet<Integer> origin = new HashSet<Integer>(node.getIn());
				for(CFGNode sucNode : node.getSuc()) {
					for(int tempNum : sucNode.getIn()) {
						varList.put(tempNum, new Var(tempNum));
						node.addLive(tempNum);
					}
				}
				for(int tempNum : node.getDef()) {
					node.removeLive(tempNum);
				}
				for(int tempNum : node.getUse()) {
					node.addLive(tempNum);
					varList.put(tempNum, new Var(tempNum));
				}
				if(!node.getIn().equals(origin)) {
					flag = false;
				}
			}
		}
		for(CFGNode node : nodeList) {
			node.update();
		}
	}
	public void linearScan() {
		for(int i = 0; i < nodeList.size(); ++i) {
			CFGNode node = nodeList.elementAt(i);
			for(int tempNum : node.getIn()) {
				Var var = varList.get(tempNum);
				if(i < var.getBegin()) {
					var.setBegin(i);
				}
				if(i > var.getEnd()) {
					var.setEnd(i);
				}
			}
		}
		
		HashSet<Var> liveList = new HashSet<Var>();
		for(int i = 0; i < nodeList.size(); ++i) {
			CFGNode node = nodeList.elementAt(i);
			for(int tempNum : node.getIn()) {
				Var var = varList.get(tempNum);
				liveList.add(var);
			}
			HashSet<Var> removeList = new HashSet<Var>();
			for(Var var : liveList) {
				if(var.getEnd() < i) {
					int color = var.getColor();
					if(!var.isSpilled()) colorList.setElementAt(false, color);
					removeList.add(var);
				}
			}
			for(Var var : removeList) {
				liveList.remove(var);
			}
			for(Var var : liveList) {
				if(!var.hasColor() && !var.isSpilled()) {
					int availableColor = -1;
					for(int j = 0; j < MAX_COLOR; ++j) {
						if(colorList.elementAt(j) == false) {
							availableColor = j;
						}
					}
					if(availableColor == -1) {
						int maxEnd = -1;
						Var spillVar = new Var(-1);
						for(Var tempVar : liveList) {
							if(!tempVar.isSpilled() && tempVar.hasColor() && tempVar.getEnd() > maxEnd) {
								maxEnd = tempVar.getEnd();
								spillVar = tempVar;
							}
						}
						var.setColor(spillVar.getColor());
						spillVar.setSpill(spillNum++);
					} else {
						colorList.setElementAt(true, availableColor);
						var.setColor(availableColor);
						usedList.setElementAt(true, availableColor);
					}
				}
			}
		}
	}
}
