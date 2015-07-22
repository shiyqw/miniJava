package minijava.visitor;

import minijava.syntaxtree.*;
import minijava.st.*;
import minijava.typecheck.PrintError;

public class BuildSTVisitor extends GJDepthFirst<MyType, MyType> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public MyType visit(Goal n, MyType argu) {
	  //System.out.println("root");
      MyType _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

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
	  //System.out.println("mainclass");
      MyType _ret=null;
      MyClass mainClass = new MyClass(n.f0.beginLine);
      ((MyClasses)argu).insertClass(n.f1.f0.toString(), mainClass);
      MyMethod mainMethod = new MyMethod(n.f6.beginLine);
      mainClass.insertMethod("main", mainMethod);
      mainMethod.insertVar(n.f11.f0.toString(), new MyIntType());
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
      n.f14.accept(this, argu);
      n.f15.accept(this, argu);
      n.f16.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public MyType visit(TypeDeclaration n, MyType argu) {
	  //System.out.println("typeDeclare");
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
		MyType _ret = null;
		MyClass newClass;
		String className;
		String errorString;

		n.f0.accept(this, argu);

		className = n.f1.f0.toString();

		newClass = new MyClass(n.f1.f0.beginLine);
		boolean flag = ((MyClasses)(argu)).insertClass(className, newClass);
		if(!flag) {
			errorString = "class already declared : " + className;
			PrintError.print(newClass.getLine(), errorString);
			//System.out.println("same class");
		}
		n.f2.accept(this, argu);
		n.f3.accept(this, newClass);
		n.f4.accept(this, newClass);
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
		MyType _ret = null;
		MyClass newClass;
		String className;
		String errorString;

		n.f0.accept(this, argu);

		className = n.f1.f0.toString();

		newClass = new MyClass(n.f1.f0.beginLine);
		newClass.setParentName(n.f3.f0.toString());
		boolean flag = ((MyClasses)(argu)).insertClass(className, newClass);
		if(!flag) {
			errorString = "class has already declared : " + className;
			PrintError.print(newClass.getLine(), errorString);
		}
		n.f4.accept(this, argu);
		n.f5.accept(this, newClass);
		n.f6.accept(this, newClass);
		n.f7.accept(this, argu);
		return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public MyType visit(VarDeclaration n, MyType argu) {
	  //System.out.println("varDeclare");
      MyType _ret=null;
      MyBasicType varType = (MyBasicType) n.f0.accept(this, argu);
      boolean flag = true;
      if(argu instanceof MyClass) {
    	  flag = ((MyClass) argu).insertField(n.f1.f0.toString(), varType);
      }
      if(argu instanceof MyMethod) {
    	  flag = ((MyMethod) argu).insertVar(n.f1.f0.toString(), varType);
      }
      if(!flag) {
    	  String errorString = "variable already declared : " + n.f1.f0.toString();
    	  PrintError.print(n.f1.f0.beginLine, errorString);
			////System.out.println("error1");
      }
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
	  //System.out.println("varDeclare");
      MyType _ret=null;
      MyMethod newMethod = new MyMethod(n.f0.beginLine);
      boolean flag = ((MyClass) argu).insertMethod(n.f2.f0.toString(), newMethod);
      if(!flag) {
    	  String errorString = "method override : " + n.f2.f0.toString();
    	  PrintError.print(n.f2.f0.beginLine, errorString);
      }
      
      n.f0.accept(this, argu);
      MyBasicType returnType = (MyBasicType) n.f1.accept(this, argu);
      newMethod.setRet(returnType);
      // ((MyClass) argu).transField(newMethod);
      n.f2.accept(this, argu);
      n.f3.accept(this, newMethod);
      n.f4.accept(this, newMethod);
      n.f5.accept(this, newMethod);
      n.f6.accept(this, newMethod);
      n.f7.accept(this, newMethod);
      n.f8.accept(this, newMethod);
      n.f9.accept(this, newMethod);
      n.f10.accept(this, newMethod);
      n.f11.accept(this, newMethod);
      n.f12.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public MyType visit(FormalParameterList n, MyType argu) {
	  //System.out.println("varDeclare");
	  ParameterList paras = new ParameterList(n.f0.f1.f0.beginLine);
	  ((MyMethod) argu).setParas(paras);
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
	  //System.out.println("varDeclare");
      MyType _ret=null;
      MyBasicType paraType = (MyBasicType) n.f0.accept(this, argu);
      boolean flag = ((MyMethod) argu).insertVar(n.f1.f0.toString(), paraType);
      if(!flag) {
    	  String errorString = "variable already declared : " + n.f1.f0.toString();
    	  PrintError.print(n.f1.f0.beginLine, errorString);
      }
      ((MyMethod) argu).addPara(paraType);
      ((MyMethod) argu).insertInit(n.f1.f0.toString());
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public MyType visit(FormalParameterRest n, MyType argu) {
	  //System.out.println("varDeclare");
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
      MyBasicType type = (MyBasicType) n.f0.accept(this, argu);
      if(n.f0.which == 3) {
    	  type = new MyClassType(((Identifier) n.f0.choice).f0.toString());
      }
      return type;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public MyType visit(ArrayType n, MyType argu) {
      MyType type=new MyArrayType();
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return type;
   }

   /**
    * f0 -> "boolean"
    */
   public MyType visit(BooleanType n, MyType argu) {
      MyType type=new MyBooleanType();
      n.f0.accept(this, argu);
      return type;
   }

   /**
    * f0 -> "int"
    */
   public MyType visit(IntegerType n, MyType argu) {
      MyType type=new MyIntType();
      n.f0.accept(this, argu);
      return type;
   }

}
