package pipe4j.core;

import pipe4j.core.connector.PipeConnectorHelper;

/**
 * Utility class to assemble and execute pipelines.
 * 
 * @author bbennett
 */
public class Pipeline {
	@SuppressWarnings("rawtypes")
	public static PipelineInfo run(Pipe... pipeline) {
		return run(-1, pipeline);
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public static PipelineInfo run(long timeoutMilis, Pipe... pipeline) {
		if (pipeline == null || pipeline.length < 2) {
			throw new IllegalArgumentException("Need at least 2 pipes!");
		}

		final long timestamp = System.currentTimeMillis();
		ThreadGroup threadGroup = new ThreadGroup("Pipeline");

		PipeThread[] threads = new PipeThread[pipeline.length];
		threads[0] = new PipeThread(pipeline[0]);
		threads[0].setIn(Null.INSTANCE);
		for (int i = 1; i < pipeline.length; i++) {
			threads[i] = new PipeThread(pipeline[i]);
			PipeConnectorHelper.connect(threads[i - 1], threads[i]);
		}
		threads[threads.length - 1].setOut(Null.INSTANCE);

		for (Thread thread : threads) {
			thread.start();
		}

		for (PipeThread thread : threads) {
			if (timeoutMilis > 0) {
				// Wait for the requested perid, minus elapsed time
				long discountedTimeout = Math
						.max(0, timeoutMilis
								- (System.currentTimeMillis() - timestamp));
				try {
					thread.join(discountedTimeout);
				} catch (InterruptedException e) {
				}
				if (thread.isAlive()) {
					thread.getPipe().cancel();
					try {
						Thread.sleep(100);
					} catch (InterruptedException ignored) {
					}
				}

				if (thread.isAlive()) {
					thread.interrupt();
					thread.close();
					try {
						Thread.sleep(100);
					} catch (InterruptedException ignored) {
					}
				}

				if (thread.isAlive()) {
					thread.stop(new InterruptedException("Thread stopped!"));
				}
			} else {
				// Wait forever
				try {
					thread.join();
				} catch (InterruptedException e) {
				}
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
