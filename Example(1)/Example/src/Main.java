import util.PrintBoard;


public class Main {

	public static void main(String[] args) {
		boolean flag = minijava.minijava2piglet.Main.translate();
		if(!flag) {
			return;
		}
		//PrintBoard.output();
		piglet.piglet2spiglet.Main.translate();
		//PrintBoard.output();
		spiglet.spiglet2kanga.Main.translate();
		//PrintBoard.output();
		kanga.kanga2mips.Main.translate();
	}
}
