package pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Pipeline {
	private List<Pipe> pipeline = new ArrayList<Pipe>();
	private ThreadGroup threadGroup;
	private PipeThread[] threads;

	public static Pipeline runPipeline(Pipe[] pipeline) throws Exception {
		Pipeline instance = new Pipeline(pipeline);
		instance.run();
		return instance;
	}
	
	public static Pipeline runPipeline(List<Pipe> pipeline) throws Exception {
		Pipeline instance = new Pipeline(pipeline);
		instance.run();
		return instance;
	}
	
	public static Pipeline runPipelineNoWait(Pipe[] pipeline) throws Exception {
		Pipeline instance = new Pipeline(pipeline);
		instance.runNoWait();
		return instance;
	}
	
	public static Pipeline runPipelineNoWait(List<Pipe> pipeline) throws Exception {
		Pipeline instance = new Pipeline(pipeline);
		instance.runNoWait();
		return instance;
	}
	
	public Pipeline() {
	}

	public Pipeline(Pipe[] pipeline) {
		this(pipeline == null ? null : Arrays.asList(pipeline));
	}

	public Pipeline(List<Pipe> pipeline) {
		this();
		if (pipeline != null) {
			for (Iterator<Pipe> iterator = pipeline.iterator(); iterator
					.hasNext();) {
				Pipe pipe = iterator.next();
				addPipe(pipe);
			}
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
		buildThreadGroup();
		return startSync();
	}

	public ThreadGroup runNoWait() throws Exception {
		buildThreadGroup();
		new Thread() {
			public void run() {
				try {
					startSync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
		return this.threadGroup;
	}

	public boolean isRunning() {
		ThreadGroup threadGroup = this.threadGroup;
		return threadGroup != null && !threadGroup.isDestroyed();
	}

	public void cancel() {
		for (int i = 0; i < threads.length; i++) {
			PipeThread thread = threads[i];
			cancelThread(thread);
		}
	}
	
	private ThreadGroup startSync()
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
				cancelThread(thread);
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

		threadGroup.destroy();
		return threadGroup;
	}

	private void cancelThread(PipeThread thread) {
		thread.getPipe().setRunning(false);
		if (thread.isAlive()) {
			thread.interrupt();
			System.out.println("Timeout exceeded for pipe "
					+ thread.getPipe().toString()
					+ ". Thread was interrupted. State is "
					+ thread.getState());
		}
	}

	private void buildThreadGroup() throws IOException {
		if (isRunning()) {
			throw new IllegalStateException("Pipeline running!");
		}

		if (pipeline == null || pipeline.size() < 2) {
			throw new IllegalArgumentException("Need at least 2 pipes!");
		}

		threadGroup = new ThreadGroup("Pipeline");
		threads = new PipeThread[pipeline.size()];
		PipedOutputStream lastOut = new PipedOutputStream();
		PipedInputStream lastIn;
		pipeline.get(0).setOutputStream(lastOut);
		threads[0] = new PipeThread(threadGroup, pipeline.get(0));

		for (int i = 1; i < pipeline.size() - 1; i++) {
			lastIn = new PipedInputStream(lastOut);
			lastOut = new PipedOutputStream();

			Pipe pipedRunnable = pipeline.get(i);
			pipedRunnable.setInputStream(lastIn);
			pipedRunnable.setOutputStream(lastOut);
			threads[i] = new PipeThread(threadGroup, pipedRunnable);
		}

		lastIn = new PipedInputStream(lastOut);
		pipeline.get(pipeline.size() - 1).setInputStream(lastIn);
		threads[threads.length - 1] = new PipeThread(threadGroup,
				pipeline.get(pipeline.size() - 1));
	}
}