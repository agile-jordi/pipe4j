package jgrapht;

import org.jgrapht.EdgeFactory;

public class PipeConnector implements EdgeFactory<Pipe, Connection> {
	public static PipeConnector INSTANCE = new PipeConnector();
	
	@Override
	public Connection createEdge(Pipe source, Pipe sink) {
		return null;
	}
}
