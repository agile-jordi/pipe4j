package pipe4j.pipe.adaptor;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import pipe4j.core.Null;
import pipe4j.pipe.AbstractPipe;

public class CollectionInAdaptor<E> extends
		AbstractPipe<Null, BlockingQueue<E>> {
	private final Collection<E> collection;

	public CollectionInAdaptor(Collection<E> collection) {
		super();
		this.collection = collection;
	}

	@Override
	public void run(Null in, BlockingQueue<E> out) throws Exception {
		for (Iterator<E> iterator = collection.iterator(); !cancelled()
				&& iterator.hasNext();) {
			E e = iterator.next();
			out.put(e);
		}
	}
}
