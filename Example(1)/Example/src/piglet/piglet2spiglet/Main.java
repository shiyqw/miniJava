package piglet.piglet2spiglet;


import java.io.ByteArrayInputStream;

import piglet.ParseException;
import piglet.PigletParser;
import piglet.TokenMgrError;
import piglet.syntaxtree.Node;
import piglet.visitor.P2SPVisitor;
import util.PrintBoard;


public class Main { 
 
    public static void main(String[] args) {
    	try {
    		new PigletParser(System.in);
    		Node root = PigletParser.Goal();
			root.accept(new P2SPVisitor());
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
    		new PigletParser(new ByteArrayInputStream(PrintBoard.getCode().getBytes()));
    		Node root = PigletParser.Goal();
    		PrintBoard.clear();
			root.accept(new P2SPVisitor());
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