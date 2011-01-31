package pipe4j.core.connector;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import pipe4j.core.Null;
import pipe4j.core.PipeThread;

public class BlockingQueuePipeConnector extends AbstractPipeConnector {
	@Override
	protected boolean supports(Class<?> in, Class<?> out) {
		return BlockingQueue.class.isAssignableFrom(in)
				&& BlockingQueue.class.isAssignableFrom(out);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void connect(PipeThread pipe1, PipeThread pipe2) throws Exception {
		BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(1);	
		pipe1.setOut(queue);
		pipe2.setIn(queue);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void close(Object in, Object out) {
		BlockingQueue bqi = (BlockingQueue) in;
		bqi.clear();
		
		BlockingQueue bqo = (BlockingQueue) out;
		try {
			bqo.put(Null.INSTANCE);
		} catch (InterruptedException ignored) {
		}
	}
}
