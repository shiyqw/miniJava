package spiglet.visitor;


import java.util.Enumeration;

import spiglet.spiglet2kanga.*;
import spiglet.syntaxtree.*;
import util.PrintBoard;

public class Sp2KVisitor extends GJDepthFirst<MyType, MyType> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
	
	
   private String[] regs = {"s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7"};
   private String turn = new String("v0");
   
   private void changeTurn() {
	   if(turn.equals("v0")) turn = new String("v1");
	   else turn = new String("v0");
   }
   
   public MyType visit(NodeOptional n, MyType argu) {
      if ( n.present() ) {
    	 String methodName = ((CFGNode) argu).getMethod().getName();
    	 String labelName = ((Label) n.node).f0.toString();
    	 PrintBoard.println(methodName + "_" + labelName);
         return n.node.accept(this,argu);
      }
      else
         return null;
   }

   public MyType visit(NodeListOptional n, MyType argu) {
      if ( n.present() ) {
         MyType _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
        	if(argu instanceof MethodCFG) {
        		e.nextElement().accept(this, ((MethodCFG) argu).getNodeAt(_count));
        	} else {
	            e.nextElement().accept(this,argu);
        	}
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public MyType visit(NodeToken n, MyType argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> "MAIN"
    * f1 -> StmtList()
    * f2 -> "END"
    * f3 -> ( Procedure() )*
    * f4 -> <EOF>
    */
   public MyType visit(Goal n, MyType argu) {
      MyType _ret=null;
      
      PrintBoard.println("MAIN");
      MethodCFG mainMethod = ((AllCFG) argu).getMethodGraph("main");
      PrintBoard.println("[" + mainMethod.getParaNum() + "]"
    		  + " [" + mainMethod.getSpillNum() + "]"
    		  + " [" + mainMethod.getMaxCallParaNum() + "]");
      
      for(int i = 0; i < MethodCFG.MAX_COLOR; ++i) {
    	  PrintBoard.println("ASTORE SPILLEDARG " + i + " " + regs[i]);
      }
      
      n.f1.accept(this, mainMethod);
      
      for(int i = MethodCFG.MAX_COLOR-1; i >= 0; --i) {
    	  PrintBoard.println("ALOAD " + regs[i] + " SPILLEDARG " + i);
      }
      
      PrintBoard.println("END");
      
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public MyType visit(StmtList n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public MyType visit(Procedure n, MyType argu) {
      MyType _ret=null;
      String methodName = n.f0.f0.toString();
      PrintBoard.println(methodName);
      MethodCFG method = ((AllCFG) argu).getMethodGraph(methodName);
      int paraNum = method.getParaNum();
      PrintBoard.println("[" + paraNum + "]" 
    		  + " [" + method.getSpillNum() + "]"
    		  + " [" + method.getMaxCallParaNum() + "]");
      
      int offset = 0;
      if(paraNum > 4) {
    	  offset = paraNum-4;
      } else {
    	  offset = 0;
      }
      
      for(int i = 0; i < MethodCFG.MAX_COLOR; ++i) {
    	  PrintBoard.println("ASTORE SPILLEDARG " + (i+offset) + " " + regs[i]);
      }
      for(int i = 0; i < paraNum && i < 4; ++i) {
    	  Var var = method.getVar(i);
    	  if(var != null) {
    		  if(method.getFirstNode().getIn().contains(var.getNum())) {
    			  if(!var.isSpilled()) {
    				  PrintBoard.println("MOVE " + regs[var.getColor()] + " a" + i);
    			  } else {
    				  //PrintBoard.println("SPILLNUM IS " + var.getSpill());
    				  PrintBoard.println("ASTORE SPILLEDARG " + var.getSpill() + " a" + i);
    			  }
    		  }
    	  }
      }
      
      if(paraNum > 4) {
    	  for(int i = 4; i < paraNum; ++i) {
    		  Var var = method.getVar(i);
	    	  if(var != null) {
	    		  if(method.getFirstNode().getIn().contains(var.getNum())) {
	    			  if(!var.isSpilled()) {
	    				  PrintBoard.println("ALOAD " + regs[method.getVar(i).getColor()]
	    						  + " SPILLEDARG " + (i-4));
	    			  } else {
	    				  PrintBoard.println("ALOAD v0 SPILLEDARG " + (i-4));
	    				  PrintBoard.println("ASTORE SPILLEDARG " + var.getSpill() + " v0");
	    			  }
	    		  }
	    	  }
    	  }
      }
      
      
      n.f4.accept(this, method);
      
      for(int i = MethodCFG.MAX_COLOR-1; i >= 0; --i) {
    	  PrintBoard.println("ALOAD " + regs[i] + " SPILLEDARG " + (i+offset));
      }
      PrintBoard.println("END");
      return _ret;
   }

   /**
    * f0 -> NoOpStmt()
    *       | ErrorStmt()
    *       | CJumpStmt()
    *       | JumpStmt()
    *       | HStoreStmt()
    *       | HLoadStmt()
    *       | MoveStmt()
    *       | PrintStmt()
    */
   public MyType visit(Stmt n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public MyType visit(NoOpStmt n, MyType argu) {
      MyType _ret=null;
      PrintBoard.println("NOOP");
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public MyType visit(ErrorStmt n, MyType argu) {
      MyType _ret=null;
      PrintBoard.println("ERROR");
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Temp()
    * f2 -> Label()
    */
   public MyType visit(CJumpStmt n, MyType argu) {
      MyType _ret=null;
      MyLabel condition = (MyLabel) n.f1.accept(this, argu);
      String methodName = ((CFGNode) argu).getMethod().getName();
      String labelName = methodName + "_" + n.f2.f0.toString();
      PrintBoard.println("CJUMP " + condition.getName() + " " + labelName);
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public MyType visit(JumpStmt n, MyType argu) {
      MyType _ret=null;
      
      String methodName = ((CFGNode) argu).getMethod().getName();
      String labelName = methodName + "_" + n.f1.f0.toString();
      
      PrintBoard.println("JUMP " + labelName);
      
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Temp()
    * f2 -> IntegerLiteral()
    * f3 -> Temp()
    */
   public MyType visit(HStoreStmt n, MyType argu) {
      MyType _ret=null;
      MyType temp1 = n.f1.accept(this, argu);
      MyType il = n.f2.accept(this, argu);
      MyType temp2 = n.f3.accept(this, argu);
      PrintBoard.println("HSTORE " + ((MyLabel) temp1).getName() + " "
    		  + ((MyLabel) il).getName() + " "
    		  + ((MyLabel) temp2).getName());
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Temp()
    * f3 -> IntegerLiteral()
    */
   public MyType visit(HLoadStmt n, MyType argu) {
      MyType _ret=null;
      int tempNum = Integer.parseInt(n.f1.f1.f0.toString());
      if(!((CFGNode) argu).getOut().contains(tempNum)) {
    	  if(!((CFGNode) argu).getLabel().equals("null")) {
    		  PrintBoard.println("NOOP");
    	  }
    	  return _ret;
      }
      MyLabel temp2 = (MyLabel) n.f2.accept(this, argu);
      MyLabel il= (MyLabel) n.f3.accept(this, argu);
      MyLabel temp1 = (MyLabel) n.f1.accept(this, argu);
      PrintBoard.println("HLOAD " + temp1.getName() + " "
    		  + temp2.getName() + " " + il.getName());
      
      Var var = ((CFGNode) argu).getMethod().getVar(tempNum);
      if(var.isSpilled()) {
    	  PrintBoard.println("ASTORE SPILLEDARG " + var.getSpill()
    			  + " " + temp1.getName());
      }
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public MyType visit(MoveStmt n, MyType argu) {
      MyType _ret=null;
      int tempNum = Integer.parseInt(n.f1.f1.f0.toString());
      
      if(!((CFGNode) argu).getOut().contains(tempNum)) {
    	  if(n.f2.f0.which == 0) {
    		  n.f2.accept(this, argu);
    	  } else if(!((CFGNode) argu).getLabel().equals("null")) {
    		  PrintBoard.println("NOOP");
    	  }
    	  return _ret;
      }
      
      Var var = ((CFGNode) argu).getMethod().getVar(tempNum);
      MyLabel exp = (MyLabel) n.f2.accept(this, argu);
      if(var.isSpilled()) {
    	  PrintBoard.println("MOVE t0 " + exp.getName());
    	  PrintBoard.println("ASTORE SPILLEDARG " + var.getSpill() + " t0");
      } else {
    	  PrintBoard.println("MOVE " + regs[var.getColor()] + " " + exp.getName());
      }
      
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public MyType visit(PrintStmt n, MyType argu) {
      MyType _ret=null;
      MyLabel exp = (MyLabel) n.f1.accept(this, argu);
      PrintBoard.println("PRINT " + exp.getName());
      return _ret;
   }

   /**
    * f0 -> Call()
    *       | HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public MyType visit(Exp n, MyType argu) {
      MyType _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> SimpleExp()
    * f4 -> "END"
    */
   public MyType visit(StmtExp n, MyType argu) {
      MyType _ret=null;
      n.f1.accept(this, argu);
      MyLabel exp = (MyLabel)n.f3.accept(this, ((MethodCFG) argu).getLastNode());
      PrintBoard.println("MOVE v0 " + exp.getName());
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> SimpleExp()
    * f2 -> "("
    * f3 -> ( Temp() )*
    * f4 -> ")"
    */
   public MyType visit(Call n, MyType argu) {
      MyType _ret=null;
      int paraNum = n.f3.size();
      for(int i = 0; i < paraNum && i < 4; ++i) {
    	  MyLabel arg = (MyLabel) ((Temp) n.f3.nodes.elementAt(i)).accept(this, argu);
    	  PrintBoard.println("MOVE a" + i + " " + arg.getName());
      }
      if(paraNum > 4) {
    	  for(int i = 4; i < paraNum; ++i) {
    		  MyLabel arg = (MyLabel) ((Temp) n.f3.nodes.elementAt(i)).accept(this, argu);
    		  PrintBoard.println("PASSARG " + (i-3) + " " + arg.getName());
    	  }
      }
      MyLabel exp = (MyLabel) n.f1.accept(this, argu);
      PrintBoard.println("CALL " + exp.getName());
      _ret = new MyLabel("v0");
      
      
      return _ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public MyType visit(HAllocate n, MyType argu) {
      MyType _ret=null;
      MyLabel exp = (MyLabel)n.f1.accept(this, argu);
      _ret = new MyLabel("HALLOCATE " + exp.getName());
      return _ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Temp()
    * f2 -> SimpleExp()
    */
   public MyType visit(BinOp n, MyType argu) {
      MyType _ret=null;
      MyLabel op = (MyLabel) n.f0.accept(this, argu);
      MyLabel temp = (MyLabel) n.f1.accept(this, argu);
      MyLabel exp = (MyLabel) n.f2.accept(this, argu);
      _ret = new MyLabel(op.getName() + " " + temp.getName() + " " 
    		  + exp.getName());
      return _ret;
   }

   /**
    * f0 -> "LT"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    */
   public MyType visit(Operator n, MyType argu) {
      MyType _ret=null;
      switch(n.f0.which) {
      case 0: _ret = new MyLabel("LT");break;
      case 1: _ret = new MyLabel("PLUS");break;
      case 2: _ret = new MyLabel("MINUS");break;
      case 3: _ret = new MyLabel("TIMES");break;
      }
      return _ret;
   }

   /**
    * f0 -> Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public MyType visit(SimpleExp n, MyType argu) {
      MyType _ret=null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public MyType visit(Temp n, MyType argu) {
      MyType _ret=null;
      int tempNum = Integer.parseInt(n.f1.f0.toString());
      Var var = ((CFGNode) argu).getMethod().getVar(tempNum);
      if(var.isSpilled()) {
    	  PrintBoard.println("ALOAD " + turn + " SPILLEDARG " + var.getSpill());
    	  _ret = new MyLabel(turn);
    	  changeTurn();
      } else {
    	  _ret = new MyLabel(regs[var.getColor()]);
      }
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public MyType visit(IntegerLiteral n, MyType argu) {
      MyType _ret=null;
      _ret = new MyLabel(n.f0.toString());
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public MyType visit(Label n, MyType argu) {
      MyType _ret=null;
      _ret = new MyLabel(n.f0.toString());
      return _ret;
   }

	
}
