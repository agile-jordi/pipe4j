package pipe;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractPipe implements Pipe {
	private InputStream inputStream;
	private OutputStream outputStream;
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
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
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