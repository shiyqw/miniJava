package spiglet.spiglet2kanga;

import java.io.ByteArrayInputStream;

import spiglet.ParseException;
import spiglet.SpigletParser;
import spiglet.TokenMgrError;
import spiglet.syntaxtree.Node;
import spiglet.visitor.*;
import util.PrintBoard;




public class Main { 
 
    public static void main(String[] args) {
    	try {
    		new SpigletParser(System.in);
			Node root = SpigletParser.Goal();
    		/*
    		 * TODO: Implement your own Visitors and other classes.
    		 * 
    		 */
    		//Traverse the Abstract Grammar Tree
    		AllCFG allCFG = new AllCFG();
    		root.accept(new BuildCFGVisitor(), allCFG);
    		root.accept(new Sp2KVisitor(), allCFG);
    	}
    	catch(TokenMgrError e){
    		//Handle Lexical Errors
    		e.printStackTrace();
    	}
    	catch (ParseException e){
    		//Handle Grammar Errors
    		e.printStackTrace();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
    
    public static void translate() {
    	try {
    		new SpigletParser(new ByteArrayInputStream(PrintBoard.getCode().getBytes()));
			Node root = SpigletParser.Goal();
			PrintBoard.clear();
    		/*
    		 * TODO: Implement your own Visitors and other classes.
    		 * 
    		 */
    		//Traverse the Abstract Grammar Tree
    		AllCFG allCFG = new AllCFG();
    		root.accept(new BuildCFGVisitor(), allCFG);
    		root.accept(new Sp2KVisitor(), allCFG);
    	}
    	catch(TokenMgrError e){
    		//Handle Lexical Errors
    		e.printStackTrace();
    	}
    	catch (ParseException e){
    		//Handle Grammar Errors
    		e.printStackTrace();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
}