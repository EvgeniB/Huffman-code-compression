

import java.io.File;
import java.io.IOException;

public class HuffmanMain {

	public static void main(String[] args) throws IOException {
		char[] c = {'a', 'b', 'c', 'd', '\n'};
		double[] w = {0.4, 0.35, 0.2, 0.05, 0.01};
		
		HuffmanCodes code = new HuffmanCodes(c, w);
		code.printCodes();
		System.out.println("Pre:");
		code.tree.printPreTree(code.tree.root);
		System.out.println("\nIn:");
		code.tree.printInTree(code.tree.root);
		System.out.println("\n");
		
		File read = new File("read.txt");
		File write = new File("write");
		File original = new File("original.txt");
		
		code.compress(read, write);
		code.decompress(write, original);
	}

}
