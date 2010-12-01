package pipe;

import junit.framework.TestCase;

public class TestPipe extends TestCase {
	public void testPipe() throws Exception {
		ThreadGroup threadGroup = new Pipeline(new Pipe[] { 
				new Producer(), 
				new Middle(), 
				new Middle(),
				new Middle(), 
				new Middle(), 
				new Consumer() 
			}).run();


		assertEquals(0, threadGroup.activeCount());
	}
}
