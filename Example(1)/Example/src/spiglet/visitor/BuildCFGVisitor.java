package spiglet.visitor;

import java.util.Enumeration;

import spiglet.spiglet2kanga.*;
import spiglet.syntaxtree.*;

public class BuildCFGVisitor extends GJDepthFirst<MyType, MyType> {

   //
   // Auto class visitors--probably don't need to be overridden.
   //


   public MyType visit(NodeSequence n, MyType argu) {
      MyType _ret=null;
      int _count=0;
      // only happens in (Label?Stmt)* 
      // create node
      CFGNode node = new CFGNode((MethodCFG) argu);
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         MyType mytype = e.nextElement().accept(this,node);
         if(_count == 0) {
        	 if(mytype instanceof MyLabel) {
        		 //put label
        		 node.setLabel(((MyLabel) mytype).getName());
        	 }
         }
         _count++;
      }
      ((MethodCFG) argu).addNode(node);
      return _ret;
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
      MethodCFG mainMethod = new MethodCFG("main", 0);
      ((AllCFG) argu).addMethodGraph(mainMethod);
      n.f1.accept(this, mainMethod);
      mainMethod.update();
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
      MethodCFG method = new MethodCFG(n.f0.f0.toString(), Integer.parseInt(n.f2.f0.toString()));
      ((AllCFG) argu).addMethodGraph(method);
      n.f4.accept(this, method);
      method.update();
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
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public MyType visit(ErrorStmt n, MyType argu) {
      MyType _ret=null;
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
      ((CFGNode) argu).setJumpStyle("CJUMP");
      ((CFGNode) argu).addUse(Integer.parseInt(n.f1.f1.f0.toString()));
      ((CFGNode) argu).setJumpLabel(n.f2.f0.toString());
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public MyType visit(JumpStmt n, MyType argu) {
      MyType _ret=null;
      
      ((CFGNode) argu).setJumpStyle("JUMP");
      ((CFGNode) argu).setJumpLabel(n.f1.f0.toString());
      
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
      ((CFGNode) argu).addUse(Integer.parseInt(n.f1.f1.f0.toString()));
      ((CFGNode) argu).addUse(Integer.parseInt(n.f3.f1.f0.toString()));
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
      ((CFGNode) argu).addDef(Integer.parseInt(n.f1.f1.f0.toString()));
      ((CFGNode) argu).addUse(Integer.parseInt(n.f2.f1.f0.toString()));
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public MyType visit(MoveStmt n, MyType argu) {
      MyType _ret=null;
      ((CFGNode) argu).addDef(Integer.parseInt(n.f1.f1.f0.toString()));
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public MyType visit(PrintStmt n, MyType argu) {
      MyType _ret=null;
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Call()
    *       | HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public MyType visit(Exp n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
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
      CFGNode returnNode = new CFGNode((MethodCFG) argu);
      n.f3.accept(this, returnNode);
      ((MethodCFG) argu).addNode(returnNode);
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
      n.f1.accept(this, argu);
      n.f3.accept(this, argu);
      ((CFGNode) argu).getMethod().updateMaxCallParaNum(n.f3.size());
      return _ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public MyType visit(HAllocate n, MyType argu) {
      MyType _ret=null;
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Temp()
    * f2 -> SimpleExp()
    */
   public MyType visit(BinOp n, MyType argu) {
      MyType _ret=null;
      ((CFGNode) argu).addUse(Integer.parseInt(n.f1.f1.f0.toString()));
      n.f2.accept(this, argu);
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
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public MyType visit(SimpleExp n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public MyType visit(Temp n, MyType argu) {
      MyType _ret=null;
      ((CFGNode) argu).addUse(Integer.parseInt(n.f1.f0.toString()));
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public MyType visit(IntegerLiteral n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public MyType visit(Label n, MyType argu) {
      MyLabel _ret = new MyLabel(n.f0.toString());
      return _ret;
   }

}
