package minijava.visitor;

import java.util.Enumeration;

import minijava.syntaxtree.*;
import minijava.st.*;
import minijava.typecheck.PrintError;


public class CheckTypeErrorVisitor extends GJDepthFirst<MyType, MyType> {

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
      MyClass mainClass = ((MyClasses) argu).getMyClass(n.f1.f0.toString());
      MyMethod mainMethod = mainClass.getMethod("main");
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
      n.f0.accept(this, argu);
      n.f1.accept(this, method);
      n.f2.accept(this, method);
      n.f3.accept(this, method);
      n.f4.accept(this, method);
      n.f5.accept(this, method);
      n.f6.accept(this, method);
      n.f7.accept(this, method);
      n.f8.accept(this, method);
      n.f9.accept(this, method);
      MyBasicType retType = (MyBasicType) n.f10.accept(this, method);
      if(!method.typeMatch(retType, method.getRet())) {
    	  String errorString = "return type not match : " + retType.getType() + "&" + method.getRet().getType();
    	  PrintError.print(n.f9.beginLine, errorString);
      }
      n.f11.accept(this, method);
      n.f12.accept(this, method);
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
      String varName = n.f0.f0.toString();
      MyBasicType varType = ((MyMethod) argu).getVar(varName);
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MyBasicType exType = (MyBasicType) n.f2.accept(this, argu);
      if(!((MyMethod) argu).typeMatch(exType, varType)) {
    	  String errorString = "assignment type not match : " + exType.getType() + "&" + varType.getType();
    	  PrintError.print(n.f0.f0.beginLine, errorString);
      }
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
      MyBasicType arrayType = ((MyMethod) argu).getVar(arrayName);
      if(!(arrayType instanceof MyArrayType)) {
    	  String errorString = "not array type : " + arrayName;
    	  PrintError.print(n.f0.f0.beginLine, errorString);
      }
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MyBasicType indexType = (MyBasicType) n.f2.accept(this, argu);
      if(!(indexType instanceof MyIntType)) {
    	  String errorString = "index not integer expression";
    	  PrintError.print(n.f0.f0.beginLine, errorString);
      }
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      MyBasicType exType = (MyBasicType) n.f5.accept(this, argu);
      if(!(exType instanceof MyIntType)) {
    	  String errorString = "array element not integer expression";
    	  PrintError.print(n.f0.f0.beginLine, errorString);
      }
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
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MyBasicType exType = (MyBasicType) n.f2.accept(this, argu);
      if(!(exType instanceof MyBooleanType)) {
    	  String errorString = "condition not boolean expression";
    	  PrintError.print(n.f0.beginLine, errorString);
      }
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
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
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MyBasicType exType = (MyBasicType) n.f2.accept(this, argu);
      if(!(exType instanceof MyBooleanType)) {
    	  String errorString = "condition not boolean expression";
    	  PrintError.print(n.f0.beginLine, errorString);
      }
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public MyType visit(PrintStatement n, MyType argu) {
      MyType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      MyBasicType exType = (MyBasicType) n.f2.accept(this, argu);
      if(!(exType instanceof MyIntType)) {
    	  String errorString = "print content not integer expression";
    	  PrintError.print(n.f0.beginLine, errorString);
      }
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
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public MyType visit(AndExpression n, MyType argu) {
      MyBasicType exType = (MyBasicType) n.f0.accept(this, argu);
      if(!(exType instanceof MyBooleanType)) {
    	  String errorString = "condition not boolean expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      n.f1.accept(this, argu);
      exType = (MyBasicType) n.f2.accept(this, argu);
      if(!(exType instanceof MyBooleanType)) {
    	  String errorString = "condition not boolean expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      MyBooleanType _ret = new MyBooleanType();
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   public MyType visit(CompareExpression n, MyType argu) {
      MyBasicType exType = (MyBasicType) n.f0.accept(this, argu);
      if(!(exType instanceof MyIntType)) {
    	  String errorString = "compared element not integer expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      n.f1.accept(this, argu);
      exType = (MyBasicType) n.f2.accept(this, argu);
      if(!(exType instanceof MyIntType)) {
    	  String errorString = "compared element not integer expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      MyBooleanType _ret = new MyBooleanType();
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public MyType visit(PlusExpression n, MyType argu) {
      MyBasicType exType = (MyBasicType) n.f0.accept(this, argu);
      if(!(exType instanceof MyIntType)) {
    	  String errorString = "plused element not integer expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      n.f1.accept(this, argu);
      exType = (MyBasicType) n.f2.accept(this, argu);
      if(!(exType instanceof MyIntType)) {
    	  String errorString = "plused element not integer expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      MyIntType _ret = new MyIntType();
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public MyType visit(MinusExpression n, MyType argu) {
      MyBasicType exType = (MyBasicType) n.f0.accept(this, argu);
      if(!(exType instanceof MyIntType)) {
    	  String errorString = "minused element not integer expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      n.f1.accept(this, argu);
      exType = (MyBasicType) n.f2.accept(this, argu);
      if(!(exType instanceof MyIntType)) {
    	  String errorString = "minused element not integer expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      MyIntType _ret = new MyIntType();
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public MyType visit(TimesExpression n, MyType argu) {
      MyBasicType exType = (MyBasicType) n.f0.accept(this, argu);
      if(!(exType instanceof MyIntType)) {
    	  String errorString = "timed not integer expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      n.f1.accept(this, argu);
      exType = (MyBasicType) n.f2.accept(this, argu);
      if(!(exType instanceof MyIntType)) {
    	  String errorString = "timed element not integer expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      MyIntType _ret = new MyIntType();
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public MyType visit(ArrayLookup n, MyType argu) {
      MyIntType _ret = new MyIntType();
      MyBasicType arrayType = (MyBasicType) n.f0.accept(this, argu);
      if(!(arrayType instanceof MyArrayType)) {
    	  String errorString = "not array expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      n.f1.accept(this, argu);
      MyBasicType indexType = (MyBasicType) n.f2.accept(this, argu);
      if(!(indexType instanceof MyIntType)) {
    	  System.out.println(indexType);
    	  String errorString = "index not integer expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public MyType visit(ArrayLength n, MyType argu) {
      MyIntType _ret = new MyIntType();
      MyBasicType arrayType = (MyBasicType) n.f0.accept(this, argu);
      if(!(arrayType instanceof MyArrayType)) {
    	  String errorString = "checked element not array expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
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
      MyType _ret=null;
      MyBasicType objType = (MyBasicType) n.f0.accept(this, argu);
	  String methodName = n.f2.f0.toString();
	  if(!(objType instanceof MyClassType)) {
		  String errorString = "not a class object";
		  PrintError.print(n.f2.f0.beginLine, errorString);
	      n.f1.accept(this, argu);
	      n.f2.accept(this, argu);
	      n.f3.accept(this, argu);
	      n.f4.accept(this, argu);
	      n.f5.accept(this, argu);
	      return (new MyBasicType("void"));
	  }
	  if(!((MyMethod) argu).getMyClass(objType.getType()).hasMethod(methodName)) {
		  String errorString = "cannot find method : " + methodName;
		  PrintError.print(n.f2.f0.beginLine, errorString);
	      n.f1.accept(this, argu);
	      n.f2.accept(this, argu);
	      n.f3.accept(this, argu);
	      n.f4.accept(this, argu);
	      n.f5.accept(this, argu);
	      return (new MyBasicType("void"));
	  }
	  MyMethod method = ((MyMethod) argu).getMyClass(objType.getType()).getMethod(methodName);
	  //System.out.println(methodName);
	  //System.out.println(method.getName());
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      ParameterList paras = (ParameterList) n.f4.accept(this, argu);
      if(!method.matchType(paras)) {
    	  String errorString = "parameter list not match";
    	  PrintError.print(n.f3.beginLine, errorString);
      }
      n.f5.accept(this, argu);
      _ret = method.getRet();
      return _ret;
   }


   public MyType visit(NodeOptional n, MyType argu) {
	  if(argu instanceof MyMethod) {
		  ParameterList paras = new ParameterList();
		  if(n.present()) {
			  return n.node.accept(this, argu);
		  } else {
			  return paras;
		  }
	  }
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }
   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public MyType visit(ExpressionList n, MyType argu) {
      MyBasicType headType = (MyBasicType) n.f0.accept(this, argu);
      ParameterList restList = (ParameterList) n.f1.accept(this, argu);
      restList.addHead(headType);
      return restList;
   }


   public MyType visit(NodeListOptional n, MyType argu) {
	  ParameterList paras = new ParameterList();
      if ( n.present() ) {
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            MyBasicType nextType = (MyBasicType) e.nextElement().accept(this,argu);
            paras.insertPara(nextType);
         }
      }
      return paras;
   }
   
   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public MyType visit(ExpressionRest n, MyType argu) {
      n.f0.accept(this, argu);
      return n.f1.accept(this, argu);
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
      MyType _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public MyType visit(IntegerLiteral n, MyType argu) {
      MyType _ret = new MyIntType();
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public MyType visit(TrueLiteral n, MyType argu) {
      MyType _ret = new MyBooleanType();
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public MyType visit(FalseLiteral n, MyType argu) {
      MyType _ret = new MyBooleanType();
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public MyType visit(Identifier n, MyType argu) {
      MyType _ret=null;
      String varName = n.f0.toString();
      if((argu instanceof MyMethod) && ((MyMethod) argu).hasVar(varName)) {
    	  _ret = ((MyMethod) argu).getVar(varName);
      }
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "this"
    */
   public MyType visit(ThisExpression n, MyType argu) {
      MyType _ret = new MyClassType(((MyMethod) argu).getName());
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public MyType visit(ArrayAllocationExpression n, MyType argu) {
      MyType _ret = new MyArrayType();
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      MyBasicType indexType = (MyBasicType) n.f3.accept(this, argu);
      if(!(indexType instanceof MyIntType)) {
    	  String errorString = "size not integer expression";
    	  PrintError.print(n.f1.beginLine, errorString);
      }
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public MyType visit(AllocationExpression n, MyType argu) {
	  MyClassType _ret = new MyClassType(n.f1.f0.toString());
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public MyType visit(NotExpression n, MyType argu) {
      MyType _ret = new MyBooleanType();
      n.f0.accept(this, argu);
      MyBasicType exType = (MyBasicType) n.f1.accept(this, argu);
      if(!(exType instanceof MyBooleanType)) {
    	  String errorString = "condition not boolean expression";
    	  PrintError.print(n.f0.beginLine, errorString);
      }
      return _ret;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public MyType visit(BracketExpression n, MyType argu) {
      n.f0.accept(this, argu);
      MyType _ret = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

}


