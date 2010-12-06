package pipe2.core;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractPipe implements Pipe {
	private AtomicBoolean cancel = new AtomicBoolean(false);

	@Override
	public void cancel() {
		cancel.set(true);
	}

	protected boolean isCancelled() {
		return this.cancel.get();
	}
}
