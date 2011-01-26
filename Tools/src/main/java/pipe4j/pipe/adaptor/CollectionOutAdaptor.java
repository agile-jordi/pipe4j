package pipe4j.pipe.adaptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;

import pipe4j.core.Null;
import pipe4j.pipe.AbstractPipe;

public class CollectionOutAdaptor<E> extends
		AbstractPipe<BlockingQueue<E>, Null> {
	private Collection<E> collection = new ArrayList<E>();
	
	@Override
	public void run(BlockingQueue<E> in, Null out) throws Exception {
		E e;
		while (!cancelled() && !((e = in.take()) instanceof Null)) {
			this.collection.add(e);
		}
	}
}
