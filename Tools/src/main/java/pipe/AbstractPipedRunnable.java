package pipe;

import java.io.Reader;
import java.io.Writer;

public abstract class AbstractPipedRunnable implements Pipe {
	private Reader reader;
	private Writer writer;

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
}