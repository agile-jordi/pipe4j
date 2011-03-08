package pipe4j.core.connector;

import pipe4j.core.BlockingBuffer;
import pipe4j.core.BlockingBufferImpl;
import pipe4j.core.Null;
import pipe4j.core.PipeThread;

public class BlockingBufferPipeConnector extends AbstractPipeConnector {
	@Override
	protected boolean supports(Class<?> in, Class<?> out) {
		return BlockingBuffer.class.isAssignableFrom(in)
				&& BlockingBuffer.class.isAssignableFrom(out);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void connect(PipeThread pipe1, PipeThread pipe2) throws Exception {
		BlockingBuffer<Object> queue = new BlockingBufferImpl<Object>();	
		pipe1.setOut(queue);
		pipe2.setIn(queue);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void close(Object in, Object out) {
		BlockingBuffer bqi = (BlockingBuffer) in;
		bqi.clear();
		
		BlockingBuffer bqo = (BlockingBuffer) out;
		try {
			bqo.put(Null.INSTANCE);
		} catch (InterruptedException ignored) {
		}
	}
}
