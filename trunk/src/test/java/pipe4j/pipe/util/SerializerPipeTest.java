package pipe4j.pipe.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;
import pipe4j.core.LinearPipeline;
import pipe4j.pipe.adaptor.CollectionInAdaptor;
import pipe4j.pipe.adaptor.CollectionOutAdaptor;

public class SerializerPipeTest extends TestCase {
	public void testSerialization() throws Exception {
		CollectionOutAdaptor collectionOutAdaptor = new CollectionOutAdaptor();

		List<Object> in = new ArrayList<Object>(3);
		in.add(new MySerializable(1));
		in.add(new MySerializable(2));
		in.add(new MySerializable(3));

		LinearPipeline.run(new CollectionInAdaptor(in), new SerializerPipe(),
				new DeserializerPipe(), collectionOutAdaptor);

		Collection<Object> result = collectionOutAdaptor.getCollection();
		assertEquals(in, result);
	}

	private static class MySerializable implements Serializable {
		private static final long serialVersionUID = 6984904378596899931L;
		private long id;

		public MySerializable(long id) {
			this.id = id;
		}

		@Override
		public boolean equals(Object obj) {
			return new Long(id).equals(new Long(((MySerializable) obj).id));
		}
	}
}
