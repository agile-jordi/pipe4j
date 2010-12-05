package pipe2.core;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Pipeline {
	public static ThreadGroup run(Pipe[] pipeline) throws Exception {
		return run(pipeline, -1);
	}

	public static ThreadGroup run(Pipe[] pipeline, long timeoutMilis)
			throws Exception {
		final long timestamp = System.currentTimeMillis();
		ThreadGroup threadGroup = new ThreadGroup("Pipeline");

		PipeThread[] threads = new PipeThread[pipeline.length];
		PipedInputStream lastIn;
		PipedOutputStream lastOut = new PipedOutputStream();
		threads[0] = new PipeThread(null, lastOut, pipeline[0]);
		for (int i = 1; i < threads.length - 1; i++) {
			lastIn = new PipedInputStream(lastOut);
			PipeThread thread = new PipeThread(lastIn, lastOut, pipeline[i]);
			threads[i] = thread;
		}
		lastIn = new PipedInputStream(lastOut);
		threads[threads.length - 1] = new PipeThread(lastIn, null,
				pipeline[threads.length - 1]);

		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++) {
			if (timeoutMilis > 0) {
				// Wait for the requested perid, minus elapsed time
				threads[i].join(timeoutMilis
						- (System.currentTimeMillis() - timestamp));
				if (threads[i].isAlive()) {
					threads[i].getPipe().cancel();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ignored) {
					}
				}
				
				if (threads[i].isAlive()) {
					threads[i].interrupt();
					threads[i].close();
				}
			} else {
				// Wait forever
				threads[i].join();
			}
		}

		// If exception happened, throw first found
		for (int i = 0; i < threads.length; i++) {
			if (threads[i].hasError()) {
				throw threads[i].getException();
			}
		}
		return threadGroup;
	}
}
