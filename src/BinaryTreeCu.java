

public class BinaryTreeCu {
TreeNodeCu root;
TreeNodeCu[] leafs;

BinaryTreeCu(){
	root = null;
}

BinaryTreeCu(TreeNodeCu r, TreeNodeCu[] h){
	root = r;
	leafs = h;
	}

public String getCode(int i){
	String result = "";
	String code = "";
	TreeNodeCu current = leafs[i];
	if(current == null)return null;
	while(current.getChild(-1) != null){
		code = String.valueOf(current.whichChild());
		result = result.concat(code);
		current = current.getChild(-1);
	}
	result = new StringBuilder(result).reverse().toString();
	return result;
}

public void printTree(TreeNodeCu current){
}

public void printInTree(TreeNodeCu current){
	if(current.getChild(0) != null) printInTree(current.getChild(0));
	System.out.print(current.getWeight() + " ");
	if(current.getChild(1) != null) printInTree(current.getChild(1));
}

public void printPreTree(TreeNodeCu current){
	System.out.print(current.getWeight() + " ");
	if(current.getChild(0) != null) printPreTree(current.getChild(0));
	if(current.getChild(1) != null) printPreTree(current.getChild(1));
}

}
