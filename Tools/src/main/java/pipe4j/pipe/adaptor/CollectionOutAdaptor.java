package pipe4j.pipe.adaptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;

import pipe4j.core.Null;
import pipe4j.pipe.AbstractPipe;

public class CollectionOutAdaptor extends
		AbstractPipe<BlockingQueue<Object>, Null> {
	private Collection<Object> collection = new ArrayList<Object>();
	
	public Collection<Object> getCollection() {
		return collection;
	}
	
	@Override
	public void run(BlockingQueue<Object> in, Null out) throws Exception {
		Object o;
		while (!cancelled() && !((o = in.take()) instanceof Null)) {
			this.collection.add(o);
		}
	}
}
