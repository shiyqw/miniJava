package kanga.kanga2mips;

import java.io.ByteArrayInputStream;

import util.PrintBoard;
import kanga.KangaParser;
import kanga.ParseException;
import kanga.TokenMgrError;
import kanga.syntaxtree.Node;
import kanga.visitor.K2MVisitor;

public class Main {

	public static void main(String[] args) {
		try {
    		new KangaParser(System.in);
			Node root = KangaParser.Goal();
    		/*
    		 * TODO: Implement your own Visitors and other classes.
    		 * 
    		 */
    		//Traverse the Abstract Grammar Tree
    		root.accept(new K2MVisitor(), 0);
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
	
	public static void translate() {
		try {
    		new KangaParser(new ByteArrayInputStream(PrintBoard.getCode().getBytes()));
			Node root = KangaParser.Goal();
    		/*
    		 * TODO: Implement your own Visitors and other classes.
    		 * 
    		 */
    		//Traverse the Abstract Grammar Tree
			PrintBoard.clear();
    		root.accept(new K2MVisitor(), 0);
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