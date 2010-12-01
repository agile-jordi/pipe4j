package pipe;

import java.io.Reader;
import java.io.Writer;

public interface Pipe {
	void setWriter(Writer writer);

	Writer getWriter();

	void setReader(Reader reader);

	Reader getReader();

	void run() throws Exception;

	void setPipeConfiguration(PipeConfiguration pipeConfiguration);

	PipeConfiguration getPipeConfiguration();

	boolean isRunning();

	void setRunning(boolean running);
}