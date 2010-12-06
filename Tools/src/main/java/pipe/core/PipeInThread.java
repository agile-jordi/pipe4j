package pipe.core;

import java.io.OutputStream;

public class PipeInThread extends PipeThread {
	private final OutputStream os;
	private final PipeIn pipe;

	public PipeInThread(ThreadGroup threadGroup, String name, OutputStream os,
			PipeIn pipe) {
		super(threadGroup, name);
		this.os = os;
		this.pipe = pipe;
	}

	@Override
	public PipeIn getPipe() {
		return pipe;
	}

	@Override
	public void run() {
		try {
			pipe.run(os);
		} catch (Exception e) {
			setException(e);
		} finally {
			close();
		}
	}

	@Override
	public void close() {
		if (os != null) {
			try {
				os.flush();
			} catch (Exception e) {
				if (!hasError()) {
					setException(e);
				}
			}
			try {
				os.close();
			} catch (Exception e) {
				if (!hasError()) {
					setException(e);
				}
			}
		}
	}
}