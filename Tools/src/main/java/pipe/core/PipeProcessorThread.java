package pipe.core;

import java.io.InputStream;
import java.io.OutputStream;

public class PipeProcessorThread extends PipeThread {
	private final InputStream is;
	private final OutputStream os;
	private final PipeProcessor pipe;

	public PipeProcessorThread(ThreadGroup threadGroup, String name,
			InputStream is, OutputStream os, PipeProcessor pipe) {
		super(threadGroup, name);
		this.is = is;
		this.os = os;
		this.pipe = pipe;
	}

	@Override
	public PipeProcessor getPipe() {
		return pipe;
	}

	@Override
	public void run() {
		try {
			pipe.run(is, os);
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