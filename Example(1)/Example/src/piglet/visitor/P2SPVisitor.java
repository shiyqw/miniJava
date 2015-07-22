package piglet.visitor;


import java.util.Enumeration;

import piglet.syntaxtree.*;
import util.PrintBoard;

public class P2SPVisitor extends GJNoArguDepthFirst<String> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //

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
   public String visit(Goal n) {
      String _ret=null;
      PrintBoard.println("MAIN");
      n.f1.accept(this);
      PrintBoard.println("END");
      n.f3.accept(this);
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public String visit(StmtList n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public String visit(Procedure n) {
      String _ret=null;
      PrintBoard.print(n.f0.f0.toString());
      PrintBoard.print(" [");
      PrintBoard.print(n.f2.f0.toString());
      PrintBoard.println("]");
      PrintBoard.println("BEGIN");
      String returnTemp = n.f4.accept(this);
      PrintBoard.println("RETURN " + returnTemp);
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
   public String visit(Stmt n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public String visit(NoOpStmt n) {
      String _ret=null;
      PrintBoard.println("NOOP");
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public String visit(ErrorStmt n) {
      String _ret=null;
      PrintBoard.println("ERROR");
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Exp()
    * f2 -> Label()
    */
   public String visit(CJumpStmt n) {
      String _ret=null;
      String expTemp = n.f1.accept(this);
      String temp = Distributor.newTemp();
      PrintBoard.println("MOVE " + temp + " " + expTemp);
      PrintBoard.print("CJUMP " + temp + " ");
      n.f2.accept(this);
      PrintBoard.println("");
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public String visit(JumpStmt n) {
      String _ret=null;
      PrintBoard.println("JUMP " + n.f1.f0.toString());
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Exp()
    * f2 -> IntegerLiteral()
    * f3 -> Exp()
    */
   public String visit(HStoreStmt n) {
      String _ret=null;
      String dataTemp = n.f1.accept(this);
      if(n.f1.f0.which != 4) {
    	  String temp = Distributor.newTemp();
    	  PrintBoard.println("MOVE " + temp + " " + dataTemp);
    	  dataTemp = temp;
      }
      String offset = n.f2.f0.toString();
      String temp = Distributor.newTemp();
      if(n.f3.f0.which == 6) {
    	  PrintBoard.println("MOVE " + temp + " " + ((Label) n.f3.f0.choice).f0.toString());
      } else {
    	  String addressTemp = n.f3.accept(this);
    	  PrintBoard.println("MOVE " + temp + " " + addressTemp);
      }
      PrintBoard.println("HSTORE " + dataTemp + " " + offset + " " + temp);
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Exp()
    * f3 -> IntegerLiteral()
    */
   public String visit(HLoadStmt n) {
      String _ret=null;
      String tempTemp = n.f1.accept(this);
      String expTemp = n.f2.accept(this);
      if(n.f2.f0.which != 4) {
    	  String temp = Distributor.newTemp();
    	  PrintBoard.println("MOVE " + temp + " " + expTemp);
    	  expTemp = temp;
      }
      String offset = n.f3.f0.toString();
      PrintBoard.println("HLOAD " + tempTemp + " " + expTemp + " " + offset);
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public String visit(MoveStmt n) {
      String _ret=null;
      String tempTemp = n.f1.accept(this);
      String expTemp = n.f2.accept(this);
      PrintBoard.println("MOVE " + tempTemp + " " + expTemp);
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> Exp()
    */
   public String visit(PrintStmt n) {
      String _ret=null;
      String expTemp = n.f1.accept(this);
      if(n.f1.f0.which < 4) {
    	  String temp = Distributor.newTemp();
    	  PrintBoard.println("MOVE " + temp + " " + expTemp);
    	  expTemp = temp;
      }
      PrintBoard.println("PRINT " + expTemp);
      return _ret;
   }

   /**
    * f0 -> StmtExp()
    *       | Call()
    *       | HAllocate()
    *       | BinOp()
    *       | Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public String visit(Exp n) {
      return n.f0.accept(this);
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> Exp()
    * f4 -> "END"
    */
   public String visit(StmtExp n) {
      String _ret=null;
      n.f1.accept(this);
      _ret = n.f3.accept(this);
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> Exp()
    * f2 -> "("
    * f3 -> ( Exp() )*
    * f4 -> ")"
    */
   public String visit(Call n) {
      String objTemp = n.f1.accept(this);
      String arguList = n.f3.accept(this);
      String temp = Distributor.newTemp();
      PrintBoard.println("MOVE " + temp + " CALL " + objTemp + "(" + arguList + ")");
      return temp;
   }


   public String visit(NodeListOptional n) {
      if ( n.present() ) {
         String _ret = new String();
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            String nextTemp = e.nextElement().accept(this);
            if(nextTemp != null && !nextTemp.startsWith("TEMP")) {
            	String temp = Distributor.newTemp();
            	PrintBoard.println("MOVE " + temp + " " + nextTemp);
            	nextTemp = temp;
            }
            _ret += (nextTemp + " ");
         }
         return _ret;
      }
      else
         return null;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> Exp()
    */
   public String visit(HAllocate n) {
      String expTemp = n.f1.accept(this);
      if(n.f1.f0.which == 3) {
    	  String temp = Distributor.newTemp();
    	  PrintBoard.println("MOVE " + temp + " " + expTemp);
    	  return new String("HALLOCATE " + temp + "\n");
      } else {
    	  return new String("HALLOCATE " + expTemp + "\n");
      }
   }

   /**
    * f0 -> Operator()
    * f1 -> Exp()
    * f2 -> Exp()
    */
   public String visit(BinOp n) {
      String _ret=null;
      String operator = n.f0.f0.choice.toString();
      String temp1 = n.f1.accept(this);
      if(n.f1.f0.which != 4) {
    	  String temp = Distributor.newTemp();
    	  PrintBoard.println("MOVE " + temp + " " + temp1);
    	  temp1 = temp;
      }
      String temp2 = n.f2.accept(this);
      if(n.f2.f0.which < 4) {
    	  String temp = Distributor.newTemp();
    	  PrintBoard.println("MOVE " + temp + " " + temp2);
    	  temp2 = temp;
      }
      _ret = new String(operator + " " + temp1 + " " + temp2 + "\n");
      return _ret;
   }

   /**
    * f0 -> "LT"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    */
   public String visit(Operator n) {
      String _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public String visit(Temp n) {
	   return new String("TEMP " + n.f1.f0.toString());
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public String visit(IntegerLiteral n) {
	   return new String(n.f0.toString());
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public String visit(Label n) {
	  PrintBoard.println(n.f0.toString());
      return new String("");
   }

}

class Distributor {
	static int tempNum = 10000;
	static String newTemp() {
		--tempNum;
		return new String("TEMP "+ tempNum);
	}
}