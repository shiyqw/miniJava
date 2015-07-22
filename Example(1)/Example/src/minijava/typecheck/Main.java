/**
 * 用于类型检查的主函数入口
 */
package minijava.typecheck;

import minijava.MiniJavaParser;
import minijava.ParseException;
import minijava.TokenMgrError;
import minijava.st.*;
import minijava.syntaxtree.Node;
import minijava.visitor.*;

public class Main {

	public static void main(String[] args) {

		try {
			new MiniJavaParser(System.in);
			Node root = MiniJavaParser.Goal();

			MyClasses my_classes = new MyClasses();
			//System.out.println(my_classes.getClass().toString());

			//build symbol table & check redefine errors
			root.accept(new BuildSTVisitor(), my_classes);
			if(PrintError.hasError()) {
				PrintError.printAll();
				return;
			}
			my_classes.update(); // update inheritance relationships and whole class tables
			my_classes.checkLoop(); // check inheritance loops 
			my_classes.checkOverride(); // check override problems
			my_classes.checkType(); // check if there is undefined class types
			if(PrintError.hasError()) {
				PrintError.printAll();
				return;
			}
			
			// check undefined problems
			root.accept(new CheckUndefinedVisitor(), my_classes);
			if(PrintError.hasError()) {
				PrintError.printAll();
				return;
			}
			
			//check type problems
			root.accept(new CheckTypeErrorVisitor(), my_classes);
			root.accept(new CheckInitializationVisitor(), my_classes);
			if(PrintError.hasError()) {
				PrintError.printAll();
				return;
			}
		} catch (TokenMgrError e) {

			// Handle Lexical Errors
			e.printStackTrace();
		} catch (ParseException e) {

			// Handle Grammar Errors
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}