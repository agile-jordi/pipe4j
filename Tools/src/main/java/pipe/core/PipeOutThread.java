package pipe.core;

import java.io.InputStream;

public class PipeOutThread extends PipeThread {
	private final InputStream is;
	private final PipeOut pipe;

	public PipeOutThread(ThreadGroup threadGroup, String name, InputStream is,
			PipeOut pipe) {
		super(threadGroup, name);
		this.is = is;
		this.pipe = pipe;
	}

	@Override
	public PipeOut getPipe() {
		return pipe;
	}

	@Override
	public void run() {
		try {
			pipe.run(is);
		} catch (Exception e) {
			setException(e);
		} finally {
			close();
		}
	}

	@Override
	public void close() {
		if (is != null) {
			try {
				is.close();
			} catch (Exception e) {
				if (!hasError()) {
					setException(e);
				}
			}
		}
	}
}