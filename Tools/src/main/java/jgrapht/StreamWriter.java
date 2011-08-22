package jgrapht;

import java.io.OutputStream;


public interface StreamWriter extends Pipe {
	void setOutputStream(OutputStream in);
}
