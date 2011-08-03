package pipe4j.core;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.concurrent.Callable;

public class CallablePipe<I extends Closeable, O extends Closeable> implements
		Callable<Result> {
	private I in;
	private O out;
	private final Pipe<I, O> pipe;

	public CallablePipe(Pipe<I, O> pipe) {
		this.pipe = pipe;
	}

	public I getIn() {
		return in;
	}

	public void setIn(I in) {
		this.in = in;
	}

	public O getOut() {
		return out;
	}

	public void setOut(O out) {
		this.out = out;
	}

	public Pipe<I, O> getPipe() {
		return pipe;
	}

	@Override
	public Result call() throws Exception {
		try {
			if (pipe instanceof ConnectorDecorator) {
				ConnectorDecorator<I, O> decorator = (ConnectorDecorator<I, O>) pipe;
				this.in = decorator.decorateIn(in);
				this.out = decorator.decorateOut(out);
			}

			pipe.run(in, out);
		} catch (Exception e) {
			ResultImpl result = new ResultImpl(Result.Type.FAILURE);
			result.setException(e);
			return result;
		} finally {
			close(in);
			close(out);
		}

		return new ResultImpl(Result.Type.SUCCESS);
	}

	private void close(Closeable closeable) {
		if (closeable instanceof Flushable) {
			try {
				((Flushable) closeable).flush();
			} catch (IOException e) {
			}
		}

		try {
			closeable.close();
		} catch (IOException e1) {
		}
	}
}
