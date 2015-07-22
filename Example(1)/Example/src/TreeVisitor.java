// The classes are basically the same as the BinaryTree 
// file except the visitor classes and the accept method
// in the Tree class

class TreeVisitor{
    public static void main(String[] a){
	//System.out.println(new TV().Start(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2));
    	System.out.println(new TV().getArray());
    }
}

class TV{
	int[] array;
	public int Start(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k, int l, 
			int m, int n, int o, int p, int q, int r, int s, int t, int u, int v, int w, int x, int y, int z) {
		return z;
	}
	public int getArray() {
		array = new int[2];
		array[0] = 0;
		array[1] = 1;
		array[2] = 2;
		return 0;
	}
}