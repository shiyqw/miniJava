/**
 * 表示标识符的类，可用于表示变量
 */
package minijava.symboltable;

public class MIdentifier extends MType {
	public MIdentifier(String v_name, int v_line, int v_column) {
		super(v_line, v_column);
		name = v_name;
	}
}

