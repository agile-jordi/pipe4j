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

		final long timestamp = System.currentTimeMillis();
		ThreadGroup threadGroup = new ThreadGroup("Pipeline");

		PipeThread[] threads = new PipeThread[pipeline.length];
		PipedInputStream lastIn;
		PipedOutputStream lastOut = new PipedOutputStream();
		threads[0] = new PipeThread(threadGroup, pipeline[0].toString(), null,
				lastOut, pipeline[0]);
		for (int i = 1; i < threads.length - 1; i++) {
			lastIn = new PipedInputStream(lastOut);
			lastOut = new PipedOutputStream();
			PipeThread thread = new PipeThread(threadGroup,
					pipeline[0].toString(), lastIn, lastOut, pipeline[i]);
			threads[i] = thread;
		}
		lastIn = new PipedInputStream(lastOut);
		threads[threads.length - 1] = new PipeThread(threadGroup,
				pipeline[0].toString(), lastIn, null,
				pipeline[threads.length - 1]);

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
