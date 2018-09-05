import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>{
	private Node first = null;
	private Node last = null;
	private int size = 0;
	
	private class Node {
		Item item;
		Node next; 
	}
	
	public Deque(){}
	
	public boolean isEmpty(){
		return first == null;
	}
	public int size(){
		return size;
	}
	public void addFirst(Item item){
		if(item == null){
			throw new java.lang.IllegalArgumentException();
		}
		Node nn = new Node();
		nn.item = item;
		if(isEmpty()){
			first = nn;
			first.next = null;
			last = first;	
		}
		else{
			Node oldfirst = first;
			first = nn;
			first.next = oldfirst;
		}
		size++;
	}
	public void addLast(Item item){
		if(item == null){
			throw new java.lang.IllegalArgumentException();
		}
		Node nn = new Node();
		nn.item = item;
		if(isEmpty()){
			first = nn;
			first.next = null;
			last = first;
		}
		else{
			last.next = nn;
			last = nn;
			last.next = null;
		}
		size++;
	}
	public Item removeFirst(){
		if(isEmpty()){
			throw new java.util.NoSuchElementException();
		}
		Node tr = first;
		first = tr.next;
		size--;
		return tr.item;
	}
	public Item removeLast(){
		if(isEmpty()){
			throw new java.util.NoSuchElementException();
		}
		if(first.next == null){
			Node tr = first;
			first = tr.next;
			size--;
			return tr.item;
		}
		else{
			Node current = first;
			while(current.next.next != null){
				current = current.next;
			}
			Node tr = current.next;
			last = current;
			last.next = null;
			size--;
			return tr.item;
		}
	}
	public Iterator<Item> iterator() {
		return new DequeueIterator();
	}
	
	private class DequeueIterator implements Iterator<Item>{
		private Node current = first;
		public boolean hasNext(){
			return current != null;
		}
		public void remove(){
			throw new java.lang.UnsupportedOperationException();
		}
		public Item next(){
			if(!hasNext()){
				throw new java.util.NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
