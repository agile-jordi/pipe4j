package pipe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.Writer;

public abstract class AbstractBufferedPipedRunnable extends
		AbstractPipedRunnable {
	public void setWriter(Writer writer) {
		super.setWriter(new BufferedWriter(writer));
	}

	public void setReader(Reader reader) {
		super.setReader(new BufferedReader(reader));
	}
}