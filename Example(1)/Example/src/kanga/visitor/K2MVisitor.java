package kanga.visitor;

import java.util.Enumeration;

import kanga.syntaxtree.*;

public class K2MVisitor extends GJDepthFirst<String, Integer> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   public String visit(NodeList n, Integer argu) {
      String _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeListOptional n, Integer argu) {
      if ( n.present() ) {
         String _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public String visit(NodeOptional n, Integer argu) {
      if ( n.present() ) {
    	 System.out.println(((Label) n.node).f0.toString() + ": ");
         return n.node.accept(this,argu);
      }
      else
         return null;
   }

   public String visit(NodeSequence n, Integer argu) {
      String _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeToken n, Integer argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> "MAIN"
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> "["
    * f5 -> IntegerLiteral()
    * f6 -> "]"
    * f7 -> "["
    * f8 -> IntegerLiteral()
    * f9 -> "]"
    * f10 -> StmtList()
    * f11 -> "END"
    * f12 -> ( Procedure() )*
    * f13 -> <EOF>
    */
   public String visit(Goal n, Integer argu) {
      String _ret=null;
      
      // print header and data
      System.out.println(".text");
      System.out.println(".globl main");
      System.out.println("main: ");
      
      int stackSize = Integer.parseInt(n.f5.f0.toString());
      int maxCallParaNum = Integer.parseInt(n.f8.f0.toString());
      int maxParaNum = Integer.parseInt(n.f2.f0.toString());
      if(maxCallParaNum > 4) maxCallParaNum -= 4;
      else maxCallParaNum = 0;
      if(maxParaNum > 4) maxParaNum -= 4;
      else maxParaNum = 0;
      stackSize += (maxCallParaNum+2);
      stackSize *= 4;
      System.out.println("sw $fp, -8($sp)");
      System.out.println("sw $ra, -4($sp)");
      System.out.println("move $fp, $sp");
      System.out.println("subu $sp, $sp, " + stackSize);
      int mainMethod = maxParaNum;
      n.f10.accept(this, mainMethod);
      
      System.out.println("addu $sp, $sp, " + stackSize);
      System.out.println("lw $fp, -8($sp)");
      System.out.println("lw $ra, -4($sp)");
      System.out.println("j $ra");
      
      n.f12.accept(this, argu);
      
      // halloc
      System.out.println();
      System.out.println(".text");
      System.out.println(".globl _halloc");
      System.out.println("_halloc:");
      System.out.println("li $v0, 9");
      System.out.println("syscall");
      System.out.println("j $ra");
      System.out.println();
      
      // print
      System.out.println(".text");
      System.out.println(".globl _print");
      System.out.println("_print:");
      System.out.println("li $v0, 1");
      System.out.println("syscall");
      System.out.println("la $a0, newl");
      System.out.println("li $v0, 4");
      System.out.println("syscall");
      System.out.println("j $ra");
      
      //error
      System.out.println(".text");
      System.out.println(".globl _error");
      System.out.println("_error:");
      System.out.println("la $a0, str_err");
      System.out.println("li $v0, 4");
      System.out.println("syscall");
      System.out.println("j $ra");
      
      //data
      System.out.println(".data");
      System.out.println(".align 0");
      System.out.println("newl: .asciiz \"\\n\"");
      System.out.println(".data");
      System.out.println(".align 0");
      System.out.println("str_err: .asciiz \"ERROR abnormal terminated\\n\"");
      System.out.println();
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public String visit(StmtList n, Integer argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> "["
    * f5 -> IntegerLiteral()
    * f6 -> "]"
    * f7 -> "["
    * f8 -> IntegerLiteral()
    * f9 -> "]"
    * f10 -> StmtList()
    * f11 -> "END"
    */
   public String visit(Procedure n, Integer argu) {
      String _ret=null;
      System.out.println();
      System.out.println(".text");
      System.out.println(".globl " + n.f0.f0.toString());
      System.out.println(n.f0.f0.toString() + ":");
      
      int stackSize = Integer.parseInt(n.f5.f0.toString());
      int maxCallParaNum = Integer.parseInt(n.f8.f0.toString());
      int maxParaNum = Integer.parseInt(n.f2.f0.toString());
      if(maxCallParaNum > 4) maxCallParaNum -= 4;
      else maxCallParaNum = 0;
      if(maxParaNum > 4) maxParaNum -= 4;
      else maxParaNum = 0;
      stackSize += (maxCallParaNum+2);
      stackSize *= 4;
      
      
      System.out.println("sw $fp, -8($sp)");
      System.out.println("sw $ra, -4($sp)");
      System.out.println("move $fp, $sp");
      System.out.println("subu $sp, $sp, " + stackSize);
      int method = maxParaNum;
      n.f10.accept(this, method);
      
      System.out.println("addu $sp, $sp, " + stackSize);
      System.out.println("lw $fp, -8($sp)");
      System.out.println("lw $ra, -4($sp)");
      System.out.println("j $ra");
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
    *       | ALoadStmt()
    *       | AStoreStmt()
    *       | PassArgStmt()
    *       | CallStmt()
    */
   public String visit(Stmt n, Integer argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public String visit(NoOpStmt n, Integer argu) {
      String _ret=null;
      System.out.println("nop");
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public String visit(ErrorStmt n, Integer argu) {
      String _ret=null;
      System.out.println("jal _error");
      //exit
      System.out.println("li $v0, 10");
      System.out.println("syscall");
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Reg()
    * f2 -> Label()
    */
   public String visit(CJumpStmt n, Integer argu) {
      String _ret=null;
      String reg = n.f1.accept(this, argu);
      System.out.println("li $gp, 1");
      String label = n.f2.f0.toString();
      System.out.println("bne $" + reg + ", $gp, " + label);
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public String visit(JumpStmt n, Integer argu) {
      String _ret=null;
      String label = n.f1.f0.toString();
      System.out.println("j " + label);
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Reg()
    * f2 -> IntegerLiteral()
    * f3 -> Reg()
    */
   public String visit(HStoreStmt n, Integer argu) {
      String _ret=null;
      String reg1 = n.f1.accept(this, argu);
      String offset = n.f2.f0.toString();
      String reg2 = n.f3.accept(this, argu);
      System.out.println("sw $" + reg2 + ", " + offset + "($" + reg1 + ")");
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Reg()
    * f2 -> Reg()
    * f3 -> IntegerLiteral()
    */
   public String visit(HLoadStmt n, Integer argu) {
      String _ret=null;
      String reg1 = n.f1.accept(this, argu);
      String reg2 = n.f2.accept(this, argu);
      String offset = n.f3.f0.toString();
      System.out.println("lw $" + reg1 + ", " + offset + "($" + reg2 + ")");
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Reg()
    * f2 -> Exp()
    */
   public String visit(MoveStmt n, Integer argu) {
      String _ret=null;
      String reg = n.f1.accept(this, argu);
      String exp = n.f2.accept(this, argu);
      System.out.println("move $" + reg + ", $" + exp);
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public String visit(PrintStmt n, Integer argu) {
      String _ret=null;
      String exp = n.f1.accept(this, argu);
      System.out.println("move $a0, $" + exp);
      System.out.println("jal _print");
      return _ret;
   }

   /**
    * f0 -> "ALOAD"
    * f1 -> Reg()
    * f2 -> SpilledArg()
    */
   public String visit(ALoadStmt n, Integer argu) {
      String _ret=null;
      String reg = n.f1.accept(this, argu);
      String spill = n.f2.accept(this, argu);
      System.out.println("lw $" + reg + ", " + spill);
      return _ret;
   }

   /**
    * f0 -> "ASTORE"
    * f1 -> SpilledArg()
    * f2 -> Reg()
    */
   public String visit(AStoreStmt n, Integer argu) {
      String _ret=null;
      String spill = n.f1.accept(this, argu);
      String reg = n.f2.accept(this, argu);
      System.out.println("sw $" + reg + ", " + spill);
      return _ret;
   }

   /**
    * f0 -> "PASSARG"
    * f1 -> IntegerLiteral()
    * f2 -> Reg()
    */
   public String visit(PassArgStmt n, Integer argu) {
      String _ret=null;
      int literal = Integer.parseInt(n.f1.f0.toString());
      String reg = n.f2.accept(this, argu);
      int offset = (literal-1)*4;
      System.out.println("sw $" + reg + ", " + offset + "($sp)");
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> SimpleExp()
    */
   public String visit(CallStmt n, Integer argu) {
      String _ret=null;
      String exp = n.f1.accept(this, argu);
      if(n.f1.f0.which == 0) {
    	  System.out.println("jalr $" + exp);
      } else if (n.f1.f0.which == 2) {
    	  System.out.println("jal " + exp);
      } 
      return _ret;
   }

   /**
    * f0 -> HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public String visit(Exp n, Integer argu) {
      String _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public String visit(HAllocate n, Integer argu) {
      String exp = n.f1.accept(this, argu);
      System.out.println("move $a0, $" + exp);
      System.out.println("jal _halloc");
      return "v0";
   }

   /**
    * f0 -> Operator()
    * f1 -> Reg()
    * f2 -> SimpleExp()
    */
   public String visit(BinOp n, Integer argu) {
      String op = n.f0.accept(this, argu);
      String reg = n.f1.accept(this, argu);
      String exp = n.f2.accept(this, argu);
      System.out.println(op + " $gp, $" + reg + ", $" + exp);
      
      return "gp";
   }

   /**
    * f0 -> "LT"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    */
   public String visit(Operator n, Integer argu) {
	   if(n.f0.which == 0) return "slt";
	   if(n.f0.which == 1) return "add";
	   if(n.f0.which == 2) return "sub";
	   if(n.f0.which == 3) return "mul";
	   return null;
   }

   /**
    * f0 -> "SPILLEDARG"
    * f1 -> IntegerLiteral()
    */
   public String visit(SpilledArg n, Integer argu) {
      int literal = Integer.parseInt(n.f1.f0.toString());
      if(literal >= argu) {
    	  return (literal-argu)*4 + "($sp)";
      } else {
    	  return (literal*4) + "($fp)";
      }
   }

   /**
    * f0 -> Reg()
    *       | IntegerLiteral()
    *       | Label()
    */
   public String visit(SimpleExp n, Integer argu) {
      String _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "a0"
    *       | "a1"
    *       | "a2"
    *       | "a3"
    *       | "t0"
    *       | "t1"
    *       | "t2"
    *       | "t3"
    *       | "t4"
    *       | "t5"
    *       | "t6"
    *       | "t7"
    *       | "s0"
    *       | "s1"
    *       | "s2"
    *       | "s3"
    *       | "s4"
    *       | "s5"
    *       | "s6"
    *       | "gp"
    *       | "t8"
    *       | "t9"
    *       | "v0"
    *       | "v1"
    */
   public String visit(Reg n, Integer argu) {
      String _ret = n.f0.choice.toString();
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public String visit(IntegerLiteral n, Integer argu) {
	  System.out.println("li $gp, " + n.f0.toString());
      return "gp";
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public String visit(Label n, Integer argu) {
	  System.out.println("la $gp, " + n.f0.toString());
      return "gp";
   }


}
