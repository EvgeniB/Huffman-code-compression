

public class MinHeapCu implements MinHeapInterfaceCu{
TreeNodeCu[] heap;
TreeNodeCu[] leafs;
int size;
	
	@Override
	public void create_heap() {
	}
	
	public void create_heap(int s) {
		heap = new TreeNodeCu[s];
		size = 0;
	}

	@Override
	public void heapify(double[] elements, char[] symbols) {
		heap = new TreeNodeCu[elements.length];
		leafs = new TreeNodeCu[elements.length];
		size = 0;
		TreeNodeCu current = null;
		for(int i=0;i<elements.length;i++){
			current = new TreeNodeCu(symbols[i], elements[i], null, true);
			leafs[i] = current;
			insert(current);
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public TreeNodeCu find_min() {
		return heap[0];
	}

	@Override
	public void insert(TreeNodeCu i) {
		if(size<heap.length){
		heap[size] = i;
		int current = size;
		int parent = 0;
		TreeNodeCu temp = null;
		while(current!=0){
			parent = (current-1)/2;
					//(int) Math.floor(heap[current].getWeight()/2);
			if (heap[parent].getWeight()>heap[current].getWeight()){
				temp = heap[parent];
				heap[parent] = heap[current];
				heap[current] = temp;
				current = parent;
			} else break;
		}
		size++;
		System.out.println("insert");
		for(int j=0;j<size;j++){
			System.out.println(heap[j].getWeight());	
			}
			System.out.println();

		}else System.out.println("not enough space in the heap");
	}

	@Override
	public TreeNodeCu extract_min() {
		if(size>0){
		TreeNodeCu temp = heap[0];
		delete_min();
		System.out.println("delete");
		for(int j=0;j<size;j++){
			System.out.println(heap[j].getWeight());	
			}
			System.out.println();
		return temp;
		} else return null;
	}

	@Override
	public void delete_min() {
		if(size>0){
		heap[0] = heap[size-1];
		size--;
		int current = 0;
		int child0 = 0, child1 = 0;
		TreeNodeCu temp = null;
		while(current < size){
			child0 = current*2+1;
			child1 = current*2+2;
			if(child0 >= size && child1 >= size)break;
			if(child0 >= size && child1 < size){
				if(heap[child1].getWeight()<heap[current].getWeight()){
					temp = heap[child1];
					heap[child1] = heap[current];
					heap[current] = temp;
					current = child1;
				}
			}
			if(child0 < size && child1 >= size){
				if (heap[child0].getWeight()<heap[current].getWeight()){
					temp = heap[child0];
					heap[child0] = heap[current];
					heap[current] = temp;
					current = child0;
				}
			}
			if(heap[child0].getWeight()>heap[current].getWeight() 
					&& heap[child1].getWeight()>heap[current].getWeight())break;
			
				if(heap[child0].getWeight() <= heap[child1].getWeight()){
					temp = heap[child0];
					heap[child0] = heap[current];
					heap[current] = temp;
					current = child0;
				} else {
					temp = heap[child1];
					heap[child1] = heap[current];
					heap[current] = temp;
					current = child1;
				}
			/*
				if (heap[child0].getWeight()<heap[current].getWeight()){
					temp = heap[child0];
					heap[child0] = heap[current];
					heap[current] = temp;
					current = child0;
				} 
				if(heap[child1].getWeight()<heap[current].getWeight()){
					temp = heap[child1];
					heap[child1] = heap[current];
					heap[current] = temp;
					current = child1;
				}*/
			}
		} else System.out.println("Can't remove an element from"
				+ "the heap when there is less than one element.");
	}
	
	public void printHeap(){
		for(int i=0;i<heap.length;i++){
		System.out.println(heap[i].getWeight());	
		}
	}

}
