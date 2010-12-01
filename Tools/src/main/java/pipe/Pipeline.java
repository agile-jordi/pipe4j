package pipe;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Pipeline {
	private List<Pipe> pipeline = new ArrayList<Pipe>();
	private ThreadGroup threadGroup;

	public Pipeline() {
	}

	public Pipeline(Pipe[] pipeline) {
		this(Arrays.asList(pipeline));
	}

	public Pipeline(List<Pipe> pipeline) {
		this();
		for (Iterator<Pipe> iterator = pipeline.iterator(); iterator.hasNext();) {
			Pipe pipe = iterator.next();
			addPipe(pipe);
		}
	}

	public Pipeline addPipe(Pipe pipe) {
		return addPipe(pipe, new PipeConfiguration());
	}

	public Pipeline addPipe(Pipe pipe, long timeoutMillis) {
		PipeConfiguration pipeConfiguration = new PipeConfiguration();
		pipeConfiguration.setTimeoutMillis(timeoutMillis);
		return addPipe(pipe, pipeConfiguration);
	}

	public Pipeline addPipe(Pipe pipe, PipeConfiguration pipeConfiguration) {
		pipe.setPipeConfiguration(pipeConfiguration);
		pipeline.add(pipe);
		return this;
	}

	public ThreadGroup getThreadGroup() {
		return threadGroup;
	}

	public ThreadGroup run() throws Exception {
		PipeThread[] threads = buildThreadGroup();
		return startSync(threads);
	}
	
	public ThreadGroup runNoWait() throws Exception {
		final PipeThread[] threads = buildThreadGroup();
		new Thread() {
			public void run() {
				try {
					startSync(threads);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
			};
		}.start();
		return this.threadGroup;
	}

	private ThreadGroup startSync(PipeThread[] threads)
			throws InterruptedException {
		for (int i = 0; i < pipeline.size(); i++) {
			threads[i].getPipe().setRunning(true);
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++) {
			PipeThread thread = threads[i];
			Pipe pipe = thread.getPipe();
			PipeConfiguration config = pipe.getPipeConfiguration();
			if (config != null && config.hasTimeout()) {
				thread.join(config.getTimeoutMillis());
				pipe.setRunning(false);
				if (thread.isAlive()) {
					thread.interrupt();
					System.out.println("Timeout exceeded for pipe "
							+ pipe.toString()
							+ ". Thread was interrupted. State is "
							+ thread.getState());
				}
			} else {
				thread.join();
				pipe.setRunning(false);
			}
		}

		PipelineException exception = null;
		for (int i = 0; i < threads.length; i++) {
			PipeThread thread = threads[i];
			if (thread.hasException()) {
				if (exception == null) {
					exception = new PipelineException(
							"Exception detected in pipeline!",
							thread.getException());
				} else {
					exception.addThrowable(thread.getException());
				}
			}
		}

		if (exception != null) {
			throw exception;
		}

		return threadGroup;
	}

	private PipeThread[] buildThreadGroup() throws IOException {
		if (threadGroup != null) {
			threadGroup.destroy();
		}

		if (pipeline == null || pipeline.size() < 2) {
			throw new IllegalArgumentException("Need at least 2 pipes!");
		}

		threadGroup = new ThreadGroup("Pipeline");
		PipeThread[] threads = new PipeThread[pipeline.size()];
		PipedWriter lastOut = new PipedWriter();
		PipedReader lastIn;
		pipeline.get(0).setWriter(lastOut);
		threads[0] = new PipeThread(threadGroup, pipeline.get(0));

		for (int i = 1; i < pipeline.size() - 1; i++) {
			lastIn = new PipedReader(lastOut);
			lastOut = new PipedWriter();

			Pipe pipedRunnable = pipeline.get(i);
			pipedRunnable.setReader(lastIn);
			pipedRunnable.setWriter(lastOut);
			threads[i] = new PipeThread(threadGroup, pipedRunnable);
		}

		lastIn = new PipedReader(lastOut);
		pipeline.get(pipeline.size() - 1).setReader(lastIn);
		threads[threads.length - 1] = new PipeThread(threadGroup,
				pipeline.get(pipeline.size() - 1));
		return threads;
	}
}