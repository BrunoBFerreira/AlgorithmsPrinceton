import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);
		while(!StdIn.isEmpty()){
				rq.enqueue(StdIn.readString());
		}
		
		Iterator<String> it = rq.iterator();
		for(int i = 0; i < k; i++){
			StdOut.println(it.next() + " ");
		}
	}

}
