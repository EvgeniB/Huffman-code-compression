

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Scanner;

public class HuffmanCodes {
char[] alphabet;
double[] weights;
String[] codes;
BinaryTreeCu tree;
HashMap<Character, String> map;
HashMap<String, Character> map_inverse;
	
	HuffmanCodes(char[] a, double[] w){
		alphabet = a;
		weights = w;
		codes = new String[a.length];
		
		MinHeapCu heap = new MinHeapCu();
		heap.heapify(weights, alphabet);
		
		double weight0 = 0, weight1 = 0;
		
		TreeNodeCu left = null;
		TreeNodeCu right= null;
		TreeNodeCu root = null;

		if(heap.size == 1){
			root = new TreeNodeCu(heap.extract_min().getCharacter(),
					heap.extract_min().getWeight(), null, true);
		}
		while(heap.size > 2){
			right  = heap.extract_min();
			left = heap.extract_min();
			weight0 = left.getWeight();
			weight1 = right.getWeight();
			System.out.println("weights " + weight0 + " " + weight1);
			
			root = new TreeNodeCu(' ', weight0 + weight1, null, false);
			root.setChild(left, 0);
			root.setChild(right, 1);
			root.getChild(0).setChild(root, -1);
			root.getChild(1).setChild(root, -1);
			heap.insert(root);
		}
		
		TreeNodeCu n1 = heap.extract_min();
		TreeNodeCu n2 = heap.extract_min();
		if(n1.getCharacter() == alphabet[0]){
		left  = n1;
		right = n2;
		} else {
		left = n2;
		right = n1;
		}
		weight0 = left.getWeight();
		weight1 = right.getWeight();
		System.out.println("weights " + weight0 + " " + weight1);
		
		root = new TreeNodeCu(' ', weight0 + weight1, null, false);
		root.setChild(left, 0);
		root.setChild(right, 1);
		root.getChild(0).setChild(root, -1);
		root.getChild(1).setChild(root, -1);
		heap.insert(root);
		
		BinaryTreeCu tree = new BinaryTreeCu(root, heap.leafs);
		for(int i=0;i<codes.length;i++){
			codes[i] = tree.getCode(i);
		}
		this.tree = tree;
		
		map = new HashMap<Character, String>();
		Character ch = 0;
		String str = null;
		for(int i=0;i<alphabet.length;i++){
			ch = alphabet[i];
			str = codes[i];
			
			map.put(ch, str);
		}
		
		map_inverse = new HashMap<String, Character>();
		ch = 0;
		str = null;
		for(int i=0;i<alphabet.length;i++){
			ch = alphabet[i];
			str = codes[i];
			
			map_inverse.put(str, ch);
		}
		
	}
	
	public void compress(File toRead, File toWrite) throws IOException{
		 Scanner filescan = new Scanner(toRead);
		 FileReader reader = new FileReader(toRead); 
		 FileOutputStream outputStream =
	                new FileOutputStream(toWrite);
		 
		    int chars = -1;
		    while(filescan.hasNextLine())  {
		        String line = filescan.nextLine();
		        chars += line.length() + 1;
		    }
		    
		 filescan.close();   
		    
		 byte[] bytes = ByteBuffer.allocate(4).putInt(chars).array();   
		 
		 outputStream.write(bytes);
		 
		 int ch = 0;
		 byte by;
		 String str = null, secondary = null;
		 
		 while(ch != -1){
			 if(str == null){
				 ch = reader.read();
				 if(ch == -1) break;
				 str = map.get((char)ch);
				 }
		 
		 /* read a character. check if its code is smaller than 8. if it is, read another
		  * one and append it. check if it's smaller than 8, if it is, append more. if 
		  * it's equal to a byte, write byte and continue. if it's bigger than a byte, 
		  * slice the code into 8 and n-8 bits. write byte and continue to work on n-8 
		  * bits. */ 
			 
		 if(str.length() == 8){
			by = (byte)Integer.parseInt(str, 2); 
			System.out.println(str);
			outputStream.write(by);
			str = null;
		 } else if(str.length() < 8){
			ch = reader.read();
			if(ch == -1){
				secondary = "";
				System.out.println("end");
				for(int i=0;i<8-str.length();i++)
					secondary += "0";
					str = str.concat(secondary);	
					by = (byte)Integer.parseInt(str, 2); 
					System.out.println(str);
					outputStream.write(by);
			} else {
			secondary = map.get((char)ch);
			//System.out.println(str + " " + secondary + " " + ch);
			}
			str = str.concat(secondary);
		 } else if(str.length() > 8){
			secondary = str.substring(8);  
			str = str.substring(0, 8);
			System.out.println(str);
			by = (byte)Integer.parseInt(str, 2); 
			outputStream.write(by);
			str = secondary;
		 }		 
		 
		 } 
		 reader.close();
		 outputStream.flush();
		 outputStream.close();
	}
	
	public void decompress(File toRead, File toWrite) throws IOException{
		FileInputStream inputStream = new FileInputStream(toRead);
		
		byte[] bytes = new byte[4];
		
		bytes[0] = (byte) inputStream.read();
		bytes[1] = (byte) inputStream.read();
		bytes[2] = (byte) inputStream.read();
		bytes[3] = (byte) inputStream.read();
		
		int size = java.nio.ByteBuffer.wrap(bytes).getInt();
		//int size = java.nio.ByteBuffer.wrap(bytes).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
		System.out.println("Size is: " + size);

		int data_byte;
		String string = "", append = "", zeros = "";
		StringBuilder string_builder = new StringBuilder();
		char ch;
		Object[] arr = new Object[2];
		int length = 0;
		int count = 0;
		
		FileWriter fileWriter = new FileWriter(toWrite);
		
		for(int i=0;i<size;i++){
			data_byte = inputStream.read();//bytes have to be broken down into codes 
			if(data_byte == -1 || count == size) {
				break;
			}
			append = Integer.toBinaryString(data_byte);
			length = append.length();
			//System.out.println(append + " " + append.length());
			for(int k=0;k<(8-length);k++){
				zeros = string_builder.append("0").toString();
				//System.out.println("inside loop " + zeros);
			}
			append = zeros + append;
			string_builder.setLength(0);
			zeros = "";
			//System.out.println("append " + append + " " + append.length());
			string += append;
			append = "";
			while(checkAllSubstrings(string)){
				arr = getCharacter(string);
				ch = (char) arr[0];
				string = (String) arr[1];
				//System.out.println(ch);
				count++;
				//System.out.println(count);
				fileWriter.write(ch);
				if(count == size) break;
			}
		}
		
		fileWriter.flush();
		fileWriter.close();
		inputStream.close();
	}
	
	public boolean checkAllSubstrings(String str){
		if(map_inverse.containsKey(str))
			return true;
		for(int i=0;i<str.length();i++){
		if(map_inverse.containsKey(str.substring(0, i)))
			return true;
					
		}
		
		return false;
	}
	
	public Object[] getCharacter(String str){
		Object[] arr = new Object[2];
		if(map_inverse.containsKey(str)){
			arr[0] = map_inverse.get(str);
			arr[1] = "";
			return arr;
			}
		for(int i=0;i<str.length();i++){
		if(map_inverse.containsKey(str.substring(0, i))){
			arr[0] = map_inverse.get(str.substring(0, i));
			arr[1] = str.substring(i);
			return arr;
			}		
		}
		
		return null;
	}
	
	public void printCodes(){
		System.out.println("Character\tWeights\t\tCode");
		for(int i=0;i<codes.length;i++){
		System.out.println(Character.toString(alphabet[i]) + "\t\t" + weights[i] + "\t\t" + codes[i]);
		}
	}
}
