package pipe4j.pipe.adaptor;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;
import pipe4j.core.Pipeline;

public class CollectionAdaptorTest extends TestCase {
	public void testAdaptor() throws Exception {
		Collection<Integer> coll = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			coll.add(i);
		}

		CollectionOutAdaptor collectionOutAdaptor = new CollectionOutAdaptor();
		Pipeline.run(new CollectionInAdaptor(coll), collectionOutAdaptor);
		
		assertEquals(coll, collectionOutAdaptor.getCollection());
	}
}
