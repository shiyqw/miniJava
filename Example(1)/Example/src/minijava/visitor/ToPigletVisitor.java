package minijava.visitor;

import util.PrintBoard;
import minijava.minijava2piglet.Distributor;
import minijava.syntaxtree.*;
import minijava.st.*;

public class ToPigletVisitor extends GJDepthFirst<MyType, MyType> {

	private Distributor distributor;
	int paraCount = 0;
	int paraNumber = 0;
	private String paraTemp = null;

   //
   // User-generated visitor methods below
   //
	// inheritance method transferring
	// this pointer
	// after equal a.b
   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> PrintStatement()
    * f15 -> "}"
    * f16 -> "}"
    */
   public MyType visit(MainClass n, MyType argu) {
      MyType _ret=null;
      distributor = new Distributor();
      MyClass mainClass = ((MyClasses) argu).getMyClass(n.f1.f0.toString());
      MyMethod mainMethod = mainClass.getMethod("main");
      PrintBoard.println("MAIN");
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, mainMethod);
      n.f15.accept(this, argu);
      n.f16.accept(this, argu);
      PrintBoard.println("END");
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public MyType visit(TypeDeclaration n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public MyType visit(ClassDeclaration n, MyType argu) {
      MyType _ret=null;
      MyClass obj = ((MyClasses) argu).getMyClass(n.f1.f0.toString());
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, obj);
      n.f4.accept(this, obj);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public MyType visit(ClassExtendsDeclaration n, MyType argu) {
      MyType _ret=null;
      MyClass obj = ((MyClasses) argu).getMyClass(n.f1.f0.toString());
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, obj);
      n.f6.accept(this, obj);
      n.f7.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public MyType visit(VarDeclaration n, MyType argu) {
      MyType _ret=null;
      if(argu instanceof MyMethod) {
    	  String varName = n.f1.f0.toString();
    	  int varOffset = ((MyMethod) argu).getVarTable().getOffset(varName);
    	  PrintBoard.println("MOVE TEMP " + (varOffset + 1) + " 0");
      }
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }
   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   
   public MyType visit(MethodDeclaration n, MyType argu) {
      MyType _ret=null;
      MyMethod method = ((MyClass) argu).getMethod(n.f2.f0.toString());
      String className = ((MyClass) argu).getName();
      String methodName = n.f2.f0.toString();
      String labelName = className + "_" + methodName;
      int paraNumber = method.getParaNum();
      distributor = new Distributor(method);
      PrintBoard.println(labelName + "[" + (paraNumber+1) + "]");
      
      n.f0.accept(this, method);
      n.f1.accept(this, method);
      n.f2.accept(this, method);
      n.f3.accept(this, method);
      n.f4.accept(this, method);
      n.f5.accept(this, method);
      n.f6.accept(this, method);
      PrintBoard.println("BEGIN");
      int paraNum = method.getParaNum();
      if(paraNum >= 19) {
    	  for(int i = 20; i <= paraNum; ++i) {
    		  PrintBoard.println("HLOAD TEMP " + i + " TEMP 19 " + (i-19)*4);
    	  }
    	  PrintBoard.println("HLOAD TEMP 19 TEMP 19 0");
      }
      n.f7.accept(this, method);
      n.f8.accept(this, method);
      n.f9.accept(this, method);
      PrintBoard.println("RETURN");
      n.f10.accept(this, method);
      n.f11.accept(this, method);
      n.f12.accept(this, method);
      PrintBoard.println("END");
      return _ret;
   }


   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public MyType visit(FormalParameterList n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public MyType visit(FormalParameter n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public MyType visit(FormalParameterRest n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public MyType visit(Type n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public MyType visit(ArrayType n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public MyType visit(BooleanType n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public MyType visit(IntegerType n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public MyType visit(Statement n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public MyType visit(Block n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public MyType visit(AssignmentStatement n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      String varName = n.f0.f0.toString();
      n.f1.accept(this, argu);
      //PrintBoard.println(varName);
      if(((MyMethod) argu).hasLocal(varName)) {
    	  int tempNum = (((MyMethod) argu).getVarTable().getOffset(varName))+1;
    	  PrintBoard.println("MOVE TEMP " + tempNum);
      } else {
    	  MyClass innerClass = ((MyMethod) argu).getMyClass(((MyMethod) argu).getName());
    	  int address = innerClass.getFieldTable().getOffset(varName);
    	  PrintBoard.println("HSTORE TEMP 0 " + (4 * (address+1)));
      }
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   public MyType visit(ArrayAssignmentStatement n, MyType argu) {
      MyType _ret=null;
      String arrayName = n.f0.f0.toString();
      String arrayTemp = distributor.newTemp();
      String indexTemp = distributor.newTemp();
      if(((MyMethod) argu).hasLocal(arrayName)) {
    	  int tempNum = (((MyMethod) argu).getVarTable().getOffset(arrayName))+1;
    	  PrintBoard.println("MOVE TEMP" + tempNum);
      } else {
    	  MyClass innerClass = ((MyMethod) argu).getMyClass(((MyMethod) argu).getName());
    	  int address = innerClass.getFieldTable().getOffset(arrayName);
    	  PrintBoard.println("HLOAD " + arrayTemp + " TEMP  0 " + 4*(address+1));
      }
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      PrintBoard.println("MOVE " + indexTemp);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      
      n.f4.accept(this, argu);
      String temp = distributor.newTemp();
      String errorLabel = distributor.newLabel();
      String correctLabel = distributor.newLabel();
      PrintBoard.println("CJUMP LT ");
      PrintBoard.println(indexTemp);
      PrintBoard.println("BEGIN");
      PrintBoard.println("HLOAD " + temp + " " + arrayTemp + " 0");
      PrintBoard.println("RETURN " + temp);
      PrintBoard.println("END");
      PrintBoard.println(errorLabel);
      PrintBoard.println("JUMP " + correctLabel);
      PrintBoard.println(errorLabel + " ERROR");
      PrintBoard.println(correctLabel + " NOOP"); 
      
      PrintBoard.println("HSTORE PLUS TIMES 4 " + indexTemp + " " + arrayTemp + " 4");
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public MyType visit(IfStatement n, MyType argu) {
      MyType _ret=null;
      String label1 = distributor.newLabel();
      String label2 = distributor.newLabel();
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      PrintBoard.print("CJUMP ");
      n.f2.accept(this, argu);
      PrintBoard.println(label2);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      PrintBoard.println("JUMP " + label1);
      PrintBoard.println(label2 + " NOOP");
      n.f6.accept(this, argu);
      PrintBoard.println(label1 + " NOOP");
      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public MyType visit(WhileStatement n, MyType argu) {
      MyType _ret=null;
      String label1 = distributor.newLabel();
      String label2 = distributor.newLabel();
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      PrintBoard.print(label1 + " CJUMP ");
      n.f2.accept(this, argu);
      PrintBoard.println(label2);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      PrintBoard.println("JUMP " + label1);
      PrintBoard.println(label2 + " NOOP");
      return _ret;
   }

   /**
    * f0 -> "PrintBoard.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public MyType visit(PrintStatement n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      PrintBoard.println("PRINT");
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
   public MyType visit(Expression n, MyType argu) {
      MyType _ret=null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public MyType visit(AndExpression n, MyType argu) {
      MyType _ret=null;
      String label = distributor.newLabel();
      String valueTemp = distributor.newTemp();
      PrintBoard.println("BEGIN");
      PrintBoard.println("MOVE " + valueTemp + " 0");
      PrintBoard.print("CJUMP ");
      n.f0.accept(this, argu);
      PrintBoard.println(label);
      n.f1.accept(this, argu);
      PrintBoard.print("CJUMP ");
      n.f2.accept(this, argu);
      PrintBoard.println(label);
      PrintBoard.println("MOVE " + valueTemp + " 1");
      PrintBoard.println(label + " NOOP");
      PrintBoard.println("RETURN " + valueTemp);
      PrintBoard.println("END");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   public MyType visit(CompareExpression n, MyType argu) {
      MyType _ret=null;
      PrintBoard.print("LT ");
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public MyType visit(PlusExpression n, MyType argu) {
      MyType _ret=null;
      PrintBoard.print("PLUS ");
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      PrintBoard.print(" ");
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public MyType visit(MinusExpression n, MyType argu) {
      MyType _ret=null;
      PrintBoard.print("MINUS ");
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      PrintBoard.print(" ");
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public MyType visit(TimesExpression n, MyType argu) {
      MyType _ret=null;
      PrintBoard.print("TIMES ");
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      PrintBoard.print(" ");
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public MyType visit(ArrayLookup n, MyType argu) {
      MyType _ret=null;
      PrintBoard.println("BEGIN");
      String arrayTemp = distributor.newTemp();
      String indexTemp = distributor.newTemp();
      PrintBoard.println("MOVE " + arrayTemp);
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      PrintBoard.println("MOVE " + indexTemp);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      String temp = distributor.newTemp();
      
      String errorLabel = distributor.newLabel();
      String correctLabel = distributor.newLabel();
      PrintBoard.println("CJUMP LT ");
      PrintBoard.println(indexTemp);
      PrintBoard.println("BEGIN");
      PrintBoard.println("HLOAD " + temp + " " + arrayTemp + " 0");
      PrintBoard.println("RETURN " + temp);
      PrintBoard.println("END");
      PrintBoard.println(errorLabel);
      PrintBoard.println("JUMP " + correctLabel);
      PrintBoard.println(errorLabel + " ERROR");
      PrintBoard.println(correctLabel + " NOOP"); 
      PrintBoard.println("HLOAD " + temp + "PLUS TIMES 4 " + indexTemp + " " + arrayTemp + " 4");
      PrintBoard.println("RETURN " + temp);
      PrintBoard.println("END");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public MyType visit(ArrayLength n, MyType argu) {
      MyType _ret=null;
      PrintBoard.println("BEGIN");
      String arrayTemp = distributor.newTemp();
      PrintBoard.println("MOVE " + arrayTemp);
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String temp = distributor.newTemp();
      PrintBoard.println("HLOAD " + temp + " " + arrayTemp + " 0");
      PrintBoard.println("RETURN " + temp);
      PrintBoard.println("END");
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public MyType visit(MessageSend n, MyType argu) {
      PrintBoard.println("CALL");
      PrintBoard.println("BEGIN");
      String classTemp = distributor.newTemp();
      PrintBoard.println("MOVE " + classTemp + " ");
      String className = ((MyClassType) n.f0.accept(this, argu)).getType();
      MyClass obj = ((MyMethod) argu).getMyClass(className);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String methodName = n.f2.f0.toString();
      MyMethod method = obj.getMethod(methodName);
      String methodTemp = distributor.newTemp();
      String temp = distributor.newTemp();
      int methodOffset = obj.getMethodTable().getOffset(methodName);
      PrintBoard.println("HLOAD " + temp + " " + classTemp + " 0");
      PrintBoard.println("HLOAD " + methodTemp + " " + temp + " " + methodOffset*4);
      PrintBoard.println("RETURN " + methodTemp);
      PrintBoard.println("END");
      n.f3.accept(this, argu);
      PrintBoard.print("( " + classTemp + " ");
      int countTemp = paraCount;
      paraCount = 0;
      int numberTemp = paraNumber;
      paraNumber = method.getParaNum();
      String tempTemp = paraTemp;
      if(paraNumber >= 19) {
    	  paraTemp = distributor.newTemp();
      }
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      paraTemp = tempTemp;
      paraCount = countTemp;
      paraNumber = numberTemp;
      PrintBoard.println(")");
      return method.getRet();
   }

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public MyType visit(ExpressionList n, MyType argu) {
      MyType _ret=null;
      ++paraCount;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public MyType visit(ExpressionRest n, MyType argu) {
      MyType _ret=null;
      ++paraCount;
      n.f0.accept(this, argu);
      if(paraCount == 19) {
    	  PrintBoard.println("BEGIN");
    	  PrintBoard.println("MOVE " + paraTemp + " HALLOCATE " + (4*(paraNumber-18)));
      }
      if(paraCount >= 19) {
    	  PrintBoard.println("HSTORE " + paraTemp + " " + 4*(paraCount-19)); 
    	  n.f1.accept(this, argu);
    	  if(paraCount == paraNumber) {
    		  PrintBoard.println("RETURN " + paraTemp);
    		  PrintBoard.println("END");
    	  }
    	  return _ret;
      }
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
   public MyType visit(PrimaryExpression n, MyType argu) {
      MyType _ret=null;
      _ret = n.f0.accept(this, argu);
      if(n.f0.which == 3) {
    	  String varName = ((Identifier) n.f0.choice).f0.toString();
    	  MyMethod method = (MyMethod) argu;
    	  if(method.hasLocal(varName)) {
    		  PrintBoard.println("TEMP " + (method.getVarTable().getOffset(varName)+1));
    	  } else {
    		  PrintBoard.println("BEGIN ");
	    	  MyClass innerClass = ((MyMethod) argu).getMyClass(((MyMethod) argu).getName());
	    	  int address = innerClass.getFieldTable().getOffset(varName);
	    	  String returnTemp = distributor.newTemp();
	    	  PrintBoard.println("HLOAD " + returnTemp + " TEMP 0 " + (4 * (address+1)));
	    	  PrintBoard.println("RETURN " + returnTemp);
	    	  PrintBoard.println("END");
    	  }
      }
      //PrintBoard.println("choice is " + n.f0.which);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public MyType visit(IntegerLiteral n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      PrintBoard.println(n.f0.toString());
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public MyType visit(TrueLiteral n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      PrintBoard.println("1");
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public MyType visit(FalseLiteral n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      PrintBoard.println("0");
      
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public MyType visit(Identifier n, MyType argu) {
      MyType _ret=null;
      String idName = n.f0.toString();
      if(argu instanceof MyMethod) {
	      MyMethod method = (MyMethod) argu;
	      if(method.hasVar(idName)) {
	    	  _ret = method.getVar(idName);
	      }
	      n.f0.accept(this, argu);
      }
      return _ret;
   }

   /**
    * f0 -> "this"
    */
   public MyType visit(ThisExpression n, MyType argu) {
      n.f0.accept(this, argu);
      PrintBoard.println("TEMP 0");
      return new MyClassType(((MyMethod) argu).getName());
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public MyType visit(ArrayAllocationExpression n, MyType argu) {
      MyType _ret=null;
      PrintBoard.println("BEGIN");
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String lengthTemp = distributor.newTemp();
      PrintBoard.println("MOVE " + lengthTemp);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      String returnTemp = distributor.newTemp();
      PrintBoard.println("MOVE " + returnTemp);
      PrintBoard.println("HALLOCATE " + "TIMES 4 PLUS 1 " + lengthTemp);
      PrintBoard.println("HSTORE " + returnTemp + " 0 " + lengthTemp);
      PrintBoard.println("RETURN " + returnTemp);
      PrintBoard.println("END");
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public MyType visit(AllocationExpression n, MyType argu) {
      MyType _ret=null;
      PrintBoard.println("BEGIN");
      MyClass newClass = ((MyMethod) argu).getMyClass(n.f1.f0.toString());
      int classSize = newClass.getFieldTable().getVarNumber();
      classSize *= 4;
      classSize += 4;
      String classTemp = distributor.newTemp();
      PrintBoard.println("MOVE " + classTemp + " HALLOCATE " + classSize);
      MethodTable methodTable = newClass.getMethodTable();
      String methodTemp = distributor.newTemp();
      
      int methodNumber = methodTable.getMethodNumber();
      PrintBoard.println("MOVE " + methodTemp + " HALLOCATE " + 4*methodNumber);
      for(int i = 0; i < methodNumber; ++i) {
    	  PrintBoard.println("HSTORE " + methodTemp + " " + i*4 + 
    			  " " + methodTable.getLabel(i));
      }
      
      for(int i = 4; i < classSize; i += 4) {
    	  PrintBoard.println("HSTORE " + classTemp + " " + i + " 0");
      }
      PrintBoard.println("HSTORE " + classTemp + " 0 " + methodTemp);
      PrintBoard.println("RETURN " + classTemp);
      PrintBoard.println("END");
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      _ret = new MyClassType(n.f1.f0.toString());
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public MyType visit(NotExpression n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      PrintBoard.println("LT ");
      n.f1.accept(this, argu);
      PrintBoard.println("1");
      return _ret;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public MyType visit(BracketExpression n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      _ret = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

}
