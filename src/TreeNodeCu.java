

public class TreeNodeCu {
private TreeNodeCu parent, left, right;
private boolean leaf;
private char character;
private double weight;

TreeNodeCu(char c, double w, TreeNodeCu p, boolean b){
	parent = p;
	character = c;
	weight = w;
	leaf = b;
	left = null;
	right = null;
	}

public void setCharacter(char c){
	character = c;
	}

public char getCharacter(){
	return character;
	}

public void setWeight(int w){
	weight = w;
	}

public double getWeight(){
	return weight;
	}

public void setLeaf(boolean b){
	leaf = b;
	}

public boolean isLeaf(){
	return leaf;
	}

public void setChild(TreeNodeCu n, int i){ //i=-1 for parent
										   //i=0  for left
										   //i=1  for right
	if(i==-1) parent = n;
	else
	if(i==0) left = n;
	else 
	if(i==1) right= n;
	else return;
	}

public int whichChild(){
	if(this == parent.getChild(0))
		return 0;
	else
	if(this == parent.getChild(1))
		return 1;
	return -1;
}

public TreeNodeCu getChild(int i){ /* -1 for parent
									   0 for left
									   1 for right
 									*/
	if(i==-1)return parent;
	if(i==0) return left;
	else
	if(i==1) return right;
	else return null;
	}
}
