import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{
	
	private Node first = null;
	private int size = 0;
	
	private class Node {
		Item item;
		Node next;
		Node prev;
	}
	
	public RandomizedQueue(){
		
	}

	public boolean isEmpty(){
		return first == null;
	}
	
	public int size(){
		return size;
	}
	
	public void enqueue(Item item){
		if(item == null){
			throw new java.lang.IllegalArgumentException();
		}
		Node nn = new Node();
		nn.item = item;
		if(isEmpty()){
			first = nn;
			first.next = null;
			first.prev = null;
		}
		else{
			Node oldfirst = first;
			first = nn;
			first.next = oldfirst;
			first.next.prev = first;
			first.prev = null;
		}
		size++;
	}
	
	public Item dequeue(){
		if(isEmpty()){
			throw new java.util.NoSuchElementException();
		}
		if(size == 1){
			size--;
			Item i = first.item;
			first = null;
			return i;
		}
		int i = StdRandom.uniform(size);
		Node current = first;
		while(i != 0){
			current = current.next;
			i--;
		}
		if(current.prev == null){
			first = current.next;
			first.prev = null;
			size--;
			return current.item;
		}
		else if(current.next == null){
			current.prev.next = null;
			size--;
			return current.item;
		}
		else{
			current.prev.next = current.next;
			current.next.prev = current.prev;
			size--;
			return current.item;
		}
		
	}
	public Item sample(){
		if(isEmpty()){
			throw new java.util.NoSuchElementException();
		}
		int i = StdRandom.uniform(size);
		Node current = first;
		while(i != 0){
			current = current.next;
			i--;
		}
		return current.item;
	}
	
	public Iterator<Item> iterator() {
		return new RandomizeQueueIterator();
	}
	
	private class RandomizeQueueIterator implements Iterator<Item>{
		private Item[] randq;
		private int index = 0;
		private int n = size;
		
		public RandomizeQueueIterator(){
			randq = (Item[]) new Object[size];
			Node current = first;
			for(int i = 0; i < size; i++){
				randq[i] = current.item;
				current = current.next;
			}
			StdRandom.shuffle(randq);
		}
		
		public boolean hasNext(){
			return index < n;
		}
		
		public void remove(){
			throw new java.lang.UnsupportedOperationException();
		}
		
		public Item next(){
			if(!hasNext()){
				throw new java.util.NoSuchElementException();
			}
			return randq[index++];
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
