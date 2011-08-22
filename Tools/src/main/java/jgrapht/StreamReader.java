package jgrapht;

import java.io.InputStream;

public interface StreamReader extends Pipe {
	void setInputStream(InputStream in);
}
