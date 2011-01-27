package pipe4j.pipe.adaptor;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import pipe4j.core.Null;
import pipe4j.pipe.AbstractPipe;

public class CollectionInAdaptor extends
		AbstractPipe<Null, BlockingQueue<Object>> {
	private final Collection<?> collection;

	public CollectionInAdaptor(Collection<?> collection) {
		super();
		this.collection = collection;
	}

	@Override
	public void run(Null in, BlockingQueue<Object> out) throws Exception {
		for (Iterator<?> iterator = collection.iterator(); !cancelled()
				&& iterator.hasNext();) {
			out.put(iterator.next());
		}
	}
}
