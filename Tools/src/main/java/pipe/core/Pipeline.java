package pipe.core;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public abstract class Pipeline {
	public static PipelineInfo run(Pipe[] pipeline) throws Exception {
		return run(pipeline, -1);
	}

	public static PipelineInfo run(Pipe[] pipeline, long timeoutMilis)
			throws Exception {
		if (pipeline == null || pipeline.length < 2) {
			throw new IllegalArgumentException("Need at least 2 pipes!");
		}

		if (!(pipeline[0] instanceof PipeIn)) {
			throw new IllegalArgumentException(
					"First pipe must be an instance of "
							+ PipeIn.class.toString());
		}

		for (int i = 1; i < pipeline.length - 1; i++) {
			if (!(pipeline[i] instanceof PipeProcessor)) {
				throw new IllegalArgumentException(
						"First pipe must be an instance of "
								+ PipeProcessor.class.toString());
			}
		}

		if (!(pipeline[pipeline.length - 1] instanceof PipeOut)) {
			throw new IllegalArgumentException(
					"Last pipe must be an instance of "
							+ PipeOut.class.toString());
		}

		final long timestamp = System.currentTimeMillis();
		ThreadGroup threadGroup = new ThreadGroup("Pipeline");

		PipeThread[] threads = new PipeThread[pipeline.length];
		PipedInputStream lastIn;
		PipedOutputStream lastOut = new PipedOutputStream();
		threads[0] = new PipeInThread(threadGroup, pipeline[0].toString(),
				lastOut, (PipeIn) pipeline[0]);
		for (int i = 1; i < threads.length - 1; i++) {
			lastIn = new PipedInputStream(lastOut);
			lastOut = new PipedOutputStream();
			PipeProcessorThread thread = new PipeProcessorThread(threadGroup,
					pipeline[0].toString(), lastIn, lastOut,
					(PipeProcessor) pipeline[i]);
			threads[i] = thread;
		}
		lastIn = new PipedInputStream(lastOut);
		threads[threads.length - 1] = new PipeOutThread(threadGroup,
				pipeline[0].toString(), lastIn,
				(PipeOut) pipeline[threads.length - 1]);

		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++) {
			if (timeoutMilis > 0) {
				// Wait for the requested perid, minus elapsed time
				long discountedTimeout = Math
						.max(0, timeoutMilis
								- (System.currentTimeMillis() - timestamp));
				threads[i].join(discountedTimeout);
				if (threads[i].isAlive()) {
					threads[i].getPipe().cancel();
					try {
						Thread.sleep(100);
					} catch (InterruptedException ignored) {
					}
				}

				if (threads[i].isAlive()) {
					threads[i].interrupt();
					threads[i].close();
					try {
						Thread.sleep(100);
					} catch (InterruptedException ignored) {
					}
				}

				if (threads[i].isAlive()) {
					threads[i]
							.stop(new InterruptedException("Thread stopped!"));
				}
			} else {
				// Wait forever
				threads[i].join();
			}
		}

		// If exception happened, get first found
		PipelineInfo info = new PipelineInfo(threadGroup);
		for (int i = 0; i < threads.length; i++) {
			if (threads[i].hasError()) {
				info.setException(threads[i].getException());
				break;
			}
		}

		return info;
	}
}
