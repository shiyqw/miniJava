package minijava.visitor;

import minijava.syntaxtree.*;
import minijava.st.*;
import minijava.typecheck.PrintError;


public class CheckInitializationVisitor extends GJDepthFirst<MyType, MyType> {
	

   //
   // User-generated visitor methods below
   //

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
      n.f10.accept(this, method);
      n.f11.accept(this, method);
      n.f12.accept(this, method);
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
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public MyType visit(AssignmentStatement n, MyType argu) {
      MyType _ret=null;
      String idName = n.f0.f0.toString();
      n.f0.accept(this, argu);
      n.f2.accept(this, argu); // right first
      ((MyMethod) argu).insertInit(idName);
      n.f1.accept(this, argu);
      n.f3.accept(this, argu);
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
      if(n.f0.which == 3) {
    	  String name = ((Identifier) n.f0.choice).f0.toString(); 
    	  // find uninitialized local variables (basic type)
    	  if(!((MyMethod) argu).isInit(name) && ((MyMethod) argu).getVar(name).isBasic()) {
    		  String errorString = "identifier not initialized : " + name;
    		  PrintError.print(((Identifier) n.f0.choice).f0.beginLine, errorString);
    	  }
      }
      n.f0.accept(this, argu);
      return _ret;
   }

}
