package pipe;

import java.io.Reader;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractPipedRunnable implements Pipe {
	private Reader reader;
	private Writer writer;
	private PipeConfiguration pipeConfiguration;
	private AtomicBoolean running = new AtomicBoolean(false);

	@Override
	public void setRunning(boolean running) {
		this.running.set(running);
	}
	
	@Override
	public boolean isRunning() {
		return this.running.get();
	}
	
	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	@Override
	public PipeConfiguration getPipeConfiguration() {
		return pipeConfiguration;
	}

	@Override
	public void setPipeConfiguration(PipeConfiguration pipeConfiguration) {
		this.pipeConfiguration = pipeConfiguration;
	}
}