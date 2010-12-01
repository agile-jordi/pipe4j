package pipe;

import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pipeline {
	private List<Pipe> pipeline = new ArrayList<Pipe>();

	public Pipeline() {
	}

	public Pipeline(Pipe[] pipeline) {
		this(Arrays.asList(pipeline));
	}

	public Pipeline(List<Pipe> pipeline) {
		this();
		this.pipeline = pipeline;
	}

	public Pipeline addPipe(Pipe pipedRunnable) {
		pipeline.add(pipedRunnable);
		return this;
	}

	public ThreadGroup run() throws Exception {
		if (pipeline == null || pipeline.size() < 2) {
			throw new IllegalArgumentException("Need at least 2 pipes!");
		}

		ThreadGroup threadGroup = new ThreadGroup("Pipeline");
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

		for (int i = 0; i < pipeline.size(); i++) {
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++) {
			Thread thread = threads[i];
			thread.join();
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
}