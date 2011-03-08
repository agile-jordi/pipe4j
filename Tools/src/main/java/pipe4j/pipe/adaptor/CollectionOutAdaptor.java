package pipe4j.pipe.adaptor;

import java.util.ArrayList;
import java.util.Collection;

import pipe4j.core.BlockingBuffer;
import pipe4j.core.Null;
import pipe4j.pipe.AbstractPipe;

public class CollectionOutAdaptor extends
		AbstractPipe<BlockingBuffer<Object>, Null> {
	private Collection<Object> collection = new ArrayList<Object>();

	public Collection<Object> getCollection() {
		return collection;
	}

	@Override
	public void run(BlockingBuffer<Object> in, Null out) throws Exception {
		Object o;
		while (!cancelled() && (o = in.take()) != null) {
			this.collection.add(o);
		}
	}
}
